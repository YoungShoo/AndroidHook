package com.shoo.hook.ams;

import android.app.Activity;
import android.util.Log;

/**
 * Created by Shoo on 17-9-11.
 */

public class TAmsHooker {

    private static final String TAG = "TAmsHooker";

    public static void test(Activity activity) {
        /*
        TAmsHooker: launchedFrom = com.meizu.flyme.launcher
         */

        String launchedFrom = LaunchedFromHooker.getLaunchedFrom(activity);
        Log.d(TAG, "launchedFrom = " + launchedFrom);
    }
}
