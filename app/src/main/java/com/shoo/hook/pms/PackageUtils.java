package com.shoo.hook.pms;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.shoo.hook.util.MD5Utils;

/**
 * Created by Shoo on 17-9-11.
 */

public class PackageUtils {

//    private static final String PACKAGE_NAME = "com.shoo.hook";

    public static String getSignCharsString(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                return packageInfo.signatures[0].toCharsString();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getSignHashCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                return packageInfo.signatures[0].hashCode();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static String getSignHexString(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                byte[] bytes = packageInfo.signatures[0].toByteArray();
                return MD5Utils.byteArrayToHexString(MD5Utils.MD5Encode(bytes));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
