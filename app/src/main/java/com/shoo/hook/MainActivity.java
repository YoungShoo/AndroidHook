package com.shoo.hook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shoo.hook.pms.TPmsHooker;
import com.shoo.hook.util.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_PREFIX + "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: savedInstanceState = " + savedInstanceState);

        setContentView(R.layout.activity_main);

        TPmsHooker.test(this);

//        TAmsHooker.test(this);
    }
}
