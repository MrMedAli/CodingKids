package com.example.admin.augscan;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Student_edit extends BottomSheetDialogFragment {
    public static final String STUDENT_UPDATE_DIALOG="updatestudent";
    private Student_edit.OnClickListener listener;




    public interface OnClickListener{
        void onClick(String text1, String text2,String text3, String text4);
    }
    public void setListener(Student_edit.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_student_edit, container, false);
        Dialog dialog=null;


        EditText name_edt = view.findViewById(R.id.student_name_edit);
        EditText lname_edt = view.findViewById(R.id.student_lname_edit);
        EditText phone1_edt = view.findViewById(R.id.student_phone1_edit);
        EditText phone2_edt = view.findViewById(R.id.student_phone2_edit);

        LinearLayout enregistrer = view.findViewById(R.id.enregistrer);
        enregistrer.setOnClickListener(v-> {
            String studentName;

            studentName= name_edt.getText().toString();
            String studentLName= lname_edt.getText().toString();
            String studentphone1= phone1_edt.getText().toString();
            String studentphone2= phone2_edt.getText().toString();
            listener.onClick(studentName, studentLName,studentphone1,studentphone2);

        });

        return view;
    }



}