package com.shoo.hook.pms;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Shoo on 17-9-11.
 */

public class PackageInfoInvocationHandler implements InvocationHandler {

    private static final String METHOD_GET_PACKAGE_INFO = "getPackageInfo";

    private final String mSign;
    private final int mHashCode;
    private Field mHaveHashCodeField;
    private Field mHashCodeField;

    public PackageInfoInvocationHandler(String sign, int hashCode) {
        mSign = sign;
        mHashCode = hashCode;
    }

    @Override
    public Object invoke(Object originObj, Method method, Object[] args) throws Throwable {
        // PackageInfo getPackageInfo(String packageName, int flags, int userId)
        if (METHOD_GET_PACKAGE_INFO.equals(method.getName())) {
            int flag = (int) args[1];
            if (PackageManager.GET_SIGNATURES == flag) {
                PackageInfo packageInfo = (PackageInfo) method.invoke(originObj, args);
                if (packageInfo != null) {
                    Signature signature = new Signature(mSign);
                    Class<?> clazz = signature.getClass();

                    // Signature.mHaveHashCode
                    ensureMHaveHashCodeField(clazz);
                    mHaveHashCodeField.set(signature, true);

                    // Signature.mHashCode
                    ensureMHashCodeField(clazz);
                    mHashCodeField.set(signature, mHashCode);

                    packageInfo.signatures[0] = signature;

                    return packageInfo;
                }
            }
        }

        return null;
    }

    private void ensureMHaveHashCodeField(Class<?> clazz) throws NoSuchFieldException {
        if (mHaveHashCodeField == null) {
            mHaveHashCodeField = clazz.getDeclaredField("mHaveHashCode");
            mHaveHashCodeField.setAccessible(true);
        }
    }

    private void ensureMHashCodeField(Class<?> clazz) throws NoSuchFieldException {
        if (mHashCodeField == null) {
            mHashCodeField = clazz.getDeclaredField("mHashCode");
            mHashCodeField.setAccessible(true);
        }
    }
}
