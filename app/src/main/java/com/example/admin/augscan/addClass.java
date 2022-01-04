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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class addClass extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems = new ArrayList<>();
    BottomAppBar bottomAppBar;
    Toolbar toolbar;
    FloatingActionButton returnbtn;
    DBhelper dBhelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        dBhelper = new DBhelper(this);

        fab= findViewById(R.id.fab_main);
        fab.setOnClickListener(v->showDialog());
        returnbtn= findViewById(R.id.back);
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(addClass.this, dashboardActivity.class);
                startActivity(intent);
            }
        });

        loadData();

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        classAdapter= new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classAdapter);

        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));


        setToolbar();
        setBottomAppBar();
    }

    private void loadData() {
        Cursor cursor = dBhelper.getClassTable();

        classItems.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBhelper.CLASS_ID));
            String className = cursor.getString(cursor.getColumnIndex(DBhelper.CLASS_NAME_KEY));
            String subjiectName = cursor.getString(cursor.getColumnIndex(DBhelper.SUBJECT_NAME_KEY));

            classItems.add(new ClassItem(id,className,subjiectName));
        }

    }

    private void setBottomAppBar() {
        bottomAppBar = findViewById(R.id.bottomAppBar);

    }
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_class_detail);


    }



    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);

        intent.putExtra("className", classItems.get(position).getClassName());
        intent.putExtra("subjectName", classItems.get(position).getSubjectName());
        intent.putExtra("postion",position);
        intent.putExtra("classID",classItems.get(position).getClassID());
        startActivity(intent);
    }

    private void showDialog(){
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((className,subjectName)->addClass(className,subjectName));
    }
    private void addClass(String className, String subjectName){
        long cid = dBhelper.addClass(className,subjectName);
        Toast.makeText(this, "la classe "+cid+" a été creé", Toast.LENGTH_SHORT).show();
        ClassItem classItem = new ClassItem(cid,className,subjectName);
        classItems.add(classItem);
        classAdapter.notifyDataSetChanged();




    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        Class_edit dialog =new Class_edit();
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme);
        dialog.show(getSupportFragmentManager(),Class_edit.CLASS_UPDATE_DIALOG);
        dialog.setListener((className,subjectName)->updateClass(position,className,subjectName));

    }

    private void updateClass(int position, String className, String subjectName) {
        dBhelper.updateClass(classItems.get(position).getClassID(),className,subjectName);
        classItems.get(position).setClassName(className);
        classItems.get(position).setSubjectName(subjectName);
        Toast.makeText(addClass.this, "Modofié avec succés",Toast.LENGTH_SHORT).show();
        classAdapter.notifyItemChanged(position);

    }

    private void deleteClass(int position) {
        dBhelper.deleteList_ClassId(classItems.get(position).getClassID());
        dBhelper.deleteClass(classItems.get(position).getClassID());
        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }
}