package com.riprap.emrox.trippin.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.riprap.emrox.trippin.R;
import com.riprap.emrox.trippin.api.StopsByRouteAPI;
import com.riprap.emrox.trippin.api.StopsByRouteResponse;
import com.riprap.emrox.trippin.data.TrippinContract;
import com.riprap.emrox.trippin.ui.secondary.DetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by scott on 2/18/2017.
 */

public class LinesListExpandableAdapter extends BaseExpandableListAdapter implements Callback<StopsByRouteResponse> {
    public static final String TAG = "LExpandablAdapter";
    private Context mContext;
    private ArrayList<String> titles;
    private HashMap<String, List<String>> dataList;

    ArrayList<String> subwayList;
    ArrayList<String> commuterRailList;
    ArrayList<String> busList;
    ArrayList<String> ferryList;

public LinesListExpandableAdapter(Context context) {

    mContext = context;
    subwayList = new ArrayList<>();
    commuterRailList = new ArrayList<>();
    busList = new ArrayList<>();
    ferryList = new ArrayList<>();
    dataList = new HashMap<>();
    titles = new ArrayList<>();
}

    public void swapCursor(Cursor cursor){
        if (cursor == null || cursor.moveToNext() == false){
            Log.e("Adapter","cursor is empty");
        }else{
            subwayList.clear();
            commuterRailList.clear();
            busList.clear();
            ferryList.clear();
            dataList.clear();

            while(cursor.moveToNext()){
                String routeName = cursor.getString(cursor.getColumnIndex(TrippinContract.Lines.COLUMN_ROUTE_NAME));
                String rt = cursor.getString(cursor.getColumnIndex(TrippinContract.Lines.COLUMN_ROUTE_TYPE));

                int routeType = Integer.parseInt(rt);

                    switch (routeType){
                        case 0:
                            subwayList.add(routeName);
                            break;
                        case 1:
                            subwayList.add(routeName);
                            break;
                        case 2:
                            commuterRailList.add(routeName);
                            break;
                        case 3:
                            busList.add(routeName);
                            break;
                        case 4:
                            ferryList.add(routeName);
                            break;
                        default:
                            break;
                    }
            }
            dataList.put(mContext.getResources().getString(R.string.line_list_title_ferry), ferryList);
            dataList.put(mContext.getResources().getString(R.string.line_list_title_bus), busList);
            dataList.put(mContext.getResources().getString(R.string.line_list_title_commuter_rail), commuterRailList);
            dataList.put(mContext.getResources().getString(R.string.line_list_title_subway), subwayList);

            ArrayList<String> titles = new ArrayList(dataList.keySet());

            this.titles = titles;
            notifyDataSetChanged();
        }
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.dataList.get(this.titles.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_item, null);
        }
        final TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.tv_expandable_list_item);
        expandedListTextView.setText(expandedListText);
//    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fetch route information.  Cache it in the database for offline use.
                TextView textView = (TextView)view.findViewById(R.id.tv_expandable_list_item);
                Cursor c = mContext.getContentResolver().query(TrippinContract.Lines.CONTENT_URI,
                        new String [] {TrippinContract.Lines.COLUMN_ROUTE_ID},
                        TrippinContract.Lines.COLUMN_ROUTE_NAME + " =?",
                        new String [] {textView.getText().toString()},
                        null);
                if (c.moveToNext()){
//                Log.e(TAG, textView.getText().toString());
//                Log.e(TAG, c.getString(c.getColumnIndex(TrippinContract.Lines.COLUMN_ROUTE_ID)));
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(StopsByRouteAPI.STOPS_BY_ROUTE_ENDPOINT_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    StopsByRouteAPI stopsByRouteAPI = retrofit.create((StopsByRouteAPI.class));
                    Call<StopsByRouteResponse> call = stopsByRouteAPI.getStopsByRoute(mContext.getString(R.string.MBTA_api_key),c.getString(c.getColumnIndex(TrippinContract.Lines.COLUMN_ROUTE_ID)));
                    call.enqueue(LinesListExpandableAdapter.this);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.dataList.get(this.titles.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.titles.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.titles.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_header, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_expandable_list_header);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    @Override
    public void onResponse(Call<StopsByRouteResponse> call, Response<StopsByRouteResponse> response) {
        Log.e(TAG, "onResponse getting called");
        StopsByRouteResponse stopsByRouteResponse = response.body();
        String [] firstAndLast = new String [2];

        //populate array with first and last stopn on a route and pass that to the dialogfragment
        if (stopsByRouteResponse != null && stopsByRouteResponse.getDirection() != null){
            Log.e(TAG,"direction count : " + stopsByRouteResponse.getDirection().size());
            ArrayList<StopsByRouteResponse.Direction> directions = stopsByRouteResponse.getDirection();
//            firstAndLast[0] = directions.get(0).getDirection_name();
//            firstAndLast[1] = directions.get(directions.size()-1).getDirection_name();
//            Bundle args = new Bundle();
//            args.putString("title", "Choose Direction");
//            args.putStringArray("list_items", firstAndLast);


            for(StopsByRouteResponse.Direction direction: directions){
                String id = direction.getDirection_id();
                String name = direction.getDirection_name();
                ArrayList<StopsByRouteResponse.Stop> stops = direction.getStop();
                for(StopsByRouteResponse.Stop stop: stops){

                }
            }

//            private String stop_order;
//            private String stop_id;
//            private String stop_name;
//            private String parent_station;
//            private String parent_station_name;
//            private String stop_lat;
//            private String stop_lon;


            Intent intent = new Intent(mContext,DetailActivity.class);
            intent.putExtra("fragmentInfo",DetailActivity.FRAGMENT_LINE);
            intent.putParcelableArrayListExtra("line_data",directions);
            mContext.startActivity(intent);

            //kill dialog
//            EventBus.getDefault().post(new DialogEvent(args));


//            for(StopsByRouteResponse.Direction d: directions){
//                String direction = d.getDirection_name();
//                ArrayList<StopsByRouteResponse.Stop> stops = d.getStop();
//                String
//            }
        }

    }

    @Override
    public void onFailure(Call<StopsByRouteResponse> call, Throwable t) {

    }


}
