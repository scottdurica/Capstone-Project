package com.riprap.emrox.trippin.ui.secondary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.riprap.emrox.trippin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LineFragment.OnFragmentInteractionListener{

    public static final int FRAGMENT_LINE = 1;

    @BindView(R.id.detail_toolbar) Toolbar toolBar;
    @BindView(R.id.fragment_container) FrameLayout fragLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);

        Intent intent = getIntent();
        //get which fragment to load.  This is so that this one activity can be
        //used to view multiple detail screens of data.
        //
        switch (intent.getIntExtra("fragmentInfo", 0)){
            case FRAGMENT_LINE:
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("line_data",intent.getParcelableArrayListExtra("line_data"));

                Fragment fragment = LineFragment.newInstance(bundle);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment, "LineFragment");
                ft.commit();
                break;

            default:
                throw new UnsupportedOperationException("Fragment not found");




        }



    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
