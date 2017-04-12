package com.example.punyaaachman.albus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((Button) findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });
    }
}
