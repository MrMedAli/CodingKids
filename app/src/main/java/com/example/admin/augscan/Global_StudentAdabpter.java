package com.example.admin.augscan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Global_StudentAdabpter extends RecyclerView.Adapter<Global_StudentAdabpter.Global_StudentViewHolder> {

    ArrayList<StudentItem> studentItems;
    Context context;

    private OnItemClickListener onItemClickListener;
    public interface  OnItemClickListener{
        void onClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public Global_StudentAdabpter(Context context,ArrayList<StudentItem> studentItems) {
        this.studentItems = studentItems;
        this.context= context;
    }

    public  static class Global_StudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView studentName;
        TextView studentLName;
        TextView codeQr;
        public LinearLayout layout;
        public Global_StudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener
        ) {
            super(itemView);
            studentName = itemView.findViewById(R.id.AdapterStudentName);
            studentLName = itemView.findViewById(R.id.AdapterStudentLName);
            codeQr = itemView.findViewById(R.id.Qr_code);

            itemView.setOnClickListener(v->onItemClickListener.onClick((getAdapterPosition())));
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(),0,0,"Modifier");
            contextMenu.add(getAdapterPosition(),1,0,"Suprimer");
        }
    }

    @NonNull
    @Override
    public Global_StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent,false);
        return new Global_StudentViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Global_StudentViewHolder holder, int i) {
        holder.studentName.setText(studentItems.get(i).getstudentname());
        holder.studentLName.setText(studentItems.get(i).getstudentlastname());
        holder.codeQr.setText(studentItems.get(i).getstudentqrcode());

    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }
}

