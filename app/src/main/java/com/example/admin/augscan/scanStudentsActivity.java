package com.example.admin.augscan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class scanStudentsActivity extends AppCompatActivity {
    public static EditText resultsearcheview;
    private FirebaseAuth firebaseAuth;
    FloatingActionButton scantosearch;
    FloatingActionButton searchbtn;
    private Global_StudentAdabpter adapter;
    RecyclerView recyclerView;
    DatabaseReference mdatabaseReference;
    BottomAppBar bottomAppBar;
    Toolbar toolbar;
    FloatingActionButton returnbtn;
    ArrayList<StudentItem> studentItems = new ArrayList<>();
    DBhelper dBhelper;
    private  RecyclerView.LayoutManager layoutManager;
    Global_StudentAdabpter global_studentAdabpter;
    public String stuName, stuLName, mobileNo, mobileNo2, Qrcode;
    public long id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_students);
        resultsearcheview = findViewById(R.id.searchfield);
        scantosearch = findViewById(R.id.scanStudentbtn);
        searchbtn = findViewById(R.id.searchbtn);
        returnbtn = findViewById(R.id.rtrnbtn);

        dBhelper = new DBhelper(this);

        loadData();
        scantosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitysearch.class));
            }
        });


        returnbtn.setOnClickListener(v-> onBackPressed());


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        adapter= new Global_StudentAdabpter(this, studentItems);
        recyclerView.setAdapter(adapter);
        //adapter.setOnItemClickListener(position ->changedStatus(position) );



        //global_studentAdabpter= new Global_StudentAdabpter(this, studentItems);
        //recyclerView.setAdapter(global_studentAdabpter);





        adapter.setOnItemClickListener(position -> gotoItemActivity(position));


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext = resultsearcheview.getText().toString();
                databasesearch(searchtext);
                // firebasesearch(searchtext);
            }
        });



        //firebaseAuth = FirebaseAuth.getInstance();
        //final FirebaseUser users = firebaseAuth.getCurrentUser();
        //String finaluser=users.getEmail();
        // String resultemail = finaluser.replace(".","");
        //mdatabaseReference = FirebaseDatabase.getInstance("https://codingkids-ed591-default-rtdb.firebaseio.com/").getReference("Users").child(resultemail).child("StudentItem");





        //global_studentAdabpter.setOnItemClickListener(position -> gotoItemActivity(position));



        setToolbar();
        setBottomAppBar();
    }

    private void databasesearch(String searchtext) {
        if (searchtext.isEmpty())
        {
            loadData();
        }
        else {
            Cursor cursor = dBhelper.getStudentQR(searchtext);
            studentItems.clear();
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(DBhelper.STUDENT_ID));
                String studentName = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_NAME_KEY));
                String studentLName = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_LASTNAME_KEY));
                String codeQr = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_QR_CODE));
                String phone1 = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_PHONE_NUMBER_P));
                String phone2 = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_PHONE_NUMBER_M));

                studentItems.add(new StudentItem(id,studentName,studentLName,codeQr,phone1,phone2));
            }
            adapter.notifyDataSetChanged();
        }

    }

    private void loadData() {
        Cursor cursor = dBhelper.getStudentTable();

        studentItems.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBhelper.STUDENT_ID));
            String studentName = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_NAME_KEY));
            String studentLName = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_LASTNAME_KEY));
            String codeQr = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_QR_CODE));
            String phone1 = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_PHONE_NUMBER_P));
            String phone2 = cursor.getString(cursor.getColumnIndex(DBhelper.STUDENT_PHONE_NUMBER_M));

            studentItems.add(new StudentItem(id,studentName,studentLName,codeQr,phone1,phone2));
        }


    }
    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, modifie_student_Dialog.class);

        intent.putExtra("studentname", studentItems.get(position).getstudentname());
        intent.putExtra("studentlastname", studentItems.get(position).getstudentlastname());
        intent.putExtra("studentqrcode", studentItems.get(position).getstudentqrcode());
        intent.putExtra("phone1", studentItems.get(position).getPhone1());
        intent.putExtra("phone2", studentItems.get(position).getPhone2());
        intent.putExtra("postion",position);
        intent.putExtra("studentid",studentItems.get(position).getstudentid());
        startActivity(intent);
    }



    //public void firebasesearch(String searchtext){
    //Query firebaseSearchQuery = mdatabaseReference.orderByChild("studentqrcode").startAt(searchtext).endAt(searchtext+"\uf8ff");
    //FirebaseRecyclerAdapter<StudentItem, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<StudentItem, UsersViewHolder>
    //             (  StudentItem.class,
    //                    R.layout.list_layout,
    //                    UsersViewHolder.class,
    //                    firebaseSearchQuery )
    {
        //        @Override
        //        protected void populateViewHolder(UsersViewHolder viewHolder, StudentItem model, int position){

        //            viewHolder.setDetails(getApplicationContext(),model.getstudentqrcode(),model.getstudentname(),model.getstudentid(),model.getstudentlastname());
        //        }
        //    };

        //     mrecyclerview.setAdapter(firebaseRecyclerAdapter);
        //}

        //public static class UsersViewHolder extends RecyclerView.ViewHolder{
        //  View mView;
        //    public UsersViewHolder(View itemView){
        //    super(itemView);
        //   mView =itemView;
        // }

        // public void setDetails(Context ctx,String studentqrcode, String studentname, String studentid, String studentlastname){
        //            TextView item_barcode = (TextView) mView.findViewById(R.id.viewstudentqrcode);
        //           TextView item_name = (TextView) mView.findViewById(R.id.viewstudentid);
        //           TextView item_category = (TextView) mView.findViewById(R.id.viewstudentname);
        //           TextView item_price = (TextView) mView.findViewById(R.id.viewstudentlastname);
//
        //          item_barcode.setText(studentqrcode);
        //            item_category.setText(studentname);
        //         item_name.setText(studentid);
        //         item_price.setText(studentlastname);
        // }

    }
    private void setBottomAppBar() {
        bottomAppBar = findViewById(R.id.bottomAppBar);

    }
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_class_detail);


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteStudent(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }
    private void showUpdateDialog(int position) {

        stuName = studentItems.get(position).getstudentname();
        stuLName = studentItems.get(position).getstudentlastname();
        mobileNo = studentItems.get(position).getPhone1();
        mobileNo2 = studentItems.get(position).getPhone2();
        Qrcode = studentItems.get(position).getstudentqrcode();
        id = studentItems.get(position).getstudentid();

        Student_edit dialog =new Student_edit();
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme);
        dialog.show(getSupportFragmentManager(),Student_edit.STUDENT_UPDATE_DIALOG);
        dialog.setListener((studentName,studentLName,studentphone1,studentphone2)->updateStudent(position,studentName,studentLName,studentphone1,studentphone2));

    }

    private void updateStudent(int position, String studentName,String studentLName,String studentphone1,String studentphone2) {
        if (studentName.isEmpty())
        {
            studentName=studentItems.get(position).getstudentname();
        }
        if (studentLName.isEmpty())
        {
            studentLName=studentItems.get(position).getstudentlastname();
        }
        if (studentphone1.isEmpty())
        {
            studentphone1=studentItems.get(position).getPhone1();
        }
        if (studentphone2.isEmpty())
        {
            studentphone2=studentItems.get(position).getPhone2();
        }
        dBhelper.updateStudent(studentItems.get(position).getstudentid(),studentName,studentLName,studentphone1,studentphone2);
        dBhelper.updateList_studentname(studentItems.get(position).getstudentid(),studentName+" "+studentLName);
        studentItems.get(position).setName(studentName);
        studentItems.get(position).setLName(studentLName);
        studentItems.get(position).setPhone1(studentphone1);
        studentItems.get(position).setPhone2(studentphone2);
        Toast.makeText(scanStudentsActivity.this, "Modofié avec succés",Toast.LENGTH_SHORT).show();

        adapter.notifyItemChanged(position);

    }

    private void deleteStudent(int position) {
        dBhelper.deleteStudent(studentItems.get(position).getstudentid());
        studentItems.remove(position);

        adapter.notifyItemRemoved(position);
    }


}
