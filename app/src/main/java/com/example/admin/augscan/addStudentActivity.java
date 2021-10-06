package com.example.admin.augscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addStudentActivity extends AppCompatActivity {
    private EditText studentid,studentname,studentlastname;
    private TextView studentqrcode;
    private FirebaseAuth firebaseAuth;
    public static TextView resulttextview;
    Button scanbutton, additemtodatabase;

    Toolbar toolbar;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");
        resulttextview = findViewById(R.id.barcodeview);
        additemtodatabase = findViewById(R.id.addstudentbuttontodatabase);
        scanbutton = findViewById(R.id.buttonscan);
        studentid = findViewById(R.id.identificateur);
        studentname= findViewById(R.id.nom);
        studentlastname = findViewById(R.id.prenom);
        studentqrcode= findViewById(R.id.barcodeview);

        setToolbar();




       // String result = finaluser.substring(0, finaluser.indexOf("@"));


        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        additemtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
            }
        });



    }
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.savebtn);


        title.setText("Ajouter un etudiant");
        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.INVISIBLE);
        back.setOnClickListener(v-> onBackPressed());
    }

// addding item to databse
public  void additem(){
        String studentidValue = studentid.getText().toString();
        String studentnameValue = studentname.getText().toString();
        String studentlastnameValue = studentlastname.getText().toString();
        String studentqrcodeValue = studentqrcode.getText().toString();
         final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
         String resultemail = finaluser.replace(".","");
    if (studentqrcodeValue.isEmpty()) {
        studentqrcode.setError("It's Empty");
        studentqrcode.requestFocus();
        return;
    }


    if(!TextUtils.isEmpty(studentidValue)&&!TextUtils.isEmpty(studentnameValue)&&!TextUtils.isEmpty(studentlastnameValue)){

        StudentItem studentItem = new StudentItem(studentidValue,studentnameValue,studentlastnameValue,studentqrcodeValue);
        databaseReference.child(resultemail).child("StudentItem").child(studentqrcodeValue).setValue(studentItem);
        databaseReferencecat.child(resultemail).child("ItemByCategory").child(studentnameValue).child(studentqrcodeValue).setValue(studentItem);
        studentid.setText("");
        studentqrcode.setText("");
        studentlastname.setText("");
        studentqrcode.setText("");
        Toast.makeText(addStudentActivity.this,studentidValue+" Added",Toast.LENGTH_SHORT).show();
    }
    else {
        Toast.makeText(addStudentActivity.this,"Please Fill all the fields",Toast.LENGTH_SHORT).show();
    }
}
















    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(addStudentActivity.this,LoginActivity.class));
        Toast.makeText(addStudentActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
