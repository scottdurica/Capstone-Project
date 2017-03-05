package com.riprap.emrox.trippin.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.riprap.emrox.trippin.R;
import com.riprap.emrox.trippin.api.RoutesAPI;
import com.riprap.emrox.trippin.api.RoutesResponse;
import com.riprap.emrox.trippin.api.model.RouteResults;
import com.riprap.emrox.trippin.data.TrippinContract;

import java.util.ArrayList;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.riprap.emrox.trippin.adapters.MyViewPagerAdapter.TAG;

/**
 * Created by scott on 2/18/2017.
 */

public class LinesSyncAdapter extends AbstractThreadedSyncAdapter implements Callback<RoutesResponse> {

    Context context;
    //sync_interval in seconds 60 seconds * 60 = 1 hour * 24 = 1 day
    public static final int SYNC_INTERVAL = 60 * 60 * 24;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private static final int WEATHER_NOTIFICATION_ID = 3004;
    private ArrayList<RouteResults.Line> lineList = new ArrayList<>();

    public LinesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.e(TAG, "onPerformSync called");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RoutesAPI.ROUTES_ENDPOINT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RoutesAPI routesAPI = retrofit.create(RoutesAPI.class);
        Call<RoutesResponse> call = routesAPI.getRoutes(context.getString(R.string.MBTA_api_key));
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<RoutesResponse> call, Response<RoutesResponse> response) {
        Log.e(TAG, "onResponse called");
        if (lineList != null && lineList.size() > 0) {
            lineList.clear();
        }
        RoutesResponse routesResponse = response.body();

        if (routesResponse != null && routesResponse.getDataList() != null) {
            ArrayList<RouteResults> routesList = routesResponse.getDataList();
            for (RouteResults m : routesList) {
                for (RouteResults.Route r : m.getRoute()) {
                    //each item in the 'route' JSON array
                    RouteResults.Line line = new RouteResults.Line(r.getRoute_id(), r.getRoute_name(), m.getRoute_type(), m.getMode_name());


                    lineList.add(line);
//                    Log.e("Valie of routeType: ", line.getRouteType());

//                    Log.e("Name ", " "+ line.getRoute_name());
//                    Log.e("ID ", " "+ line.getRoute_id());
//                    Log.e("Route Type ", " "+ line.getRouteType());
//                    Log.e("MOde Name ", " "+ line.getModeName());
                }
            }
            Vector<ContentValues> cVVector = new Vector<ContentValues>(lineList.size());
            for (RouteResults.Line line : lineList) {
                ContentValues values = new ContentValues();
                values.put(TrippinContract.Lines.COLUMN_ROUTE_NAME, line.getRoute_name());
                values.put(TrippinContract.Lines.COLUMN_ROUTE_ID, line.getRoute_id());
                values.put(TrippinContract.Lines.COLUMN_ROUTE_TYPE, line.getRouteType());
                values.put(TrippinContract.Lines.COLUMN_MODE_NAME, line.getModeName());

                cVVector.add(values);

            }

            int inserted = 0;

            if (cVVector.size() > 0) {
                // delete old data so we don't build up an endless history
                //passing in null for selection will deleta all data from table
                getContext().getContentResolver().delete(TrippinContract.Lines.CONTENT_URI,null,null);
                // add new data to database
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
               int result = getContext().getContentResolver().bulkInsert(TrippinContract.Lines.CONTENT_URI, cvArray);
                Log.e(TAG, " db insertion: " + result);
            }
        }
    }
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }
    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (accountManager.getPassword(newAccount)==null) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            ContentResolver.setIsSyncable(newAccount,context.getString(R.string.content_authority),1);
            onAccountCreated(newAccount, context);

        }
        return newAccount;
    }
    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }
    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        LinesSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
//        LinesSyncAdapter.configurePeriodicSync(context, 1, 1);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started if no initial data is in database.
         */
        Cursor c = context.getContentResolver().query(TrippinContract.Lines.CONTENT_URI,
                null,
                null,
                null,
                null,
                null);
        if (c == null || !c.moveToNext()){
            syncImmediately(context);
            Log.e(TAG, "SYNC IMMEDIATELY");
        }

    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
    @Override
    public void onFailure(Call<RoutesResponse> call, Throwable t) {

    }
}
