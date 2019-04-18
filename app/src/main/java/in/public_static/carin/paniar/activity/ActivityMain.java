package in.public_static.carin.paniar.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.adapters.MainPagerAdapter;
import in.public_static.carin.paniar.databinding.ActivityMainBinding;

public class ActivityMain extends ActivityCompatParent {
    ActivityMainBinding root;
    boolean exit = false;
    private LinearLayout imgSupport;
    private LinearLayout imgInsurance;
    private LinearLayout imgMain;
    private LinearLayout imgList;
    private LinearLayout imgProfile;

    private ImageView icSupport;
    private ImageView icInsurance;
    private ImageView icMain;
    private ImageView icList;
    private ImageView icProfile;

    private TextView txtSupport;
    private TextView txtInsurance;
    private TextView txtMain;
    private TextView txtList;
    private TextView txtProfile;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        root = DataBindingUtil.setContentView(this, R.layout.activity_main);

        imgSupport = (LinearLayout) findViewById(R.id.imgWaitingList);
        imgInsurance = (LinearLayout) findViewById(R.id.imgReciept);
        imgMain = (LinearLayout) findViewById(R.id.imgOrder);
        imgList = (LinearLayout) findViewById(R.id.imgApprovedList);
        imgProfile = (LinearLayout) findViewById(R.id.imgProfile);

        icSupport = (ImageView) findViewById(R.id.icSupport);
        icInsurance = (ImageView) findViewById(R.id.icInsurance);
        icMain = (ImageView) findViewById(R.id.icMain);
        icList = (ImageView) findViewById(R.id.icList);
        icProfile = (ImageView) findViewById(R.id.icProfile);

        txtSupport = (TextView) findViewById(R.id.txtSupport);
        txtInsurance = (TextView) findViewById(R.id.txtInsurance);
        txtList = (TextView) findViewById(R.id.txtList);
        txtProfile = (TextView) findViewById(R.id.txtProfile);
        txtMain = (TextView) findViewById(R.id.txtMain);


        root.vpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        root.vpMain.setOffscreenPageLimit(4);

        root.vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        root.vpMain.setCurrentItem(2);
        root.imgApprovedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.vpMain.setCurrentItem(3);
                bottomNavView(icList, R.color.cyanide, txtList);
            }
        });

        root.imgOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.vpMain.setCurrentItem(2);
                bottomNavView(icMain, R.color.pink, txtMain);
            }
        });

        root.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.vpMain.setCurrentItem(4);
                bottomNavView(icProfile, R.color.light_brown, txtProfile);

//                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.nav_zoom_in);
//                anim.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                txtProfile.startAnimation(anim);
            }
        });

        root.imgReciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.vpMain.setCurrentItem(1);
                bottomNavView(icInsurance, R.color.dark_yellow, txtInsurance);
            }
        });

        root.imgWaitingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.vpMain.setCurrentItem(0);
                bottomNavView(icSupport, R.color.blue, txtSupport);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
        } else {
            Toast.makeText(ActivityMain.this, "برای خروج کلید بازگشت را دوباره لمس کنید.", Toast.LENGTH_SHORT).show();
            exit = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);
        }
    }

    @Override
    protected void responseOk(String name, String response) {
        super.responseOk(name, response);
        switch (name) {
            case "purchased":

                break;
            case "approved":

                break;
            case "not_approved":

                break;
        }
    }

    private void bottomNavView(ImageView icon, int Color, TextView text) {
        icSupport.setColorFilter(getResources().getColor(R.color.gray));
        icInsurance.setColorFilter(getResources().getColor(R.color.gray));
        icMain.setColorFilter(getResources().getColor(R.color.gray));
        icList.setColorFilter(getResources().getColor(R.color.gray));
        icProfile.setColorFilter(getResources().getColor(R.color.gray));

        icon.setColorFilter(getResources().getColor(Color));


        txtSupport.setVisibility(View.GONE);
        txtInsurance.setVisibility(View.GONE);
        txtList.setVisibility(View.GONE);
        txtProfile.setVisibility(View.GONE);
        txtMain.setVisibility(View.GONE);

        text.setVisibility(View.VISIBLE);

    }

    private void bottomNavViewMain(ImageView icon, int Color) {
        icSupport.setColorFilter(getResources().getColor(R.color.gray));
        icInsurance.setColorFilter(getResources().getColor(R.color.gray));
        icMain.setColorFilter(getResources().getColor(R.color.gray));
        icList.setColorFilter(getResources().getColor(R.color.gray));
        icProfile.setColorFilter(getResources().getColor(R.color.gray));

        icon.setColorFilter(getResources().getColor(Color));


        txtSupport.setVisibility(View.GONE);
        txtInsurance.setVisibility(View.GONE);
        txtList.setVisibility(View.GONE);
        txtProfile.setVisibility(View.GONE);
        txtMain.setVisibility(View.GONE);


    }
}

