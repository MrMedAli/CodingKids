package com.example.admin.augscan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class scanStudentsActivity extends AppCompatActivity {
    public static EditText resultsearcheview;
    private FirebaseAuth firebaseAuth;
    ImageButton scantosearch;
    Button searchbtn;
    Adapter adapter;
    RecyclerView mrecyclerview;
    DatabaseReference mdatabaseReference;
    ImageButton returnbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_students);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(resultemail).child("StudentItem");
        resultsearcheview = findViewById(R.id.searchfield);
        scantosearch = findViewById(R.id.imageButtonsearch);
        searchbtn = findViewById(R.id.searchbtnn);
        returnbtn= findViewById(R.id.back);

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(scanStudentsActivity.this, dashboardActivity.class);
                startActivity(intent);
            }
        });


        mrecyclerview = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(manager);
        mrecyclerview.setHasFixedSize(true);


        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));


        scantosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitysearch.class));
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext = resultsearcheview.getText().toString();
                firebasesearch(searchtext);
            }
        });
    }

    public void firebasesearch(String searchtext){
        Query firebaseSearchQuery = mdatabaseReference.orderByChild("studentqrcode").startAt(searchtext).endAt(searchtext+"\uf8ff");
        FirebaseRecyclerAdapter<StudentItem, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<StudentItem, UsersViewHolder>
                (  StudentItem.class,
                        R.layout.list_layout,
                        UsersViewHolder.class,
                        firebaseSearchQuery )
        {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, StudentItem model, int position){

                viewHolder.setDetails(getApplicationContext(),model.getstudentqrcode(),model.getstudentname(),model.getstudentid(),model.getstudentlastname());
            }
        };

        mrecyclerview.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
            public UsersViewHolder(View itemView){
            super(itemView);
            mView =itemView;
        }

    public void setDetails(Context ctx,String studentqrcode, String studentname, String studentid, String studentlastname){
                TextView item_barcode = (TextView) mView.findViewById(R.id.viewstudentqrcode);
                TextView item_name = (TextView) mView.findViewById(R.id.viewstudentid);
                TextView item_category = (TextView) mView.findViewById(R.id.viewstudentname);
                TextView item_price = (TextView) mView.findViewById(R.id.viewstudentlastname);

                item_barcode.setText(studentqrcode);
                item_category.setText(studentname);
                item_name.setText(studentid);
                item_price.setText(studentlastname);
    }

    }
}
