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


public class Class_edit extends BottomSheetDialogFragment {
    public static final String CLASS_UPDATE_DIALOG="updateClass";
    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(String text1, String text2);
    }
    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_modifie_class_dialog, container, false);
        Dialog dialog=null;

        EditText class_edt = view.findViewById(R.id.stu_name_edit);
        EditText subject_edt = view.findViewById(R.id.stu_regNo_edit);
        LinearLayout enregistrer = view.findViewById(R.id.enregistrer);
        enregistrer.setOnClickListener(v-> {
            String className= class_edt.getText().toString();
            String subName= subject_edt.getText().toString();
            listener.onClick(className, subName);
            dismiss();
        });

        return view;
    }



}