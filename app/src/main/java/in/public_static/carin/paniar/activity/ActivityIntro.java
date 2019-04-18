package in.public_static.carin.paniar.activity;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.utils.Utils;

/**
 * Created by amin on 2/25/17.
 */

public class ActivityIntro extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Enable/disable fullscreen */
        setFullscreen(true);
        super.onCreate(savedInstanceState);

        /**
         * Standard slide (like Google's intros)
         */
        /*addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_1_title))
                .description(getResources().getString(R.string.intro_1_dsc))
                .image(R.drawable.intro_1)
                .background(R.color.colorAccent)
                .backgroundDark(R.color.colorPrimaryDark)
//                .permission(Manifest.permission.CAMERA)
                .build());*/

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .backgroundDark(R.color.colorPrimary)
                .fragment(R.layout.fragment_info_1, R.style.AppTheme_NoActionBar)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .backgroundDark(R.color.colorPrimary)
                .fragment(R.layout.fragment_info_2, R.style.AppTheme_NoActionBar)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .backgroundDark(R.color.colorPrimary)
                .fragment(R.layout.fragment_info_3, R.style.AppTheme_NoActionBar)
                .build());


        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .backgroundDark(R.color.colorPrimary)
                .fragment(R.layout.fragment_info_4, R.style.AppTheme_NoActionBar)
                .build());
        Utils.getInstance().writePrefrences("show_intro", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        return;
    }


}
