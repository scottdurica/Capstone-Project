package com.riprap.emrox.trippin.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.riprap.emrox.trippin.R;
import com.riprap.emrox.trippin.data.TrippinContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by scott on 2/16/2017.
 */

public class SavedTripsRecyclerViewAdapter extends RecyclerView.Adapter<SavedTripsRecyclerViewAdapter.MyViewHolder> {

    private Cursor dataCursor;
    private Context context;

    public SavedTripsRecyclerViewAdapter(Context context, Cursor cursor) {
        this.dataCursor = cursor;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(context).
                inflate(R.layout.trip_list_card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        dataCursor.moveToPosition(position);
        holder.name.setText(dataCursor.getString(dataCursor.getColumnIndex(TrippinContract.TripEntry.COLUMN_NAME)));
        holder.startPoint.setText(dataCursor.getString(dataCursor.getColumnIndex(TrippinContract.TripEntry.COLUMN_START_POINT)));
        holder.endPoint.setText(dataCursor.getString(dataCursor.getColumnIndex(TrippinContract.TripEntry.COLUMN_END_POINT)));
    }

    @Override
    public int getItemCount() {

        return (dataCursor == null)? 0 : dataCursor.getCount();
    }

    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_trip_name)TextView name;
        @BindView(R.id.tv_start_destination) TextView startPoint;
        @BindView(R.id.tv_end_destination) TextView endPoint;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
