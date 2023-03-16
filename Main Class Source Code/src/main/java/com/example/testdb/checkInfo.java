package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class checkInfo extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private static com.example.databasehelper.DataBaseHelper dbHelper;
    private static ArrayList<String> room_ID, floor_NO, room_NO, creator, date, timeslot, capacity, price, promocode, status;
    private static RoomsListAdapter customRoomListAdapter;
    private String startDate;
    private String endDate;
    private String floorNo;
    private String roomNo;
    private static ImageButton homeButton,refreshBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(com.example.supermain.R.layout.activity_check_info);

        recyclerView = findViewById(com.example.supermain.R.id.listRoom);
        homeButton = (ImageButton)findViewById(com.example.supermain.R.id.homeButton);
        refreshBtn = findViewById(com.example.supermain.R.id.refresnBtn);
        dbHelper = new com.example.databasehelper.DataBaseHelper(checkInfo.this);
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

        customRoomListAdapter = new RoomsListAdapter(checkInfo.this, room_ID, floor_NO, room_NO, creator, date, timeslot, capacity, price, promocode, status);
        LinearLayoutManager layoutManager = new LinearLayoutManager(checkInfo.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customRoomListAdapter);


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
        Intent intent = getIntent();
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        floorNo=intent.getStringExtra("FloorNo");
        roomNo=intent.getStringExtra("RoomNo");

        ArrayList<String> Rid = dbHelper.getRoomIds(startDate,endDate,roomNo,floorNo);
        if (Rid.size()==0) {
            Toast.makeText(this, "No Room Data", Toast.LENGTH_SHORT).show();
        } else {
            for(int i=0;i<Rid.size();i++)
            {
                ArrayList<String> temp=dbHelper.getDataFromRoomID(Rid.get(i));
                room_ID.add(temp.get(0));
                floor_NO.add(temp.get(1));
                room_NO.add(temp.get(2));
                creator.add(temp.get(3));
                date.add(temp.get(4));
                timeslot.add(temp.get(5));
                capacity.add(temp.get(6));
                price.add(temp.get(7));
                promocode.add(temp.get(8));
                status.add(temp.get(9));
            }
        }
    }

}