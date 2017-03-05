package com.riprap.emrox.trippin.bus;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by scott on 3/2/2017.
 */

public class DialogEvent {

    Bundle bundle;

    public DialogEvent(Bundle bundle) {
            this.bundle = bundle;
        }

    public Bundle getBundle() {
            return bundle;

    }
}
