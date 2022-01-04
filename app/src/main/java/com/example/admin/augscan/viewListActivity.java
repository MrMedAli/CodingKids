package com.example.admin.augscan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class viewListActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    RecyclerView mrecyclerview;
    DatabaseReference mdatabaseReference;
   private TextView totalnoofitem, totalnoofsum;
   private int counttotalnoofitem =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        totalnoofitem= findViewById(R.id.totalnoitem);
        totalnoofsum = findViewById(R.id.totalsum);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        mdatabaseReference = FirebaseDatabase.getInstance("https://codingkids-ed591-default-rtdb.firebaseio.com/").getReference("Users").child(resultemail).child("StudentItem");
        mrecyclerview = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(manager);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));


        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    counttotalnoofitem = (int) dataSnapshot.getChildrenCount();
                    totalnoofitem.setText(Integer.toString(counttotalnoofitem));
                }else{
                    totalnoofitem.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    mdatabaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            int sum=0;
            for(DataSnapshot ds : dataSnapshot.getChildren()){
                Map<String,Object> map = (Map<String, Object>) ds.getValue();
                Object price = map.get("studentlastname");
                int pValue = Integer.parseInt(String.valueOf(price));
                sum += pValue;
                totalnoofsum.setText(String.valueOf(sum));

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }

    //@Override
//    protected  void  onStart() {
    //      super.onStart();
//
            //      FirebaseRecyclerAdapter<StudentItem, scanStudentsActivity.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<StudentItem, scanStudentsActivity.UsersViewHolder>
    //  (  StudentItem.class,
    //                  R.layout.list_layout,
    //                  scanStudentsActivity.UsersViewHolder.class,
    //                  mdatabaseReference )
    //  {
    //      @Override
            //protected void populateViewHolder(scanStudentsActivity.UsersViewHolder viewHolder, StudentItem model, int position){
//
    //              viewHolder.setDetails(getApplicationContext(),model.getstudentqrcode(),model.getstudentname(),model.getstudentid(),model.getstudentlastname());
    //      }
            //  };
//
    //      mrecyclerview.setAdapter(firebaseRecyclerAdapter);
    //}
//    public static class UsersViewHolder extends RecyclerView.ViewHolder{
//        View mView;
//        public UsersViewHolder(View itemView){
//            super(itemView);
//            mView =itemView;
//        }
//
//        public void setDetails(Context ctx, String studentqrcode, String studentname, String studentid, String studentlastname){
//            TextView item_barcode = (TextView) mView.findViewById(R.id.viewstudentqrcode);
//            TextView item_name = (TextView) mView.findViewById(R.id.viewstudentid);
//            TextView item_category = (TextView) mView.findViewById(R.id.viewstudentname);
//            TextView item_price = (TextView) mView.findViewById(R.id.viewstudentlastname);
//
//            item_barcode.setText(studentqrcode);
//            item_category.setText(studentname);
//            item_name.setText(studentid);
//            item_price.setText(studentlastname);
//        }
//
//    }

}
