package com.example.cryptopass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Password.db";
    public static final String TABLE_NAME="Password_table";
    public static final String COL_1="ID";
    public static final String COL_2="ACCOUNT";
    public static final String COL_3="PASSWORD";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCOUNT TEXT,PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String account,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,account);
        contentValues.put(COL_3,password);
        long result =db.insert(TABLE_NAME,null,contentValues);
        if (result== -1){
            return false;
        }
        else
            return true;
    }


    public boolean updateList(String account,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,account);
        contentValues.put(COL_2,password);
        long result=db.update(TABLE_NAME,contentValues,"ACCOUNT = ?",new String[] {password});
        if (result==-1){
            return false;
        }
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


    public void deleteData(String ACCOUNT){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"ACCOUNT = ? ",new String[] {ACCOUNT});
        Log.d("Positionnnnn",""+db.delete(TABLE_NAME,"ACCOUNT = ? ",new String[] {ACCOUNT}));

    }
}
