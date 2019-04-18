package in.public_static.carin.paniar.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import in.public_static.carin.paniar.App;
import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.databinding.ActivitySplashBinding;
import in.public_static.carin.paniar.utils.Utils;

public class ActivitySplash extends AppCompatActivity {

    ActivitySplashBinding root;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        root = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        root.llLogo.setVisibility(View.VISIBLE);
        animFadeIn.reset();
        root.llLogo.clearAnimation();
        root.llLogo.startAnimation(animFadeIn);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (Utils.getInstance().isFirstUse()) {
//                    if (App.IsInternetConnected()) {
//                        Utils.getInstance().setFirstUseFalse();
//                        startActivity(new Intent(ActivitySplash.this, ActivityNumber.class));
//                        startActivity(new Intent(ActivitySplash.this, ActivityIntro.class));
//                    } else {
//                        Toast.makeText(ActivitySplash.this, "ارتباط شما با اینترنت برقرار نیست.", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    if (Utils.getInstance().isUserLogged()) {
//                        if (App.IsInternetConnected()) {
//                            startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
//                        } else {
//                            Toast.makeText(ActivitySplash.this, "ارتباط شما با اینترنت برقرار نیست.", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        if (App.IsInternetConnected()) {
//                            startActivity(new Intent(ActivitySplash.this, ActivityNumber.class));
//                        } else {
//                            Toast.makeText(ActivitySplash.this, "ارتباط شما با اینترنت برقرار نیست.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                ActivitySplash.this.finish();
//            }
//        }, 3000);

        Intent intent = new Intent(ActivitySplash.this,ActivityMain.class);
        ActivitySplash.this.startActivity(intent);
        finish();
    }
}
