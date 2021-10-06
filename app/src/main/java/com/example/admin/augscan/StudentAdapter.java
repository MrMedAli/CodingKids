package com.example.admin.augscan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    ArrayList<StudentItem> studentItems;
    Context context;

    private OnItemClickListener onItemClickListener;
    public interface  OnItemClickListener{
        void onClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public StudentAdapter(Context context, ArrayList<StudentItem> studentItems) {
        this.studentItems = studentItems;
        this.context= context;
    }

    public  static class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView roll;
        TextView name;
        TextView status;
        public StudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener
        ) {
            super(itemView);
            roll = itemView.findViewById(R.id.roll);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            itemView.setOnClickListener(v->onItemClickListener.onClick((getAdapterPosition())));
        }
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_items, parent,false);
        return new StudentViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  StudentViewHolder holder, int i) {
        holder.roll.setText(studentItems.get(i).getRoll());
        holder.name.setText(studentItems.get(i).getstudentlastname());
        holder.status.setText(studentItems.get(i).getStatus());

    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }
}
