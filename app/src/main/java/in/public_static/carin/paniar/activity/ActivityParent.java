package in.public_static.carin.paniar.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.HttpURLConnection;
import java.util.Hashtable;
import java.util.Map;

import in.public_static.carin.paniar.App;
import in.public_static.carin.paniar.utils.Utils;

public class ActivityParent extends Activity {

    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerDialog();
        prepareViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void registerDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("در حال برقراری ارتباط با سرور...");
        dialog.setCancelable(true);
    }

    protected void assignViews() {

    }

    protected void prepareViews() {
        setTypeFace(getWindow().getDecorView());
    }

    protected void setViewFunctions() {

    }

    protected void writeChanges() {

    }

    protected void setUI() {

    }

    protected void checkCasheAndRequest() {

    }

    protected void lunchRequest(final String method, String url, final String name) {
        url = url.replace("+", "");
        url = url.replace(" ", "%20");
        url = App.webServiceUrl + url;

        Log.d("volley", "lunchRequest: " + url);
        dialog.show();
        if (!Utils.getInstance().isNetworkAvailable(App.context)) {
            Toast.makeText(this, "اتصال به اینترنت برقرار نیست.", Toast.LENGTH_SHORT).show();
            dialog.hide();
            return;
        }
        final int[] mStatusCode = {0};
        int mMethod = 0;
        switch (method) {
            case "post":
                mMethod = Request.Method.POST;
                break;
            case "get":
                mMethod = Request.Method.GET;
                break;
            case "put":
                mMethod = Request.Method.PUT;
                break;
            case "delete":
                mMethod = Request.Method.DELETE;
                break;

        }
        StringRequest request = new StringRequest(mMethod, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                switch (mStatusCode[0]) {
                    case HttpURLConnection.HTTP_OK:    //200
                        responseOk(name, s);
                        break;
                    case HttpURLConnection.HTTP_NO_CONTENT:    //204
                        responseNoContent(name);
                        break;
                    default:
                        responseDefaultOk(name, s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse == null) {
                    return;
                }
                Log.d("volley_response", "onErrorResponse: " + volleyError.getMessage());
                switch (volleyError.networkResponse.statusCode) {
                    case HttpURLConnection.HTTP_BAD_REQUEST: //400
                        responseBadRequest(name);
                        break;
                    case HttpURLConnection.HTTP_UNAUTHORIZED: //401
                        responseUnAuthorized(name);
                        break;
                    case HttpURLConnection.HTTP_FORBIDDEN:  //403
                        responseForbidden(name);
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:   //404
                        responseNotFound(name, volleyError);
                        break;
                    case HttpURLConnection.HTTP_CLIENT_TIMEOUT:  //408
                        responseServerTimeOut(name);
                        break;
                    case HttpURLConnection.HTTP_REQ_TOO_LONG:  //414
                        responseUrlTooLong(name);
                        break;
                    case HttpURLConnection.HTTP_INTERNAL_ERROR:    //500
                        responseServerInternalError(name);
                        break;
                    default:
                        responseDefaultError(name);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<>();
                params.put("head", "head");
                if (method == "post") {
                    return params;
                } else {
                    return super.getParams();
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response != null) {
                    mStatusCode[0] = response.statusCode;
                }
                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        request.setRetryPolicy(policy);
        App.getInstance().addToRequestQueue(request);
    }

    protected void responseOk(String name, String response) {
        dialog.hide();
    }

    protected void responseNoContent(String name) {
        dialog.hide();
    }

    protected void responseDefaultOk(String name, String response) {
        dialog.hide();
    }

    protected void responseBadRequest(String name) {
        dialog.hide();
    }

    protected void responseUnAuthorized(String name) {
        dialog.hide();
    }

    protected void responseForbidden(String name) {
        dialog.hide();
    }

    protected void responseNotFound(String name, VolleyError volleyError) {
        dialog.hide();
        Log.d("volley 404", "responseNotFound: " + volleyError.getMessage());
    }

    protected void responseServerTimeOut(String name) {
        dialog.hide();
    }

    protected void responseUrlTooLong(String name) {
        dialog.hide();
    }

    protected void responseServerInternalError(String name) {
        dialog.hide();
    }

    protected void responseDefaultError(String name) {
        dialog.hide();
    }

    private void setTypeFace(View root) {
        if (!(root instanceof ViewGroup)) {
            try {
                Utils.getInstance().prepareTextView(root, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        ViewGroup vg = (ViewGroup) root;
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            setTypeFace(child);
        }
    }


}
