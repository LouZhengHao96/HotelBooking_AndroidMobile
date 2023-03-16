package com.example.testdb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentCheckRoomAdapter extends RecyclerView.Adapter<StudentCheckRoomAdapter.StudentCheckViewHolder> {

    private Context context;
    private ArrayList roomNoArray, floorNoArray, timeslotArray, roomIDArray;
    private String dateHeader;
    private String date;
    private String origCost = "";

    StudentCheckRoomAdapter(Context context, ArrayList roomNo, ArrayList floorNo, ArrayList timeslot, ArrayList roomIDArray, String dateHeader, String date){
        this.context = context;
        this.roomNoArray = roomNo;
        this.floorNoArray = floorNo;
        this.timeslotArray = timeslot;
        this.roomIDArray = roomIDArray;
        this.dateHeader = dateHeader;
        this.date = date;
    }

    StudentCheckRoomAdapter(Context context, ArrayList roomNo, ArrayList floorNo, ArrayList timeslot, ArrayList roomIDArray, String dateHeader, String date, String origCost){
        this.context = context;
        this.roomNoArray = roomNo;
        this.floorNoArray = floorNo;
        this.timeslotArray = timeslot;
        this.roomIDArray = roomIDArray;
        this.dateHeader = dateHeader;
        this.date = date;
        this.origCost = origCost;
    }

    @NonNull
    @Override
    public StudentCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(com.example.uowroombooking.R.layout.student_check_row, parent, false);
        return new StudentCheckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCheckViewHolder holder, int position) {
        holder.roomNoText.setText(String.valueOf(roomNoArray.get(position)));
        holder.floorNoText.setText(String.valueOf(floorNoArray.get(position)));
        holder.timeSlotText.setText(String.valueOf(timeslotArray.get(position)));
        holder.roomIDText.setText(String.valueOf(roomIDArray.get(position)));
    }

    @Override
    public int getItemCount() {
        return roomNoArray.size();
    }

    public class StudentCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView roomNoText, floorNoText, timeSlotText, roomIDText;

        public StudentCheckViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNoText = itemView.findViewById(com.example.uowroombooking.R.id.roomNoText);
            floorNoText = itemView.findViewById(com.example.uowroombooking.R.id.floorNoText);
            timeSlotText = itemView.findViewById(com.example.uowroombooking.R.id.timeslot);
            roomIDText = itemView.findViewById(com.example.uowroombooking.R.id.roomIDText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), StudentCheckPayment.class);
            intent.putExtra("chosenDateHeader", dateHeader);
            intent.putExtra("chosenDate", date);
            intent.putExtra("chosenRoom", roomNoText.getText());
            intent.putExtra("chosenFloor", floorNoText.getText());
            intent.putExtra("chosenTime", timeSlotText.getText());
            intent.putExtra("chosenID", roomIDText.getText());
            intent.putExtra("origCost", origCost);
            v.getContext().startActivity(intent);
        }
    }
}
