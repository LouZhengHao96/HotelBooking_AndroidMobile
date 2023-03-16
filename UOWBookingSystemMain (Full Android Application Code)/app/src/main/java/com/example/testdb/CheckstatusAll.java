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

public class CheckstatusAll extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private static com.example.databasehelper.DataBaseHelper dbHelper;
    private static ArrayList<String> room_ID, floor_NO, room_NO, creator, date, timeslot, capacity, price, promocode, status;
    private static RoomsListAdapter customRoomListAdapter;

    private static ImageButton homeButton,refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.supermain.R.layout.activity_super_approveroom);

        // creates arrayList of all room elements to add onto the rows of recyclerView
        // arrayLists will store all the data from each row in database
        recyclerView = findViewById(com.example.supermain.R.id.roomsList);
        homeButton = findViewById(com.example.supermain.R.id.homeButton);
        refreshBtn = findViewById(com.example.supermain.R.id.refresnBtn);
        dbHelper = new com.example.databasehelper.DataBaseHelper(CheckstatusAll.this);
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
        refreshBtn = findViewById(com.example.supermain.R.id.refresnBtn);
        storeRoomDataInArrays();

        customRoomListAdapter = new RoomsListAdapter(CheckstatusAll.this, room_ID, floor_NO, room_NO, creator, date, timeslot, capacity, price, promocode, status);
        recyclerView.setAdapter(customRoomListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CheckstatusAll.this);
        recyclerView.setLayoutManager(layoutManager);

        homeButton.setOnClickListener(homeListener);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });



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
        Intent intent=getIntent();
        String RoomNo=intent.getStringExtra("RoomNo");
        String FloorNo=intent.getStringExtra("FloorNo");
        Cursor cursor = dbHelper.getRoomsOnNumber(FloorNo,RoomNo);
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