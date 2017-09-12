package com.shoo.hook;

import android.app.Application;
import android.content.Context;

import com.shoo.hook.ams.AmsHooker;
import com.shoo.hook.pms.PmsHooker;

/**
 * Created by Shoo on 17-9-11.
 */

public class HookApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PmsHooker.hook();
        AmsHooker.hook();
    }
}
