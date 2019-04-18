package in.public_static.carin.paniar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.public_static.carin.paniar.App;
import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.activity.ActivityPopUpMain;
import in.public_static.carin.paniar.cunstractor.CommissionItem;
import in.public_static.carin.paniar.utils.Utils;


public class FragmentProfile extends Fragment {

    ArrayList<CommissionItem> items;

    ImageView imgSetting;
    TextView txtMoney;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        imgSetting = (ImageView) view.findViewById(R.id.imgSetting);
        txtMoney = (TextView) view.findViewById(R.id.txtMoney);
        items = new ArrayList<>();
        TextView img_info = (TextView) view.findViewById(R.id.img_info);
        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.context, ActivityPopUpMain.class);
                intent.putExtra("type", "fragment_profile");
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lunchRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
        lunchRequest();
    }

    private void lunchRequest() {
        String url = App.webServiceUrl + "bime_api/commission/token/" + Utils.getInstance().getUserToken();
        Log.d("volley_url", "url :" + url);
        StringRequest request = new StringRequest(Request.Method.GET, App.webServiceUrl + "bime_api/commission/token/" + Utils.getInstance().getUserToken(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("volley_response", "onResponse: waiting :" + response);
                items.clear();
                int total = 0;
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        CommissionItem item = new CommissionItem();
                        JSONObject object = array.getJSONObject(i);
                        item.setAmount(object.getString("transaction_amount"));
                        item.setRefNumber(object.getString("bime_id"));
                        int amount = (int) (Double.parseDouble(item.getAmount()) + 0.5);
                        total += amount;
                        items.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                txtMoney.setText(total + "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_response", "onErrorResponse: " + error.getMessage());
            }
        });
        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        request.setRetryPolicy(policy);
        App.getInstance().addToRequestQueue(request);
    }
}