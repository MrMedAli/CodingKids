package com.example.admin.augscan;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class modifie_student_Dialog extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton call;
    private  String classNanme, subjectName;
    private int position;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private DBhelper dBhelper;


    private long sid;
    private String name,lname,phone1,phone2,qr;
    TextView name_edt;
    TextView lname_edt;
    TextView phone1_edt;
    TextView phone2_edt;
    TextView qrcode;

    ClassAdapter classAdapter;

    ArrayList<ClassItem> classItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        dBhelper = new DBhelper(this);

        name_edt = findViewById(R.id.student_name_edit);
        lname_edt = findViewById(R.id.student_lname_edit);
        phone1_edt =findViewById(R.id.student_phone1_edit);
        phone2_edt =findViewById(R.id.student_phone2_edit);
        qrcode =findViewById(R.id.stdqr);
        call=findViewById(R.id.call);


        dBhelper = new DBhelper(this);


        Intent intent =getIntent();
        name=intent.getStringExtra("studentname");
        lname = intent.getStringExtra("studentlastname");
        phone1 = intent.getStringExtra("phone1");
        phone2 = intent.getStringExtra("phone2");
        qr = intent.getStringExtra("studentqrcode");
        position = intent.getIntExtra("postion", -1);
        sid =intent.getLongExtra("studentid", -1);

        name_edt.setText(name);
        lname_edt.setText(lname);
        phone1_edt.setText(phone1);
        phone2_edt.setText(phone2);
        qrcode.setText(qr);

        loadData();

        recyclerView =findViewById(R.id.recyclerViewClass);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        classAdapter= new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classAdapter);

        //classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "tel:" + phone1.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });


    }

    private void gotoItemActivity(int position) {
    }
    private void loadData() {
        classItems.clear();
        Cursor cursor = dBhelper.getListTablesid(sid);
        while (cursor.moveToNext()){
            long cid = cursor.getLong(cursor.getColumnIndex(DBhelper.CLASS_ID));
            Cursor cursor2 = dBhelper.getClassTablestudent(cid);
            while (cursor2.moveToNext()){
                int id = cursor2.getInt(cursor2.getColumnIndex(DBhelper.CLASS_ID));
                String className = cursor2.getString(cursor2.getColumnIndex(DBhelper.CLASS_NAME_KEY));
                String subjiectName = cursor2.getString(cursor2.getColumnIndex(DBhelper.SUBJECT_NAME_KEY));

                classItems.add(new ClassItem(id,className,subjiectName));
            }
        }





    }

}
