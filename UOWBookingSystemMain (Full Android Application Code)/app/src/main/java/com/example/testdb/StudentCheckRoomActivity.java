package com.example.testdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentCheckRoomActivity extends AppCompatActivity {

    private ImageButton homeButton;
    private String chosenDate;
    private String chosenDateHeader;
    private String origCost;
    private String exemptedRoomID;
    private TextView dateHeader;
    private RecyclerView recyclerView;
    private com.example.databasehelper.DataBaseHelper myDb;
    private ArrayList<String> roomNoArray;
    private ArrayList<String> floorNoArray;
    private ArrayList<String> timeslotArray;
    private ArrayList<String> roomIDArray;
    private StudentCheckRoomAdapter adapter;

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goHome();
        }
    };

    public void goHome(){
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.uowroombooking.R.layout.activity_student_check_room);

        homeButton = (ImageButton)findViewById(com.example.uowroombooking.R.id.homeButtonCheck);
        dateHeader = (TextView)findViewById(com.example.uowroombooking.R.id.dateHeader);
        recyclerView = (RecyclerView)findViewById(com.example.uowroombooking.R.id.recyclerView);
        myDb = new com.example.databasehelper.DataBaseHelper(StudentCheckRoomActivity.this);

        roomNoArray = new ArrayList<>();
        floorNoArray = new ArrayList<>();
        timeslotArray = new ArrayList<>();
        roomIDArray = new ArrayList<>();

        homeButton.setOnClickListener(homeListener);
        Intent intent = getIntent();
        chosenDate = intent.getStringExtra("chosenDate");
        chosenDateHeader = intent.getStringExtra("chosenDateHeader");
        origCost = intent.getStringExtra("origCost");
        exemptedRoomID = intent.getStringExtra("exemptedRoomID");

        dateHeader.setText(chosenDateHeader);
        storeRoomInArrays();

        if(origCost.equals("")){
            adapter = new StudentCheckRoomAdapter(StudentCheckRoomActivity.this, roomNoArray, floorNoArray, timeslotArray, roomIDArray, chosenDateHeader, chosenDate);
        }
        else{
            adapter = new StudentCheckRoomAdapter(StudentCheckRoomActivity.this, roomNoArray, floorNoArray, timeslotArray, roomIDArray, chosenDateHeader, chosenDate, origCost);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentCheckRoomActivity.this));
    }


    public void storeRoomInArrays(){
        Cursor cursor = myDb.getRoomsOnDate(chosenDate);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                if(!exemptedRoomID.equals(cursor.getString(cursor.getColumnIndex("ID")))){
                    roomIDArray.add(cursor.getString(cursor.getColumnIndex("ID")));
                    roomNoArray.add(cursor.getString(cursor.getColumnIndex("ROOM_NUM")));
                    floorNoArray.add(cursor.getString(cursor.getColumnIndex("FLOOR_NUM")));
                    timeslotArray.add(cursor.getString(cursor.getColumnIndex("TIMESLOT")));
                }
            }
        }
    }
}