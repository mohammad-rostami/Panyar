package in.public_static.carin.paniar.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.layer_net.stepindicator.StepIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import in.public_static.carin.paniar.App;
import in.public_static.carin.paniar.OnItemListener;
import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.Struct;
import in.public_static.carin.paniar.adapters.Adapter_Recycler;
import in.public_static.carin.paniar.cunstractor.BimeItem;
import in.public_static.carin.paniar.databinding.ActivityOrderBinding;
import in.public_static.carin.paniar.utils.Constants;
import in.public_static.carin.paniar.utils.Statics;
import in.public_static.carin.paniar.utils.Utils;

public class ActivityOrder extends ActivityCompatParent {
    private int PERMISSION_ALL;
    public static ActivityOrderBinding root;
    String record_id = "";
    int gender = Statics._MAN;
    int cityCode = -1;
    int age = Statics._AGE_KID;
    int building_type = Statics._AJORI;
    int c_kind = Statics._PERSON;
    int cityPosition = -1;
    int statePosition = -1;
    boolean twEnabled = true;
    Button clicked;
    boolean harigh_payed = false;
    boolean seil_payed = false;
    boolean toofan_payed = false;
    boolean zelzele_payed = false;
    boolean post_payed = false;
    private boolean firstPart;
    private boolean secondPart;
    private boolean thirdPart;
    private StepIndicator stepIndicator;
    private Adapter_Recycler mAdapter;
    private ArrayList<Struct> mArray = new ArrayList<>();
    private String totalPrice;
    private String seilPrice = " ";
    private String toofanPrice = " ";
    private String zelzelehPrice = " ";
    private String harighPrice = " ";
    private boolean firstFormDone = false;
    private boolean secondFormDone = false;
    private boolean thirdFormDone = false;
    static Activity activity;
    public static boolean isSecondForm;
    final String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = DataBindingUtil.setContentView(this, R.layout.activity_order);
        prepareViews();
        setViewFunctions();
        activity = this;

        root.textView7.setText("استعلام قیمت بیمه");
        stepIndicator = (StepIndicator) findViewById(R.id.step);
        stepIndicator.setStepsCount(5);
        stepIndicator.setCurrentStepPosition(0);
        stepIndicator.setClickable(false);

