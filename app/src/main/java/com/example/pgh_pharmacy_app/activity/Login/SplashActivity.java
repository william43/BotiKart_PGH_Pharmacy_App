package com.example.pgh_pharmacy_app.activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pgh_pharmacy_app.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginSplashActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

}
