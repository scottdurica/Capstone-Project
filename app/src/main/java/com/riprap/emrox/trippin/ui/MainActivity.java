package com.riprap.emrox.trippin.ui;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.riprap.emrox.trippin.R;
import com.riprap.emrox.trippin.adapters.MyViewPagerAdapter;
import com.riprap.emrox.trippin.data.TrippinContract;
import com.riprap.emrox.trippin.sync.LinesSyncAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AlertsFragment.OnFragmentInteractionListener
        , CurrentTripFragment.OnFragmentInteractionListener, MapsFragment.OnFragmentInteractionListener
        , StationsFragment.OnFragmentInteractionListener, SavedTripsFragment.OnFragmentInteractionListener {

    private MyViewPagerAdapter mMyViewPagerAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Log.e(TAG, "registedeed");
        mMyViewPagerAdapter = new MyViewPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mMyViewPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        fab.setVisibility(View.VISIBLE);
                        break;
                    default:
                        fab.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //start the syncadapter service up...
        LinesSyncAdapter.initializeSyncAdapter(this);
//        LinesSyncAdapter.syncImmediately(this);
//        addTestTrips();
    }



    private void addTestTrips() {
        ContentValues values = new ContentValues();
        values.put(TrippinContract.TripEntry.COLUMN_NAME,"State House");
        values.put(TrippinContract.TripEntry.COLUMN_START_POINT,"Lisa's House");
        values.put(TrippinContract.TripEntry.COLUMN_END_POINT,"12 State Street, Boston");

        Uri uri = getContentResolver().insert(TrippinContract.TripEntry.CONTENT_URI,values);
        Log.e(TAG, "Uri is: " + uri);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
