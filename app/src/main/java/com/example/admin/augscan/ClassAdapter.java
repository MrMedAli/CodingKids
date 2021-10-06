package com.example.admin.augscan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    ArrayList<ClassItem> classItems;
    Context context;

    private OnItemClickListener onItemClickListener;
    public interface  OnItemClickListener{
        void onClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ClassAdapter(Context context,ArrayList<ClassItem> classItems) {
        this.classItems = classItems;
        this.context= context;
    }

    public  static class ClassViewHolder extends RecyclerView.ViewHolder{
        TextView className;
        TextView subjectName;
        public ClassViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener
        ) {
            super(itemView);
            className = itemView.findViewById(R.id.class_tv);
            subjectName = itemView.findViewById(R.id.subject_tv);
            itemView.setOnClickListener(v->onItemClickListener.onClick((getAdapterPosition())));
        }
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_items, parent,false);
        return new ClassViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  ClassViewHolder holder, int i) {
        holder.className.setText(classItems.get(i).getClassName());
        holder.subjectName.setText(classItems.get(i).getSubjectName());

    }

    @Override
    public int getItemCount() {
        return classItems.size();
    }
}
