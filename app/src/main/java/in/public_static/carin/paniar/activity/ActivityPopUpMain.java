package in.public_static.carin.paniar.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.databinding.ActivityPopUpMainBinding;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityPopUpMain extends Activity {
    ActivityPopUpMainBinding root;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = DataBindingUtil.setContentView(this, R.layout.activity_pop_up_main);
        TextView txtClose = (TextView) findViewById(R.id.close);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        String type = extras.getString("type");
        switch (type) {
            case "hariq_maskan":
                root.txtDsc.setText(R.string.pop_up_hariq_maskooni);
                break;
            case "my_bime_list":
                root.txtDsc.setText(R.string.pop_up_my_bime_list);
                break;
            case "waiting_for_approve":
                root.txtDsc.setText(R.string.pop_up_waiting_for_approve);
                break;
            case "waiting_for_purchase":
                root.txtDsc.setText(R.string.pop_up_waiting_for_purchase);
                break;
            case "fragment_profile":
                root.txtDsc.setText(R.string.pop_up_fragment_user);
                break;
            case "order":
                root.txtTitle.setText(getString(R.string.pop_up_order_title));
                StringBuilder builder = new StringBuilder();
                builder.append(getString(R.string.pop_up_order_1)).append("\n").append(getString(R.string.pop_up_order_2)).append("\n").append(getString(R.string.pop_up_order_3));
                root.txtDsc.setText(builder.toString());
                break;
        }
    }
}
