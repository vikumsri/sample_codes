package com.example.modelpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.List;

public class EditProfile extends AppCompatActivity {

    private DBHelper database;
    private EditText userName, dob, password;
    private String Gender;
    private RadioButton maleGender, femaleGender;
    private Button editButton, deleteButton, searchButton;

    private String userNameVal;
    private String passwordVal;
    private String dateOfBirthVal;
    private String gender;
    private String userId;

    private boolean isSearched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        database = new DBHelper(this);

        userName = (EditText) findViewById(R.id.epUserNameEditText);
        dob = (EditText) findViewById(R.id.epDobEditText);
        password = (EditText) findViewById(R.id.epPasswordEditText);
        maleGender = (RadioButton) findViewById(R.id.epMaleRadio);
        femaleGender = (RadioButton) findViewById(R.id.epFemaleBtn);
        searchButton = (Button)findViewById(R.id.epSearchBtn);
        editButton = (Button)findViewById(R.id.epEditBtn);
        deleteButton = (Button)findViewById(R.id.epDeleteBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();

          getUserData();

        searchButton.setOnClickListener(v -> searchUserData());

        editButton.setOnClickListener(v -> editProfileDetails());


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUserInfo(userId);
                }
            });



    }

    private void getUserData() {

        userNameVal = userName.getText().toString();
        passwordVal = password.getText().toString();
        dateOfBirthVal = dob.getText().toString();
        gender = getGender();
    }

    private void deleteUserInfo(String userId) {
        database.deleteInfo(userId);
    }

    private void editProfileDetails() {

        getUserData();

        ContentValues cv = new ContentValues();

        cv.put(UserProfile.Users.COLUMN_USERNAME, userNameVal);
        cv.put(UserProfile.Users.COLUMN_PASSWORD, passwordVal);
        cv.put(UserProfile.Users.COLUMN_DOB, dateOfBirthVal);
        cv.put(UserProfile.Users.COLUMN_GENDER, gender);

         database.updateInfo(cv, userId);

    }

    private void searchUserData(){

        List userData = database.readAllInfor(userName.getText().toString());

        userId = userData.get(0).toString();
        userNameVal = userData.get(1).toString();
        passwordVal = userData.get(2).toString();
        dateOfBirthVal = userData.get(3).toString();
        gender = userData.get(4).toString();

        userName.setText(userNameVal);
        password.setText(passwordVal);
        dob.setText(dateOfBirthVal);

        if(userData.get(4).toString().equals("Male"))
        {
            maleGender.setChecked(true);
            femaleGender.setChecked(false);
        }
        else{
            femaleGender.setChecked(true);
            maleGender.setChecked(false);
        }

        isSearched = true;

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