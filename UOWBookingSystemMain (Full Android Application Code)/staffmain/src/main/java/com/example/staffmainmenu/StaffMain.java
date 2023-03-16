package com.example.staffmainmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import com.example.staffcreateroom.StaffCreateRoom;

public class StaffMain extends AppCompatActivity {
    public Button createRoomBtn;
    public Button launchManageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        launchManageBtn = (Button)findViewById(R.id.launchManageBtn);
        createRoomBtn = (Button)findViewById(R.id.createRoomBtn);

        launchManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchRoomIntent = new Intent(StaffMain.this, com.example.staffmainmenuv2.StaffLaunchRoom.class);
                startActivity(launchRoomIntent);
            }
        });

        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMain.this, StaffCreateRoom.class);
                startActivity(intent);
            }
        });

    }
}