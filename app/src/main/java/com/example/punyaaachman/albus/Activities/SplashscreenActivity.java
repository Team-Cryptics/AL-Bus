package com.example.punyaaachman.albus.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.punyaaachman.albus.POJO.Profile;
import com.example.punyaaachman.albus.POJO.User;
import com.example.punyaaachman.albus.R;

import static com.example.punyaaachman.albus.POJO.GlobalVariables.profile;

public class SplashscreenActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        profile = new Profile(new User("abcdef@gmail.com", "abc", "def", "9876543210"));

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}
