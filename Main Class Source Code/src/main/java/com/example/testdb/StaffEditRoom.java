package com.example.testdb;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.databasehelper.DataBaseHelper;

import java.util.Calendar;

public class StaffEditRoom extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner RoomNumberSpinner,timingSpinner,FloorNumberSpinner,CapacitySpinner;
    private EditText editTextPrice,editTextPromoCode;
    private Button DateButton,updateBtn,launchBtn,deleteBtn;
    private String[]timeslotArray,RNArray,FNArray,CapArray;
    private int year;
    private int month;
    private int day;
    private String roomID;
    private String price;
    private String promocode;
    private String timeslot;
    private String date;
    private String RoomNo;
    private String FloorNo;
    private String Capacity;
    private String roomStatus;
    private DataBaseHelper myDB;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener ThisDateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.staffmainmenuv2.R.layout.activity_staff_edit_room);

        RoomNumberSpinner = findViewById(com.example.staffmainmenuv2.R.id.RoomNumberSpinner);
        FloorNumberSpinner= findViewById(com.example.staffmainmenuv2.R.id.FloorNumberSpinner);
        timingSpinner = findViewById(com.example.staffmainmenuv2.R.id.timingSpinner);
        editTextPrice = findViewById(com.example.staffmainmenuv2.R.id.editTextPrice);
        editTextPromoCode=findViewById(com.example.staffmainmenuv2.R.id.editTextPromoCode);
        DateButton=findViewById(com.example.staffmainmenuv2.R.id.DateButton);
        updateBtn=findViewById(com.example.staffmainmenuv2.R.id.updateBtn);
        launchBtn=findViewById(com.example.staffmainmenuv2.R.id.launchBtn);
        deleteBtn=findViewById(com.example.staffmainmenuv2.R.id.deleteBtn);
        CapacitySpinner=findViewById(com.example.staffmainmenuv2.R.id.CapacitySpinner);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        timeslotArray=getResources().getStringArray(com.example.staffmainmenuv2.R.array.DurationArray);
        RNArray=getResources().getStringArray(com.example.staffmainmenuv2.R.array.RoomArray);
        FNArray=getResources().getStringArray(com.example.staffmainmenuv2.R.array.FloorArray);
        CapArray=getResources().getStringArray(com.example.staffmainmenuv2.R.array.CapacityArray);

        Intent intent = getIntent();
        roomID = intent.getStringExtra("ID");
        price= intent.getStringExtra("price");
        promocode= intent.getStringExtra("promocode");
        timeslot=intent.getStringExtra("timeslot");
        date=intent.getStringExtra("date");
        RoomNo=intent.getStringExtra("room_no");
        FloorNo=intent.getStringExtra("floor_no");
        Capacity=intent.getStringExtra("capacity");
        roomStatus = intent.getStringExtra("room_status");

        int timeslotindex=getArray(timeslotArray,timeslot);
        int capacityindex=getArray(CapArray,Capacity);
        int floorindex=getArray(FNArray,FloorNo);
        int Roomindex=getArray(RNArray,RoomNo);

        editTextPrice.setText(price);
        editTextPromoCode.setText(promocode);
        DateButton.setText(date);
        DateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(StaffEditRoom.this,
                        ThisDateListener, year, month, day);
                dialog.getWindow();
                dialog.show();
            }
        });
        ThisDateListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                month=month+1;
                String date=dayOfMonth+"-"+month+"-"+year;
                DateButton.setText(date);
            }
        };

        // spinners for the different dropdown menu
        ArrayAdapter<CharSequence> timeslotAdapter = ArrayAdapter.createFromResource(this, com.example.staffmainmenuv2.R.array.DurationArray, android.R.layout.simple_spinner_item);
        timeslotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timingSpinner.setAdapter(timeslotAdapter);
        timingSpinner.setOnItemSelectedListener(this);
        timingSpinner.setSelection(timeslotindex);

        ArrayAdapter<CharSequence> roomSpinnerAdapter = ArrayAdapter.createFromResource(this, com.example.staffmainmenuv2.R.array.RoomArray, android.R.layout.simple_spinner_item);
        roomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoomNumberSpinner.setAdapter(roomSpinnerAdapter);
        RoomNumberSpinner.setOnItemSelectedListener(this);
        RoomNumberSpinner.setSelection(Roomindex);

        ArrayAdapter<CharSequence> floorAdapter = ArrayAdapter.createFromResource(this, com.example.staffmainmenuv2.R.array.FloorArray, android.R.layout.simple_spinner_item);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FloorNumberSpinner.setAdapter(floorAdapter);
        FloorNumberSpinner.setOnItemSelectedListener(this);
        FloorNumberSpinner.setSelection(floorindex);

        ArrayAdapter<CharSequence> CapacityAdapter = ArrayAdapter.createFromResource(this, com.example.staffmainmenuv2.R.array.CapacityArray, android.R.layout.simple_spinner_item);
        CapacityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CapacitySpinner.setAdapter(CapacityAdapter);
        CapacitySpinner.setOnItemSelectedListener(this);
        CapacitySpinner.setSelection(capacityindex);

        // update room click listener
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper myDB = new DataBaseHelper(StaffEditRoom.this);
                boolean update = myDB.updateRoom(roomID, DateButton.getText().toString(), timingSpinner.getSelectedItem().toString(), FloorNumberSpinner.getSelectedItem().toString(), RoomNumberSpinner.getSelectedItem().toString(), CapacitySpinner.getSelectedItem().toString(), editTextPrice.getText().toString(), editTextPromoCode.getText().toString());
                if (update == true) {
                    Toast.makeText(StaffEditRoom.this,"Room Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(StaffEditRoom.this,"Room Failed to Updated", Toast.LENGTH_LONG).show();
                }
                finish();
                startActivity(new Intent(StaffEditRoom.this, StaffLaunchRoom.class));
            }
        });

        // delete room click listener
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        // launch button click listener
        // changes status to update
        launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper myDB = new DataBaseHelper(StaffEditRoom.this);
                if (roomStatus.equals("NOT APPROVED")) {
                    Toast.makeText(StaffEditRoom.this, "Room not approved, can't launch", Toast.LENGTH_LONG).show();
                } if ((roomStatus.equals("NOT BOOKED")) || (roomStatus.equals("BOOKED"))) {
                    Toast.makeText(StaffEditRoom.this, "Room already launched", Toast.LENGTH_LONG).show();
                } if (roomStatus.equals("APPROVED")) {
                    myDB.launchRoom(roomID);
                    Toast.makeText(StaffEditRoom.this, "Room launched", Toast.LENGTH_LONG).show();
                }
                finish();
                startActivity(new Intent(StaffEditRoom.this, StaffLaunchRoom.class));
            }
        });
    }

    public void confirmDelete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Alert!");
        alert.setMessage("Are you sure you want to delete this room?");

        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataBaseHelper myDB = new DataBaseHelper(StaffEditRoom.this);
                myDB.deleteRoom(roomID);
                finish();
                startActivity(new Intent(StaffEditRoom.this, StaffLaunchRoom.class));
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.create().show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getArray(String[] Sarray,String compare)
    {
        int results=-1;
        for(int i=0;i<Sarray.length;i++) {
            if(compare.equals(Sarray[i]))
                results=i;

        }
        return results;
    }

}