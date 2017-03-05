package com.riprap.emrox.trippin.ui.secondary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.riprap.emrox.trippin.R;
import com.riprap.emrox.trippin.api.StopsByRouteResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LineFragment extends Fragment {

    ArrayList<StopsByRouteResponse.Direction>directions;
    private SectionedRecyclerViewAdapter mAdapter;
    @BindView(R.id.recyclerview)RecyclerView mRecyclerView;

    private OnFragmentInteractionListener mListener;


    public LineFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LineFragment newInstance(Bundle bundle) {
        LineFragment fragment = new LineFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            directions = (ArrayList)getArguments().getParcelableArrayList("line_data");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_line, container, false);
        ButterKnife.bind(this,rootView);
        mAdapter = new SectionedRecyclerViewAdapter();


        for (StopsByRouteResponse.Direction d : directions) {
            ArrayList<String> stopNamesList = new ArrayList<>();
            String header = d.getDirection_name();
            ArrayList<StopsByRouteResponse.Stop> stops = d.getStop();
            for (StopsByRouteResponse.Stop stop: stops){
                stopNamesList.add(stop.getStop_name());
            }
            mAdapter.addSection(new LinesSection(header, stopNamesList));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public class LinesSection extends StatelessSection{

        ArrayList<String> list;
        String header;

        public LinesSection(String header, ArrayList<String> list) {
            super(R.layout.rv_lines_header, R.layout.rv_lines_list_item);
            this.header = header;
            this.list = list;
        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }
        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            itemHolder.lineName.setText(list.get(position));

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format("Clicked on position #%s of Section %s", mAdapter.getSectionPosition(itemHolder.getAdapterPosition()), header), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.title.setText(header);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_list_item)TextView lineName;
        View rootView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.rootView = itemView;

        }
    }
    class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header_item)TextView title;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
