package com.espaciounido.unadcalendar.views;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;

import com.espaciounido.unadcalendar.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_splash)
public class SplashActivity extends RoboActivity {

    private final int SPLASH_TIME = 3000;//time in mills
    private final CountDownTimer splashTimer = new CountDownTimer(SPLASH_TIME,1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashTimer.cancel();
    }


}
