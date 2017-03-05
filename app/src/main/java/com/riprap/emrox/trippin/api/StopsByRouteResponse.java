package com.riprap.emrox.trippin.api;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by scott on 2/22/2017.
 */

public class StopsByRouteResponse implements Parcelable{

    private ArrayList<StopsByRouteResponse.Direction> direction;

    protected StopsByRouteResponse(Parcel in) {
        direction = in.createTypedArrayList(Direction.CREATOR);
    }

    public ArrayList<Direction> getDirection() {
        return direction;
    }
    public void setDirection(ArrayList<Direction> direction) {
        this.direction = direction;
    }

    //parcelable stuff
    public static final Creator<StopsByRouteResponse> CREATOR = new Creator<StopsByRouteResponse>() {
        @Override
        public StopsByRouteResponse createFromParcel(Parcel in) {
            return new StopsByRouteResponse(in);
        }

        @Override
        public StopsByRouteResponse[] newArray(int size) {
            return new StopsByRouteResponse[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(direction);
    }

    public static class Direction implements Parcelable{

        private String direction_id;
        private String direction_name;
        private ArrayList<StopsByRouteResponse.Stop> stop;

        protected Direction(Parcel in) {
            direction_id = in.readString();
            direction_name = in.readString();
            stop = in.createTypedArrayList(Stop.CREATOR);
        }


        public String getDirection_id() {
            return direction_id;
        }
        public void setDirection_id(String direction_id) {
            this.direction_id = direction_id;
        }
        public String getDirection_name() {
            return direction_name;
        }
        public void setDirection_name(String direction_name) {this.direction_name = direction_name;}
        public ArrayList<Stop> getStop() {
            return stop;
        }
        public void setStop(ArrayList<Stop> stop) {
            this.stop = stop;
        }

        //parcelable stuff
        public static final Creator<Direction> CREATOR = new Creator<Direction>() {
            @Override
            public Direction createFromParcel(Parcel in) {
                return new Direction(in);
            }

            @Override
            public Direction[] newArray(int size) {
                return new Direction[size];
            }
        };
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(direction_id);
            parcel.writeString(direction_name);
            parcel.writeTypedList(stop);
        }
    }

    public static class Stop implements Parcelable {

        private String stop_order;
        private String stop_id;
        private String stop_name;
        private String parent_station;
        private String parent_station_name;
        private String stop_lat;
        private String stop_lon;

        protected Stop(Parcel in) {
            stop_order = in.readString();
            stop_id = in.readString();
            stop_name = in.readString();
            parent_station = in.readString();
            parent_station_name = in.readString();
            stop_lat = in.readString();
            stop_lon = in.readString();
        }



        public String getStop_order() {return stop_order;}
        public void setStop_order(String stop_order) {
            this.stop_order = stop_order;
        }
        public String getStop_id() {
            return stop_id;
        }
        public void setStop_id(String stop_id) {
            this.stop_id = stop_id;
        }
        public String getStop_name() {
            return stop_name;
        }
        public void setStop_name(String stop_name) {
            this.stop_name = stop_name;
        }
        public String getParent_station() {
            return parent_station;
        }
        public void setParent_station(String parent_station) {this.parent_station = parent_station;}
        public String getParent_station_name() {
            return parent_station_name;
        }
        public void setParent_station_name(String parent_station_name) {this.parent_station_name = parent_station_name;}
        public String getStop_lat() {
            return stop_lat;
        }
        public void setStop_lat(String stop_lat) {
            this.stop_lat = stop_lat;
        }
        public String getStop_lon() {
            return stop_lon;
        }
        public void setStop_lon(String stop_lon) {
            this.stop_lon = stop_lon;
        }

        //parcelable stuff
        public static final Creator<Stop> CREATOR = new Creator<Stop>() {
            @Override
            public Stop createFromParcel(Parcel in) {
                return new Stop(in);
            }

            @Override
            public Stop[] newArray(int size) {
                return new Stop[size];
            }
        };
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(stop_order);
            parcel.writeString(stop_id);
            parcel.writeString(stop_name);
            parcel.writeString(parent_station);
            parcel.writeString(parent_station_name);
            parcel.writeString(stop_lat);
            parcel.writeString(stop_lon);
        }
    }

}
