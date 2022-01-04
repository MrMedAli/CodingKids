package com.example.admin.augscan;

        import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class StudentDialog extends DialogFragment {

    public static final String STUDENT_ADD_DIALOG="addStudent";
    private FirebaseAuth firebaseAuth;
    private DBhelper dBhelper;
    public static EditText Code;
    public static TextView id;
    public static TextView name;
    DatabaseReference mdatabaseReference;

    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(String text1, String text2, long text3);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=null;
        if(getTag().equals(STUDENT_ADD_DIALOG))dialog=getAddStudentDialog();
        dBhelper = new DBhelper(dialog.getContext());
        return dialog;
    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.student_dialog, null);
        builder.setView(view);
        TextView title =view.findViewById(R.id.titleDialog);
        title.setText("Ajouter un nouvelle étudiant");

        //TextView Code = view.findViewById(R.id.Sedt01);
        Code = view.findViewById(R.id.Sedt01);
        name = view.findViewById(R.id.Sedt02);
        id = view.findViewById(R.id.Sedt03);
        Button cam =view.findViewById(R.id.scanbtnDialog);
        Button search =view.findViewById(R.id.search_id);
        name.setHint("Nom");
        id.setHint("Prenom");
        Code.setHint("code QR");
        Button cancel = view.findViewById(R.id.SCancel_id);
        Button add = view.findViewById(R.id.Sadd_id);
        cancel.setOnClickListener(v-> dismiss());



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext = Code.getText().toString();

                if(databasesearch(searchtext) > 0){
                    StudentDialog.name.setText(dBhelper.getStudentTable_Name(String.valueOf(databasesearch(searchtext))));
                    StudentDialog.id.setText(dBhelper.getStudentTable_LName(String.valueOf(databasesearch(searchtext))));
                }else{
                    Toast.makeText(getContext(), "etudiant inexistant", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getDialog().getContext(), scanfordialog.class));

            }
        });
        //roll_edt.setText(String.valueOf(Integer.parseInt(Roll)+1));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Roll = "";

                String searchtext = Code.getText().toString();
                String FName = name.getText()+" "+id.getText();

                listener.onClick(Roll, FName, databasesearch(searchtext));
                Code.setText("");
            }
        });

        return builder.create();
    }
    public long databasesearch(String searchtext){

        return dBhelper.getStudentId(searchtext);

    }

}


