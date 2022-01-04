package com.example.admin.augscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class dashboardActivity extends AppCompatActivity implements View.OnClickListener  {
    private FirebaseAuth firebaseAuth;
    TextView firebasenameview;
    Toolbar toolbar;







    private LinearLayout addStudents, deleteStudents, scanStudents, viewInventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebasenameview = findViewById(R.id.firebasename);
        setToolbar();
        // this is for username to appear after login

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();

        String finaluser=users.getEmail();
        String result = finaluser.substring(0, finaluser.indexOf("@"));
        String resultemail = result.replace(".","");
        firebasenameview.setText("Bien venu "+resultemail);
//        toast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(dashboardActivity.this, users.getEmail(), Toast.LENGTH_SHORT).show();
//            }
//        });


        addStudents = (LinearLayout) findViewById(R.id.addStudents);
        deleteStudents = (LinearLayout) findViewById(R.id.deleteStudents);
        scanStudents = (LinearLayout) findViewById(R.id.scanStudents);
        viewInventory = (LinearLayout) findViewById(R.id.viewInventory);

        addStudents.setOnClickListener(this);
        deleteStudents.setOnClickListener(this);
        scanStudents.setOnClickListener(this);
        viewInventory.setOnClickListener(this);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_class_detail);

        toolbar.inflateMenu(R.menu.detail_class_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));

    }

    private boolean onMenuItemClick(MenuItem menuItem) {

        Logout();

        return true;
    }


    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.addStudents : i = new Intent(this, addStudentActivity.class); startActivity(i); break;
            case R.id.deleteStudents : i = new Intent(this, deleteStudentsActivity.class);startActivity(i); break;
            case R.id.scanStudents : i = new Intent(this, scanStudentsActivity.class);startActivity(i); break;
            case R.id.viewInventory : i = new Intent(this, addClass.class);startActivity(i); break;
            default: break;
        }
    }





    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(dashboardActivity.this,LoginActivity.class));
        Toast.makeText(dashboardActivity.this,"déconnecté avec succès", Toast.LENGTH_SHORT).show();

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
