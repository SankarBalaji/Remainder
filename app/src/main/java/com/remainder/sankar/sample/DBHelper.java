package com.remainder.sankar.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 9/12/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "remainder.db";
    //Remainder DB Table names
    public static final String REMAINDERS_TABLE_NAME = "remainders";
    public static final String REMAINDERS_TABLE_PRIMARYKEY = "remainder_number";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    //Column Index
    //New Remainders table
    private static final String REMAINDERS_COLUMN_ID = "Id";
    private static final String REMAINDERS_COLUMN_DESCRIPTION = "Description";
    private static final String REMAINDERS_COLUMN_TYPE = "Type";
    private static final String REMAINDERS_COLUMN_DATE = "Date";
    private static final String REMAINDERS_COLUMN_TIME = "Time";
    private static final String REMAINDERS_COLUMN_RECURRENT = "Recurrent";

    //remainder_number
    private static final String REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ROW ="Row";
    private static final String REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ID ="Id";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    //Used for Testing
    public void clearTablesOnInstall (){
        SQLiteDatabase db = this.getWritableDatabase();
        //Drop tables
        db.execSQL("DROP TABLE IF EXISTS "+REMAINDERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+REMAINDERS_TABLE_PRIMARYKEY);

        //Create Tables
        db.execSQL("create table "+REMAINDERS_TABLE_PRIMARYKEY+" ("+REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ROW+" integer primary key,"+REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ID+" integer)");
        db.execSQL(
                "create table " + REMAINDERS_TABLE_NAME +
                        "("+REMAINDERS_COLUMN_ID+" integer primary key, "
                        +REMAINDERS_COLUMN_DESCRIPTION+" text,"
                        +REMAINDERS_COLUMN_TYPE+" text,"
                        +REMAINDERS_COLUMN_DATE+" text,"
                        +REMAINDERS_COLUMN_TIME+" text,"
                        +REMAINDERS_COLUMN_RECURRENT+" integer)"
        );
        updateRemainderId(0, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + REMAINDERS_TABLE_NAME +
                        "(id integer primary key, description text,type text,date text, time text,recurrent id)"
        );
    }

    private void updateRemainderId (int row, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ROW, row);
        contentValues.put(REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ID, id);
        db.insert(REMAINDERS_TABLE_PRIMARYKEY, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertRemId (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ID, id);
        String whereClause = REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ROW+" = ?";
        String[] whereArguments = new String[] {"0"};
        db.update(REMAINDERS_TABLE_PRIMARYKEY, contentValues, whereClause, whereArguments);
        return true;
    }

    public boolean insertRemainder  (int id, String desc, String type, String date, String time, int recurrent)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMAINDERS_COLUMN_ID, id);
        contentValues.put(REMAINDERS_COLUMN_DESCRIPTION, desc);
        contentValues.put(REMAINDERS_COLUMN_TYPE, type);
        contentValues.put(REMAINDERS_COLUMN_DATE, date);
        contentValues.put(REMAINDERS_COLUMN_TIME, time);
        contentValues.put(REMAINDERS_COLUMN_RECURRENT, recurrent);
        db.insert(REMAINDERS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getRemainder(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+REMAINDERS_TABLE_NAME+" where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public int getLastRemainderId (){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+REMAINDERS_TABLE_PRIMARYKEY, null );
        res.moveToFirst();
        int lastRemId = res.getInt(res.getColumnIndex(REMAINDERS_TABLE_PRIMARYKEY_COLUMN_ID));
        return lastRemId;
    }

    public String getAllRemainder(String date)
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String tableToQuery =  REMAINDERS_TABLE_NAME;
        String[] columnsToReturn = new String[] {REMAINDERS_COLUMN_ID, REMAINDERS_COLUMN_DESCRIPTION,
                REMAINDERS_COLUMN_TYPE,REMAINDERS_COLUMN_DATE,REMAINDERS_COLUMN_TIME, REMAINDERS_COLUMN_RECURRENT};
        String selection = REMAINDERS_COLUMN_DATE+" =?";
        String[] selectionArgs = {date};
        Cursor res = db.query(tableToQuery, columnsToReturn, selection, selectionArgs, null, null, null);
        res.moveToFirst();
        StringBuffer result = new StringBuffer();
        result.append("I am in DBBBBBBBBBB");
        while(res.isAfterLast() == false){
            StringBuffer str= new StringBuffer();
            str.append("******************");
            str.append("ID:"+res.getInt(res.getColumnIndex(REMAINDERS_COLUMN_ID)));
            str.append("Desc:" + res.getString(res.getColumnIndex(REMAINDERS_COLUMN_DESCRIPTION)));
            str.append("type:" + res.getString(res.getColumnIndex(REMAINDERS_COLUMN_TYPE)));
            str.append("date:" + res.getString(res.getColumnIndex(REMAINDERS_COLUMN_DATE)));
            str.append("time:" + res.getString(res.getColumnIndex(REMAINDERS_COLUMN_TIME)));
            str.append("Recurrenct:"+res.getInt(res.getColumnIndex(REMAINDERS_COLUMN_RECURRENT)));
            str.append("******************");
            result.append(str.toString());
            res.moveToNext();
        }
        return result.toString();
    }

    public String getAllRemainder()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+REMAINDERS_TABLE_NAME, null );
        res.moveToFirst();
        StringBuffer result = new StringBuffer();
        result.append("I am in DBBBBBBBBBB");
        while(res.isAfterLast() == false){
            StringBuffer str= new StringBuffer();
            str.append("******************");
            str.append("ID:"+res.getInt(res.getColumnIndex(REMAINDERS_COLUMN_ID)));
            str.append("Desc:" + res.getString(res.getColumnIndex(REMAINDERS_COLUMN_DESCRIPTION)));
            str.append("type:" + res.getString(res.getColumnIndex(REMAINDERS_COLUMN_TYPE)));
            str.append("date:" + res.getString(res.getColumnIndex(REMAINDERS_COLUMN_DATE)));
            str.append("time:" + res.getString(res.getColumnIndex(REMAINDERS_COLUMN_TIME)));
            str.append("Recurrenct:"+res.getInt(res.getColumnIndex(REMAINDERS_COLUMN_RECURRENT)));
            str.append("******************");
            result.append(str.toString());
            res.moveToNext();
        }
        return result.toString();
    }
}
