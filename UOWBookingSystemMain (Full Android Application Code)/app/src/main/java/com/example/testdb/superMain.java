package com.example.testdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class superMain extends AppCompatActivity {

    private Button statusButton;
    private Button manageButton;
    private Button TARButton;
    public ImageButton logoutBtn;
    private com.example.databasehelper.DataBaseHelper myDb;

    private View.OnClickListener buttonClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Cursor cursor=myDb.getRoomNotApproved();
            if (v.getId() == com.example.supermain.R.id.roomStatus){
                goToRoomStatus();
            }
            else if (v.getId() == com.example.supermain.R.id.toManageButton){
                goToManage();
            }
            else if ((v.getId() == com.example.supermain.R.id.roomApprove)&& cursor.getCount()==0){
                Toast.makeText(superMain.this, "No Rooms To Be Approved", Toast.LENGTH_SHORT).show();
            }
            else if (v.getId() == com.example.supermain.R.id.roomApprove){
                goToApprove();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.supermain.R.layout.supermain);
        statusButton = (Button)findViewById(com.example.supermain.R.id.roomStatus);
        manageButton=(Button)findViewById(com.example.supermain.R.id.toManageButton);
        TARButton=findViewById(com.example.supermain.R.id.roomApprove);
        myDb=new com.example.databasehelper.DataBaseHelper(this);
        statusButton.setOnClickListener(buttonClickListener);
        manageButton.setOnClickListener(buttonClickListener);
        TARButton.setOnClickListener(buttonClickListener);
        logoutBtn=(ImageButton)findViewById(com.example.supermain.R.id.LogoutbtnMain);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchRoomIntent = new Intent(superMain.this, MainActivity.class);
                startActivity(launchRoomIntent);
                Toast.makeText(superMain.this, "Logged out", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void goToRoomStatus(){
        Intent intent = new Intent(this, checkStatus.class);
        startActivity(intent);
    }

    public void goToManage(){
        Intent intent = new Intent(this, superCheckusage.class);
        startActivity(intent);
    }

    public void goToApprove(){
        Intent intent = new Intent(this, approveRoomMain.class);
        startActivity(intent);
    }
}