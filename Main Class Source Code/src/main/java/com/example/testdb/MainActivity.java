package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    TextView Register_txt, showHideText;
    Button l_B;
    com.example.databasehelper.DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        l_B=findViewById(R.id.Login_B);
        Register_txt=findViewById(R.id.Register_txt);
        showHideText=findViewById(R.id.showHideText);
        myDb=new com.example.databasehelper.DataBaseHelper(MainActivity.this);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());


        //TO bring u to the register page
        Register_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            openRegisterPage();
            }
        });

        //Show or hide the password field
        showHideText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showHideText.getText().equals("Show Password")){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideText.setText("Hide Password");
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideText.setText("Show Password");
                }
            }
        });


        //TO verify login
        l_B.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                String userid=username.getText().toString().trim();
                String pwd=password.getText().toString().trim();
                Boolean Loginistrue= myDb.checkUser(userid,pwd);
                if(Loginistrue== true && userid.contains("STAFF"))
                {
                    Toast.makeText(MainActivity.this, "STAFF Successfully Logged in", Toast.LENGTH_LONG).show();
                    openStaffPage();

                }
                else if(Loginistrue== true && userid.contains("STU"))
                {
                    Toast.makeText(MainActivity.this, "STUDENT Successfully Logged in", Toast.LENGTH_LONG).show();
                    openStudentPage();
                }
                else if(Loginistrue== true && userid.contains("SUPER"))
                {
                    Toast.makeText(MainActivity.this, "SUPER Successfully Logged in", Toast.LENGTH_LONG).show();
                    openSuperPage();
                }
                else
                    Toast.makeText(MainActivity.this,"Failed to log in",Toast.LENGTH_LONG).show();



            }
        });
    }




    //Methods to bring you to different pages
    public void openRegisterPage(){
        Intent registerIntent= new Intent(this,RegisterPage.class);
        startActivity(registerIntent);
    }

    public void openStaffPage(){
        Intent StaffIntent= new Intent(this, StaffMainMenu.class);
        startActivity(StaffIntent);
    }

    public void openStudentPage(){
        Intent StudentIntent= new Intent(this, StudentActivity.class);
        startActivity(StudentIntent);
    }

    public void openSuperPage(){
        Intent SuperIntent= new Intent(this, superMain.class);
        startActivity(SuperIntent);
    }


}