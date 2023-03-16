package com.example.staffcreateroom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



import java.util.Calendar;

public class StaffCreateRoom extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner DurationS, FloorS, RoomS, CapacityS;
    private EditText DateS,PriceS,PromoCodeS;
    private Button MakeRoom;
    private DatePickerDialog.OnDateSetListener sDateSetListener;
    private com.example.databasehelper.DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_create);
        DurationS = findViewById(R.id.DurationS);
        FloorS = findViewById(R.id.FloorS);
        RoomS = findViewById(R.id.RoomS);
        CapacityS = findViewById(R.id.CapacityS);
        DateS = findViewById(R.id.DateS);
        MakeRoom=findViewById(R.id.MakeRoom);
        PriceS=findViewById(R.id.PriceS);
        PromoCodeS=findViewById(R.id.PromoCodeS);
        myDb= new com.example.databasehelper.DataBaseHelper(StaffCreateRoom.this);
    ;

    //Date picker
    DateS.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(StaffCreateRoom.this,
                 sDateSetListener, year, month, day);
        dialog.getWindow();
        dialog.show();
    }
    });
    sDateSetListener= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
           month=month+1;
           String date=dayOfMonth+"/"+month+"/"+year;
           DateS.setText(date);
        }
    };

    //Duration/Floor/Room/Capacity
    ArrayAdapter<CharSequence> DurationA = ArrayAdapter.createFromResource(this, R.array.DurationArray, android.R.layout.simple_spinner_item);
        DurationA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DurationS.setAdapter(DurationA);

    ArrayAdapter<CharSequence> FloorA = ArrayAdapter.createFromResource(this, R.array.FloorArray, android.R.layout.simple_spinner_item);
        FloorA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FloorS.setAdapter(FloorA);

    ArrayAdapter<CharSequence> RoomA = ArrayAdapter.createFromResource(this, R.array.RoomArray, android.R.layout.simple_spinner_item);
        RoomA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoomS.setAdapter(RoomA);

    ArrayAdapter<CharSequence> CapacityA = ArrayAdapter.createFromResource(this, R.array.CapacityArray, android.R.layout.simple_spinner_item);
        CapacityA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CapacityS.setAdapter(CapacityA);


//On btn click
        MakeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean results=myDb.addRoom(DateS.getText(),DurationS.getSelectedItem().toString(),FloorS.getSelectedItem().toString(),RoomS.getSelectedItem().toString(),CapacityS.getSelectedItem().toString(),PriceS.getText(),PromoCodeS.getText());
                if (results==true){
                    Toast.makeText(StaffCreateRoom.this,"just press the back button", Toast.LENGTH_LONG).show();
                }

            }
        });



}


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String text=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
