package com.feedbackedu.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.feedbackedu.myapplication.model.User;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userdb";

    private static final String TABLE_USER = "User";

    private static final String KEY_ID = "id";
    private static final String KEY_PICTURE = "picture";
    private static final String KEY_PASSWORD = "password";


    public DatabaseHandler(final Context context) {
        super(context, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        final String CREATE_CONTACTS_TABLE = "CREATE TABLE  " + DatabaseHandler.TABLE_USER + "(" + DatabaseHandler.KEY_ID + " TEXT,"
                + DatabaseHandler.KEY_PICTURE + " TEXT," + DatabaseHandler.KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.TABLE_USER);
        this.onCreate(db);
    }

    public boolean addUser(User u) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHandler.KEY_ID, u.getId());
        cv.put(DatabaseHandler.KEY_PICTURE, u.getPicture());
        cv.put(DatabaseHandler.KEY_PASSWORD, u.getPasscode());
        return this.getWritableDatabase().insert(DatabaseHandler.TABLE_USER, null, cv) >= 0;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();
        Cursor c = this.getWritableDatabase().rawQuery("select * from " + DatabaseHandler.TABLE_USER, new String[]{});
        if (c != null && c.moveToFirst()) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(DatabaseHandler.KEY_ID));
                String picture = c.getString(c.getColumnIndex(DatabaseHandler.KEY_PICTURE));
                String passcode = c.getString(c.getColumnIndex(DatabaseHandler.KEY_PASSWORD));
                users.add(new User(id, picture, passcode));
            }
        }
        return users;
    }

}
