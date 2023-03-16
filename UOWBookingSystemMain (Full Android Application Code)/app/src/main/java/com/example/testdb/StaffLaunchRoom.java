package com.example.testdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StaffLaunchRoom extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private static com.example.databasehelper.DataBaseHelper dbHelper;
    private static ArrayList<String> room_ID, floor_NO, room_NO, creator, date, timeslot, capacity, price, promocode, status;
    private static RoomListAdapter customRoomListAdapter;
    private static RoomListAdapter.RecyclerViewCLickListener RVClick;
    private static ImageButton homeButton,refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.staffmainmenuv2.R.layout.activity_staff_launch_room);

        // creates arrayList of all room elements to add onto the rows of recyclerView
        // arrayLists will store all the data from each row in database
        recyclerView = findViewById(com.example.staffmainmenuv2.R.id.roomList);
        homeButton = (ImageButton)findViewById(com.example.staffmainmenuv2.R.id.homeButton);
        refreshBtn = (ImageButton)findViewById(com.example.staffmainmenuv2.R.id.refresnBtn);
        dbHelper = new com.example.databasehelper.DataBaseHelper(StaffLaunchRoom.this);
        room_ID = new ArrayList<>();
        floor_NO = new ArrayList<>();
        room_NO = new ArrayList<>();
        creator = new ArrayList<>();
        date = new ArrayList<>();
        timeslot = new ArrayList<>();
        capacity = new ArrayList<>();
        price = new ArrayList<>();
        promocode = new ArrayList<>();
        status = new ArrayList<>();
        storeRoomDataInArrays();
        //ON CLICK
        setOnClickListner();
        customRoomListAdapter = new RoomListAdapter(StaffLaunchRoom.this, room_ID, floor_NO, room_NO, creator, date, timeslot, capacity, price, promocode, status,RVClick);
        recyclerView.setAdapter(customRoomListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(StaffLaunchRoom.this);
        recyclerView.setLayoutManager(layoutManager);

        // added refresh button to see changes in database in launch room view
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        // home button to go to main menu
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(StaffLaunchRoom.this, StaffMainMenu.class));
            }
        });
    }

    //on click listner
    private void setOnClickListner() {
        RVClick = new RoomListAdapter.RecyclerViewCLickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent= new Intent(getApplicationContext(), StaffEditRoom.class);
                intent.putExtra("ID", room_ID.get(position));
                intent.putExtra("date",date.get(position));
                intent.putExtra("floor_no",floor_NO.get(position));
                intent.putExtra("room_no",room_NO.get(position));
                intent.putExtra("timeslot",timeslot.get(position));
                intent.putExtra("capacity",capacity.get(position));
                intent.putExtra("price",price.get(position));
                intent.putExtra("promocode",promocode.get(position));
                intent.putExtra("room_status", status.get(position));
                startActivity(intent);
            }
        };
    }

    // creates cursor to read data from database by column and puts it into array
    private void storeRoomDataInArrays() {
        Cursor cursor = dbHelper.readAllRooms();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No Room Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                room_ID.add(cursor.getString(0));
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