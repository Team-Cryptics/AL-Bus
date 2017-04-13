package com.example.punyaaachman.albus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punyaaachman.albus.Fragments.ProfileFragment;
import com.example.punyaaachman.albus.Fragments.WalletFragment;
import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private AlertDialog alertDialog;

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
                    setAlertDialog();
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
    void setAlertDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Use flashlight");
        alertDialog.setMessage("Do you wanna use flashlight");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        GlobalVariables.useFlash = true;
                        dialog.dismiss();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new BarcodeCaptureFragment()).commit();
//                        Intent intent = new Intent(HomeActivity.this, BarcodeCaptureFragment.class);
//                        intent.putExtra(BarcodeCaptureFragment.UseFlash, useFlash);
//                        startActivityForResult(intent, RC_BARCODE_CAPTURE);
                        //SEND INTENT
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        GlobalVariables.useFlash = false;
                        dialog.dismiss();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, new BarcodeCaptureFragment()).commit();
//                        Intent intent = new Intent(HomeActivity.this, BarcodeCaptureFragment.class);
//                        intent.putExtra(BarcodeCaptureFragment.UseFlash, useFlash);
//                        startActivityForResult(intent, RC_BARCODE_CAPTURE);
                    }
                });

        alertDialog.show();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureFragment.BarcodeObject);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                    //  startActivity(new Intent(MainActivity.this,SelectStationActivity.class));
                    //                statusMessage.setText(R.string.barcode_success);
                    //              barcodeValue.setText(barcode.displayValue);
                    Log.i("TAG",barcode.displayValue);

                    Intent intent = new Intent(HomeActivity.this,SelectStationActivity.class);
                    intent.putExtra("MSG",barcode.displayValue);
                    startActivity(intent);

                } else {
                    //            statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //      statusMessage.setText(String.format(getString(R.string.barcode_error),
                CommonStatusCodes.getStatusCodeString(resultCode);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
