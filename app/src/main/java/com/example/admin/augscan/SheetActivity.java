package com.example.admin.augscan;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

public class SheetActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);


        showTable();
        settoolbar();
    }

    private void settoolbar() {
        String month = getIntent().getStringExtra("month");

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        title.setText(month);
        subtitle.setVisibility(View.GONE);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.savebtn);
        save.setVisibility(View.GONE);



        back.setOnClickListener(v-> onBackPressed());
    }

    private void showTable() {
        DBhelper dBhelper = new DBhelper(this);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        long[] idArray = getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");
        String month = getIntent().getStringExtra("month");

        int DAY_IN_MONTH = getDayInMonth(month);

        //row setup
        int rowSize = idArray.length+1;
        TableRow[] rows = new TableRow[rowSize];
        TextView[] rolls_tvs = new TextView[rowSize];
        TextView[] name_tvs = new TextView[rowSize];
        TextView[][] status_tvs = new TextView[rowSize][DAY_IN_MONTH +1];


        for (int i = 0; i<rowSize; i++){
            rolls_tvs[i]=new TextView(this);
            name_tvs[i]=new TextView(this);
            for (int j=0;j<= DAY_IN_MONTH;j++){
                status_tvs[i][j]=new TextView(this);
            }
        }


        //header
        rolls_tvs[0].setText("");
        rolls_tvs[0].setTypeface(rolls_tvs[0].getTypeface(), Typeface.BOLD);
        name_tvs[0].setText("Nom");
        name_tvs[0].setTypeface(name_tvs[0].getTypeface(), Typeface.BOLD);

        for (int i=0;i<= DAY_IN_MONTH;i++){
            status_tvs[0][i].setText(String.valueOf(i));
            status_tvs[0][i].setTypeface(status_tvs[0][i].getTypeface(), Typeface.BOLD);
        }


        for (int i=1; i<rowSize; i++){
            rolls_tvs[i].setText(String.valueOf(rollArray[i-1]));
            name_tvs[i].setText(nameArray[i-1]);
            for (int j=1;j<= DAY_IN_MONTH;j++){
                String day = String.valueOf(j);
                if (day.length()==1) day="0"+day;
                String date = day+"."+month;
                String status =dBhelper.getStatus(idArray[i-1],date);

                status_tvs[i][j].setText(status);



            }
        }

        for (int i=0; i < rowSize; i++)
        {
            rows[i] = new TableRow(this);

            if(i%2==0)
                rows[i].setBackgroundColor(Color.parseColor("#EEEEEE"));
            else                 rows[i].setBackgroundColor(Color.parseColor("#E4E4E4"));


            rolls_tvs[i].setPadding(16,16,16,16);
            name_tvs[i].setPadding(16,16,16,16);

            rows[i].addView(rolls_tvs[i]);
            rows[i].addView(name_tvs[i]);
            for (int j=1;j<= DAY_IN_MONTH;j++){

                rows[i].addView(status_tvs[i][j]);

                status_tvs[i][j].setPadding(16,16,16,16);

            }

            tableLayout.addView(rows[i]);

        }
        tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
    }

    private int getDayInMonth(String month) {
        int monthIndex = Integer.valueOf(month.substring(0,2));
        int year = Integer.valueOf(month.substring(4));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,monthIndex);
        calendar.set(Calendar.YEAR,year);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}