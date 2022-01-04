package com.example.admin.augscan;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyDialog extends DialogFragment {
    public static final String CLASS_ADD_DIALOG="addClass";
    public static final String STUDENT_ADD_DIALOG="addStudent";


    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(String text1, String text2);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=null;
        if(getTag().equals(CLASS_ADD_DIALOG))dialog=getAddClassDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }


    private Dialog getAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);
        TextView title =view.findViewById(R.id.titleDialog);
        title.setText("Ajouter une nouvelle Classe");

        EditText class_edt = view.findViewById(R.id.edt01);
        EditText subject_edt = view.findViewById(R.id.ed02);
        ImageButton cam =view.findViewById(R.id.imageButtonsearch);
        class_edt.setHint("CLasse");
        subject_edt.setHint("Sujet");
        Button cancel = view.findViewById(R.id.Cancel_id);
        Button add = view.findViewById(R.id.add_id);

        cancel.setOnClickListener(v-> dismiss());
        add.setOnClickListener(v-> {
            String className= class_edt.getText().toString();
            String subName= subject_edt.getText().toString();
            listener.onClick(className, subName);
            dismiss();
        });
        cam.setVisibility(View.GONE);
        return builder.create();
    }
}
