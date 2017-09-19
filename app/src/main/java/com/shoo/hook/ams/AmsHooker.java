package com.shoo.hook.ams;

import android.util.Log;

import com.shoo.hook.AbsHooker;
import com.shoo.hook.DefaultInvocationHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by Shoo on 17-9-11.
 */

public class AmsHooker extends AbsHooker {

    private static final String TAG = "AmsHooker";

    private static Object activityManager;

    static {
        try {
            // Singleton<IActivityManager> ActivityManagerNative.gDefault
            Class clazz = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = clazz.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            // IActivityManager Singleton.mInstance
//            clazz = gDefault.getClass();  // invalid Class
            clazz = Class.forName("android.util.Singleton");
            Field mInstanceField = clazz.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            activityManager = mInstanceField.get(gDefault);

            Object activityManagerProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{Class.forName("android.app.IActivityManager")},
                    new DefaultInvocationHandler(activityManager));
            mInstanceField.set(gDefault, activityManagerProxy);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            Log.w(TAG, "hook failed!", e);
        }
    }

    public static Object getActivityManager() {
        return activityManager;
    }
}
