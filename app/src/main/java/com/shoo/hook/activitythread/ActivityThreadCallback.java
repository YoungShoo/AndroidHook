package com.shoo.hook.activitythread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.shoo.hook.util.Constants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoo on 17-9-18.
 */

public class ActivityThreadCallback implements Handler.Callback {

    private static final String TAG = Constants.TAG_PREFIX + "ActivityThreadCallback";

    private static Handler mH;

    static {
        Object activityThread = ActivityThreadHooker.getActivityThread();
        if (activityThread != null) {
            try {
                // Handler ActivityThread.mH
                Field mHField = activityThread.getClass().getDeclaredField("mH");
                mHField.setAccessible(true);
                mH = (Handler) mHField.get(activityThread);

                // Handler.Callback Handler.mCallback
                Field mCallbackField = Handler.class.getDeclaredField("mCallback");
                mCallbackField.setAccessible(true);
                mCallbackField.set(mH, ActivityThreadCallback.getInstance());

            } catch (NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "static initializer: hook ActivityThread.mH.mCallback failed!", e);
            }
        }
    }

    private static volatile ActivityThreadCallback sInstance;

    public static ActivityThreadCallback getInstance() {
        if (sInstance == null) {
            synchronized (ActivityThreadCallback.class) {
                if (sInstance == null) {
                    sInstance = new ActivityThreadCallback(mH);
                }
            }
        }

        return sInstance;
    }

    private final Handler mDefaultHandler;
    private List<Handler.Callback> mCallbacks;

    public ActivityThreadCallback(Handler handler) {
        mDefaultHandler = handler;
        mCallbacks = new ArrayList<>();
    }

    @Override
    public boolean handleMessage(Message msg) {
        for (Handler.Callback callback : mCallbacks) {
            if (callback.handleMessage(msg)) {
                return true;
            }
        }

        if (mDefaultHandler != null) {
            mDefaultHandler.handleMessage(msg);
            return true;
        }

        return false;
    }

    public void addCallback(Handler.Callback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallbacks.add(0, callback);
    }

    public void removeCallback(Handler.Callback callback) {
        mCallbacks.remove(callback);
    }
}
