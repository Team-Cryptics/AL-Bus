package com.example.punyaaachman.albus.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.punyaaachman.albus.POJO.GlobalVariables.profile;

public class PayActivity extends AppCompatActivity {
    TextView tvFrom, tvTo, tvAmount, tvBalance;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dref;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        firebaseDatabase=FirebaseDatabase.getInstance();
        dref=firebaseDatabase.getReference();
        auth=FirebaseAuth.getInstance();

        tvFrom = (TextView) findViewById(R.id.tvFrom);
        tvTo = (TextView) findViewById(R.id.tvTo);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvBalance = (TextView) findViewById(R.id.tvBalancePay);

        tvFrom.setText(GlobalVariables.b);
        tvTo.setText(GlobalVariables.d);

        int diff = GlobalVariables.dest-GlobalVariables.begin;    ////////// beginning and final station
        if(diff<3) {
            GlobalVariables.price=10;
        }
        else if (diff<=3) {
            GlobalVariables.price=20;
        }

        tvAmount.setText("Rs."+GlobalVariables.price);
        tvBalance.setText(Double.toString(GlobalVariables.profile.getUser().getBalance()));


    }

    public void onPayClickListener (View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnPay:

                if(profile!=null) {
                    if (GlobalVariables.profile.getUser().getBalance() >= GlobalVariables.price) {

                        GlobalVariables.profile.getUser().setBalance(GlobalVariables.profile.getUser().getBalance() - GlobalVariables.price);

                        GlobalVariables.isTicket=true;
                        dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile);

                        startActivity(new Intent(PayActivity.this, TicketActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Not sufficient balance", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                break;
        }
    }
}
