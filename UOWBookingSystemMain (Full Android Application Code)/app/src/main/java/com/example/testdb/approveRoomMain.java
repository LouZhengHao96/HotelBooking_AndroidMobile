package com.example.testdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class approveRoomMain extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private static com.example.databasehelper.DataBaseHelper dbHelper;
    private static ArrayList<String> floor_NO, room_NO, creator, date, timeslot, capacity, price, promocode, status,Room_ID;
    private static NotApprovedListAdapter customRoomListAdapter;
    private static NotApprovedListAdapter.RecyclerViewCLickListener RVClick;
    private static ImageButton homeButton,refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.supermain.R.layout.activity_approve_room_main);
        homeButton = (ImageButton)findViewById(com.example.supermain.R.id.homeButton);
        homeButton.setOnClickListener(homeListener);
        refreshBtn = findViewById(com.example.supermain.R.id.refresnBtn);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        // creates arrayList of all room elements to add onto the rows of recyclerView
        // arrayLists will
        recyclerView = findViewById(com.example.supermain.R.id.NotAPRoomList);
        dbHelper = new com.example.databasehelper.DataBaseHelper(approveRoomMain.this);
        floor_NO = new ArrayList<>();
        room_NO = new ArrayList<>();
        creator = new ArrayList<>();
        date = new ArrayList<>();
        timeslot = new ArrayList<>();
        capacity = new ArrayList<>();
        price = new ArrayList<>();
        promocode = new ArrayList<>();
        status = new ArrayList<>();
        Room_ID=new ArrayList<>();
        storeRoomDataInArrays();
        //ON CLICK
        setOnClickListner();
        customRoomListAdapter = new NotApprovedListAdapter(approveRoomMain.this, floor_NO, room_NO, creator, date, timeslot, capacity, price, promocode, status,Room_ID,RVClick);
        recyclerView.setAdapter(customRoomListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(approveRoomMain.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setOnClickListner() {
        RVClick=new NotApprovedListAdapter.RecyclerViewCLickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent= new Intent(getApplicationContext(), approveroomedit.class);
                intent.putExtra("RoomID",Room_ID.get(position));
                intent.putExtra("username",creator.get(position));
                intent.putExtra("date",date.get(position));
                intent.putExtra("floor_no",floor_NO.get(position));
                intent.putExtra("room_no",room_NO.get(position));
                intent.putExtra("timeslot",timeslot.get(position));
                intent.putExtra("capacity",capacity.get(position));
                intent.putExtra("price",price.get(position));
                intent.putExtra("promocode",promocode.get(position));
                intent.putExtra("status",status.get(position));
                startActivity(intent);
            }
        };
    }

    public void goHome(){
        Intent intent = new Intent(this, superMain.class);
        startActivity(intent);
    }

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goHome();
        }
    };

    private void storeRoomDataInArrays() {
        Cursor cursor =dbHelper.getRoomNotApproved() ;
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No Room Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Room_ID.add(cursor.getString(0));
                floor_NO.add(cursor.getString(1));
                room_NO.add(cursor.getString(2));
                creator.add(cursor.getString(3));
                date.add(cursor.getString(4));
                timeslot.add(cursor.getString(5));
                capacity.add(cursor.getString(6));
                price.add(cursor.getString(7));
                promocode.add(cursor.getString(8));
                status.add(cursor.getString(9));
            }
        }
    }

}