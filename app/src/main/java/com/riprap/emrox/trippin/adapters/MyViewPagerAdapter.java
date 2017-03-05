package com.riprap.emrox.trippin.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.riprap.emrox.trippin.R;
import com.riprap.emrox.trippin.ui.AlertsFragment;
import com.riprap.emrox.trippin.ui.CurrentTripFragment;
import com.riprap.emrox.trippin.ui.MapsFragment;
import com.riprap.emrox.trippin.ui.SavedTripsFragment;
import com.riprap.emrox.trippin.ui.StationsFragment;

/**
 * Adapter for the main viewpager
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    public static final String TAG = MyViewPagerAdapter.class.getSimpleName();

    public static final int CURRENT_TRIP = 0;
    public static final int ALERTS = 1;
    public static final int MAPS = 2;
    public static final int STATIONS = 3;
    public static final int SAVED_TRIPS = 4;
    private Context mContext;

    public MyViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case CURRENT_TRIP:
                return CurrentTripFragment.newInstance();
            case ALERTS:
                return AlertsFragment.newInstance();
            case MAPS:
                return MapsFragment.newInstance();
            case STATIONS:
                return StationsFragment.newInstance();
            case SAVED_TRIPS:
                return SavedTripsFragment.newInstance();
        }
        Log.e(TAG, "position unknown");
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case CURRENT_TRIP:
                return mContext.getResources().getString(R.string.tab_title_current_trip);
            case ALERTS:
                return mContext.getResources().getString(R.string.tab_title_alerts);
            case MAPS:
                return mContext.getResources().getString(R.string.tab_title_maps);
            case STATIONS:
                return mContext.getResources().getString(R.string.tab_title_stations);
            case SAVED_TRIPS:
                return mContext.getResources().getString(R.string.tab_title_saved_trips);
        }
        Log.e(TAG, "Unable to get tab titles");
        return null;
    }

}
