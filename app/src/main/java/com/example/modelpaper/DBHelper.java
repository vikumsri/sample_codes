package com.example.modelpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE "+ UserProfile.Users.TABLE_NAME + " ("
                + UserProfile.Users._ID +" INTEGER PRIMARY KEY , "
                + UserProfile.Users.COLUMN_USERNAME +" , "
                + UserProfile.Users.COLUMN_PASSWORD +" , "
                + UserProfile.Users.COLUMN_DOB + " , "
                + UserProfile.Users.COLUMN_GENDER + " )";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addInfo(String username, String password, String dateOfBirth, String gender){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserProfile.Users.COLUMN_USERNAME, username);
        values.put(UserProfile.Users.COLUMN_DOB, dateOfBirth);
        values.put(UserProfile.Users.COLUMN_PASSWORD, password);
        values.put(UserProfile.Users.COLUMN_GENDER, gender);

        long newRowId = db.insert(UserProfile.Users.TABLE_NAME, null, values);

        return newRowId;

    }

    public List readAllInfor(){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
          UserProfile.Users._ID,
                UserProfile.Users.COLUMN_USERNAME,
                UserProfile.Users.COLUMN_PASSWORD,
                UserProfile.Users.COLUMN_DOB,
                UserProfile.Users.COLUMN_GENDER,

        };

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List Ids = new ArrayList();
        List Usernames = new ArrayList();
        List Passwords = new ArrayList();
        List DateOfBirths = new ArrayList();
        List Genders = new ArrayList();

        while(cursor.moveToNext()){

            Ids.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users._ID))));
            Usernames.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_USERNAME))));
            Passwords.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_PASSWORD))));
            DateOfBirths.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_DOB))));
            Genders.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_GENDER))));
        }

        List dataList = new ArrayList();

        dataList.add(Ids);
        dataList.add(Usernames);
        dataList.add(Passwords);
        dataList.add(DateOfBirths);
        dataList.add(Genders);

        return dataList;


    }

    public List readAllInfor(String userName){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UserProfile.Users._ID,
                UserProfile.Users.COLUMN_USERNAME,
                UserProfile.Users.COLUMN_PASSWORD,
                UserProfile.Users.COLUMN_DOB,
                UserProfile.Users.COLUMN_GENDER,

        };

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,
                projection,
                UserProfile.Users.COLUMN_USERNAME + " = "+ "'"+userName+"'",
                null,
                null,
                null,
                null
        );

        List UserData = new ArrayList();

        while(cursor.moveToNext()){

            UserData.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users._ID))));
            UserData.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_USERNAME))));
            UserData.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_PASSWORD))));
            UserData.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_DOB))));
            UserData.add(cursor.getString((cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_GENDER))));
        }

        return UserData;


    }

    public void updateInfo(ContentValues cv, String condition){

        SQLiteDatabase db = getWritableDatabase();

        db.update(UserProfile.Users.TABLE_NAME, cv, UserProfile.Users._ID+" = "+condition, null);

    }

    public void deleteInfo(String userId){

        SQLiteDatabase db = getWritableDatabase();

        db.delete(
                UserProfile.Users.TABLE_NAME,
                UserProfile.Users._ID + " = " + userId,
                null
        );
    }
}
