package com.example.punyaaachman.albus.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.punyaaachman.albus.R;

public class FeedbackActivity extends AppCompatActivity {

    LinearLayout llFeedback;
    Button btFeedback;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        
        llFeedback = (LinearLayout) findViewById(R.id.layout_feedback);
        btFeedback= (Button) findViewById(R.id.button_feedback);
        
        llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedbackActivity.this, "Your response has been recorded", Toast.LENGTH_SHORT).show();
            }
        });
        
        btFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedbackActivity.this, "Your comments have been recorded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
