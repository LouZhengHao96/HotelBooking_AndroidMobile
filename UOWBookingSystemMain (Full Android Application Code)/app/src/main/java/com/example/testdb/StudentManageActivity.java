package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasehelper.DataBaseHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StudentManageActivity extends AppCompatActivity {

    DataBaseHelper myDb = new DataBaseHelper(StudentManageActivity.this);
    private RecyclerView parentView;
//    private RecyclerView childView;
    private ImageButton homeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.uowroombooking.R.layout.activity_student_manage);

        parentView = (RecyclerView)findViewById(com.example.uowroombooking.R.id.parentView);
        homeButton = (ImageButton)findViewById(com.example.uowroombooking.R.id.homeButton2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(StudentManageActivity.this);
        StudentManageParentAdapter parentAdapter = new StudentManageParentAdapter(initParent());
        parentView.setAdapter(parentAdapter);
        parentView.setLayoutManager(layoutManager);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    public ArrayList<StudentManageParent> initParent(){
        ArrayList<StudentManageParent> resultArray = new ArrayList<>();

        //StudentManageParent is a arraylist item that accepts (String, ArrayList<StudentManageChild>) as parameters
        ArrayList<String> data = myDb.getStudentBookedDates();
        for(int i = 0; i < data.size(); i++){
            Calendar date = myDb.stringToCalendar(data.get(i));
            String dateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date.getTime());
            StudentManageParent temp = new StudentManageParent(dateString, initChild(data.get(i)));
            resultArray.add(temp);
        }
        return resultArray;
    }

    public ArrayList<StudentManageChild> initChild(String date){
        ArrayList<StudentManageChild> resultArray = new ArrayList<>();
        ArrayList<String> data = myDb.getStudentBookedSlots(date);
        for (int i = 0; i < data.size(); i++){
            String roomID = data.get(i);
            ArrayList<String> temp = myDb.getRoomDataFromRoomID(roomID);
            StudentManageChild newChild = new StudentManageChild(temp.get(0), temp.get(1), roomID);
            resultArray.add(newChild);
        }
        return resultArray;
    }

    public void goHome(){
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

}