        root.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                        ActivityCompat.requestPermissions(ActivityOrder.this, PERMISSIONS, PERMISSION_ALL);
                        Toast.makeText(ActivityOrder.this, "دسترسی لازم برای موقعیت یابی صادر نشده است", Toast.LENGTH_LONG).show();
                    }
                    if (hasPermissions(getApplicationContext(), PERMISSIONS)) {
                        isSecondForm = true;
                        Intent intent = new Intent(ActivityOrder.this, Activity_Map.class);
                        ActivityOrder.this.startActivity(intent);
                    }

                } else {
                    isSecondForm = true;
                    Intent intent = new Intent(ActivityOrder.this, Activity_Map.class);
                    ActivityOrder.this.startActivity(intent);
                }

            }
        });
        root.map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                        ActivityCompat.requestPermissions(ActivityOrder.this, PERMISSIONS, PERMISSION_ALL);
                        Toast.makeText(ActivityOrder.this, "دسترسی لازم برای موقعیت یابی صادر نشده است", Toast.LENGTH_LONG).show();
                    }
                    if (hasPermissions(getApplicationContext(), PERMISSIONS)) {
                        isSecondForm = false;
                        Intent intent = new Intent(ActivityOrder.this, Activity_Map.class);
                        ActivityOrder.this.startActivity(intent);
                    }

                } else {
                    isSecondForm = false;
                    Intent intent = new Intent(ActivityOrder.this, Activity_Map.class);
                    ActivityOrder.this.startActivity(intent);
                }
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new Adapter_Recycler(getApplicationContext(), mArray, new OnItemListener() {
            @Override
            public void onItemSelect(int position) {

            }

            @Override
            public void onItemClick(int position) {
                goToNextStep();
            }
        }, 1, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        try {
            Bundle extras = getIntent().getExtras();
            BimeItem item = (BimeItem) extras.getSerializable("obj");
            record_id = item.getBime_id() + "";
            setUI(item);
        } catch (Exception e) {
            e.printStackTrace();
        }

        root.bimeGozar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.steptwoFirst.setVisibility(View.VISIBLE);
                root.btnOk.setVisibility(View.GONE);
            }
        });
        root.bimePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.steptwoSecond.setVisibility(View.VISIBLE);
                root.btnOk.setVisibility(View.GONE);
            }
        });
        root.bimeDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.steptwoThird.setVisibility(View.VISIBLE);
                root.btnOk.setVisibility(View.GONE);
            }
        });
        root.firstCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.steptwoFirst.setVisibility(View.GONE);
                root.btnOk.setVisibility(View.VISIBLE);

                root.edtName.setText("");
                root.edtFamily.setText("");
                root.edtCountryCode.setText("");
                root.edtTel.setText("");
                root.edtCell.setText("");


            }
        });
        root.firstConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkName(root.edtName.getText().toString())) {
                    root.edtName.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.edtName.setError("نام را وارد کنید");
                }
                if (!checkFamily(root.edtFamily.getText().toString())) {
                    root.edtFamily.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.edtFamily.setError("نام خانوادگی صحیح را وارد کنید");
                }
                if (!checkCountryCode(root.edtCountryCode.getText().toString())) {
                    root.edtCountryCode.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.edtCountryCode.setError("کد ملی ۱۰ رقمی را وارد کنید");
                }
                if (!checkTel(root.edtTel.getText().toString())) {
                    root.edtTel.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.edtTel.setError("شماره تلفن ۱۱ رقمی را وارد کنید");
                }
                if (!checkCell(root.edtCell.getText().toString())) {
                    root.edtCell.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.edtCell.setError("شماره موبایل ۱۱ رقمی را وارد کنید");
                } else {
                    root.steptwoFirst.setVisibility(View.GONE);
                    root.btnOk.setVisibility(View.VISIBLE);
                    firstFormDone = true;
                    root.bimeGozar.setBackgroundResource(R.drawable.card_back_do);
                    root.txtBimeGozar.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
        root.secondCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.steptwoSecond.setVisibility(View.GONE);
                root.btnOk.setVisibility(View.VISIBLE);
                root.txtTargetState.setText("نام استان");
                root.txtTargetCity.setText("نام شهر");
                root.edtTargetPostalCode.setText("");
                root.edtTargetAddress.setText("");


            }
        });
        root.secondConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allFilled = true;
                if (root.edtTargetPostalCode.getText().toString().length() != 10) {
                    root.edtTargetPostalCode.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.edtTargetPostalCode.setError("کد پستی ۱۰ رقمی را وارد کنید");
                    allFilled = false;
                }
                if (root.edtTargetAddress.getText().toString().length() < 8) {
                    root.edtTargetAddress.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.edtTargetAddress.setError("آدرس را وارد کنید");
                    allFilled = false;
                }
                if (root.txtTargetState.getText().toString().equals("نام استان")) {
                    root.llTargetState.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.llTargetState.setBackgroundResource(R.drawable.card_back_red);

                    allFilled = false;
                }
                if (root.txtTargetCity.getText().toString().equals("نام شهر")) {
                    root.llTargetCity.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    root.llTargetCity.setBackgroundResource(R.drawable.card_back_red);
                    allFilled = false;
                } else if (allFilled) {
                    root.steptwoSecond.setVisibility(View.GONE);
                    root.btnOk.setVisibility(View.VISIBLE);
                    secondFormDone = true;
                    root.bimePlace.setBackgroundResource(R.drawable.card_back_do);
                    root.txtBimePlace.setTextColor(getResources().getColor(R.color.white));

                }
            }
        });
        root.thirdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.steptwoThird.setVisibility(View.GONE);
                root.btnOk.setVisibility(View.VISIBLE);
                root.edtAddress.setText("");

            }
        });
        root.thirdConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.chbPost.isChecked()) {
                    if (!checkAddress(root.edtAddress.getText().toString())) {
                        root.edtAddress.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                        root.edtAddress.setError("آدرس را وارد کنید");
                    }
                    if (!checkAddress(root.edtAddressPost.getText().toString())) {
                        root.edtAddressPost.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                        root.edtAddressPost.setError("کد پستی ۱۰ رقمی را وارد کنید");
                    } else {
                        root.steptwoThird.setVisibility(View.GONE);
                        root.btnOk.setVisibility(View.VISIBLE);
                        thirdFormDone = true;
                        root.bimeDelivery.setBackgroundResource(R.drawable.card_back_do);
                        root.txtBimeDelivery.setTextColor(getResources().getColor(R.color.white));
                    }
                } else {
                    root.steptwoThird.setVisibility(View.GONE);
                    root.btnOk.setVisibility(View.VISIBLE);
                    thirdFormDone = true;
                    root.bimeDelivery.setBackgroundResource(R.drawable.card_back_do);
                    root.txtBimeDelivery.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
        root.txtRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.context, ActivityPopUpMain.class);
                intent.putExtra("type", "order");
                startActivity(intent);
            }
        });
        if (firstFormDone) {
            root.bimeGozar.setBackgroundResource(R.drawable.card_back_do);
            root.txtBimeGozar.setTextColor(getResources().getColor(R.color.white));
        }
        if (secondFormDone) {
            root.bimePlace.setBackgroundResource(R.drawable.card_back_do);
            root.txtBimePlace.setTextColor(getResources().getColor(R.color.white));

        }
        if (thirdFormDone) {
            root.bimeDelivery.setBackgroundResource(R.drawable.card_back_do);
            root.txtBimeDelivery.setTextColor(getResources().getColor(R.color.white));

        }
        root.chbPost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    root.llAddress.setVisibility(View.VISIBLE);
                    root.tilPost1.setVisibility(View.VISIBLE);
                } else {
                    root.llAddress.setVisibility(View.GONE);
                    root.tilPost1.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (root.stepFour.getVisibility() == View.VISIBLE) {
            root.btnOk.setVisibility(View.GONE);
            root.stepFour.setVisibility(View.GONE);
            root.stepthree.setVisibility(View.VISIBLE);
            stepIndicator.setCurrentStepPosition(2);
            root.textView7.setText("نتیجه استعلام");
            root.btnOk.setText("تایید و ادامه");

        } else if (root.stepthree.getVisibility() == View.VISIBLE) {
            root.stepthree.setVisibility(View.GONE);
            root.steptwo.setVisibility(View.VISIBLE);
            root.btnOk.setVisibility(View.VISIBLE);
            stepIndicator.setCurrentStepPosition(1);
            root.textView7.setText("ورود مشخصات");
            root.btnOk.setText("تایید و ادامه");

        } else if (root.steptwoFirst.getVisibility() == View.VISIBLE) {
            root.steptwoFirst.setVisibility(View.GONE);
            root.btnOk.setVisibility(View.VISIBLE);

        } else if (root.steptwoSecond.getVisibility() == View.VISIBLE) {
            root.steptwoSecond.setVisibility(View.GONE);
            root.btnOk.setVisibility(View.VISIBLE);

        } else if (root.steptwoThird.getVisibility() == View.VISIBLE) {
            root.steptwoThird.setVisibility(View.GONE);
            root.btnOk.setVisibility(View.VISIBLE);

        } else if (root.steptwo.getVisibility() == View.VISIBLE && root.steptwoFirst.getVisibility() == View.GONE &&
                root.steptwoSecond.getVisibility() == View.GONE && root.steptwoThird.getVisibility() == View.GONE) {
            root.steptwo.setVisibility(View.GONE);
            root.stepOne.setVisibility(View.VISIBLE);
            stepIndicator.setCurrentStepPosition(0);
            root.textView7.setText("استعلام قیمت بیمه");
            root.btnOk.setText("تایید و ادامه");

        } else if (root.stepOne.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        }

    }

    @Override
    protected void prepareViews() {
        super.prepareViews();
    }

    @Override
    protected void setViewFunctions() {
        super.setViewFunctions();
        root.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (root.stepOne.getVisibility() == View.VISIBLE) {
                    if (!checkStep(1)) {
                        return;
                    }
                    root.stepOne.setVisibility(View.GONE);
                    root.steptwo.setVisibility(View.VISIBLE);
                    stepIndicator.setCurrentStepPosition(1);
                    root.textView7.setText("ورود مشخصات");

                } else if (root.steptwo.getVisibility() == View.VISIBLE) {
                    if (!checkStep(2)) {
                        return;
                    }
                    root.steptwo.setVisibility(View.GONE);
                    root.stepthree.setVisibility(View.VISIBLE);
                    root.textView7.setText("نتیجه استعلام");
                    dialog.show();
                    StringBuilder sb = new StringBuilder();
                    long price = Long.parseLong(root.edtTargetPrice.getText().toString().replace(" ", ""));
                    price += Long.parseLong(root.edtStuffPrice.getText().toString().replace(" ", ""));
                    sb.append(App.webServiceUrl).append("bime_api/price/token/").append(Utils.getInstance().getUserToken())
                            .append("/target_price/").append(price).append("/city_code/").append(cityCode).append("/building_type/").append(building_type)
                            .append("/age/").append(age);
                    if (root.chbSeil.isChecked()) {
                        sb.append("/seil/1");
                    }
                    if (root.chbZelzele.isChecked()) {
                        sb.append("/zelzele/1");
                    }
                    if (root.chbToofan.isChecked()) {
                        sb.append("/toofan/1");
                    }
                    if (root.chbPost.isChecked()) {
                        sb.append("/post/1");
                    }
                    Log.d("volley_url", "onClick: " + sb.toString());
                    StringRequest request = new StringRequest(Request.Method.GET, sb.toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.hide();
                            Log.d("volley_response", "onResponse: " + response);
                            root.btnOk.setVisibility(View.GONE);

                            try {
                                long price = 0;
                                JSONArray array = new JSONArray(response);
                                JSONObject obj = array.getJSONObject(0);
                                if (obj.getInt("type") == Constants._TYPE_HARIGH_MASKOONI_ALBORZ) {
                                    JSONObject object = obj.getJSONObject("data");
                                    JSONObject objHarigh = object.getJSONObject("harigh");
                                    StringBuilder priceBuilder = new StringBuilder();
                                    StringBuilder priceListBuilder = new StringBuilder();
                                    StringBuilder dscBuilder = new StringBuilder();
                                    if (objHarigh.getBoolean("permitted")) {
                                        priceListBuilder.append("ریز حساب :").append("\n");
                                        priceListBuilder.append("پوشش حریق مسکونی = ").append(objHarigh.getLong("price")).append(" ریال");
                                        harighPrice = String.valueOf(objHarigh.getLong("price"));

                                        price += objHarigh.getLong("price");
                                        if (root.chbSeil.isChecked()) {
                                            JSONObject objSeil = object.getJSONObject("seil");
                                            if (objSeil.getBoolean("permitted")) {
                                                root.llSeil.setVisibility(View.VISIBLE);
                                                priceListBuilder.append("\n").append("پوشش سیل = ").append(objSeil.getLong("price")).append(" ریال");
                                                price += objSeil.getLong("price");
                                                seilPrice = String.valueOf(objSeil.getLong("price"));
                                            } else {
                                                dscBuilder.append("\n").append("پوشش سیل برای شهر شما بدون تایید کارشناس قابل تایید نیست. با لمس کلید درخواست خود را ثبت کنید تا کارشناس ما با شما تماس بگیرد.");
                                            }
                                        }
                                        if (root.chbToofan.isChecked()) {
                                            JSONObject objToofan = object.getJSONObject("toofan");
                                            if (objToofan.getBoolean("permitted")) {
                                                root.llToofan.setVisibility(View.VISIBLE);
                                                priceListBuilder.append("\n").append("پوشش طوفان = ").append(objToofan.getLong("price")).append(" ریال");
                                                price += objToofan.getLong("price");
                                                toofanPrice = String.valueOf(objToofan.getLong("price"));
                                            } else {
                                                dscBuilder.append("\n").append("پوشش طوفان برای شهر شما بدون تایید کارشناس قابل تایید نیست. با لمس کلید درخواست خود را ثبت کنید تا کارشناس ما با شما تماس بگیرد.");
                                            }
                                        }
                                        if (root.chbZelzele.isChecked()) {
                                            JSONObject objZelzele = object.getJSONObject("zelzele");
                                            if (objZelzele.getBoolean("permitted")) {
                                                root.llZelzeleh.setVisibility(View.VISIBLE);
                                                priceListBuilder.append("\n").append("پوشش زلزله = ").append(objZelzele.getLong("price")).append(" ریال");
                                                price += objZelzele.getLong("price");
                                                zelzelehPrice = String.valueOf(objZelzele.getLong("price"));

                                            } else {
                                                dscBuilder.append("\n").append("پوشش زلزله برای ساختمان بالای سی و پنج سال بدون تایید کارشناس قابل تایید نیست. با لمس کلید درخواست خود را ثبت کنید تا کارشناس ما با شما تماس بگیرد.");
                                            }
                                        }
                                        if (root.chbPost.isChecked()) {
                                            priceListBuilder.append("\n").append("هزینه پستی = ").append(object.getLong("post")).append(" ریال");
                                            price += object.getLong("post");
                                        }
                                        priceBuilder.append("مبلغ قابل پرداخت : ").append(price).append(" ریال");
                                        totalPrice = String.valueOf(price);

                                        mArray.clear();
                                        for (int i = 0; i < 1; i++) {
                                            Struct struct = new Struct();
                                            struct.strName = String.valueOf(price);
                                            mArray.add(struct);
                                        }
                                        mAdapter.notifyDataSetChanged();


                                    } else {
                                        dscBuilder.append("ساختمان بالای پنجاه سال بدون تایید کارشناس قابل بیمه کردن نیست. با زدن کلید ادامه درخواست خود را ثبت کنید تا کارشناس ما با شما تماس بگیرد.");
                                    }
//                                    makePriceList(priceBuilder.toString(), priceListBuilder.toString(), dscBuilder.toString());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.hide();
                            Log.d("volley_code", "onErrorResponse: " + error.networkResponse.statusCode);
                        }
                    });
                    App.getInstance().addToRequestQueue(request);
                    stepIndicator.setCurrentStepPosition(2);

                } else if (root.stepthree.getVisibility() == View.VISIBLE) {
                    if (!checkStep(3)) {
                        return;
                    }
                    root.textView7.setText("پیش نمایش اطلاعات");
                    root.btnOk.setText("تایید و پرداخت");
                } else if (root.stepFour.getVisibility() == View.VISIBLE) {
                    if (root.chkRules.isChecked()) {
                        stepIndicator.setCurrentStepPosition(4);
//                        clicked = root.btnOk;
                        if (record_id.length() == 0) {
                            createNewRecord();
                        } else {
                            updateRecord();
                        }
                    } else {
                        root.chkRules.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                        root.txtRules.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                        root.txtRules1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                        Toast.makeText(getApplicationContext(), "قوانین را نپذیرفته اید", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        root.txtTargetState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder.this);
                builder.setTitle("استان را انتخاب کنید.")
                        .setSingleChoiceItems(R.array.states, 1000, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cityPosition = -1;
                                root.txtTargetCity.setText("نام شهر");
                                statePosition = i;
                                dialogInterface.dismiss();
                                root.txtTargetState.setText(getResources().getStringArray(R.array.states)[i]);
                                root.llTargetState.setBackgroundResource(R.drawable.combo_back);
                            }
                        }).create().show();
            }
        });
        root.llTargetState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder.this);
                builder.setTitle("استان را انتخاب کنید.")
                        .setSingleChoiceItems(R.array.states, 1000, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cityPosition = -1;
                                root.txtTargetCity.setText("نام شهر");
                                statePosition = i;
                                dialogInterface.dismiss();
                                root.txtTargetState.setText(getResources().getStringArray(R.array.states)[i]);
                                root.llTargetState.setBackgroundResource(R.drawable.combo_back);
                            }
                        }).create().show();
            }
        });

        root.txtTargetCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statePosition < 0) {
                    Toast.makeText(ActivityOrder.this, "ابتدا استان را انتخاب کنید.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] params = {root.txtTargetState.getText().toString()};
                final Cursor cursor = App.bank.rawQuery("SELECT * FROM city WHERE state=?", params);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder.this);
                builder.setTitle("استان را انتخاب کنید.")
                        .setSingleChoiceItems(cursor, 1000, "city", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cursor.moveToPosition(i);
                                cityPosition = i;
                                cityCode = cursor.getInt(cursor.getColumnIndex("code"));
                                dialogInterface.dismiss();
                                root.txtTargetCity.setText(cursor.getString(cursor.getColumnIndex("city")));
                                root.llTargetCity.setBackgroundResource(R.drawable.combo_back);
                            }
                        }).create().show();
            }
        });
        root.llTargetCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statePosition < 0) {
                    Toast.makeText(ActivityOrder.this, "ابتدا استان را انتخاب کنید.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] params = {root.txtTargetState.getText().toString()};
                final Cursor cursor = App.bank.rawQuery("SELECT * FROM city WHERE state=?", params);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder.this);
                builder.setTitle("استان را انتخاب کنید.")
                        .setSingleChoiceItems(cursor, 1000, "city", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cursor.moveToPosition(i);
                                cityPosition = i;
                                cityCode = cursor.getInt(cursor.getColumnIndex("code"));
                                dialogInterface.dismiss();
                                root.txtTargetCity.setText(cursor.getString(cursor.getColumnIndex("city")));
                                root.llTargetCity.setBackgroundResource(R.drawable.combo_back);
                            }
                        }).create().show();
            }
        });

        root.edtTargetPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (twEnabled) {
                    String s = null;
                    try {
                        // The comma in the format specifier does the trick
                        s = Spacer(root.edtTargetPrice.getText().toString());
                        twEnabled = false;
                        root.edtTargetPrice.setTextKeepState(s);
                        Selection.setSelection(root.edtTargetPrice.getText(), s.length());
                        twEnabled = true;
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });

        root.edtStuffPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (twEnabled) {
                    String s = null;
                    try {
                        // The comma in the format specifier does the trick
                        s = Spacer(root.edtStuffPrice.getText().toString());
                        twEnabled = false;
                        root.edtStuffPrice.setTextKeepState(s);
                        Selection.setSelection(root.edtStuffPrice.getText(), s.length());
                        twEnabled = true;
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });

        root.rbCompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbPerson.setChecked(false);
                    root.llGender.setVisibility(View.GONE);
