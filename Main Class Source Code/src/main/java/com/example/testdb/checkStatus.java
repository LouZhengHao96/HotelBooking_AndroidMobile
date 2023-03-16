package com.example.testdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.databasehelper.DataBaseHelper;

public class checkStatus extends AppCompatActivity {
    private Spinner RoomNoSelection,FloorNoSelection;
    private Button SelectTRoom;
    private DataBaseHelper myDb;
    private ImageButton imageButton;


        @Override
        protected void onCreate(Bundle savedInstanceState)
        {

            super.onCreate(savedInstanceState);
            setContentView(com.example.supermain.R.layout.activity_check_status);
            myDb = new DataBaseHelper(checkStatus.this);
            imageButton = (ImageButton)findViewById(com.example.supermain.R.id.imageButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), superMain.class);
                    finish();
                    startActivity(intent);
                }
            });

            RoomNoSelection=findViewById(com.example.supermain.R.id.RoomNoSelection);
            FloorNoSelection=findViewById(com.example.supermain.R.id.FloorNoSelection);
            SelectTRoom=findViewById(com.example.supermain.R.id.SelectTRoom);

            ArrayAdapter<CharSequence> FloorA = ArrayAdapter.createFromResource(this, com.example.supermain.R.array.floorArray, android.R.layout.simple_spinner_item);
            FloorA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            FloorNoSelection.setAdapter(FloorA);


            ArrayAdapter<CharSequence> RoomA = ArrayAdapter.createFromResource(this, com.example.supermain.R.array.roomArray, android.R.layout.simple_spinner_item);
            RoomA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            RoomNoSelection.setAdapter(RoomA);



            SelectTRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (FloorNoSelection.getSelectedItem().toString().equals("Floor")){
                        AlertDialog.Builder alert = new AlertDialog.Builder(checkStatus.this);
                        alert.setTitle("No Floor Selected");
                        alert.setMessage("Please select a floor number.");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                    else if(RoomNoSelection.getSelectedItem().toString().equals("Room")){
                        AlertDialog.Builder alert = new AlertDialog.Builder(checkStatus.this);
                        alert.setTitle("No Room Selected");
                        alert.setMessage("Please select a room number.");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                    else if(myDb.hasAvailRoomStatus(FloorNoSelection.getSelectedItem().toString(), RoomNoSelection.getSelectedItem().toString()) == false){
                        AlertDialog.Builder alert = new AlertDialog.Builder(checkStatus.this);
                        alert.setTitle("Room not found");
                        alert.setMessage("The room you're looking for doesn't seem to exist.\nTry to enter a valid room");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.show();
                    }
                    else {
                        Intent intent = new Intent(checkStatus.this, CheckstatusAll.class);
                        intent.putExtra("FloorNo",FloorNoSelection.getSelectedItem().toString());
                        intent.putExtra("RoomNo",RoomNoSelection.getSelectedItem().toString());
                        startActivity(intent);
                    }
                }
            });

        };
}