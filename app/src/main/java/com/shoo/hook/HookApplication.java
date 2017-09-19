package com.shoo.hook;

import android.app.Application;
import android.content.Context;

import com.shoo.hook.activitythread.LaunchActivityHooker;

/**
 * Created by Shoo on 17-9-19.
 */

public class HookApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LaunchActivityHooker.hook(MainActivity.class, SecondActivity.class);
    }
}
