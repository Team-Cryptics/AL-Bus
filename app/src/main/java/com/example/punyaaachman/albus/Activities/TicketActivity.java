package com.example.punyaaachman.albus.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.POJO.Trips;
import com.example.punyaaachman.albus.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class TicketActivity extends AppCompatActivity {
    TextView tvFrom, tvTo, tvAmount;
    Button btScreenshot,btProceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        btScreenshot = (Button) findViewById(R.id.bt_ss);
        btProceed= (Button) findViewById(R.id.bt_proceed);
        btScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();
            }
        });
        btProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });

        tvFrom = (TextView) findViewById(R.id.tvFromR);
        tvTo = (TextView) findViewById(R.id.tvToR);
        tvAmount = (TextView) findViewById(R.id.tvAmountR);


        tvFrom.setText(GlobalVariables.b);
        tvTo.setText(GlobalVariables.d);
        tvAmount.setText(String.valueOf("Rs. " + GlobalVariables.price));

//        Intent serviceIntent = new Intent(this,DefaulterService.class);
//        startService(serviceIntent);

       // startService(new Intent(TicketActivity.this, MapService.class));

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
