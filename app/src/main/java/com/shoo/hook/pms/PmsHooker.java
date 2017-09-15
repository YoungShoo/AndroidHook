package com.shoo.hook.pms;

import com.shoo.hook.DefaultInvocationHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by Shoo on 17-9-8.
 */

public class PmsHooker {

    private static DefaultInvocationHandler sInvocationHandler;

    static {
        try {
            // get ActivityThread.sPackageManager
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            Object sPackageManager = sPackageManagerField.get(null);

            // reset ActivityThread.sPackageManager
            sInvocationHandler = new DefaultInvocationHandler(sPackageManager);
            Object packageManagerProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{Class.forName("android.content.pm.IPackageManager")},
                    sInvocationHandler);
            sPackageManagerField.set(null, packageManagerProxy);

        } catch (ClassNotFoundException
                | IllegalAccessException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void hookPackageInfo(String sign, int hashCode) {
        if (sInvocationHandler != null) {
            sInvocationHandler.addHandler(new PackageInfoInvocationHandler(sign, hashCode));
        }
    }
}
