package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.databasehelper.DataBaseHelper;

public class StudentActivity extends AppCompatActivity {

    private Button toBookButton;
    private Button toManageButton;
    private DataBaseHelper myDb;
    public ImageButton logoutBtn;

    private View.OnClickListener buttonClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if (v.getId() == com.example.uowroombooking.R.id.toBookButton){
                goToBook();
            }
            else if (v.getId() == com.example.uowroombooking.R.id.toManageButton ){
                if (myDb.hasBookings()){
                    goToManage();
                }
                else{
                    Toast.makeText(StudentActivity.this, "No bookings available", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.uowroombooking.R.layout.activity_student_main);
        toBookButton = (Button)findViewById(com.example.uowroombooking.R.id.toBookButton);
        toManageButton = (Button)findViewById(com.example.uowroombooking.R.id.toManageButton);
        logoutBtn=(ImageButton)findViewById(com.example.uowroombooking.R.id.LogoutbtnMainStu);
        myDb = new DataBaseHelper(StudentActivity.this);

        toBookButton.setOnClickListener(buttonClickListener);
        toManageButton.setOnClickListener(buttonClickListener);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchRoomIntent = new Intent(StudentActivity.this, MainActivity.class);
                startActivity(launchRoomIntent);
                Toast.makeText(StudentActivity.this, "Logged out", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void goToBook(){
        Intent intent = new Intent(this, StudentBookingActivity.class);
        startActivity(intent);
    }

    public void goToManage(){
        Intent intent = new Intent(this, StudentManageActivity.class);
        startActivity(intent);
    }


}