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
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table remainder " +
                        "(id integer primary key, description text,type text,date text, time text,recurrent id)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertRemainder  (int id, String desc, String type, String date, String time, int recurrent)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("description", desc);
        contentValues.put("type", type);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("recurrent", recurrent);
        db.insert("remainder", null, contentValues);
        return true;
    }

    public Cursor getRemainder(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from remainder where id="+id+"", null );
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

    public String getAllRemainder()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from remainder", null );
        res.moveToFirst();
        StringBuffer result = new StringBuffer();
        while(res.isAfterLast() == false){
            StringBuffer str= new StringBuffer();
            str.append("******************");
            str.append("ID:"+res.getInt(res.getColumnIndex("id")));
            str.append("Desc:" + res.getString(res.getColumnIndex("description")));
            str.append("type:" + res.getString(res.getColumnIndex("type")));
            str.append("date:" + res.getString(res.getColumnIndex("date")));
            str.append("time:" + res.getString(res.getColumnIndex("time")));
            str.append("Recurrenct:"+res.getInt(res.getColumnIndex("recurrent")));
            str.append("******************");
            result.append(str.toString());
            res.moveToNext();
        }
        return result.toString();
    }
}
