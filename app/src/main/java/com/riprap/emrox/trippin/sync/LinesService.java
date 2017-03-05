package com.riprap.emrox.trippin.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LinesService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static LinesSyncAdapter sLinesSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.e("LineSyncService", "onCreate called");
        synchronized (sSyncAdapterLock) {
            if (sLinesSyncAdapter == null) {
                sLinesSyncAdapter = new LinesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sLinesSyncAdapter.getSyncAdapterBinder();
    }
}