//                    root.parentEdtFamily.setVisibility(View.GONE);
                    c_kind = Statics._COMPANY;
                }
            }
        });

        root.rbPerson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbCompany.setChecked(false);
                    root.llGender.setVisibility(View.VISIBLE);
//                    root.parentEdtFamily.setVisibility(View.VISIBLE);
                    c_kind = Statics._PERSON;
                }
            }
        });

        root.rbMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbWoman.setChecked(false);
                }
            }
        });

        root.rbWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbMan.setChecked(false);
                }
            }
        });

        root.rbAjori.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbAiinName.setChecked(false);
                    root.rbBotoni.setChecked(false);
                    root.rbFelezi.setChecked(false);
                    building_type = Statics._AJORI;
                }
            }
        });

        root.rbFelezi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbAiinName.setChecked(false);
                    root.rbBotoni.setChecked(false);
                    root.rbAjori.setChecked(false);
                    building_type = Statics._FELEZI;
                }
            }
        });

        root.rbBotoni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbAiinName.setChecked(false);
                    root.rbAjori.setChecked(false);
                    root.rbFelezi.setChecked(false);
                    building_type = Statics._BOTONI;
                }
            }
        });

        root.rbAiinName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbAjori.setChecked(false);
                    root.rbBotoni.setChecked(false);
                    root.rbFelezi.setChecked(false);
                    building_type = Statics._AIIN_NAME;
                }
            }
        });

        root.rbTeenage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbkid.setChecked(false);
                    root.rbOld.setChecked(false);
                    age = Statics._AGE_YOUNG;
                }
            }
        });
        root.rbkid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbTeenage.setChecked(false);
                    root.rbOld.setChecked(false);
                    age = Statics._AGE_KID;
                }
            }
        });
        root.rbOld.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    root.rbkid.setChecked(false);
                    root.rbTeenage.setChecked(false);
                    age = Statics._AGE_OLD;
                }
            }
        });
    }

    private void makePriceList(String price, String priceList, String dsc) {
        root.steptwo.removeAllViews();

        View child = getLayoutInflater().inflate(R.layout.nerkh_item, null);//child.xml
        root.steptwo.addView(child);

        TextView txtItemTitle = (TextView) child.findViewById(R.id.txtItemTitle);
        TextView txtItemPay = (TextView) child.findViewById(R.id.txtItemPay);
        TextView txtPrice = (TextView) child.findViewById(R.id.txtPrice);
        TextView txtPriceList = (TextView) child.findViewById(R.id.txtPriceList);
        TextView txtDsc = (TextView) child.findViewById(R.id.txtDsc);
        final Button btnPay = (Button) child.findViewById(R.id.btnPay);

        Utils.getInstance().prepareTextView(txtItemTitle);
        Utils.getInstance().prepareTextView(txtItemPay);
        Utils.getInstance().prepareTextView(txtPrice);
        Utils.getInstance().prepareTextView(txtPriceList);
        Utils.getInstance().prepareTextView(txtDsc);
        Utils.getInstance().prepareTextView(btnPay);

        txtPrice.setText(price);
        txtPriceList.setText(priceList);
        txtDsc.setText(dsc);
        if (record_id.length() > 0) {
            btnPay.setText("اعمال تغییرات");
        }
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = btnPay;
                if (record_id.length() == 0) {
                    createNewRecord();
                } else {
                    updateRecord();
                }
            }
        });
    }

    private String Spacer(String number) {
        number = number.replace(" ", "");
        StringBuilder strB = new StringBuilder();
        strB.append(number);
        int Three = 0;

        for (int i = number.length(); i > 0; i--) {
            Three++;
            if (Three == 3) {
                strB.insert(i - 1, " ");
                Three = 0;
            }
        }
        return strB.toString();
    }// end Spacer()

    private boolean checkStep(int step) {
        boolean param = true;
        firstPart = true;
        secondPart = true;
        thirdPart = true;
        switch (step) {
            case 1:
                if (!checkTargetPrice(root.edtTargetPrice.getText().toString())) {
                    param = false;
                }
                if (!checkStuffPrice(root.edtStuffPrice.getText().toString())) {
                    param = false;
                }
                break;
            case 3:
//                if (!checkState()) {
//                    return false;
//                }
//                if (!checkCity()) {
//                    return false;
//                }
//                if (!checkPostalCode(root.edtTargetPostalCode.getText().toString())) {
//                    param = false;
//                }
//                if (!checkAddress(root.edtTargetAddress.getText().toString())) {
//                    param = false;
//                }
//                if (root.txtTargetState.getText().toString() == "نام استان") {
//                    param = false;
//                    Toast.makeText(this, "استان را انتخاب کنید", Toast.LENGTH_SHORT).show();
//                }
                break;

            case 2:
                if (!checkName(root.edtName.getText().toString())) {
                    root.edtName.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
//                    Toast.makeText(this, "نام نمیتواند کوچکتر از ۴ کاراکتر باشد.", Toast.LENGTH_SHORT).show();
                    firstPart = false;
                    param = false;
                }
                if (!checkFamily(root.edtFamily.getText().toString())) {
                    root.edtFamily.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    firstPart = false;
                    param = false;
                }
                if (!checkCountryCode(root.edtCountryCode.getText().toString())) {
                    root.edtCountryCode.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    firstPart = false;
                    param = false;
                }
                if (!checkTel(root.edtTel.getText().toString())) {
                    root.edtTel.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));

                    firstPart = false;
                    param = false;
                }
                if (!checkCell(root.edtCell.getText().toString())) {
                    root.edtCell.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    firstPart = false;
                    param = false;
                }

                if (!checkState()) {
                    root.llTargetState.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    secondPart = false;
                    param = false;
                }
                if (!checkCity()) {
                    root.llTargetCity.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    secondPart = false;
                    param = false;
                }
                if (!checkPostalCode(root.edtTargetPostalCode.getText().toString())) {
                    root.edtTargetPostalCode.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    secondPart = false;
                    param = false;
                }
                if (!checkAddress(root.edtTargetAddress.getText().toString())) {
                    root.edtTargetAddress.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    secondPart = false;
                    param = false;
                }
                if (root.txtTargetState.getText().toString() == "نام استان") {
                    root.txtTargetState.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                    secondPart = false;
                    param = false;
//                    Toast.makeText(this, "استان را انتخاب کنید", Toast.LENGTH_SHORT).show();
                }
                if (root.chbPost.isChecked()) {
                    if (!checkAddress(root.edtAddress.getText().toString())) {
                        root.edtAddress.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                        thirdPart = false;
                        param = false;
                    }
                }

                warning();
                break;
            default:
        }


        return param;
    }

    private boolean checkName(String name) {
        boolean param = true;
        if (name.length() < 4) {
            param = false;
        }
        return param;
    }

    private boolean checkFamily(String name) {
        if (root.rbCompany.isChecked()) {
            return true;
        }
        boolean param = true;
        if (name.length() < 4) {
//            Toast.makeText(this, "نام خانوادگی نمیتواند کوچکتر از ۴ کاراکتر باشد.", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkCountryCode(String code) {
        boolean param = true;
        if (root.rbCompany.isChecked()) {
            if (!code.startsWith("0")) {
//                Toast.makeText(this, "کدملی معتبر نیست", Toast.LENGTH_SHORT).show();
                param = false;
            } else if (code.length() != 10) {
//                Toast.makeText(this, "کدملی معتبر نیست", Toast.LENGTH_SHORT).show();
                param = false;
            }
        } else {
            if (code.length() != 10) {
//                Toast.makeText(this, "شناسه ملی معتبر نیست", Toast.LENGTH_SHORT).show();
                param = false;
            }
        }
        return param;
    }

    private boolean checkAddress(String address) {
        boolean param = true;
        if (address.length() < 8) {
//            Toast.makeText(this, "آدرس نا معتبر است", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkTel(String tel) {
        boolean param = true;
        if (!tel.startsWith("0")) {
//            Toast.makeText(this, "کد شهر در شماره تلفن ذکر نشده است", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkCell(String cell) {
        boolean param = true;
        if (!cell.startsWith("09")) {
//            Toast.makeText(this, "شماره همراه نامعتبر است", Toast.LENGTH_SHORT).show();
            param = false;
        }
        if (cell.length() != 11) {
//            Toast.makeText(this, "شماره همراه نامعتبر است", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkPostalCode(String code) {
        boolean param = true;
        if (code.length() != 10) {
//            Toast.makeText(this, "کد پستی معتبر نیست", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkTargetPrice(String price) {
        price = price.replace(" ", "");
        boolean param = true;
        try {
            if (Long.parseLong(price) < 10000000) {
                Toast.makeText(this, "ارزش مکان معتبر نیست", Toast.LENGTH_SHORT).show();
                param = false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "ارزش مکان معتبر نیست", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkStuffPrice(String price) {
        price = price.replace(" ", "");
        boolean param = true;
        try {
            if (Long.parseLong(price) < 10000000) {
                Toast.makeText(this, "مبلغ وسایل معتبر نیست", Toast.LENGTH_SHORT).show();
                param = false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "مبلغ وسایل معتبر نیست", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkState() {
        boolean param = true;
        if (statePosition < 0) {
//            Toast.makeText(this, "استان را انتخاب کنید", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkCity() {
        boolean param = true;
        if (cityPosition < 0) {
//            Toast.makeText(this, "نام شهر را وارد کنید", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private boolean checkTimeForPurchase() {
        boolean param = true;
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) > 21) {
            Toast.makeText(this, "از ساعت 10 شب به بعد پرداخت مقدور نیست.", Toast.LENGTH_SHORT).show();
            param = false;
        }
        return param;
    }

    private void createNewRecord() {
//        startActivity(new Intent(ActivityOrder.this, ActivityPay.class));
        StringBuilder sb = new StringBuilder();
        int seil = Statics._NOT_REQUESTED;
        int toofan = Statics._NOT_REQUESTED;
        int zelzele = Statics._NOT_REQUESTED;
        int post = Statics._NOT_REQUESTED;
        if (root.chbSeil.isChecked()) seil = Statics._REQUESTED;
        if (root.chbToofan.isChecked()) toofan = Statics._REQUESTED;
        if (root.chbZelzele.isChecked()) zelzele = Statics._REQUESTED;
        if (root.chbPost.isChecked()) post = Statics._REQUESTED;
        String family = null;
        try {
            family = URLEncoder.encode(root.edtFamily.getText().toString().replace(" ", "%20"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (family.length() == 0) {
            family = "%20";
        }
        try {
            sb.append("alborz_api/")
                    .append("bime/")
                    .append("token/").append(Utils.getInstance().getUserToken())
                    .append("/c_kind/").append(c_kind)
                    .append("/c_name/").append(URLEncoder.encode(root.edtName.getText().toString().replace(" ", "%20"), "UTF-8"))
                    .append("/c_family/").append(family)
                    .append("/c_gender/").append(gender)
                    .append("/c_address/").append(URLEncoder.encode(root.edtAddress.getText().toString().replace(" ", "%20"), "UTF-8"))
                    .append("/c_c_code/").append(root.edtCountryCode.getText().toString())
                    .append("/c_tel/").append(root.edtTel.getText().toString())
                    .append("/c_cell/").append(root.edtCell.getText().toString())
                    .append("/t_city/").append(cityCode)
                    .append("/t_address/").append(URLEncoder.encode(root.edtTargetAddress.getText().toString().replace(" ", "%20"), "UTF-8"))
                    .append("/t_p_code/").append(root.edtTargetPostalCode.getText().toString())
                    .append("/t_price/").append(root.edtTargetPrice.getText().toString().replace(" ", ""))
                    .append("/t_stuff_price/").append(root.edtStuffPrice.getText().toString().replace(" ", ""))
                    .append("/t_type/").append(building_type)
                    .append("/t_age/").append(age)
                    .append("/seil/").append(seil)
                    .append("/toofan/").append(toofan)
                    .append("/zelzele/").append(zelzele)
                    .append("/post/").append(post);

            lunchRequest("post", sb.toString(), "add_record");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void updateRecord() {
        StringBuilder sb = new StringBuilder();
        int seil = Statics._NOT_REQUESTED;
        int toofan = Statics._NOT_REQUESTED;
        int zelzele = Statics._NOT_REQUESTED;
        int post = Statics._NOT_REQUESTED;
        if (root.chbSeil.isChecked()) seil = Statics._REQUESTED;
        if (root.chbToofan.isChecked()) toofan = Statics._REQUESTED;
        if (root.chbZelzele.isChecked()) zelzele = Statics._REQUESTED;
        if (root.chbPost.isChecked()) post = Statics._REQUESTED;
        String family = null;
        try {
            family = URLEncoder.encode(root.edtFamily.getText().toString().replace(" ", "%20"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (family.length() == 0) {
            family = "%20";
        }
        try {
            sb.append("alborz_api/")
                    .append("update_bime/")
                    .append("token/").append(Utils.getInstance().getUserToken())
                    .append("/id/").append(record_id)
                    .append("/c_kind/").append(c_kind)
                    .append("/c_name/").append(URLEncoder.encode(root.edtName.getText().toString().replace(" ", "%20"), "UTF-8"))
                    .append("/c_family/").append(family)
                    .append("/c_gender/").append(gender)
                    .append("/c_address/").append(URLEncoder.encode(root.edtAddress.getText().toString().replace(" ", "%20"), "UTF-8"))
                    .append("/c_c_code/").append(root.edtCountryCode.getText().toString())
                    .append("/c_tel/").append(root.edtTel.getText().toString())
                    .append("/c_cell/").append(root.edtCell.getText().toString())
                    .append("/t_city/").append(cityCode)
                    .append("/t_address/").append(URLEncoder.encode(root.edtTargetAddress.getText().toString().replace(" ", "%20"), "UTF-8"))
                    .append("/t_p_code/").append(root.edtTargetPostalCode.getText().toString())
                    .append("/t_price/").append(root.edtTargetPrice.getText().toString().replace(" ", ""))
                    .append("/t_stuff_price/").append(root.edtStuffPrice.getText().toString().replace(" ", ""))
                    .append("/t_type/").append(building_type)
                    .append("/t_age/").append(age)
                    .append("/seil/").append(seil)
                    .append("/toofan/").append(toofan)
                    .append("/zelzele/").append(zelzele)
                    .append("/post/").append(post);

            lunchRequest("post", sb.toString(), "update_record");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void responseOk(String name, String response) {
        super.responseOk(name, response);

        if (name == "add_record" || name == "update_record") {
            try {
                final JSONObject object = new JSONObject(response);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActivityOrder.this, "امکان تغییر شهر بعد از ثبت فرم بیمه وجود ندارد.", Toast.LENGTH_SHORT).show();
                    }
                };
                root.txtTargetCity.setOnClickListener(listener);
                root.txtTargetState.setOnClickListener(listener);
                root.llTargetState.setOnClickListener(listener);
                root.llTargetState.setOnClickListener(listener);
//                clicked.setText("پرداخت");
//                clicked.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
                if (checkTimeForPurchase()) {
                    Intent intent = new Intent(ActivityOrder.this, ActivityPay.class);
                    try {
                        stepIndicator.setCurrentStepPosition(4);
                        record_id = object.getString("id");
                        intent.putExtra("id", record_id);
                        startActivity(intent);
//                                ActivityOrder.this.finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                    }
//                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    protected void setUI(BimeItem item) {
        switch (item.getC_kind()) {
            case Statics._PERSON:
                root.rbPerson.setChecked(true);
                break;
            case Statics._COMPANY:
                root.rbCompany.setChecked(true);
                break;
        }
        switch (item.getC_gender()) {
            case Statics._MAN:
                root.rbMan.setChecked(true);
                break;
            case Statics._WOMAN:
                root.rbWoman.setChecked(true);
                break;
        }
        root.edtName.setText(item.getC_name());
        root.edtFamily.setText(item.getC_family());
        root.edtCountryCode.setText(item.getC_c_code());
        root.edtAddress.setText(item.getC_address());
        root.edtTel.setText(item.getC_tel());
        root.edtCell.setText(item.getC_cell());

        cityCode = item.getT_city();
        cityPosition = 0;
        statePosition = 0;
        String[] param = {cityCode + ""};
        Cursor cursor = App.bank.rawQuery("SELECT * FROM city WHERE code=?", param);
        cursor.moveToPosition(0);
        root.txtTargetCity.setText(cursor.getString(cursor.getColumnIndex("city")));
        root.txtTargetState.setText(cursor.getString(cursor.getColumnIndex("state")));

        root.edtTargetAddress.setText(item.getT_address());
        switch (item.getT_age()) {
            case Statics._AGE_KID:
                root.rbkid.setChecked(true);
                break;
            case Statics._AGE_YOUNG:
                root.rbTeenage.setChecked(true);
                break;
            case Statics._AGE_OLD:
                root.rbOld.setChecked(true);
                break;
        }
        switch (item.getT_type()) {
            case Statics._AJORI:
                root.rbAjori.setChecked(true);
                break;
            case Statics._FELEZI:
                root.rbFelezi.setChecked(true);
                break;
            case Statics._BOTONI:
                root.rbBotoni.setChecked(true);
                break;
            case Statics._AIIN_NAME:
                root.rbAiinName.setChecked(true);
                break;
        }
        root.edtTargetPostalCode.setText(item.getT_p_code());
        root.edtTargetPrice.setText(item.getT_price());
        root.edtStuffPrice.setText(item.getT_stuff_price());
        if (item.getSeil_process_state() != Statics._NOT_REQUESTED) {
            root.chbSeil.setChecked(true);
        }
        if (item.getToofan_process_state() != Statics._NOT_REQUESTED) {
            root.chbToofan.setChecked(true);
        }
        if (item.getZelzele_process_state() != Statics._NOT_REQUESTED) {
            root.chbZelzele.setChecked(true);
        }
        if (item.getPost_state() != Statics._NOT_REQUESTED) {
            root.chbPost.setChecked(true);
        }
        age = item.getT_age();
        gender = item.getC_gender();
        building_type = item.getT_type();
        c_kind = item.getC_kind();

        if (item.getBime_process_state() == Statics._PAYED
                || item.getSeil_process_state() == Statics._PAYED
                || item.getToofan_process_state() == Statics._PAYED
                || item.getZelzele_process_state() == Statics._PAYED) {
            root.edtTargetPrice.setEnabled(false);
            root.edtTargetPrice.setKeyListener(null);

            root.edtStuffPrice.setEnabled(false);
            root.edtStuffPrice.setKeyListener(null);
            root.rbAiinName.setEnabled(false);
            root.rbAjori.setEnabled(false);
            root.rbMan.setEnabled(false);
            root.rbWoman.setEnabled(false);
            root.rbPerson.setEnabled(false);
            root.rbCompany.setEnabled(false);
            root.rbFelezi.setEnabled(false);
            root.rbBotoni.setEnabled(false);
            root.rbkid.setEnabled(false);
            root.rbOld.setEnabled(false);
            root.rbTeenage.setEnabled(false);
        }
        if (item.getBime_process_state() == Statics._PAYED) {
            harigh_payed = true;
        }
        if (item.getSeil_process_state() == Statics._PAYED) {
            seil_payed = true;
            root.chbSeil.setEnabled(false);
        }
        if (item.getToofan_process_state() == Statics._PAYED) {
            toofan_payed = true;
            root.chbToofan.setEnabled(false);
        }
        if (item.getZelzele_process_state() == Statics._PAYED) {
            zelzele_payed = true;
            root.chbZelzele.setEnabled(false);
        }
        if (item.getPost_state() == Statics._PAYED) {
            post_payed = true;
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityOrder.this, "امکان تغییر شهر بعد از ثبت فرم بیمه وجود ندارد.", Toast.LENGTH_SHORT).show();
            }
        };
        root.txtTargetCity.setOnClickListener(listener);
        root.txtTargetState.setOnClickListener(listener);
        root.llTargetCity.setOnClickListener(listener);
        root.llTargetState.setOnClickListener(listener);
    }

    private void warning() {

        if (!firstPart) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    root.bimeGozar.setBackgroundResource(R.drawable.card_back_red);
                    root.txtBimeGozar.setTextColor(getResources().getColor(R.color.darkGray));
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    root.bimeGozar.setBackgroundResource(R.drawable.card_back);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            root.bimeGozar.startAnimation(animation);

        }
        if (!secondPart) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    root.bimePlace.setBackgroundResource(R.drawable.card_back_red);
                    root.txtBimePlace.setTextColor(getResources().getColor(R.color.darkGray));
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    root.bimePlace.setBackgroundResource(R.drawable.card_back);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            root.bimePlace.startAnimation(animation);

        }
        if (!thirdPart) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    root.bimeDelivery.setBackgroundResource(R.drawable.card_back_red);
                    root.txtBimeDelivery.setTextColor(getResources().getColor(R.color.darkGray));
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    root.bimeDelivery.setBackgroundResource(R.drawable.card_back);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            root.bimeDelivery.startAnimation(animation);

        }
    }

    public static void address_setter(String address) {
        if (isSecondForm) {
            root.edtTargetAddress.setText(address);
        } else if (!isSecondForm) {
            root.edtAddress.setText(address);

        }
    }

    private void goToNextStep() {
        root.stepthree.setVisibility(View.GONE);
        root.stepFour.setVisibility(View.VISIBLE);
        root.btnOk.setVisibility(View.VISIBLE);
        stepIndicator.setCurrentStepPosition(3);
        root.textView7.setText("پیش نمایش");

        root.txtName.setText(root.edtName.getText().toString() + root.edtFamily.getText().toString());
        root.txtCodeMelli.setText(root.edtCountryCode.getText().toString());
        root.txtAddress.setText(root.edtTargetAddress.getText().toString());
        root.txtPostCode.setText(root.edtTargetPostalCode.getText().toString());

        root.txtTotalPrice.setText(totalPrice);
        try {
//            root.llSeil.setVisibility(View.VISIBLE);
            root.txtSeilPrice.setText(seilPrice);
        } catch (Exception e) {
//            root.llSeil.setVisibility(View.GONE);
        }
        try {
//            root.llFire.setVisibility(View.VISIBLE);
            root.txtHarighPrice.setText(harighPrice);
        } catch (Exception e) {
//            root.llFire.setVisibility(View.GONE);
        }
        try {
//            root.llToofan.setVisibility(View.VISIBLE);
            root.txtTofanPrice.setText(toofanPrice);
        } catch (Exception e) {
//            root.llToofan.setVisibility(View.GONE);
        }
        try {
//            root.llZelzeleh.setVisibility(View.VISIBLE);
            root.txtZelzelehPrice.setText(zelzelehPrice);
        } catch (Exception e) {
//            root.llZelzeleh.setVisibility(View.GONE);
        }
    }

    public static void closeActivity() {
        activity.finish();
    }

    public static boolean hasPermissions(Context context, String... permissions) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
