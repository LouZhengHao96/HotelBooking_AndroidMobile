package com.example.testdb;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class superCheckusage extends AppCompatActivity {

    private Button Date;
    private Button checkUsagebutton;
    private Spinner RoomNoSelection,FloorNoSelection;
    private TextView selectedDate;
    Calendar startDate=Calendar.getInstance();
    Calendar endDate=Calendar.getInstance();
    private String startDatestring;
    private String endDatestring;
    private String origDate;
    private com.example.databasehelper.DataBaseHelper myDb;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(com.example.supermain.R.layout.activity_super_checkusage);
        imageButton = (ImageButton)findViewById(com.example.supermain.R.id.imageButton3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), superMain.class);
                finish();
                startActivity(intent);
            }
        });

        RoomNoSelection=findViewById(com.example.supermain.R.id.RoomSelection);
        FloorNoSelection=findViewById(com.example.supermain.R.id.FloorSelection);

        ArrayAdapter<CharSequence> FloorA = ArrayAdapter.createFromResource(this, com.example.supermain.R.array.floorArray, android.R.layout.simple_spinner_item);
        FloorA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FloorNoSelection.setAdapter(FloorA);

        ArrayAdapter<CharSequence> RoomA = ArrayAdapter.createFromResource(this, com.example.supermain.R.array.roomArray, android.R.layout.simple_spinner_item);
        RoomA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoomNoSelection.setAdapter(RoomA);


        Date=findViewById(com.example.supermain.R.id.Datebutton);
        selectedDate=findViewById(com.example.supermain.R.id.selectedDate);
        origDate = selectedDate.getText().toString();
        checkUsagebutton=findViewById(com.example.supermain.R.id.checkUsagebutton);
        MaterialDatePicker.Builder<Pair<Long, Long>> builder=MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT DATE");
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker=builder.build();

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");

            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection)
            {
                startDate.setTimeInMillis(selection.first);
                endDate.setTimeInMillis(selection.second);
                selectedDate.setText("Selected Dates : "+ "\n"+ DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate.getTime()) + " " +" - " +DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate.getTime()));
                SimpleDateFormat date=new SimpleDateFormat("dd-M-yyyy");
                startDatestring=date.format(startDate.getTime());
                endDatestring=date.format(endDate.getTime());

            }

//            @Override
//            public void onPositiveButtonClick(Object selection) {
//                selectedDate.setText("Selected Dates : "+materialDatePicker.getHeaderText());
//            }
        });

        checkUsagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FloorNoSelection.getSelectedItem().toString().equals("Floor")){
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(superCheckusage.this);
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
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(superCheckusage.this);
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
                else if(selectedDate.getText().toString().equals(origDate)){
                    AlertDialog.Builder alert = new AlertDialog.Builder(superCheckusage.this);
                    alert.setTitle("Date Range Not Selected");
                    alert.setMessage("Please select a valid date range to continue.");
                    alert.setCancelable(false);
                    alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
                else{
                    Intent intent=new Intent(superCheckusage.this, checkInfo.class);
                    intent.putExtra("FloorNo",FloorNoSelection.getSelectedItem().toString());
                    intent.putExtra("RoomNo",RoomNoSelection.getSelectedItem().toString());
                    intent.putExtra("startDate",startDatestring);
                    intent.putExtra("endDate",endDatestring);
                    startActivity(intent);
                }
            }
        });

    }

}






