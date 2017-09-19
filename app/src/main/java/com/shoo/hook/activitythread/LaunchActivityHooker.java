package com.shoo.hook.activitythread;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Shoo on 17-9-19.
 */

public class LaunchActivityHooker {

    private static final String TAG = "LaunchActivityHooker";

    public static final int LAUNCH_ACTIVITY = 100;

    private static Field intentField;

    static {
        try {
            Class<?> clazz = Class.forName("android.app.ActivityThread$ActivityClientRecord");
            intentField = clazz.getDeclaredField("intent");
            intentField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            Log.e(TAG, "static initializer: get ActivityThread$ActivityClientRecord.intent failed!", e);
        }
    }

    public static void hook(final Class<?> superClass, final Class<?> dstClass) {
        ActivityThreadCallback.getInstance().addCallback(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (intentField == null) {
                    return false;
                }

                if (LAUNCH_ACTIVITY == msg.what) {
                    Object obj = msg.obj;

                    try {
                        Intent intent = (Intent) intentField.get(obj);
                        ComponentName component = intent.getComponent();
                        String originClassName = component.getClassName();

                        if (!dstClass.getName().equals(originClassName)
                                && superClass.isAssignableFrom(Class.forName(originClassName))) {
                            intent.setComponent(new ComponentName(component.getPackageName(), dstClass.getName()));
                        }
                    } catch (ClassNotFoundException | IllegalAccessException e) {
                        Log.e(TAG, "handleMessage: failed! msg = " + msg, e);
                    }
                }

                return false;
            }
        });
    }

}
