package com.riprap.emrox.trippin.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by scott on 2/16/2017.
 */

public class TrippinProvider extends ContentProvider {

    public static final String TAG = TrippinProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TrippinDbHelper mOpenHelper;

    private static final int TRIPS = 100;
    private static final int TRIP = 101;

    private static final int LINES = 110;
    private static final int LINE = 111;

    static UriMatcher buildUriMatcher(){
     final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TrippinContract.CONTENT_AUTHORITY;
        //create a code for each type of Uri needed.
        //trips table
        matcher.addURI(authority, TrippinContract.PATH_TRIPS, TRIPS);
        matcher.addURI(authority, TrippinContract.PATH_TRIPS + "/#", TRIP);
        //lines table
        matcher.addURI(authority,TrippinContract.PATH_LINES, LINES);
        matcher.addURI(authority, TrippinContract.PATH_LINES + "/#", LINE);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new TrippinDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);

        switch (match){
            case TRIP:
                return TrippinContract.TripEntry.CONTENT_ITEM_TYPE;
            case TRIPS:
                return TrippinContract.TripEntry.CONTENT_TYPE;
            case GradientDrawable.LINE:
                return TrippinContract.TripEntry.CONTENT_ITEM_TYPE;
            case LINES:
                return TrippinContract.TripEntry.CONTENT_TYPE;
            default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectonArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)){
            case TRIPS:
                retCursor = mOpenHelper.getReadableDatabase().query(TrippinContract.TripEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectonArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TRIP:
                retCursor = mOpenHelper.getReadableDatabase().query(TrippinContract.TripEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectonArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case LINES:
                retCursor = mOpenHelper.getReadableDatabase().query(TrippinContract.Lines.TABLE_NAME,
                        projection,
                        selection,
                        selectonArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case LINE:
                retCursor = mOpenHelper.getReadableDatabase().query(TrippinContract.Lines.TABLE_NAME,
                        projection,
                        selection,
                        selectonArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        Uri retUri;

        switch (match){
            case TRIPS:{
                long id = mOpenHelper.getWritableDatabase().insert(TrippinContract.TripEntry.TABLE_NAME,
                        null, contentValues);
                if (id > 0){
                    retUri = TrippinContract.TripEntry.buildTripUri(id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case LINES:{
                long id = mOpenHelper.getWritableDatabase().insert(TrippinContract.Lines.TABLE_NAME,
                        null, contentValues);
                if (id > 0){
                    retUri = TrippinContract.Lines.buildTripUri(id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri... " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
//        if (selection == null) selection = "1";

        switch (match){
            case TRIP:
                rowsDeleted = mOpenHelper.getWritableDatabase().delete(TrippinContract.TripEntry.TABLE_NAME,
                        selection,selectionArgs);
                break;
            case LINES:
                rowsDeleted = mOpenHelper.getWritableDatabase().delete(TrippinContract.Lines.TABLE_NAME,
                        selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String Selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case TRIP:
                rowsUpdated = mOpenHelper.getWritableDatabase().update(TrippinContract.TripEntry.TABLE_NAME,
                        contentValues, Selection, selectionArgs);
                break;
            case LINE:
                rowsUpdated = mOpenHelper.getWritableDatabase().update(TrippinContract.Lines.TABLE_NAME,
                        contentValues, Selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LINES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(TrippinContract.Lines.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
