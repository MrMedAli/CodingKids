package com.example.admin.augscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class deleteStudentsActivity extends AppCompatActivity {
    public static TextView resultdeleteview;
    private FirebaseAuth firebaseAuth;
    LinearLayout deletebtn;
    FloatingActionButton scantodelete;
    DatabaseReference databaseReference;
    ImageButton returnbtn;
    DBhelper dBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dBhelper = new DBhelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_students);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://codingkids-ed591-default-rtdb.firebaseio.com/").getReference("Users");

        resultdeleteview = findViewById(R.id.barcodedelete);
        scantodelete = findViewById(R.id.buttonscandelete);
        deletebtn= findViewById(R.id.deleteItemToTheDatabasebtn);
        returnbtn= findViewById(R.id.back);

        returnbtn.setOnClickListener(v-> onBackPressed());


        scantodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitydel.class));
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClass();
            }
        });

    }
    private void deleteClass() {
        String deletebarcodevalue = resultdeleteview.getText().toString();

        if(!TextUtils.isEmpty(deletebarcodevalue)){
            long sid= dBhelper.getStudentTable_ID(deletebarcodevalue);
            dBhelper.deleteList_StudentId(sid);
            dBhelper.deleteStudent_QR(deletebarcodevalue);
            Toast.makeText(deleteStudentsActivity.this,"supprimé avec succès",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(deleteStudentsActivity.this,"\n" + "Veuillez scanner le code QR",Toast.LENGTH_SHORT).show();
        }


    }

    public void deletefrmdatabase()
    {
        String deletebarcodevalue = resultdeleteview.getText().toString();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        if(!TextUtils.isEmpty(deletebarcodevalue)){

            databaseReference.child(resultemail).child("StudentItem").child(deletebarcodevalue).removeValue();
            Toast.makeText(deleteStudentsActivity.this,"supprimé avec succès",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(deleteStudentsActivity.this,"\n" + "Veuillez scanner le code QR",Toast.LENGTH_SHORT).show();
        }
    }
}
