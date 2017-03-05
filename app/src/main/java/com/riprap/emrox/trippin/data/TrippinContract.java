package com.riprap.emrox.trippin.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by scott on 2/16/2017.
 */

public class TrippinContract {

    public static final String CONTENT_AUTHORITY = "com.riprap.emrox.trippin";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //trip table  this is the user's list of trips
    public static final String PATH_TRIPS = "trips";

    public static final class TripEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRIPS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRIPS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRIPS;

        public static final String TABLE_NAME = "trips";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_START_POINT = "start_point";
        public static final String COLUMN_END_POINT = "end_point";

        public static Uri buildTripUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
    //lines table  this is a complete list of available lines.
    public static final String PATH_LINES = "lines";

    public static final class Lines implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LINES).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LINES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LINES;

        public static final String TABLE_NAME = "lines";

        public static final String COLUMN_ROUTE_NAME = "route_name";
        public static final String COLUMN_ROUTE_ID = "route_id";
        public static final String COLUMN_ROUTE_TYPE = "route_type";
        public static final String COLUMN_MODE_NAME = "mode_name";

        public static Uri buildTripUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    //stops table  list of stops.  some will be duplicated, aside from direction...
    public static final String PATH_STOPS = "stops";

    public static final class Stops implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STOPS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOPS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOPS;

        public static final String TABLE_NAME = "stops";

        public static final String COLUMN_DIRECTION_ID = "direction_id";
        public static final String COLUMN_DIRECTION_NAME = "direction_name";

        public static final String COLUMN_STOP_ORDER = "stop_order";
        public static final String COLUMN_STOP_ID = "stop_id";
        public static final String COLUMN_STOP_NAME = "stop_name";
        public static final String COLUMN_PARENT_STATION = "parent_station";
        public static final String COLUMN_STATION_NAME = "station_name";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LONG = "long";

        public static Uri buildTripUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
