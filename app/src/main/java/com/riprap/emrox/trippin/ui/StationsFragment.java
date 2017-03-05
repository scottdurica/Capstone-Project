package com.riprap.emrox.trippin.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.riprap.emrox.trippin.R;
import com.riprap.emrox.trippin.adapters.LinesListExpandableAdapter;
import com.riprap.emrox.trippin.bus.DialogEvent;
import com.riprap.emrox.trippin.data.TrippinContract;
import com.riprap.emrox.trippin.ui.dialog.MyDialogListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StationsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,MyDialogListFragment.DialogClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "StationsFragment";
    public static final int LINES_LOADER = 1;
    private static final String[] DETAIL_COLUMNS = {
            TrippinContract.Lines._ID,
            TrippinContract.Lines.COLUMN_ROUTE_NAME,
            TrippinContract.Lines.COLUMN_ROUTE_TYPE,
            TrippinContract.Lines.COLUMN_ROUTE_ID,
            TrippinContract.Lines.COLUMN_MODE_NAME
    };

    @BindView(R.id.expandableListView) ExpandableListView expandableListView;
    LinesListExpandableAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;




    public StationsFragment() {
        // Required empty public constructor
    }

    public static StationsFragment newInstance() {
        StationsFragment fragment = new StationsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        // Prepare the loader. Either re-connect with an existing one,
        // or start a new one.
        Loader loader = getLoaderManager().getLoader(LINES_LOADER);
        if (loader != null && !loader.isReset()) {
            getLoaderManager().restartLoader(LINES_LOADER, null, this);
        } else {
            getLoaderManager().initLoader(LINES_LOADER, null, this);
        }
        EventBus.getDefault().register(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDialogRequestReceived(DialogEvent dialogEvent){
        DialogFragment dialog = new MyDialogListFragment();
        dialog.setArguments(dialogEvent.getBundle());
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(),"tag");
    }
    //callback sent from dialog that has the selected direction
    @Override
    public void itemChosen(String selection) {
        Log.e(TAG, "value chosen was " + selection);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stations, container, false);
        ButterKnife.bind(this, rootView);

        mAdapter = new LinesListExpandableAdapter(getActivity());
        expandableListView.setAdapter(mAdapter);

        return rootView;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                TrippinContract.Lines.CONTENT_URI,
                DETAIL_COLUMNS,
                null,
                null,
                null
        );
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null) Log.e(TAG, "cursor count: " + data.getCount());

        mAdapter.swapCursor(data);
        Log.e(TAG, "Load Finished");
        getLoaderManager().destroyLoader(LINES_LOADER);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
