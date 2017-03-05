package com.riprap.emrox.trippin.api.model;

import java.util.ArrayList;

/**
 * Created by scott on 2/23/2017.
 */

public class StopsByRouteResults {

    private ArrayList<Direction> direction;




    public static class Direction {

        private String direction_id;
        private String direction_name;
        private ArrayList<Stop> stop;

    }




    public static class Stop {

        private String stop_order;
        private String stop_id;
        private String stop_name;
        private String parent_station;
        private String parent_station_name;
        private String stop_lat;
        private String stop_lon;


    }
}
