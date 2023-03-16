package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StaffMainMenu extends AppCompatActivity {
    public Button launchManageBtn;
    public Button createRoomBtn;
    public ImageButton logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.staffmainmenuv2.R.layout.activity_staff_main_menu);

        launchManageBtn = (Button)findViewById(com.example.staffmainmenuv2.R.id.launchManageBtn);
        createRoomBtn = (Button)findViewById(com.example.staffmainmenuv2.R.id.createRoomBtn);
        logoutBtn=(ImageButton)findViewById(com.example.staffmainmenuv2.R.id.Logoutbtn);

        createRoomBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMainMenu.this, StaffCreateRoom.class);
                startActivity(intent);
            }
        });

        launchManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchRoomIntent = new Intent(StaffMainMenu.this, StaffLaunchRoom.class);
                startActivity(launchRoomIntent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchRoomIntent = new Intent(StaffMainMenu.this, MainActivity.class);
                startActivity(launchRoomIntent);
                Toast.makeText(StaffMainMenu.this, "Logged out", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}