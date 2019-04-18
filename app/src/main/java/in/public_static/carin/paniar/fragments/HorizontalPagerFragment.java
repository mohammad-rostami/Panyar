package in.public_static.carin.paniar.fragments;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.public_static.carin.paniar.CardItem;
import in.public_static.carin.paniar.CardPagerAdapter;
import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.ShadowTransformer;

public class HorizontalPagerFragment extends Fragment {

    private TextView homeInsurance;
    private TextView happeningInsurance;
    private TextView jobInsurance;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        homeInsurance = (TextView) view.findViewById(R.id.home_insurance);
        happeningInsurance = (TextView) view.findViewById(R.id.happening_insurance);
        jobInsurance = (TextView) view.findViewById(R.id.job_insurance);

        homeInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                tabBackground(homeInsurance);
            }
        });
        happeningInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
                tabBackground(happeningInsurance);

            }
        });
        jobInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
                tabBackground(jobInsurance);

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardPagerAdapter mCardAdapter;
        ShadowTransformer mCardShadowTransformer;

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setPageMargin(70);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(1));
        mCardAdapter.addCardItem(new CardItem(2));
        mCardAdapter.addCardItem(new CardItem(3));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
//        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabBackground(homeInsurance);
                        break;
                    case 1:
                        tabBackground(happeningInsurance);
                        break;
                    case 2:
                        tabBackground(jobInsurance);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void tabBackground(TextView txt) {
        homeInsurance.setBackgroundColor(Color.parseColor("#ffffff"));
        happeningInsurance.setBackgroundColor(Color.parseColor("#ffffff"));
        jobInsurance.setBackgroundColor(Color.parseColor("#ffffff"));
        homeInsurance.setTextColor(Color.parseColor("#777777"));
        happeningInsurance.setTextColor(Color.parseColor("#777777"));
        jobInsurance.setTextColor(Color.parseColor("#777777"));

        txt.setBackgroundDrawable(getResources().getDrawable(R.drawable.active_tab_back));
        txt.setTextColor(Color.parseColor("#ffffff"));
    }
}

