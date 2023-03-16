package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class approveroomedit extends AppCompatActivity {
    private TextView AEdate,AEFloor,AERoom,AETime,AECapacity,AEPromocode,AEPrice,AEStatus;
    private Button ApproveBtn;
    private String RoomiD;
    private com.example.databasehelper.DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.supermain.R.layout.activity_approveroomedit);
        AEdate=findViewById(com.example.supermain.R.id.AEdate);
        AEFloor=findViewById(com.example.supermain.R.id.AEFloor);
        AERoom=findViewById(com.example.supermain.R.id.AERoom);
        AETime=findViewById(com.example.supermain.R.id.AETime);
        AECapacity=findViewById(com.example.supermain.R.id.AECapacity);
        AEPromocode=findViewById(com.example.supermain.R.id.AEPromoCode);
        AEPrice=findViewById(com.example.supermain.R.id.AEPrice);
        AEStatus=findViewById(com.example.supermain.R.id.AEStatus);
        ApproveBtn=findViewById(com.example.supermain.R.id.ApproveBtn);

        myDb=new com.example.databasehelper.DataBaseHelper(approveroomedit.this);

        String price="";
        String promocode="";
        String timeslot="";
        String date="";
        String RoomNo="";
        String FloorNo="";
        String Capacity="";
        String Status="";
        RoomiD="";

        Intent intent=getIntent();
        price= intent.getStringExtra("price");
        promocode= intent.getStringExtra("promocode");
        timeslot=intent.getStringExtra("timeslot");
        date=intent.getStringExtra("date");
        RoomNo=intent.getStringExtra("room_no");
        FloorNo=intent.getStringExtra("floor_no");
        Capacity=intent.getStringExtra("capacity");
        Status=intent.getStringExtra("status");
        RoomiD=intent.getStringExtra("RoomID");

        AEdate.setText(date);
        AEFloor.setText(FloorNo);
        AERoom.setText(RoomNo);
        AETime.setText(timeslot);
        AEPrice.setText(price);
        AEPromocode.setText(promocode);
        AECapacity.setText(Capacity);
        AEStatus.setText(Status);


        ApproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.approveRoom(RoomiD);

                Toast.makeText(approveroomedit.this, "Room Approved", Toast.LENGTH_LONG).show();
                finish();
                 Intent intent = new Intent(approveroomedit.this, approveRoomMain.class);
                 startActivity(intent);
            }


        });


        }
    }
