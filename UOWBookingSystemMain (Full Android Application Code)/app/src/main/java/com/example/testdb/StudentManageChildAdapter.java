package com.example.testdb;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentManageChildAdapter extends RecyclerView.Adapter<StudentManageChildAdapter.ChildViewHolder> {

    private ArrayList<StudentManageChild> childArrayList;

    //constructor
    public StudentManageChildAdapter(ArrayList<StudentManageChild> childArrayList){
        this.childArrayList = childArrayList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the corresponding layout of the child item
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.uowroombooking.R.layout.student_manage_row_child, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        StudentManageChild studentManageChild = childArrayList.get(position);
        holder.roomFloor.setText(studentManageChild.getRoomFloor());
        holder.timeSlot.setText(studentManageChild.getTimeSlot());
        holder.roomID.setText(studentManageChild.getRoomID());
    }

    @Override
    public int getItemCount() {
        return childArrayList.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView roomFloor;
        private TextView timeSlot;
        private TextView roomID;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            roomFloor = itemView.findViewById(com.example.uowroombooking.R.id.roomFloor);
            timeSlot = itemView.findViewById(com.example.uowroombooking.R.id.timeslot);
            roomID = itemView.findViewById(com.example.uowroombooking.R.id.roomID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), StudentTimeActivity.class);
            intent.putExtra("chosenID", roomID.getText());
            v.getContext().startActivity(intent);
        }
    }
}
