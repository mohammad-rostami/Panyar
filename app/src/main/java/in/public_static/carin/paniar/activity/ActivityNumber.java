package in.public_static.carin.paniar.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import in.public_static.carin.paniar.App;
import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.databinding.ActivityNumberBinding;
import in.public_static.carin.paniar.utils.Utils;


public class ActivityNumber extends ActivityCompatParent {
    ActivityNumberBinding root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = DataBindingUtil.setContentView(this, R.layout.activity_number);
        prepareViews();
        setViewFunctions();
    }

    @Override
    protected void prepareViews() {
        super.prepareViews();
    }

    @Override
    protected void setViewFunctions() {
        super.setViewFunctions();
        root.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = root.edtPhone.getText().toString();
                if (phoneNumberIsValid(phoneNumber)) {
                    sendNumber(phoneNumber);
                } else {
                    Snackbar.make(root.edtPhone, R.string.invalid_phone_number, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        root.edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (root.edtCode.getText().toString().length() == 6) {
                    String phoneNumber = root.edtPhone.getText().toString();
                    if (phoneNumber.startsWith("0")) {
                        phoneNumber = "98" + phoneNumber.substring(1);
                    }
                    lunchRequest("get", "validation_api/token/number/" + phoneNumber + "/code/" + root.edtCode.getText().toString(), "get_token");
                }
            }
        });

    }

    private boolean phoneNumberIsValid(String phoneNumber) {
        if (phoneNumber.startsWith("09")) {
            return phoneNumber.length() == 11;
        } else if (phoneNumber.startsWith("989")) {
            return phoneNumber.length() == 12;
        } else {
            return false;
        }
    }

    @Override
    protected void responseOk(String name, String response) {
        super.responseOk(name, response);

        try {
            JSONObject object = new JSONObject(response);
            Utils.getInstance().setUserToken(object.getString("token"));
            Intent intent = new Intent(ActivityNumber.this, ActivityMain.class);
            intent.putExtra("after_login", true);
            startActivity(intent);
            ActivityNumber.this.finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void responseNoContent(String name) {
        super.responseNoContent(name);
        Toast.makeText(ActivityNumber.this, "لطفا کد پنج رقمی که برایتان ارسال می شود را وارد کنید", Toast.LENGTH_SHORT).show();
        stepTow();
    }

    private void sendNumber(String number) {
        number = number.replace("+", "");
        if (number.startsWith("0")) {
            number = "98" + number.substring(1);
        }
        final String correctNumber = number;
        lunchRequest("get", "validation_api/code/number/" + correctNumber, "get_code");
    }

    private void stepTow() {
        root.imgDsc.setImageDrawable(ContextCompat.getDrawable(App.context, R.drawable.otp_valid_dsc));
        root.edtCode.setVisibility(View.VISIBLE);
        root.edtPhone.setVisibility(View.GONE);
        root.submitButton.setText("");
        root.submitButton.setBackgroundResource(R.drawable.btn_green_with_tick);
        root.submitButton.setEnabled(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    root.submitButton.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 15000);
    }
}

