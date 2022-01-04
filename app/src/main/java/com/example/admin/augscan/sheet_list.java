package com.example.admin.augscan;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;

public class sheet_list extends AppCompatActivity {
    private ListView sheetlist;
    private ArrayAdapter adapter;
    private ArrayList<String> listItems = new ArrayList();
    private long cid;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);

        cid = getIntent().getLongExtra("classID",-1);
        loadListItems();
        sheetlist = findViewById(R.id.sheetlist);
        adapter = new ArrayAdapter(this, R.layout.sheet_list,R.id.date_list_item,listItems);
        sheetlist.setAdapter(adapter);

        sheetlist.setOnItemClickListener((adapterView, view, i, l) -> openSheetActivity(i));
        setToolbar();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);

        subtitle.setVisibility(View.GONE);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.savebtn);
        save.setVisibility(View.GONE);


        title.setText("Liste de présence");

        back.setOnClickListener(v-> onBackPressed());

    }

    private void openSheetActivity(int i) {
        long[] idArray = getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");
        Intent intent = new Intent(this, SheetActivity.class);
        intent.putExtra("idArray",idArray);
        intent.putExtra("rollArray",rollArray);
        intent.putExtra("nameArray",nameArray);
        intent.putExtra("month",listItems.get(i));

        startActivity(intent);
    }

    private void loadListItems() {
        Cursor cursor = new DBhelper(this).getDistinctMonths(cid);

        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex(DBhelper.DATE_KEY));
            listItems.add(date.substring(3));
        }
    }
}