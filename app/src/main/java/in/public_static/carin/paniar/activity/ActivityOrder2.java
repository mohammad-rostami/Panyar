//package in.public_static.carin.paniar.activity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.text.Editable;
//import android.text.Selection;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Calendar;
//
//import in.public_static.carin.paniar.App;
//import in.public_static.carin.paniar.R;
//import in.public_static.carin.paniar.cunstractor.BimeItem;
//import in.public_static.carin.paniar.databinding.ActivityOrderBinding;
//import in.public_static.carin.paniar.utils.Constants;
//import in.public_static.carin.paniar.utils.Statics;
//import in.public_static.carin.paniar.utils.Utils;
//
//public class ActivityOrder2 extends ActivityCompatParent {
//    ActivityOrderBinding root;
//    String record_id = "";
//    int gender = Statics._MAN;
//    int cityCode = -1;
//    int age = Statics._AGE_KID;
//    int building_type = Statics._AJORI;
//    int c_kind = Statics._PERSON;
//    int cityPosition = -1;
//    int statePosition = -1;
//    boolean twEnabled = true;
//    Button clicked;
//    boolean harigh_payed = false;
//    boolean seil_payed = false;
//    boolean toofan_payed = false;
//    boolean zelzele_payed = false;
//    boolean post_payed = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        root = DataBindingUtil.setContentView(this, R.layout.activity_order2);
//        prepareViews();
//        setViewFunctions();
//        try {
//            Bundle extras = getIntent().getExtras();
//            BimeItem item = (BimeItem) extras.getSerializable("obj");
//            record_id = item.getBime_id() + "";
//            setUI(item);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(App.context, ActivityPopUpMain.class);
//        intent.putExtra("type", "order");
//        startActivity(intent);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onBackPressed() {
//        Animation animHideFromUpRightCorner;
//        if (root.stepFive.getVisibility() == View.VISIBLE) {
//            root.btnOk.setVisibility(View.VISIBLE);
//            animHideFromUpRightCorner = AnimationUtils.loadAnimation(ActivityOrder2.this, R.anim.to_up_right_corner);
//            animHideFromUpRightCorner.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    root.stepFour.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    root.stepFive.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            root.stepFive.startAnimation(animHideFromUpRightCorner);
//        } else if (root.stepFour.getVisibility() == View.VISIBLE) {
//            animHideFromUpRightCorner = AnimationUtils.loadAnimation(ActivityOrder2.this, R.anim.to_up_right_corner);
//            animHideFromUpRightCorner.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    root.stepThree.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    root.stepFour.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            root.stepFour.startAnimation(animHideFromUpRightCorner);
//        } else if (root.stepThree.getVisibility() == View.VISIBLE) {
//            animHideFromUpRightCorner = AnimationUtils.loadAnimation(ActivityOrder2.this, R.anim.to_up_right_corner);
//            animHideFromUpRightCorner.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    root.stepTow.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    root.stepThree.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            root.stepThree.startAnimation(animHideFromUpRightCorner);
//        } else if (root.stepTow.getVisibility() == View.VISIBLE) {
//            animHideFromUpRightCorner = AnimationUtils.loadAnimation(ActivityOrder2.this, R.anim.to_up_right_corner);
//            animHideFromUpRightCorner.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    root.stepOne.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    root.stepTow.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            root.stepTow.startAnimation(animHideFromUpRightCorner);
//        } else if (root.stepOne.getVisibility() == View.VISIBLE) {
//            super.onBackPressed();
//        }
//
//    }
//
//    @Override
//    protected void prepareViews() {
//        super.prepareViews();
//    }
//
//    @Override
//    protected void setViewFunctions() {
//        super.setViewFunctions();
//        root.btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (root.stepOne.getVisibility() == View.VISIBLE) {
//                    if (!checkStep(1)) {
//                        return;
//                    }
//                    Animation animShowFromUpRightCorner = AnimationUtils.loadAnimation(ActivityOrder2.this, R.anim.from_up_right_corner);
//                    animShowFromUpRightCorner.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                            root.stepTow.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            root.stepOne.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                    root.stepTow.startAnimation(animShowFromUpRightCorner);
//                } else if (root.stepTow.getVisibility() == View.VISIBLE) {
//                    if (!checkStep(2)) {
//                        return;
//                    }
//                    Animation animShowFromUpRightCorner = AnimationUtils.loadAnimation(ActivityOrder2.this, R.anim.from_up_right_corner);
//                    animShowFromUpRightCorner.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                            root.stepThree.setVisibility(View.VISIBLE);
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            root.stepTow.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                    root.stepThree.startAnimation(animShowFromUpRightCorner);
//                } else if (root.stepThree.getVisibility() == View.VISIBLE) {
//                    if (!checkStep(3)) {
//                        return;
//                    }
//                    Animation animShowFromUpRightCorner = AnimationUtils.loadAnimation(ActivityOrder2.this, R.anim.from_up_right_corner);
//                    animShowFromUpRightCorner.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                            root.stepFour.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            root.stepThree.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                    root.stepFour.startAnimation(animShowFromUpRightCorner);
//                } else if (root.stepFour.getVisibility() == View.VISIBLE) {
//                    dialog.show();
//                    StringBuilder sb = new StringBuilder();
//                    long price = Long.parseLong(root.edtTargetPrice.getText().toString().replace(" ", ""));
//                    price += Long.parseLong(root.edtStuffPrice.getText().toString().replace(" ", ""));
//                    sb.append(App.webServiceUrl).append("bime_api/price/token/").append(Utils.getInstance().getUserToken())
//                            .append("/target_price/").append(price).append("/city_code/").append(cityCode).append("/building_type/").append(building_type)
//                            .append("/age/").append(age);
//                    if (root.chbSeil.isChecked()) {
//                        sb.append("/seil/1");
//                    }
//                    if (root.chbZelzele.isChecked()) {
//                        sb.append("/zelzele/1");
//                    }
//                    if (root.chbToofan.isChecked()) {
//                        sb.append("/toofan/1");
//                    }
//                    if (root.chbPost.isChecked()) {
//                        sb.append("/post/1");
//                    }
//                    Log.d("volley_url", "onClick: " + sb.toString());
//                    StringRequest request = new StringRequest(Request.Method.GET, sb.toString(), new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            dialog.hide();
//                            Log.d("volley_response", "onResponse: " + response);
//                            root.btnOk.setVisibility(View.GONE);
//                            Animation animShowFromUpRightCorner = AnimationUtils.loadAnimation(ActivityOrder2.this, R.anim.from_up_right_corner);
//                            animShowFromUpRightCorner.setAnimationListener(new Animation.AnimationListener() {
//                                @Override
//                                public void onAnimationStart(Animation animation) {
//                                    root.stepFive.setVisibility(View.VISIBLE);
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animation animation) {
//                                    root.stepFour.setVisibility(View.GONE);
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animation animation) {
//
//                                }
//                            });
//                            root.stepFive.startAnimation(animShowFromUpRightCorner);
//                            try {
//                                long price = 0;
//                                JSONArray array = new JSONArray(response);
//                                JSONObject obj = array.getJSONObject(0);
//                                if (obj.getInt("type") == Constants._TYPE_HARIGH_MASKOONI_ALBORZ) {
//                                    JSONObject object = obj.getJSONObject("data");
//                                    JSONObject objHarigh = object.getJSONObject("harigh");
//                                    StringBuilder priceBuilder = new StringBuilder();
//                                    StringBuilder priceListBuilder = new StringBuilder();
//                                    StringBuilder dscBuilder = new StringBuilder();
//                                    if (objHarigh.getBoolean("permitted")) {
//                                        priceListBuilder.append("ریز حساب :").append("\n");
//                                        priceListBuilder.append("پوشش حریق مسکونی = ").append(objHarigh.getLong("price")).append(" ریال");
//                                        price += objHarigh.getLong("price");
//                                        if (root.chbSeil.isChecked()) {
//                                            JSONObject objSeil = object.getJSONObject("seil");
//                                            if (objSeil.getBoolean("permitted")) {
//                                                priceListBuilder.append("\n").append("پوشش سیل = ").append(objSeil.getLong("price")).append(" ریال");
//                                                price += objSeil.getLong("price");
//                                            } else {
//                                                dscBuilder.append("\n").append("پوشش سیل برای شهر شما بدون تایید کارشناس قابل تایید نیست. با لمس کلید درخواست خود را ثبت کنید تا کارشناس ما با شما تماس بگیرد.");
//                                            }
//                                        }
//                                        if (root.chbToofan.isChecked()) {
//                                            JSONObject objToofan = object.getJSONObject("toofan");
//                                            if (objToofan.getBoolean("permitted")) {
//                                                priceListBuilder.append("\n").append("پوشش طوفان = ").append(objToofan.getLong("price")).append(" ریال");
//                                                price += objToofan.getLong("price");
//                                            } else {
//                                                dscBuilder.append("\n").append("پوشش طوفان برای شهر شما بدون تایید کارشناس قابل تایید نیست. با لمس کلید درخواست خود را ثبت کنید تا کارشناس ما با شما تماس بگیرد.");
//                                            }
//                                        }
//                                        if (root.chbZelzele.isChecked()) {
//                                            JSONObject objZelzele = object.getJSONObject("zelzele");
//                                            if (objZelzele.getBoolean("permitted")) {
//                                                priceListBuilder.append("\n").append("پوشش زلزله = ").append(objZelzele.getLong("price")).append(" ریال");
//                                                price += objZelzele.getLong("price");
//                                            } else {
//                                                dscBuilder.append("\n").append("پوشش زلزله برای ساختمان بالای سی و پنج سال بدون تایید کارشناس قابل تایید نیست. با لمس کلید درخواست خود را ثبت کنید تا کارشناس ما با شما تماس بگیرد.");
//                                            }
//                                        }
//                                        if (root.chbPost.isChecked()) {
//                                            priceListBuilder.append("\n").append("هزینه پستی = ").append(object.getLong("post")).append(" ریال");
//                                            price += object.getLong("post");
//                                        }
//                                        priceBuilder.append("مبلغ قابل پرداخت : ").append(price).append(" ریال");
//                                    } else {
//                                        dscBuilder.append("ساختمان بالای پنجاه سال بدون تایید کارشناس قابل بیمه کردن نیست. با زدن کلید ادامه درخواست خود را ثبت کنید تا کارشناس ما با شما تماس بگیرد.");
//                                    }
//
//                                    makePriceList(priceBuilder.toString(), priceListBuilder.toString(), dscBuilder.toString());
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            dialog.hide();
//                            Log.d("volley_code", "onErrorResponse: " + error.networkResponse.statusCode);
//                        }
//                    });
//                    App.getInstance().addToRequestQueue(request);
//                } else if (root.stepFour.getVisibility() == View.VISIBLE) {
//
//                } else {
//                    Toast.makeText(ActivityOrder2.this, "بیمه را انتخاب کنید", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        root.txtTargetState.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder2.this);
//                builder.setTitle("استان را انتخاب کنید.")
//                        .setSingleChoiceItems(R.array.states, 1000, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                cityPosition = -1;
//                                root.txtTargetCity.setText("نام شهر");
//                                statePosition = i;
//                                dialogInterface.dismiss();
//                                root.txtTargetState.setText(getResources().getStringArray(R.array.states)[i]);
//                            }
//                        }).create().show();
//            }
//        });
//        root.llTargetState.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder2.this);
//                builder.setTitle("استان را انتخاب کنید.")
//                        .setSingleChoiceItems(R.array.states, 1000, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                cityPosition = -1;
//                                root.txtTargetCity.setText("نام شهر");
//                                statePosition = i;
//                                dialogInterface.dismiss();
//                                root.txtTargetState.setText(getResources().getStringArray(R.array.states)[i]);
//                            }
//                        }).create().show();
//            }
//        });
//
//        root.txtTargetCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (statePosition < 0) {
//                    Toast.makeText(ActivityOrder2.this, "ابتدا استان را انتخاب کنید.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String[] params = {root.txtTargetState.getText().toString()};
//                final Cursor cursor = App.bank.rawQuery("SELECT * FROM city WHERE state=?", params);
//                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder2.this);
//                builder.setTitle("استان را انتخاب کنید.")
//                        .setSingleChoiceItems(cursor, 1000, "city", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                cursor.moveToPosition(i);
//                                cityPosition = i;
//                                cityCode = cursor.getInt(cursor.getColumnIndex("code"));
//                                dialogInterface.dismiss();
//                                root.txtTargetCity.setText(cursor.getString(cursor.getColumnIndex("city")));
//                            }
//                        }).create().show();
//            }
//        });
//        root.llTargetCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (statePosition < 0) {
//                    Toast.makeText(ActivityOrder2.this, "ابتدا استان را انتخاب کنید.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String[] params = {root.txtTargetState.getText().toString()};
//                final Cursor cursor = App.bank.rawQuery("SELECT * FROM city WHERE state=?", params);
//                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder2.this);
//                builder.setTitle("استان را انتخاب کنید.")
//                        .setSingleChoiceItems(cursor, 1000, "city", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                cursor.moveToPosition(i);
//                                cityPosition = i;
//                                cityCode = cursor.getInt(cursor.getColumnIndex("code"));
//                                dialogInterface.dismiss();
//                                root.txtTargetCity.setText(cursor.getString(cursor.getColumnIndex("city")));
//                            }
//                        }).create().show();
//            }
//        });
//
//        root.edtTargetPrice.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (twEnabled) {
//                    String s = null;
//                    try {
//                        // The comma in the format specifier does the trick
//                        s = Spacer(root.edtTargetPrice.getText().toString());
//                        twEnabled = false;
//                        root.edtTargetPrice.setTextKeepState(s);
//                        Selection.setSelection(root.edtTargetPrice.getText(), s.length());
//                        twEnabled = true;
//                    } catch (NumberFormatException e) {
//
//                    }
//                }
//            }
//        });
//
//        root.edtStuffPrice.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (twEnabled) {
//                    String s = null;
//                    try {
//                        // The comma in the format specifier does the trick
//                        s = Spacer(root.edtStuffPrice.getText().toString());
//                        twEnabled = false;
//                        root.edtStuffPrice.setTextKeepState(s);
//                        Selection.setSelection(root.edtStuffPrice.getText(), s.length());
//                        twEnabled = true;
//                    } catch (NumberFormatException e) {
//
//                    }
//                }
//            }
//        });
//
//        root.rbCompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbPerson.setChecked(false);
//                    root.llGender.setVisibility(View.GONE);
//                    root.parentEdtFamily.setVisibility(View.GONE);
//                    c_kind = Statics._COMPANY;
//                }
//            }
//        });
//
//        root.rbPerson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbCompany.setChecked(false);
//                    root.llGender.setVisibility(View.VISIBLE);
//                    root.parentEdtFamily.setVisibility(View.VISIBLE);
//                    c_kind = Statics._PERSON;
//                }
//            }
//        });
//
//        root.rbMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbWoman.setChecked(false);
//                }
//            }
//        });
//
//        root.rbWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbMan.setChecked(false);
//                }
//            }
//        });
//
//        root.rbAjori.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbAiinName.setChecked(false);
//                    root.rbBotoni.setChecked(false);
//                    root.rbFelezi.setChecked(false);
//                    building_type = Statics._AJORI;
//                }
//            }
//        });
//
//        root.rbFelezi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbAiinName.setChecked(false);
//                    root.rbBotoni.setChecked(false);
//                    root.rbAjori.setChecked(false);
//                    building_type = Statics._FELEZI;
//                }
//            }
//        });
//
//        root.rbBotoni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbAiinName.setChecked(false);
//                    root.rbAjori.setChecked(false);
//                    root.rbFelezi.setChecked(false);
//                    building_type = Statics._BOTONI;
//                }
//            }
//        });
//
//        root.rbAiinName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbAjori.setChecked(false);
//                    root.rbBotoni.setChecked(false);
//                    root.rbFelezi.setChecked(false);
//                    building_type = Statics._AIIN_NAME;
//                }
//            }
//        });
//
//        root.rbTeenage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbkid.setChecked(false);
//                    root.rbOld.setChecked(false);
//                    age = Statics._AGE_YOUNG;
//                }
//            }
//        });
//        root.rbkid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbTeenage.setChecked(false);
//                    root.rbOld.setChecked(false);
//                    age = Statics._AGE_KID;
//                }
//            }
//        });
//        root.rbOld.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    root.rbkid.setChecked(false);
//                    root.rbTeenage.setChecked(false);
//                    age = Statics._AGE_OLD;
//                }
//            }
//        });
//    }
//
//    private void makePriceList(String price, String priceList, String dsc) {
//        root.stepFive.removeAllViews();
//
//        View child = getLayoutInflater().inflate(R.layout.nerkh_item, null);//child.xml
//        root.stepFive.addView(child);
//
//        TextView txtItemTitle = (TextView) child.findViewById(R.id.txtItemTitle);
//        TextView txtItemPay = (TextView) child.findViewById(R.id.txtItemPay);
//        TextView txtPrice = (TextView) child.findViewById(R.id.txtPrice);
//        TextView txtPriceList = (TextView) child.findViewById(R.id.txtPriceList);
//        TextView txtDsc = (TextView) child.findViewById(R.id.txtDsc);
//        final Button btnPay = (Button) child.findViewById(R.id.btnPay);
//
//        Utils.getInstance().prepareTextView(txtItemTitle);
//        Utils.getInstance().prepareTextView(txtItemPay);
//        Utils.getInstance().prepareTextView(txtPrice);
//        Utils.getInstance().prepareTextView(txtPriceList);
//        Utils.getInstance().prepareTextView(txtDsc);
//        Utils.getInstance().prepareTextView(btnPay);
//
//        txtPrice.setText(price);
//        txtPriceList.setText(priceList);
//        txtDsc.setText(dsc);
//        if (record_id.length() > 0) {
//            btnPay.setText("اعمال تغییرات");
//        }
//        btnPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clicked = btnPay;
//                if (record_id.length() == 0) {
//                    createNewRecord();
//                } else {
//                    updateRecord();
//                }
//            }
//        });
//    }
//
//    /**
//     * @param number
//     * @return
//     */
//
//    private String Spacer(String number) {
//        number = number.replace(" ", "");
//        StringBuilder strB = new StringBuilder();
//        strB.append(number);
//        int Three = 0;
//
//        for (int i = number.length(); i > 0; i--) {
//            Three++;
//            if (Three == 3) {
//                strB.insert(i - 1, " ");
//                Three = 0;
//            }
//        }
//        return strB.toString();
//    }// end Spacer()
//
//    private boolean checkStep(int step) {
//        boolean param = true;
//        switch (step) {
//            case 1:
//                if (!checkName(root.edtName.getText().toString())) {
//                    param = false;
//                }
//                if (!checkFamily(root.edtFamily.getText().toString())) {
//                    param = false;
//                }
//                if (!checkCountryCode(root.edtCountryCode.getText().toString())) {
//                    param = false;
//                }
//                if (!checkAddress(root.edtAddress.getText().toString())) {
//                    param = false;
//                }
//                if (!checkTel(root.edtTel.getText().toString())) {
//                    param = false;
//                }
//                if (!checkCell(root.edtCell.getText().toString())) {
//                    param = false;
//                }
//                break;
//            case 2:
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
//                break;
//            case 3:
//                if (!checkTargetPrice(root.edtTargetPrice.getText().toString())) {
//                    param = false;
//                }
//                if (!checkStuffPrice(root.edtStuffPrice.getText().toString())) {
//                    param = false;
//                }
//                break;
//            default:
//
//        }
//
//        return param;
//    }
//
//    private boolean checkName(String name) {
//        boolean param = true;
//        if (name.length() < 4) {
//            Toast.makeText(this, "نام نمیتواند کوچکتر از ۴ کاراکتر باشد.", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkFamily(String name) {
//        if (root.rbCompany.isChecked()) {
//            return true;
//        }
//        boolean param = true;
//        if (name.length() < 4) {
//            Toast.makeText(this, "نام خانوادگی نمیتواند کوچکتر از ۴ کاراکتر باشد.", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkCountryCode(String code) {
//        boolean param = true;
//        if (root.rbCompany.isChecked()) {
//            if (!code.startsWith("0")) {
//                Toast.makeText(this, "کدملی معتبر نیست", Toast.LENGTH_SHORT).show();
//                param = false;
//            } else if (code.length() != 10) {
//                Toast.makeText(this, "کدملی معتبر نیست", Toast.LENGTH_SHORT).show();
//                param = false;
//            }
//        } else {
//            if (code.length() != 10) {
//                Toast.makeText(this, "شناسه ملی معتبر نیست", Toast.LENGTH_SHORT).show();
//                param = false;
//            }
//        }
//        return param;
//    }
//
//    private boolean checkAddress(String address) {
//        boolean param = true;
//        if (address.length() < 10) {
//            Toast.makeText(this, "آدرس نا معتبر است", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkTel(String tel) {
//        boolean param = true;
//        if (!tel.startsWith("0")) {
//            Toast.makeText(this, "کد شهر در شماره تلفن ذکر نشده است", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkCell(String cell) {
//        boolean param = true;
//        if (!cell.startsWith("09")) {
//            Toast.makeText(this, "شماره همراه نامعتبر است", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        if (cell.length() != 11) {
//            Toast.makeText(this, "شماره همراه نامعتبر است", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkPostalCode(String code) {
//        boolean param = true;
//        if (code.length() != 10) {
//            Toast.makeText(this, "کد پستی معتبر نیست", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkTargetPrice(String price) {
//        price = price.replace(" ", "");
//        boolean param = true;
//        try {
//            if (Long.parseLong(price) < 10000000) {
//                Toast.makeText(this, "ارزش مکان معتبر نیست", Toast.LENGTH_SHORT).show();
//                param = false;
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "ارزش مکان معتبر نیست", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkStuffPrice(String price) {
//        price = price.replace(" ", "");
//        boolean param = true;
//        try {
//            if (Long.parseLong(price) < 10000000) {
//                Toast.makeText(this, "مبلغ وسایل معتبر نیست", Toast.LENGTH_SHORT).show();
//                param = false;
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "مبلغ وسایل معتبر نیست", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkState() {
//        boolean param = true;
//        if (statePosition < 0) {
//            Toast.makeText(this, "استان را انتخاب کنید", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkCity() {
//        boolean param = true;
//        if (cityPosition < 0) {
//            Toast.makeText(this, "نام شهر را وارد کنید", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private boolean checkTimeForPurchase() {
//        boolean param = true;
//        Calendar calendar = Calendar.getInstance();
//        if (calendar.get(Calendar.HOUR_OF_DAY) > 21) {
//            Toast.makeText(this, "از ساعت 10 شب به بعد پرداخت مقدور نیست.", Toast.LENGTH_SHORT).show();
//            param = false;
//        }
//        return param;
//    }
//
//    private void createNewRecord() {
////        startActivity(new Intent(ActivityOrder.this, ActivityPay.class));
//        StringBuilder sb = new StringBuilder();
//        int seil = Statics._NOT_REQUESTED;
//        int toofan = Statics._NOT_REQUESTED;
//        int zelzele = Statics._NOT_REQUESTED;
//        int post = Statics._NOT_REQUESTED;
//        if (root.chbSeil.isChecked()) seil = Statics._REQUESTED;
//        if (root.chbToofan.isChecked()) toofan = Statics._REQUESTED;
//        if (root.chbZelzele.isChecked()) zelzele = Statics._REQUESTED;
//        if (root.chbPost.isChecked()) post = Statics._REQUESTED;
//        String family = null;
//        try {
//            family = URLEncoder.encode(root.edtFamily.getText().toString().replace(" ", "%20"), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        if (family.length() == 0) {
//            family = "%20";
//        }
//        try {
//            sb.append("alborz_api/")
//                    .append("bime/")
//                    .append("token/").append(Utils.getInstance().getUserToken())
//                    .append("/c_kind/").append(c_kind)
//                    .append("/c_name/").append(URLEncoder.encode(root.edtName.getText().toString().replace(" ", "%20"), "UTF-8"))
//                    .append("/c_family/").append(family)
//                    .append("/c_gender/").append(gender)
//                    .append("/c_address/").append(URLEncoder.encode(root.edtAddress.getText().toString().replace(" ", "%20"), "UTF-8"))
//                    .append("/c_c_code/").append(root.edtCountryCode.getText().toString())
//                    .append("/c_tel/").append(root.edtTel.getText().toString())
//                    .append("/c_cell/").append(root.edtCell.getText().toString())
//                    .append("/t_city/").append(cityCode)
//                    .append("/t_address/").append(URLEncoder.encode(root.edtTargetAddress.getText().toString().replace(" ", "%20"), "UTF-8"))
//                    .append("/t_p_code/").append(root.edtTargetPostalCode.getText().toString())
//                    .append("/t_price/").append(root.edtTargetPrice.getText().toString().replace(" ", ""))
//                    .append("/t_stuff_price/").append(root.edtStuffPrice.getText().toString().replace(" ", ""))
//                    .append("/t_type/").append(building_type)
//                    .append("/t_age/").append(age)
//                    .append("/seil/").append(seil)
//                    .append("/toofan/").append(toofan)
//                    .append("/zelzele/").append(zelzele)
//                    .append("/post/").append(post);
//
//            lunchRequest("post", sb.toString(), "add_record");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateRecord() {
//        StringBuilder sb = new StringBuilder();
//        int seil = Statics._NOT_REQUESTED;
//        int toofan = Statics._NOT_REQUESTED;
//        int zelzele = Statics._NOT_REQUESTED;
//        int post = Statics._NOT_REQUESTED;
//        if (root.chbSeil.isChecked()) seil = Statics._REQUESTED;
//        if (root.chbToofan.isChecked()) toofan = Statics._REQUESTED;
//        if (root.chbZelzele.isChecked()) zelzele = Statics._REQUESTED;
//        if (root.chbPost.isChecked()) post = Statics._REQUESTED;
//        String family = null;
//        try {
//            family = URLEncoder.encode(root.edtFamily.getText().toString().replace(" ", "%20"), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        if (family.length() == 0) {
//            family = "%20";
//        }
//        try {
//            sb.append("alborz_api/")
//                    .append("update_bime/")
//                    .append("token/").append(Utils.getInstance().getUserToken())
//                    .append("/id/").append(record_id)
//                    .append("/c_kind/").append(c_kind)
//                    .append("/c_name/").append(URLEncoder.encode(root.edtName.getText().toString().replace(" ", "%20"), "UTF-8"))
//                    .append("/c_family/").append(family)
//                    .append("/c_gender/").append(gender)
//                    .append("/c_address/").append(URLEncoder.encode(root.edtAddress.getText().toString().replace(" ", "%20"), "UTF-8"))
//                    .append("/c_c_code/").append(root.edtCountryCode.getText().toString())
//                    .append("/c_tel/").append(root.edtTel.getText().toString())
//                    .append("/c_cell/").append(root.edtCell.getText().toString())
//                    .append("/t_city/").append(cityCode)
//                    .append("/t_address/").append(URLEncoder.encode(root.edtTargetAddress.getText().toString().replace(" ", "%20"), "UTF-8"))
//                    .append("/t_p_code/").append(root.edtTargetPostalCode.getText().toString())
//                    .append("/t_price/").append(root.edtTargetPrice.getText().toString().replace(" ", ""))
//                    .append("/t_stuff_price/").append(root.edtStuffPrice.getText().toString().replace(" ", ""))
//                    .append("/t_type/").append(building_type)
//                    .append("/t_age/").append(age)
//                    .append("/seil/").append(seil)
//                    .append("/toofan/").append(toofan)
//                    .append("/zelzele/").append(zelzele)
//                    .append("/post/").append(post);
//
//            lunchRequest("post", sb.toString(), "update_record");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void responseOk(String name, String response) {
//        super.responseOk(name, response);
//
//        if (name == "add_record" || name == "update_record") {
//            try {
//                final JSONObject object = new JSONObject(response);
//                View.OnClickListener listener = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(ActivityOrder2.this, "امکان تغییر شهر بعد از ثبت فرم بیمه وجود ندارد.", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                root.txtTargetCity.setOnClickListener(listener);
//                root.txtTargetState.setOnClickListener(listener);
//                root.llTargetState.setOnClickListener(listener);
//                root.llTargetState.setOnClickListener(listener);
//                clicked.setText("پرداخت");
//                clicked.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (checkTimeForPurchase()) {
//                            Intent intent = new Intent(ActivityOrder2.this, ActivityPay.class);
//                            try {
//                                record_id = object.getString("id");
//                                intent.putExtra("id", record_id);
//                                startActivity(intent);
//                                ActivityOrder2.this.finish();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//                });
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    protected void setUI(BimeItem item) {
//        switch (item.getC_kind()) {
//            case Statics._PERSON:
//                root.rbPerson.setChecked(true);
//                break;
//            case Statics._COMPANY:
//                root.rbCompany.setChecked(true);
//                break;
//        }
//        switch (item.getC_gender()) {
//            case Statics._MAN:
//                root.rbMan.setChecked(true);
//                break;
//            case Statics._WOMAN:
//                root.rbWoman.setChecked(true);
//                break;
//        }
//        root.edtName.setText(item.getC_name());
//        root.edtFamily.setText(item.getC_family());
//        root.edtCountryCode.setText(item.getC_c_code());
//        root.edtAddress.setText(item.getC_address());
//        root.edtTel.setText(item.getC_tel());
//        root.edtCell.setText(item.getC_cell());
//
//        cityCode = item.getT_city();
//        cityPosition = 0;
//        statePosition = 0;
//        String[] param = {cityCode + ""};
//        Cursor cursor = App.bank.rawQuery("SELECT * FROM city WHERE code=?", param);
//        cursor.moveToPosition(0);
//        root.txtTargetCity.setText(cursor.getString(cursor.getColumnIndex("city")));
//        root.txtTargetState.setText(cursor.getString(cursor.getColumnIndex("state")));
//
//        root.edtTargetAddress.setText(item.getT_address());
//        switch (item.getT_age()) {
//            case Statics._AGE_KID:
//                root.rbkid.setChecked(true);
//                break;
//            case Statics._AGE_YOUNG:
//                root.rbTeenage.setChecked(true);
//                break;
//            case Statics._AGE_OLD:
//                root.rbOld.setChecked(true);
//                break;
//        }
//        switch (item.getT_type()) {
//            case Statics._AJORI:
//                root.rbAjori.setChecked(true);
//                break;
//            case Statics._FELEZI:
//                root.rbFelezi.setChecked(true);
//                break;
//            case Statics._BOTONI:
//                root.rbBotoni.setChecked(true);
//                break;
//            case Statics._AIIN_NAME:
//                root.rbAiinName.setChecked(true);
//                break;
//        }
//        root.edtTargetPostalCode.setText(item.getT_p_code());
//        root.edtTargetPrice.setText(item.getT_price());
//        root.edtStuffPrice.setText(item.getT_stuff_price());
//        if (item.getSeil_process_state() != Statics._NOT_REQUESTED) {
//            root.chbSeil.setChecked(true);
//        }
//        if (item.getToofan_process_state() != Statics._NOT_REQUESTED) {
//            root.chbToofan.setChecked(true);
//        }
//        if (item.getZelzele_process_state() != Statics._NOT_REQUESTED) {
//            root.chbZelzele.setChecked(true);
//        }
//        if (item.getPost_state() != Statics._NOT_REQUESTED) {
//            root.chbPost.setChecked(true);
//        }
//        age = item.getT_age();
//        gender = item.getC_gender();
//        building_type = item.getT_type();
//        c_kind = item.getC_kind();
//
//        if (item.getBime_process_state() == Statics._PAYED
//                || item.getSeil_process_state() == Statics._PAYED
//                || item.getToofan_process_state() == Statics._PAYED
//                || item.getZelzele_process_state() == Statics._PAYED) {
//            root.edtTargetPrice.setEnabled(false);
//            root.edtTargetPrice.setKeyListener(null);
//
//            root.edtStuffPrice.setEnabled(false);
//            root.edtStuffPrice.setKeyListener(null);
//            root.rbAiinName.setEnabled(false);
//            root.rbAjori.setEnabled(false);
//            root.rbMan.setEnabled(false);
//            root.rbWoman.setEnabled(false);
//            root.rbPerson.setEnabled(false);
//            root.rbCompany.setEnabled(false);
//            root.rbFelezi.setEnabled(false);
//            root.rbBotoni.setEnabled(false);
//            root.rbkid.setEnabled(false);
//            root.rbOld.setEnabled(false);
//            root.rbTeenage.setEnabled(false);
//        }
//        if (item.getBime_process_state() == Statics._PAYED) {
//            harigh_payed = true;
//        }
//        if (item.getSeil_process_state() == Statics._PAYED) {
//            seil_payed = true;
//            root.chbSeil.setEnabled(false);
//        }
//        if (item.getToofan_process_state() == Statics._PAYED) {
//            toofan_payed = true;
//            root.chbToofan.setEnabled(false);
//        }
//        if (item.getZelzele_process_state() == Statics._PAYED) {
//            zelzele_payed = true;
//            root.chbZelzele.setEnabled(false);
//        }
//        if (item.getPost_state() == Statics._PAYED) {
//            post_payed = true;
//        }
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ActivityOrder2.this, "امکان تغییر شهر بعد از ثبت فرم بیمه وجود ندارد.", Toast.LENGTH_SHORT).show();
//            }
//        };
//        root.txtTargetCity.setOnClickListener(listener);
//        root.txtTargetState.setOnClickListener(listener);
//        root.llTargetCity.setOnClickListener(listener);
//        root.llTargetState.setOnClickListener(listener);
//    }
//}
