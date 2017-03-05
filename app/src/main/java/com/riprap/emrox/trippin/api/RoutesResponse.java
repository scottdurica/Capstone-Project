package com.riprap.emrox.trippin.api;

import com.riprap.emrox.trippin.api.model.RouteResults;

import java.util.ArrayList;

/**
 * Created by scott on 2/17/2017.
 */

public class RoutesResponse {

    private ArrayList<RouteResults> mode;

    public RoutesResponse() {
    }

    public ArrayList<RouteResults> getDataList() {
        return mode;
    }

    public void setDataList(ArrayList<RouteResults> mode) {
        this.mode = mode;
    }
}
