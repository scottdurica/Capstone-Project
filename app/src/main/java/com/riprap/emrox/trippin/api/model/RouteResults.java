package com.riprap.emrox.trippin.api.model;

import java.util.ArrayList;

/**
 * Created by scott on 2/17/2017.
 */

public class RouteResults {

    String  route_type;
    String mode_name;
    ArrayList<Route>route;

    public String getRoute_type() {
        return route_type;
    }
    public String getMode_name() {
        return mode_name;
    }

    public ArrayList<Route> getRoute() {
        return route;
    }

    public static class Route {

        private String route_type;
        private String mode_name;
        private ArrayList<Line> route;
        String route_id;
        String route_name;

        public String getRoute_id() {
            return route_id;
        }
        public String getRoute_name() {
            return route_name;
        }
        public Route(String route_type, String mode_name) {
            this.route_type = route_type;
            this.mode_name = mode_name;
        }
    }


    public static class Line {

        private String route_id;
        private String route_name;
        private String routeType;
        private String modeName;

        public String getRouteType() {
            return routeType;
        }
        public String getModeName() {
            return modeName;
        }
        public Line(String route_id, String route_name, String routeType, String modeName) {
            this.route_id = route_id;
            this.route_name = route_name;
            this.routeType = routeType;
            this.modeName = modeName;
        }
        public String getRoute_id() {
            return route_id;
        }
        public String getRoute_name() {
            return route_name;
        }

    }
}
