package com.example.sqlite_addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "addressbook.db";
    public static final String TABLE_NAME = "details_table";
    public static final String COL_1 = "name";
    public static final String COL_2 = "address";
    public static final String COL_3 = "contactno";
    public static final String COL_4 = "emailid";

    public DatabaseHelper( Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COL_1 + " TEXT, " +
                        COL_2 + " TEXT, " +
                        COL_3 + " TEXT, " +
                        COL_4 + " TEXT PRIMARY KEY);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String address, String contactNo , String emailId){
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, address);
        contentValues.put(COL_3, contactNo);
        contentValues.put(COL_4, emailId);
        result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else return true;

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME,null);
        return res;
    }
}
