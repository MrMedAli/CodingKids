package com.example.admin.augscan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scanfordialog extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    int MY_PERMISSIONS_REQUEST_CAMERA=0;
    DBhelper dBhelper;

    ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dBhelper = new DBhelper(this);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {



        StudentDialog.Code.setText(result.getText());
        String searchtext = StudentDialog.Code.getText().toString();

        if(databasesearch(searchtext) > 0){
            StudentDialog.name.setText(dBhelper.getStudentTable_Name(String.valueOf(databasesearch(searchtext))));
            StudentDialog.id.setText(dBhelper.getStudentTable_LName(String.valueOf(databasesearch(searchtext))));
        }else{
            Toast.makeText(this, "etudiant inexistant", Toast.LENGTH_SHORT).show();
        }

        onBackPressed();
    }

    private long databasesearch(String searchtext){

        return dBhelper.getStudentId(searchtext);

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        scannerView.setResultHandler(this);
//        scannerView.startCamera();
//    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
