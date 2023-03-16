package com.example.testdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.databasehelper.DataBaseHelper;

import java.text.DateFormat;
import java.util.ArrayList;

public class StudentTimeActivity extends AppCompatActivity{


    private String chosenID;
    private ArrayList<String> origRoomData;
    private DataBaseHelper myDb;
    private TextView dateTextEdit;
    private Button stuRoomEdit, stuUpdateButton, stuDeleteButton;
    private String origCost;
    private ImageButton homeButton;

    private View.OnClickListener updateRoom = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //check if no available rooms before proceeding with rebooking
            if (myDb.getAvailRoomDates().size() < 1){
                AlertDialog.Builder noAvailDate = new AlertDialog.Builder(StudentTimeActivity.this);
                noAvailDate.setCancelable(false);
                noAvailDate.setTitle("No Available Rooms Found");
                noAvailDate.setMessage("There are no more available rooms to book.\nPlease try again later");
                noAvailDate.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                noAvailDate.show();
            }
            //if there are avail rooms, continue with re-booking
            else{
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentTimeActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Confirm Change?");
                alertDialog.setMessage("Room will be removed once change has started. \nContinue Editing?");

                alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        origCost = myDb.getBookingCost(chosenID);
                        myDb.revertBookedStatus(chosenID);
                        goChange();
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(StudentTimeActivity.this, "Change Booking Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        }
    };

    private View.OnClickListener deleteRoom = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentTimeActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Confirm Deletion?");

            alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    myDb.revertBookedStatus(chosenID);
                    Toast.makeText(StudentTimeActivity.this, "Booking Deleted", Toast.LENGTH_SHORT).show();
                    if(myDb.hasBookings()){
                        goManage();
                    }
                    else {
                        goMain();
                    }
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Toast.makeText(StudentTimeActivity.this, "Deletion Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(com.example.uowroombooking.R.layout.activity_student_time);

        Intent intent = getIntent();
        chosenID = intent.getStringExtra("chosenID");
        myDb = new DataBaseHelper(StudentTimeActivity.this);
        origRoomData = myDb.getRoomDataFromRoomID(chosenID);

        dateTextEdit = (TextView)findViewById(com.example.uowroombooking.R.id.dateTextEdit);
        stuRoomEdit = (Button)findViewById(com.example.uowroombooking.R.id.stuRoomEdit);
        stuUpdateButton = (Button)findViewById(com.example.uowroombooking.R.id.stuUpdateButton);
        stuDeleteButton = (Button)findViewById(com.example.uowroombooking.R.id.stuDeleteButton);
        homeButton = (ImageButton)findViewById(com.example.uowroombooking.R.id.homeButtonEdit);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        dateTextEdit.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(myDb.stringToCalendar(myDb.getRoomDataFromRoomID(chosenID).get(2)).getTime()));
        stuRoomEdit.setText(origRoomData.get(0) + " | " + origRoomData.get(1));
        stuUpdateButton.setOnClickListener(updateRoom);
        stuDeleteButton.setOnClickListener(deleteRoom);
    }

    public void goChange(){
        Intent intent = new Intent(this, StudentChangeBookingActivity.class);
        intent.putExtra("exemptedRoomID", chosenID);
        intent.putExtra("origCost", origCost);
        startActivity(intent);
    }

    public void goManage(){
        Intent intent = new Intent(this, StudentManageActivity.class);
        finish();
        startActivity(intent);
    }

    public void goMain(){
        Intent intent = new Intent(this, StudentActivity.class);
        finish();
        startActivity(intent);
    }

    public void goHome(){
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }
}