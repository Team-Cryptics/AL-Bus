package com.example.punyaaachman.albus;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashscreenActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        (new Handler()).postDelayed(new Runnable()
        {
            @Override public void run()
            {
                startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
                finish();
            }
        },3000);
    }
}
