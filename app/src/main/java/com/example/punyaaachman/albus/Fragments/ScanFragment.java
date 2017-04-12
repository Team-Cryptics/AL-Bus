package com.example.punyaaachman.albus.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.punyaaachman.albus.BarcodeCaptureActivity;
import com.example.punyaaachman.albus.R;
import com.example.punyaaachman.albus.SelectStationActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Punya Aachman on 12-Apr-17.
 */

public class ScanFragment extends Fragment
{
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    boolean useFlash;
    private AlertDialog alertDialog;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
        setAlertDialog();
        return rootView;
    }

    void setAlertDialog() {
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Use flashlight");
        alertDialog.setMessage("Do you wanna use flashlight");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        useFlash = true;
                        dialog.dismiss();
                        Intent intent = new Intent(getContext(), BarcodeCaptureActivity.class);
                        intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash);
                        startActivityForResult(intent, RC_BARCODE_CAPTURE);
                        //SEND INTENT
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        useFlash = false;
                        dialog.dismiss();
                        Intent intent = new Intent(getContext(), BarcodeCaptureActivity.class);
                        intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash);
                        startActivityForResult(intent, RC_BARCODE_CAPTURE);


                    }
                });

        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                    //  startActivity(new Intent(MainActivity.this,SelectStationActivity.class));
    //                statusMessage.setText(R.string.barcode_success);
      //              barcodeValue.setText(barcode.displayValue);
                    Log.i("TAG",barcode.displayValue);

                    Intent intent = new Intent(getContext(),SelectStationActivity.class);
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
