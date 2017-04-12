package com.example.punyaaachman.albus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.punyaaachman.albus.Fragments.ProfileFragment;
import com.example.punyaaachman.albus.Fragments.ScanFragment;
import com.example.punyaaachman.albus.Fragments.WalletFragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId())
            {
                case R.id.navigation_profile:
                    fragmentTransaction.replace(R.id.content, new ProfileFragment()).commit();
                    return true;
                case R.id.navigation_scan:
                    fragmentTransaction.replace(R.id.content, new ScanFragment()).commit();
                    return true;
                case R.id.navigation_wallet:
                    fragmentTransaction.replace(R.id.content, new WalletFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_scan);
    }

    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.cvCards:
                startActivity(new Intent(HomeActivity.this, CardsActivity.class));
                break;
            case R.id.cvTrips:
                startActivity(new Intent(HomeActivity.this, TripsActivity.class));
                break;
            case R.id.cvHelp:
                startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                break;
            case R.id.cvSignOut:
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.btnAddMoney:
                if (((EditText) findViewById(R.id.etMoney)).getText().toString().isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Invalid Amount", Toast.LENGTH_SHORT).show();
                } else {
                    findViewById(R.id.btnAddMoney).setVisibility(View.GONE);
                    findViewById(R.id.pbAddMoney).setVisibility(View.VISIBLE);

                    //add money
                    (new Handler()).postDelayed(new Runnable()
                    {
                        @Override public void run()
                        {
                            findViewById(R.id.btnAddMoney).setVisibility(View.VISIBLE);
                            findViewById(R.id.pbAddMoney).setVisibility(View.GONE);
                            Toast.makeText(HomeActivity.this, "Money Successfully Added", Toast.LENGTH_SHORT).show();
                        }
                    },1000);
                }
                break;
        }
    }
}
