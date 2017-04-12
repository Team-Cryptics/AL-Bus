package com.example.punyaaachman.albus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.punyaaachman.albus.Fragments.ProfileFragment;
import com.example.punyaaachman.albus.Fragments.ScanFragment;
import com.example.punyaaachman.albus.Fragments.WalletFragment;

public class HomeActivity extends AppCompatActivity
{
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_scan);
    }

}
