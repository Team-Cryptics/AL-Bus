package com.example.punyaaachman.albus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PayActivity extends AppCompatActivity {

    Button bt1;
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        bt1 = (Button)findViewById(R.id.button2);
        tv1 = (TextView)findViewById(R.id.textView4);
        tv1 = (TextView)findViewById(R.id.textView6);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
