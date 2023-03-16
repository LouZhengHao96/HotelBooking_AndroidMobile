package com.example.testdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.databasehelper.DataBaseHelper;

public class StudentCheckPayment extends AppCompatActivity {

    private Button stuPayButton;
    private Button stuRoom;
    private TextView dateText;
    private EditText promoCode;
    private String chosenDateHeader;
    private String chosenDate;
    private String chosenRoom;
    private String chosenFloor;
    private String chosenTime;
    private String chosenID;
    private ImageButton homeButton;
    private DataBaseHelper myDb;
    private String cost;
    private String origCost;

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cost = myDb.getCost(chosenID);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(StudentCheckPayment.this);
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle("Payment Successful");

            alertBuilder.setPositiveButton("Manage Bookings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    goToManageBooking();
                    Toast.makeText(StudentCheckPayment.this, "Payment Done", Toast.LENGTH_SHORT).show();
                }
            });
            alertBuilder.setNegativeButton("Make Another Booking", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    goBook();
                    Toast.makeText(StudentCheckPayment.this, "Payment Done", Toast.LENGTH_SHORT).show();
                }
            });
            //no promo and new booking
            if(promoCode.getText().toString().matches("") && origCost.equals("")){
                String result = String.format("Total cost of booking: $%.2f", Double.parseDouble(cost));
                alertBuilder.setMessage(result);
                myDb.addBooking(chosenID, String.valueOf(cost));
                alertBuilder.show();

            }
            //all other new booking
            else if(origCost.equals("")){
                if(myDb.isValidPromocode(chosenID, promoCode.getText().toString()) == false){
                    AlertDialog.Builder alertBuilderWrong = new AlertDialog.Builder(StudentCheckPayment.this);
                    alertBuilderWrong.setCancelable(false);
                    alertBuilderWrong.setTitle("Promo Code Invalid! Try again");
                    alertBuilderWrong.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertBuilderWrong.show();
                }
                else{
                    Double discCost = Double.parseDouble(cost) * 0.7;
                    String result = String.format("Total cost of booking: $%.2f \nAfter discount (30%%): $%.2f", Double.parseDouble(cost), discCost);
                    alertBuilder.setMessage(result);
                    myDb.addBooking(chosenID, String.valueOf(discCost));
                    alertBuilder.show();
                }
            }
            //all change bookings
            else{
                double origCostVal = Double.parseDouble(origCost);
                double roomCost = Double.parseDouble(cost);
                String result = "";
                //refund no need promocode
                if(origCostVal > roomCost){
                    result = String.format("Original Booking Price: $%.2f \nCost of New Booking: $%.2f \nAmount refundable: $%.2f", origCostVal, roomCost, origCostVal - roomCost);
                    myDb.addBooking(chosenID, "0");
                }
                else if(origCostVal == roomCost){
                    result = String.format("No Cost Incurred");
                    myDb.addBooking(chosenID, "0");
                }
                //need to pay; check if have promocode
                else{
                    //no promocode
                    if(promoCode.getText().toString().matches("")){
                        result += String.format("Total cost of booking: $%.2f", roomCost - origCostVal);
                        alertBuilder.setMessage(result);
                        myDb.addBooking(chosenID, String.valueOf(roomCost - origCostVal));
                        alertBuilder.show();
                    }
                    //invalid promocode
                    else if(myDb.isValidPromocode(chosenID, promoCode.getText().toString()) == false){
                        AlertDialog.Builder alertBuilderWrong = new AlertDialog.Builder(StudentCheckPayment.this);
                        alertBuilderWrong.setCancelable(false);
                        alertBuilderWrong.setTitle("Promo Code Invalid! Try again");
                        alertBuilderWrong.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertBuilderWrong.show();
                    }
                    //valid promocode
                    else{
                        double newCost = roomCost * 0.7;
                        result = String.format("Original Booking Price: $%.2f \nNew Booking Cost (After 30%% OFF): $%.2f \n", origCostVal, newCost);
                        //if origCost is cheaper than discounted price
                        if(origCostVal < newCost){
                            result += String.format("Total Payable: $%.2f", newCost - origCostVal);
                            myDb.addBooking(chosenID, String.valueOf(origCostVal - newCost));
                        }
                        //if
                        else if(origCostVal == newCost){
                            result += String.format("No Cost Incurred");
                            myDb.addBooking(chosenID, "0");
                        }
                        else{
                            result += String.format("Total refundable: $%.2f", origCostVal - newCost);
                            myDb.addBooking(chosenID, "0");
                        }
                    }
                }
                alertBuilder.setMessage(result);
                alertBuilder.show();
            }
        }
    };

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goHome();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.uowroombooking.R.layout.activity_student_check_payment);
        stuPayButton = (Button)findViewById(com.example.uowroombooking.R.id.stuPayButton);
        stuRoom = (Button)findViewById(com.example.uowroombooking.R.id.stuRoom);
        dateText = (TextView)findViewById(com.example.uowroombooking.R.id.dateText);
        homeButton = (ImageButton)findViewById(com.example.uowroombooking.R.id.homeButton);
        myDb = new DataBaseHelper(StudentCheckPayment.this);
        promoCode =(EditText)findViewById(com.example.uowroombooking.R.id.promoCode);

        //get data from intent
        Intent intent = getIntent();
        chosenDateHeader = intent.getStringExtra("chosenDateHeader");
        chosenDate = intent.getStringExtra("chosenDate");
        chosenRoom = intent.getStringExtra("chosenRoom");
        chosenFloor = intent.getStringExtra("chosenFloor");
        chosenTime = intent.getStringExtra("chosenTime");
        chosenID = intent.getStringExtra("chosenID");
        origCost = intent.getStringExtra("origCost");


        dateText.setText(chosenDateHeader);
        stuRoom.setText("#"+ chosenFloor + "-" + chosenRoom + "   " + chosenTime);
        stuPayButton.setOnClickListener(buttonClickListener);
        homeButton.setOnClickListener(homeListener);
    }

    public void goToManageBooking(){
        Intent intent = new Intent(this, StudentManageActivity.class);
        startActivity(intent);
    }

    public void goHome(){
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }
    public void goBook(){
        Intent intent = new Intent(this, StudentBookingActivity.class);
        finish();
        startActivity(intent);
    }
}