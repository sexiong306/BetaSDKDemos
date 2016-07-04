package com.sexiong306.splashscreendemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class SplashActivity extends BaseActivity {
    Handler mHandler;
    TextView timer;
    MyCountDownTimer mMyCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        timer = $(R.id.timer);
        long delay = 6000;
        mMyCountDownTimer = new MyCountDownTimer(delay,1000);
        mMyCountDownTimer.start();

        mHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent();
                i.setClass(SplashActivity.this, new Random().nextInt()%2==0?MainActivity.class:LoginActivity.class);
                startActivity(i);
                SplashActivity.this.finish();
            }
        };
        mHandler.postDelayed(runnable,delay);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(runnable);
                mHandler.post(runnable);
            }
        });
    }

    class MyCountDownTimer extends CountDownTimer{
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        /**
         * Callback fired on regular interval.
         *
         * @param millisUntilFinished The amount of time until finished.
         */
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText("倒计时(" + millisUntilFinished/1000 + ")");
        }

        /**
         * Callback fired when the time is up.
         */
        @Override
        public void onFinish() {
            timer.setText("正在加载...");
        }
    }
}
