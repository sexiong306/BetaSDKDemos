package com.sexiong306.splashscreendemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public  <T extends View> T $(int resId){
        return (T)findViewById(resId);
    }
}
