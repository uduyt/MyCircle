package com.mycircle;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class myDataBaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private String upgradeMode;

    public static final String DATABASE_NAME="privatedatabase.db";
    public static int DATABASE_VERSION=1;

    //Contacts table columns
    public static final String CONTACTS_TABLE_NAME="table_user_circles";
    public static final String CONTACTS_UID="_id";

    public static final String CONTACTS_PERSON_ID="person_id";
    public static final String CONTACTS_NAME="name";
    public static final String CONTACTS_ALIAS="alias";
    public static final String CONTACTS_LATITUDE="latitude";
    public static final String CONTACTS_LONGITUDE="longitude";

    //User circles table columns
    public static final String CIRCLES_TABLE_NAME="table_user_circles";
    public static final String CIRCLES_UID="_id";

    public static final String CIRCLES_CIRCLE_NAME="circle_name";
    public static final String CIRCLES_CIRCLE_ID="circle_id";
    public static final String CIRCLES_CIRCLE_STATE="circle_state";
    public static final String CIRCLES_CREATOR_ID="creator_id";
    public static final String CIRCLES_PERSONS="persons";

    //Single circle table columns
   /* public static final String CIRCLE_TABLE_NAME="circle";
    public static final String CIRCLE_UID="_id";

    public static final String CIRCLE_PERSON_ID="person_id";*/

    public myDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Circles table
        String query1="CREATE TABLE " + CIRCLES_TABLE_NAME + " (" + CIRCLES_UID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CIRCLES_CIRCLE_NAME + "VARCHAR(255), "  + CIRCLES_CIRCLE_ID + "INT(2147483647), "
                + CIRCLES_CIRCLE_STATE + "VARCHAR(255), "  + CIRCLES_CREATOR_ID + "INT(2147483647), "
                + CIRCLES_PERSONS + "INT(2147483647))";
        try {
            db.execSQL(query1);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "error creating circles database",Toast.LENGTH_LONG).show();
        }

        //Create Contacts table
        String query2="CREATE TABLE " + CONTACTS_TABLE_NAME + " (" + CONTACTS_UID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CONTACTS_PERSON_ID + "INT(2147483647), "  + CONTACTS_NAME + "VARCHAR(255), "
                + CONTACTS_ALIAS + "VARCHAR(255), "  + CONTACTS_LATITUDE + "DECIMAL(10,20) "
                + CONTACTS_LONGITUDE + "DECIMAL(10,20))";
        try {
            db.execSQL(query2);

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "error creating contacts database",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void AddCircleTable(Circle circle, SQLiteDatabase db){



        /*String query="CREATE TABLE " + CIRCLE_TABLE_NAME + String.valueOf(circleId)+ " (" + CIRCLE_UID
                + "INTEGER PRIMARY KEY AUTOINCREMENT, " + CIRCLE_PERSON_ID + "INT(2147483647))";

        try {
            db.execSQL(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

}
