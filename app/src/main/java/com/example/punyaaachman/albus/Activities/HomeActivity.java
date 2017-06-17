package com.example.punyaaachman.albus.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.punyaaachman.albus.POJO.Profile;
import com.example.punyaaachman.albus.R;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.punyaaachman.albus.POJO.GlobalVariables.profile;


public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    TextView tvName, tvNumber, tvProfileBalanced, tvImage;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private AlertDialog alertDialog;
    FirebaseDatabase firebase;
    DatabaseReference dref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GlobalVariables.isTicket=false; //To change balance in further activities and not call on event value listenergit
        firebase = FirebaseDatabase.getInstance();
        dref = firebase.getReference();

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_profile);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            firebase = FirebaseDatabase.getInstance();
            dref = firebase.getReference();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    fragmentTransaction.replace(R.id.content, new ProfileFragment(), "p").commit();
                    GlobalVariables.isProfile = true;

                    //Cant use this because at app startup, it will be null
                    //         dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile);

                    dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            profile = dataSnapshot.getValue(Profile.class);

//                                Log.i("TAG", profile.getUser().getEmail());

                            if (GlobalVariables.isProfile&&!GlobalVariables.isTicket) {
                                tvImage = (TextView) findViewById(R.id.tvImage);
                                tvName = (TextView) findViewById(R.id.tvName);
                                tvNumber = (TextView) findViewById(R.id.tvNumber);
                                tvProfileBalanced = (TextView) findViewById(R.id.tvBalanceProfile);

//                                    GlobalVariables.isTicket=false;
//                                    if(GlobalVariables.trip!=null&&GlobalVariables.isTicket==false) {
//                                        GlobalVariables.profile.getTripsList().add(GlobalVariables.trip);
//                                        Log.i("TAG","Profile "+GlobalVariables.profile.getTripsList().get(0).getStart());
//                                    }

                                tvName.setText(profile.getUser().getFirstName() + " " + profile.getUser().getLastName());
                                tvNumber.setText(profile.getUser().getNumber());
                                tvProfileBalanced.setText(Double.toString(profile.getUser().getBalance()));
                                tvImage.setText(Character.toString(profile.getUser().getFirstName().charAt(0)) + Character.toString(profile.getUser().getLastName().charAt(0)));

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    return true;
                case R.id.navigation_scan:
                    GlobalVariables.isProfile = false;
                    setAlertDialog();
                    if (profile != null) {
                        dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                profile = dataSnapshot.getValue(Profile.class);

                                Log.i("TAG", profile.getUser().getEmail());

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    return true;
                case R.id.navigation_wallet:
                    fragmentTransaction.replace(R.id.content, new WalletFragment()).commit();
                    GlobalVariables.isProfile = false;

                    if (profile != null) {
                      //  dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile);
                        dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                profile = dataSnapshot.getValue(Profile.class);

                                if (!GlobalVariables.isProfile&&!GlobalVariables.isTicket) {
                                    Log.i("TAG", profile.getUser().getEmail());
                                    ((TextView) findViewById(R.id.tvBalanceWallet)).setText("Rs. " + profile.getUser().getBalance());
                                    findViewById(R.id.btnAddMoney).setVisibility(View.VISIBLE);
                                    findViewById(R.id.pbAddMoney).setVisibility(View.GONE);

                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                profile = dataSnapshot.getValue(Profile.class);

                                if (!GlobalVariables.isProfile&&!GlobalVariables.isTicket) {
                                    Log.i("TAG", profile.getUser().getEmail());
                                    ((TextView) findViewById(R.id.tvBalanceWallet)).setText("Rs. " + profile.getUser().getBalance());
                                    findViewById(R.id.btnAddMoney).setVisibility(View.VISIBLE);
                                    findViewById(R.id.pbAddMoney).setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                profile = dataSnapshot.getValue(Profile.class);
//
//                                if (!GlobalVariables.isProfile&&!GlobalVariables.isTicket) {
//                                    Log.i("TAG", profile.getUser().getEmail());
//                                    ((TextView) findViewById(R.id.tvBalanceWallet)).setText("Rs. " + profile.getUser().getBalance());
//                                    findViewById(R.id.btnAddMoney).setVisibility(View.VISIBLE);
//                                    findViewById(R.id.pbAddMoney).setVisibility(View.GONE);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
                    }
                    return true;
            }
            return false;
        }

    };


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
            case R.id.cvCloud:
                startActivity(new Intent(HomeActivity.this, CloudActivity.class ));
                break;
        //    case R.id.cvFeedback :
         //       startActivity(new Intent(HomeActivity.this,FeedbackActivity.class));
         //
            //       break;
            case R.id.cvSignOut:
                mAuth.signOut();
                mAuth.addAuthStateListener(mAuthStateListener);
                break;

            case R.id.btnAddMoney:
                if (((EditText) findViewById(R.id.etMoney)).getText().toString().isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Invalid Amount", Toast.LENGTH_SHORT).show();
                } else {
                    dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile);
                    findViewById(R.id.btnAddMoney).setVisibility(View.GONE);
                    findViewById(R.id.pbAddMoney).setVisibility(View.VISIBLE);
                    //////////////////////////////////////
                    //add money
                    addMoney();

                    findViewById(R.id.btnAddMoney).setVisibility(View.VISIBLE);
                    findViewById(R.id.pbAddMoney).setVisibility(View.GONE);
                    Toast.makeText(HomeActivity.this, "Money Successfully Added", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void addMoney() {
        //  profile.getUser().setBalance(profile.getUser().getBalance() + Double.valueOf(((EditText) findViewById(R.id.etMoney)).getText().toString()));

        double a = profile.getUser().getBalance();
        String b = ((EditText) findViewById(R.id.etMoney)).getText().toString();
        double c = Double.parseDouble(b);
        double d = a + c;
        profile.getUser().setBalance(d);
        ((EditText) findViewById(R.id.etMoney)).setText(" ");
        ((TextView) findViewById(R.id.tvBalanceWallet)).setText(Double.toString(profile.getUser().getBalance()));
        dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureFragment.BarcodeObject);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                    //                statusMessage.setText(R.string.barcode_success);
                    //              barcodeValue.setText(barcode.displayValue);
                    Log.i("TAG", barcode.displayValue);

                    Intent intent = new Intent(HomeActivity.this, SelectStationActivity.class);
                    intent.putExtra("MSG", barcode.displayValue);
                    startActivity(intent);

                } else {
                    //            statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //      statusMessage.setText(String.format(getString(R.string.barcode_error),
                CommonStatusCodes.getStatusCodeString(resultCode);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

//    public class Work extends AsyncTask<Voi>

}
