package com.shoo.hook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.shoo.hook.util.Constants;

/**
 * Created by Shoo on 17-9-19.
 */

public class SecondActivity extends Activity {

    private static final String TAG = Constants.TAG_PREFIX + "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: savedInstanceState = " + savedInstanceState);
    }
}
