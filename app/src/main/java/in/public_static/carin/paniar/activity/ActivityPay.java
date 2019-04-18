package in.public_static.carin.paniar.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import in.public_static.carin.paniar.App;
import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.databinding.ActivityPayBinding;
import in.public_static.carin.paniar.utils.Utils;

public class ActivityPay extends ActivityParent {
    ActivityPayBinding root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = DataBindingUtil.setContentView(this, R.layout.activity_pay);
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        StringBuilder builder = new StringBuilder();
        builder.append(App.webServiceUrl).append("alborz_api/pay/token/").append(Utils.getInstance().getUserToken()).append("/id/").append(id);
        webView(builder.toString());
    }

    //Metodo llamar el webview
    private void webView(String address) {
        //Habilitar JavaScript (Videos youtube)
        root.web.getSettings().setJavaScriptEnabled(true);

        //Handling Page Navigation
        root.web.setWebViewClient(new MyWebViewClient());

        //Load a URL on WebView
        root.web.loadUrl(address);


    }

    // Metodo Navigating web page history
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPay.this);
//        builder.setMessage("آیا پرداخت را لغو میکنید؟");
//        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        }).setPositiveButton("بله", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                ActivityPay.this.finish();
//            }
//        }).create().show();
//
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Subclase WebViewClient() para Handling Page Navigation
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("volley_response", "onPageFinished: url = " + url);
            if (url.contains(App.webServiceUrl + "alborz_api/pay_approve/token/") && url.contains(Utils.getInstance().getUserToken())) {
                Toast.makeText(ActivityPay.this, "پرداخت انجام شد.", Toast.LENGTH_SHORT).show();
                ActivityOrder.closeActivity();
                ActivityPay.this.finish();
            }

            if (url.contains("https://pep.shaparak.ir/")) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                root.web.setLayoutParams(layoutParams);
                root.llUrl.setVisibility(View.VISIBLE);
            }
        }
    }
}
