package com.riprap.emrox.trippin.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by scott on 2/16/2017.
 */

public class TrippinDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "trips.db";

    public TrippinDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TRIPS_TABLE = "CREATE TABLE " + TrippinContract.TripEntry.TABLE_NAME
                + " ("
                + TrippinContract.TripEntry._ID + " INTEGER PRIMARY KEY, "
                + TrippinContract.TripEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, "
                + TrippinContract.TripEntry.COLUMN_START_POINT + " TEXT NOT NULL, "
                + TrippinContract.TripEntry.COLUMN_END_POINT + " TEXT NOT NULL"
                +");";
        final String SQL_CREATE_LINES_TABLE = "CREATE TABLE " + TrippinContract.Lines.TABLE_NAME
                + " ("
                + TrippinContract.Lines._ID + " INTEGER PRIMARY KEY, "
                + TrippinContract.Lines.COLUMN_ROUTE_NAME + " TEXT NOT NULL, "
                + TrippinContract.Lines.COLUMN_MODE_NAME + " TEXT NOT NULL, "
                + TrippinContract.Lines.COLUMN_ROUTE_TYPE + " TEXT NOT NULL, "
                + TrippinContract.Lines.COLUMN_ROUTE_ID + " TEXT NOT NULL"
                +");";

        sqLiteDatabase.execSQL(SQL_CREATE_TRIPS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LINES_TABLE);

    }




    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TrippinContract.TripEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TrippinContract.Lines.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
