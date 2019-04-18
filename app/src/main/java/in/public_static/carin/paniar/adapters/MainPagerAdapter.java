package in.public_static.carin.paniar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.public_static.carin.paniar.fragments.FragmentMyBimeList;
import in.public_static.carin.paniar.fragments.FragmentProfile;
import in.public_static.carin.paniar.fragments.FragmentWaitingForApprove;
import in.public_static.carin.paniar.fragments.FragmentWaitingForPurchaseList;
import in.public_static.carin.paniar.fragments.HorizontalPagerFragment;


public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final static int COUNT = 5;

    private final static int WAITING_FOR_APPROVE = 0;
    private final static int MY_BIME_LIST = 1;
    private final static int HORIZONTAL = 2;
    private final static int WAITING_FOR_PURCHASE = 3;
    private final static int USER_PROFILE = 4;

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case WAITING_FOR_APPROVE:
                return new FragmentWaitingForApprove();
            case MY_BIME_LIST:
                return new FragmentMyBimeList();
            case HORIZONTAL:
                return new HorizontalPagerFragment();
            case WAITING_FOR_PURCHASE:
                return new FragmentWaitingForPurchaseList();
            case USER_PROFILE:
                return new FragmentProfile();
            default:
                return new FragmentWaitingForApprove();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }


}
