package com.shoo.hook.ams;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Shoo on 17-9-18.
 */

public class LaunchedFromHooker {

    private static final String TAG = "LaunchedFromHooker";

    private static Method getLaunchedFromPackageMethod;
    private static Method getActivityTokenMethod;
    private static Object activityManager = AmsHooker.getActivityManager();

    /**
     * 启动 activity 的来源包名
     *
     * @param activity
     * @return
     */
    public static String getLaunchedFrom(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return getLaunchedFromByReferrer(activity);
        }

        ensureGetLaunchedFromPackageMethod();
        ensureGetActivityTokenMethod();
        if (activityManager == null || getLaunchedFromPackageMethod == null || getActivityTokenMethod == null) {
            return null;
        }

        try {
            IBinder binder = (IBinder) getActivityTokenMethod.invoke(activity);
            return (String) getLaunchedFromPackageMethod.invoke(activityManager, binder);
        } catch (InvocationTargetException | IllegalAccessException e) {
            Log.w(TAG, "getLaunchedFrom failed!", e);
        }

        return null;
    }

    /**
     * 启动 activity 的来源包名：API 22(Android 5.1) 以之后版本可通过此方法获取
     *
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private static String getLaunchedFromByReferrer(Activity activity) {
        Uri referrer = activity.getReferrer();
        return referrer != null ? referrer.getHost() : null;
    }

    /**
     * String IActivityManager.getLaunchedFromPackage(IBinder activityToken)
     */
    private static void ensureGetLaunchedFromPackageMethod() {
        if (getLaunchedFromPackageMethod == null && activityManager != null) {
            Class<?> clazz = activityManager.getClass();
            try {
                getLaunchedFromPackageMethod = clazz.getDeclaredMethod("getLaunchedFromPackage", IBinder.class);
            } catch (NoSuchMethodException e) {
                Log.w(TAG, "ensureGetLaunchedFromPackageMethod failed!", e);
            }
        }
    }

    /**
     * IBinder Activity.getActivityToken
     */
    private static void ensureGetActivityTokenMethod() {
        if (getActivityTokenMethod == null) {
            try {
                getActivityTokenMethod = Activity.class.getDeclaredMethod("getActivityToken");
            } catch (NoSuchMethodException e) {
                Log.w(TAG, "ensureGetActivityTokenMethod failed!", e);
            }
        }
    }
}
