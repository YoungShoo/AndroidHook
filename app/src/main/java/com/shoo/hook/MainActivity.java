package com.shoo.hook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shoo.hook.pms.TPmsHooker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TPmsHooker.test(this);

//        TAmsHooker.test(this);
    }
}
