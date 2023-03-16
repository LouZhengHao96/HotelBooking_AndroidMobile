package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;

public class StaffCreateRoom extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private Spinner DurationS, FloorS, RoomS, CapacityS;
    private EditText PriceS,PromoCodeS;
    private Button DateS, MakeRoom;
    private DatePickerDialog.OnDateSetListener sDateSetListener;
    private com.example.databasehelper.DataBaseHelper myDb;
    private Calendar calendar = Calendar.getInstance();
    private ImageButton imageButton;
    private int year;
    private int month;
    private int day;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.staffmainmenuv2.R.layout.activity_staff_create);
        imageButton = (ImageButton)findViewById(com.example.staffmainmenuv2.R.id.homeButtonBook);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StaffMainMenu.class);
                finish();
                startActivity(intent);
            }
        });
        DurationS = findViewById(com.example.staffmainmenuv2.R.id.DurationS);
        FloorS = findViewById(com.example.staffmainmenuv2.R.id.FloorS);
        RoomS = findViewById(com.example.staffmainmenuv2.R.id.RoomS);
        CapacityS = findViewById(com.example.staffmainmenuv2.R.id.CapacityS);
        DateS = findViewById(com.example.staffmainmenuv2.R.id.DateS);
        MakeRoom=findViewById(com.example.staffmainmenuv2.R.id.MakeRoom);
        PriceS=findViewById(com.example.staffmainmenuv2.R.id.PriceS);
        PromoCodeS=findViewById(com.example.staffmainmenuv2.R.id.PromoCodeS);
        myDb= new com.example.databasehelper.DataBaseHelper(StaffCreateRoom.this);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

    //Date picker
    DateS.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
            DatePickerDialog DateSelectFragment = DatePickerDialog.newInstance(StaffCreateRoom.this, year, month, day);
            DateSelectFragment.setMinDate(calendar);
            DateSelectFragment.show(getSupportFragmentManager(), "DatePickerDialog");
        }
    });

    //Duration/Floor/Room/Capacity
    ArrayAdapter<CharSequence> DurationA = ArrayAdapter.createFromResource(this, com.example.staffmainmenuv2.R.array.DurationArray, android.R.layout.simple_spinner_item);
        DurationA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DurationS.setAdapter(DurationA);

    ArrayAdapter<CharSequence> FloorA = ArrayAdapter.createFromResource(this, com.example.staffmainmenuv2.R.array.FloorArray, android.R.layout.simple_spinner_item);
        FloorA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FloorS.setAdapter(FloorA);

    ArrayAdapter<CharSequence> RoomA = ArrayAdapter.createFromResource(this, com.example.staffmainmenuv2.R.array.RoomArray, android.R.layout.simple_spinner_item);
        RoomA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoomS.setAdapter(RoomA);

    ArrayAdapter<CharSequence> CapacityA = ArrayAdapter.createFromResource(this, com.example.staffmainmenuv2.R.array.CapacityArray, android.R.layout.simple_spinner_item);
        CapacityA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CapacityS.setAdapter(CapacityA);


//On btn click
        MakeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidRm=myDb.isValidRoom(selectedDate, RoomS.getSelectedItem().toString(),FloorS.getSelectedItem().toString(),DurationS.getSelectedItem().toString());
                if(DateS.getText().toString().equals("Select date"))
                {
                    Toast.makeText(StaffCreateRoom.this, "Please enter a Date", Toast.LENGTH_LONG).show();
                }
                else if(PriceS.getText().toString().equals(""))
                {
                    Toast.makeText(StaffCreateRoom.this, "Please enter a Price", Toast.LENGTH_LONG).show();
                }
                else if(isValidRm==false)
                {
                    Toast.makeText(StaffCreateRoom.this, "Room already exists", Toast.LENGTH_LONG).show();
                }

                else {
                    boolean results = myDb.addRoom(selectedDate, DurationS.getSelectedItem().toString(), FloorS.getSelectedItem().toString(), RoomS.getSelectedItem().toString(), CapacityS.getSelectedItem().toString(), PriceS.getText().toString(), PromoCodeS.getText().toString());
                    if (results == true) {
                        Toast.makeText(StaffCreateRoom.this, "Room Created", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(StaffCreateRoom.this, StaffMainMenu.class);
                        startActivity(intent);


                    }
                }
            }
        });



    }

    //On date set for datepicker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        selectedDate = String.valueOf(dayOfMonth) + '-' + String.valueOf(monthOfYear + 1) + '-' + String.valueOf(year);
        String selectedDateDisplay = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        DateS.setText(selectedDateDisplay);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String text=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
