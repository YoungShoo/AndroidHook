package com.shoo.hook.activitythread;

import android.util.Log;

import com.shoo.hook.util.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Shoo on 17-9-18.
 */

public class ActivityThreadHooker {

    private static final String TAG = Constants.TAG_PREFIX + "ActivityThreadHooker";

    private static Object activityThread;

    static {
        try {
            Class<?> clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = clazz.getDeclaredMethod("currentActivityThread");
            activityThread = currentActivityThreadMethod.invoke(null);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            Log.e(TAG, "static initializer: hook ActivityThread failed!", e);
        }
    }

    public static Object getActivityThread() {
        return activityThread;
    }



}
