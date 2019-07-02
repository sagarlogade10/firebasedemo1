package com.example.sharman_1.firebasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_1 = "Id";
    public static final String Col_2 = "Name";
    public static final String COL_3 = "Email";
    public static final String COL_4 = "Password";
    public static SQLiteDatabase db;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT, Email Text, Password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate(db);

    }
    public long addUser(String Name, String Email,String Password)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("Email",Email);
        contentValues.put("Password",Password);
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return res;
    }

    public static void insert(String name,String email,String password){
        ContentValues cv = new ContentValues();
        cv.put(keys.COL_2, name);
        cv.put(keys.COL_3, email);
        cv.put(keys.COL_4 , password);

        db.insertOrThrow(keys.TABLE_NAME,null,cv);

    }




    public boolean checkUser(String Email, String Passsword){
        String[] columns= {COL_1};
        SQLiteDatabase db=getReadableDatabase();
        String selection=COL_3+"=?"+" and "+ COL_4+ "=?";
        String[] selectionArgs= {Email, Passsword};
        Cursor cursor=db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count =cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return true;
        else
            return false;


    }
}
