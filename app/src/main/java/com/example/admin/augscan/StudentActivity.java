package com.example.admin.augscan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    Toolbar toolbar;
    private  String classNanme, subjectName;
    private int position;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private DBhelper dBhelper;
    private ArrayList<Student_class> studentItems = new ArrayList<>();
    private long cid;
    private MyCalendar calendar;
    private TextView subtitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        calendar = new MyCalendar();

        dBhelper = new DBhelper(this);
        Intent intent =getIntent();
        classNanme = intent.getStringExtra("className");
        subjectName = intent.getStringExtra("subjectName");
        position = intent.getIntExtra("position", -1);
        cid =intent.getLongExtra("classID", -1);;

        loadData();

        setToolbar();
        recyclerView = findViewById(R.id.student_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new StudentAdapter(this, studentItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position ->changedStatus(position) );
        loadStatusData();


    }

    private void loadData() {
        Cursor cursor = dBhelper.getListTable(cid);
        studentItems.clear();

        while (cursor.moveToNext()){
            long lid = cursor.getLong(cursor.getColumnIndex(DBhelper.LIST_ID));
            int roll= cursor.getInt(cursor.getColumnIndex(DBhelper.STUDENT_ROLL_KEY));
            String name = cursor.getString(cursor.getColumnIndex(DBhelper.LIST_STUDENT_Name));
            studentItems.add(new Student_class(lid,name,roll));
        }
        cursor.close();
    }

    private void changedStatus(int position) {
        String status = studentItems.get(position).getStatus();

        if (status.equals("P")) status="A";
        else status="P";

        studentItems.get(position).setStatus(status);
        adapter.notifyItemChanged(position);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        subtitle = toolbar.findViewById(R.id.subtitle_toolbar);

        subtitle.setText(subjectName+" | "+ calendar.getDate());
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.savebtn);
        save.setOnClickListener(v->saveStatus());


        title.setText(classNanme);

        back.setOnClickListener(v-> onBackPressed());
        toolbar.inflateMenu(R.menu.student_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
    }

    private void saveStatus() {
        for (Student_class student_class : studentItems){
            String status = student_class.getStatus();
            if(status !="P" ){
                status = "A";
            }
            long value = dBhelper.addStatus(student_class.getlid(),cid,calendar.getDate(),status);
            Toast.makeText(StudentActivity.this,"Enregistré",Toast.LENGTH_SHORT).show();

            if (value == -1)dBhelper.updateStatus(student_class.getlid(),calendar.getDate(),status) ;

        }
    }

    private  void loadStatusData(){
        for (Student_class student_class : studentItems){
            String status = dBhelper.getStatus(student_class.getlid(),calendar.getDate());
            if(status!=null) student_class.setStatus(status);
            else student_class.setStatus("");
        }
        adapter.notifyDataSetChanged();
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.add_student){
            showAddStudentDialog();
        }else if(menuItem.getItemId() == R.id.show_Calendar){
            showCalendar();
        }else if(menuItem.getItemId() == R.id.show_attendance_sheet){
            openSheetList();
        }
        return true;
    }

    private void openSheetList() {
        long[] idArray = new long[studentItems.size()];
        String[] nameArray = new String[studentItems.size()];
        int[] rollArray = new int[studentItems.size()];

        for (int i =0 ; i < idArray.length;i++)
            idArray[i]= studentItems.get(i).getlid();
        for (int i =0 ; i < rollArray.length;i++)
            rollArray[i]= studentItems.get(i).getRoll();
        for (int i =0 ; i < nameArray.length;i++)
            nameArray[i]= studentItems.get(i).getName();
        Intent intent = new Intent(this, sheet_list.class);
        intent.putExtra("classID", cid);
        intent.putExtra("idArray",idArray);
        intent.putExtra("rollArray",rollArray);
        intent.putExtra("nameArray",nameArray);
        startActivity(intent);
    }

    private void showCalendar() {
        calendar.show(getSupportFragmentManager(),"");
        calendar.setOnCalendarOkClickListener((this::onCalenderOkClicked));
    }

    private void onCalenderOkClicked(int year, int month, int day) {
        calendar.setDate(year,month,day);
        subtitle.setText(subjectName+" | "+ calendar.getDate());
        loadStatusData();
    }

    private void showAddStudentDialog() {
        StudentDialog dialog = new StudentDialog();
        dialog.show(getSupportFragmentManager(),StudentDialog.STUDENT_ADD_DIALOG);
        dialog.setListener((roll,name,sid)->addStudent(roll,name,sid));

    }


    private void addStudent(String roll_string, String name, long sid) {
        Cursor cursor = dBhelper.getListTable(cid);
        int roll=1;
        while (cursor.moveToNext()){
            roll+=1;
        }
        cursor.close();
        long lid= dBhelper.addList(cid, sid, roll, name);
        Student_class studentItem = new Student_class(lid,name,roll);
        studentItems.add(studentItem);
        adapter.notifyItemChanged(studentItems.size()-1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                break;
            case 1:
                deleteStudent(item.getGroupId());
        }

        return super.onContextItemSelected(item);
    }

    private void deleteStudent(int position) {
        dBhelper.deleteList(studentItems.get(position).getlid());
        studentItems.remove(position);
        adapter.notifyItemRemoved(position);

    }
}