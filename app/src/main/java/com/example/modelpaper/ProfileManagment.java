package com.example.modelpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.time.Duration;

public class ProfileManagment extends AppCompatActivity {

    private Button updateProfileButton;
    private Button registerProfileButton;
    private DBHelper database;
    private EditText userName, dob, password;
    private String Gender;
    private RadioButton maleGender, femaleGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_managment);

        database = new DBHelper(this);
        updateProfileButton = (Button)findViewById(R.id.updateProfileBtn);
        registerProfileButton = (Button)findViewById(R.id.registerProfileBtn);
        userName = (EditText) findViewById(R.id.userNameEditText);
        dob = (EditText) findViewById(R.id.dobEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        maleGender = (RadioButton) findViewById(R.id.maleRadio);
        femaleGender = (RadioButton) findViewById(R.id.femaleRadio);

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditProfile.class);
                startActivity(intent);
            }
        });

        registerProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userNameVal = userName.getText().toString();
                final String passwordVal = password.getText().toString();
                final String dobVal = dob.getText().toString();
                final String genderVal = getGender();

                if(!genderVal.isEmpty() && !userNameVal.isEmpty() && !passwordVal.isEmpty() && !dobVal.isEmpty()){
                    long statusCode = database.addInfo(
                        userNameVal,
                        passwordVal,
                        dobVal,
                        genderVal
                    );

                    if(statusCode > -1)
                    {
                        Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Fill relevant fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getGender(){

        if(maleGender.isChecked()){
            return "Male";
        }
        else if(femaleGender.isChecked()){
            return  "Female";
        }

        return null;
    }
}