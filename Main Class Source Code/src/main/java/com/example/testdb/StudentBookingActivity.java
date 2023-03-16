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


public class StudentBookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button stuBookDateButton;
    private Button checkButton;
    private ImageButton homeButton;
    private Calendar outCalendar = Calendar.getInstance();
    private String chosenDateHeader;
    private String chosenDate;

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
            DatePickerDialog DateSelectFragment = DatePickerDialog.newInstance(StudentBookingActivity.this, outCalendar.get(Calendar.YEAR), outCalendar.get(Calendar.MONTH), outCalendar.get(Calendar.DAY_OF_MONTH));
            if(getSelectableDates().length > 0){
                DateSelectFragment.setSelectableDays(getSelectableDates());
                DateSelectFragment.show(getSupportFragmentManager(), "DatePickerDialog");
            }
            else{
                Toast.makeText(StudentBookingActivity.this, "No available booking dates found", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(stuBookDateButton.getText().toString().equals("Tap to Select Date") == false){
                goToCheckRooms();
            }
            else{
                Toast.makeText(StudentBookingActivity.this, "Unable to proceed as no valid date was selected", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.uowroombooking.R.layout.activity_booking);
        stuBookDateButton = (Button)findViewById(com.example.uowroombooking.R.id.stuBookDateButton);
        checkButton = (Button)findViewById(com.example.uowroombooking.R.id.stuCheckButton);
        homeButton = (ImageButton)findViewById(com.example.uowroombooking.R.id.homeButtonBook);

        stuBookDateButton.setOnClickListener(dateClickListener);
        checkButton.setOnClickListener(buttonClickListener);
        homeButton.setOnClickListener(homeListener);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        outCalendar.set(Calendar.YEAR, year);
        outCalendar.set(Calendar.MONTH, monthOfYear);
        outCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(outCalendar.getTime());
        stuBookDateButton.setText(selectedDate);
        chosenDate = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(year);
        chosenDateHeader = DateFormat.getDateInstance(DateFormat.MEDIUM).format(outCalendar.getTime());
        Toast.makeText(this, "Booking Date Selected", Toast.LENGTH_SHORT).show();
    }

    // method to get all dates with at least one room that has status available or approved to be booked
    public Calendar[] getSelectableDates(){
        com.example.databasehelper.DataBaseHelper myDb = new com.example.databasehelper.DataBaseHelper(StudentBookingActivity.this);
        ArrayList<Calendar> roomDates = myDb.getAvailRoomDates();
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
        intent.putExtra("origCost", "");
        intent.putExtra("exemptedRoomID", "");
        startActivity(intent);
    }


}