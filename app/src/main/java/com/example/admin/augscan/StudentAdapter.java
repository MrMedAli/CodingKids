package com.example.admin.augscan;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    ArrayList<Student_class> studentItems;
    Context context;

    private OnItemClickListener onItemClickListener;
    public interface  OnItemClickListener{
        void onClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public StudentAdapter(Context context, ArrayList<Student_class> studentItems) {
        this.studentItems = studentItems;
        this.context= context;
    }

    public  static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView roll;
        TextView name;
        TextView status;
        CardView cardView;
        public StudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener
        ) {
            super(itemView);
            roll = itemView.findViewById(R.id.roll);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.cardview);
            itemView.setOnClickListener(v->onItemClickListener.onClick((getAdapterPosition())));
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(),0,0,"Modifier");
            contextMenu.add(getAdapterPosition(),1,0,"suprimer");
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
        holder.roll.setText(studentItems.get(i).getRoll()+"");
        holder.name.setText(studentItems.get(i).getName());
        holder.status.setText(studentItems.get(i).getStatus());
        holder.cardView.setCardBackgroundColor(getColor(i));

    }
    private int getColor(int i) {
        String status = studentItems.get(i).getStatus();
        if(status.equals("P"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
        else if (status.equals("A"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.absent)));
        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.white)));
    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }

}
