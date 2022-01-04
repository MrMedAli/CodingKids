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
    private EditText studentid,studentname,studentlastname,studentphone1,studentphone2;
    private TextView studentqrcode;
    private FirebaseAuth firebaseAuth;
    public static TextView resulttextview;
    Button scanbutton, additemtodatabase;
    DBhelper dBhelper;

    Toolbar toolbar;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        firebaseAuth = FirebaseAuth.getInstance();
        dBhelper = new DBhelper(this);

        databaseReference = FirebaseDatabase.getInstance("https://codingkids-ed591-default-rtdb.firebaseio.com").getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance("https://codingkids-ed591-default-rtdb.firebaseio.com").getReference("Users");
        resulttextview = findViewById(R.id.addStudentID);
        additemtodatabase = findViewById(R.id.addStudentBtn);
        scanbutton = findViewById(R.id.scanbtn);
        //studentid = findViewById(R.id.StudentID);
        studentname= findViewById(R.id.addStudentName);
        studentlastname = findViewById(R.id.addStudentLName);
        studentqrcode= findViewById(R.id.addStudentID);
        studentphone1 = findViewById(R.id.addStudentPhone);
        studentphone2= findViewById(R.id.addStudentPhone2);






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

               // String studentidValue = studentid.getText().toString();
                String studentnameValue = studentname.getText().toString();
                String studentlastnameValue = studentlastname.getText().toString();
                String studentqrcodeValue = studentqrcode.getText().toString();
                String studentphone1Value = studentphone1.getText().toString();
                String studentphone2Value = studentphone2.getText().toString();

                addStudent(studentnameValue,studentlastnameValue,studentqrcodeValue,studentphone1Value,studentphone2Value);
            }
        });



    }
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);

        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.savebtn);



        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.INVISIBLE);
        back.setOnClickListener(v-> onBackPressed());
    }

    private void addStudent(String StudentName, String StudentLName, String QrCode, String Phone1, String Phone2){
            String studentnameValue = StudentName;
            String studentlastnameValue = StudentLName;
            String studentqrcodeValue = QrCode;
            String studentphone1Value = Phone1;
            String studentphone2Value = Phone2;

            if (studentqrcodeValue.isEmpty()) {
                studentqrcode.setError("Vide");
                studentqrcode.requestFocus();
                return;
            }
            if (studentnameValue.isEmpty()) {
                studentname.setError("Vide");
                studentname.requestFocus();
                return;
            }
            if (studentlastnameValue.isEmpty()) {
                studentlastname.setError("Vide");
                studentlastname.requestFocus();
                return;
            }
            if (studentphone1Value.isEmpty()) {
                studentphone1.setError("Vide");
                studentphone1.requestFocus();
                return;
            }
            if (studentphone2Value.isEmpty()) {
                studentphone2Value = "000000000";
            }


            if(!TextUtils.isEmpty(studentnameValue)&&!TextUtils.isEmpty(studentlastnameValue)){
                long Sid = dBhelper.addStudent(StudentName,StudentLName,QrCode,Phone1,Phone2);

                studentqrcode.setText("");
                studentlastname.setText("");
                studentname.setText("");
                studentqrcode.setText("");
                studentphone1.setText("");
                studentphone2.setText("");

                Toast.makeText(addStudentActivity.this,Sid+" est ajouté",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(addStudentActivity.this,"Veuillez remplir tous les champs",Toast.LENGTH_SHORT).show();
            }



        //StudentItem studentItem = new StudentItem(Long.toString(Sid),StudentName,StudentLName,QrCode,Phone1,Phone2);
        //additem(Long.toString(Sid),StudentName,StudentLName,QrCode,Phone1,Phone2);

    }
// addding item to databse
public  void additem(String Sid, String StudentName, String StudentLName, String QrCode, String Phone1, String Phone2){
        String studentidValue = Sid;
        String studentnameValue = StudentName;
        String studentlastnameValue = StudentLName;
        String studentqrcodeValue = QrCode;
        String studentphone1Value = Phone1;
        String studentphone2Value = Phone2;
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
    if (studentqrcodeValue.isEmpty()) {
        studentqrcode.setError("Vide");
        studentqrcode.requestFocus();
        return;
    }
    if (studentnameValue.isEmpty()) {
        studentname.setError("Vide");
        studentname.requestFocus();
        return;
    }
    if (studentlastnameValue.isEmpty()) {
        studentlastname.setError("Vide");
        studentlastname.requestFocus();
        return;
    }
    if (studentphone1Value.isEmpty()) {
        studentphone1.setError("Vide");
        studentphone1.requestFocus();
        return;
    }
    if (studentphone2Value.isEmpty()) {
        studentphone2Value = "000000000";
    }


    if(!TextUtils.isEmpty(studentidValue)&&!TextUtils.isEmpty(studentnameValue)&&!TextUtils.isEmpty(studentlastnameValue)){

        studentid.setText("");
        studentqrcode.setText("");
        studentlastname.setText("");
        studentname.setText("");
        studentqrcode.setText("");
        studentphone1.setText("");
        studentphone2.setText("");

        Toast.makeText(addStudentActivity.this,studentidValue+" est ajouté",Toast.LENGTH_SHORT).show();
    }
    else {
        Toast.makeText(addStudentActivity.this,"Veuillez remplir tous les champs",Toast.LENGTH_SHORT).show();
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
