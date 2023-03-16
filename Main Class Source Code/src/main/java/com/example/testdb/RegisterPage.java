package com.example.testdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity {
    com.example.databasehelper.DataBaseHelper myDb;
    EditText usernameR, passwordR;
    Button Register_B;
    TextView showHideTextR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Register_B = findViewById(R.id.Register_B);
        usernameR = findViewById(R.id.usernameR);
        passwordR = findViewById(R.id.passwordR);
        showHideTextR=findViewById(R.id.showHideTextR);
        myDb = new com.example.databasehelper.DataBaseHelper(RegisterPage.this);
        passwordR.setTransformationMethod(PasswordTransformationMethod.getInstance());

        showHideTextR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showHideTextR.getText().equals("Show Password")){
                    passwordR.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideTextR.setText("Hide Password");
                }
                else{
                    passwordR.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideTextR.setText("Show Password");
                }
            }
        });

        //Register student into database
        Register_B.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              String userNameRTrim = usernameR.getText().toString().trim();
                                              String PasswordRTrim = passwordR.getText().toString().trim();
                                              if (userNameRTrim.matches("")|| PasswordRTrim.matches("")) {
                                                  Toast.makeText(RegisterPage.this, "Please enter a Username or Password", Toast.LENGTH_SHORT).show();}

                                              else if (userNameRTrim.contains("STU")==false && userNameRTrim.contains("STAFF") == false&& userNameRTrim.contains("SUPER")==false) {
                                                  Toast.makeText(RegisterPage.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
                                              }

                                              else if (myDb.Validate_User(userNameRTrim)==true){
                                                  boolean isInserted = myDb.addUser(usernameR.getText().toString().trim(), passwordR.getText().toString().trim());
                                                  if (isInserted == true) {
                                                      Toast.makeText(RegisterPage.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                                  }
                                                  Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                                                  startActivity(intent);
                                              }
                                              else
                                              Toast.makeText(RegisterPage.this, "Registration Failed. The username already exists", Toast.LENGTH_SHORT).show();
                                          }

                                      }
        );
    }
}