package com.example.punyaaachman.albus.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.POJO.Profile;
import com.example.punyaaachman.albus.POJO.Trips;
import com.example.punyaaachman.albus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import static com.example.punyaaachman.albus.POJO.GlobalVariables.profile;

public class TicketActivity extends AppCompatActivity {
    TextView tvFrom, tvTo, tvAmount;
    Button btScreenshot;

    FirebaseDatabase firebase;
    DatabaseReference dref;
    private FirebaseAuth mAuth;
    Trips trips;
    ArrayList<Trips> tripsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        btScreenshot = (Button) findViewById(R.id.bt_ss);
        btScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();
            }
        });

        tvFrom = (TextView) findViewById(R.id.tvFromR);
        tvTo = (TextView) findViewById(R.id.tvToR);
        tvAmount = (TextView) findViewById(R.id.tvAmountR);

        firebase =FirebaseDatabase.getInstance();
        dref = firebase.getReference();

        mAuth = FirebaseAuth.getInstance();


        dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                profile = dataSnapshot.getValue(Profile.class);
                trips = new Trips(GlobalVariables.b,GlobalVariables.d,(GlobalVariables.price));
                tripsList = profile.getTripsList();

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tripsList.add(trips);
        profile.setTripsList(tripsList);


        dref.child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(profile);

        tvFrom.setText(GlobalVariables.b);
        tvTo.setText(GlobalVariables.d);
        tvAmount.setText(String.valueOf(GlobalVariables.price));

        startService(new Intent(TicketActivity.this,MapService.class));

    }

    public void startService(View view) {
        startService(new Intent(getBaseContext(), MapService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MapService.class));
    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File

                    (mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }
    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

}
