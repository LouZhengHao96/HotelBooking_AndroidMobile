package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class StudentChangeBookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button stuChangeBookDateButton;
    private Button stuChangeCheckButton;
    private ImageButton homeButton;
    private Calendar outCalendar = Calendar.getInstance();
    private String chosenDateHeader;
    private String chosenDate;
    private String exemptedRoomID;
    private String origCost;

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

    private View.OnClickListener dateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog DateSelectFragment = DatePickerDialog.newInstance(StudentChangeBookingActivity.this, outCalendar.get(Calendar.YEAR), outCalendar.get(Calendar.MONTH), outCalendar.get(Calendar.DAY_OF_MONTH));
            if(getSelectableDates().length > 0){
                DateSelectFragment.setSelectableDays(getSelectableDates());
                DateSelectFragment.show(getSupportFragmentManager(), "DatePickerDialog");
            }
            else{
                Toast.makeText(StudentChangeBookingActivity.this, "No available booking dates found", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getSelectableDates().length > 0){
                goToCheckRooms();
            }
            else{
                Toast.makeText(StudentChangeBookingActivity.this, "Unable to proceed as no valid date was selected", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.uowroombooking.R.layout.activity_change_booking);
        Intent intent = getIntent();
        exemptedRoomID = intent.getStringExtra("exemptedRoomID");
        origCost = intent.getStringExtra("origCost");

        stuChangeBookDateButton = (Button)findViewById(com.example.uowroombooking.R.id.stuChangeBookDateButton);
        stuChangeCheckButton = (Button)findViewById(com.example.uowroombooking.R.id.stuChangeCheckButton);
        homeButton = (ImageButton)findViewById(com.example.uowroombooking.R.id.homeButtonBook);

        stuChangeBookDateButton.setOnClickListener(dateClickListener);
        stuChangeCheckButton.setOnClickListener(buttonClickListener);
        homeButton.setOnClickListener(homeListener);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        outCalendar.set(Calendar.YEAR, year);
        outCalendar.set(Calendar.MONTH, monthOfYear);
        outCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(outCalendar.getTime());
        stuChangeBookDateButton.setText(selectedDate);
        chosenDate = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(year);
        chosenDateHeader = DateFormat.getDateInstance(DateFormat.MEDIUM).format(outCalendar.getTime());
        Toast.makeText(this, "New Booking Date Selected", Toast.LENGTH_SHORT).show();
    }

    // method to get all dates with at least one room that has status available or approved to be booked
    public Calendar[] getSelectableDates(){
        com.example.databasehelper.DataBaseHelper myDb = new com.example.databasehelper.DataBaseHelper(StudentChangeBookingActivity.this);
        ArrayList<Calendar> roomDates = myDb.getAvailRoomDates(exemptedRoomID);
        Calendar[] resultArray = new Calendar[roomDates.size()];
        for(int i = 0; i < roomDates.size(); i++){
            resultArray[i] = roomDates.get(i);
        }
        return resultArray;
    }

    public void goToCheckRooms(){
        Intent intent = new Intent(this, StudentCheckRoomActivity.class);
        intent.putExtra("chosenDateHeader", chosenDateHeader);
        intent.putExtra("chosenDate", chosenDate);
        intent.putExtra("origCost", origCost);
        intent.putExtra("exemptedRoomID", exemptedRoomID);
        finish();
        startActivity(intent);
    }


}