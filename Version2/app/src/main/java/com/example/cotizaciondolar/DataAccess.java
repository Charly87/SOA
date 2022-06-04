package com.example.cotizaciondolar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DataAccess {

    private Context context;

    public DataAccess(Context context) {
        this.context = context;
    }

    public void DataUssers(){String name; String date;}

    public long insertUserHistory(String username) {
        UserHistoryDb dbHelper = new UserHistoryDb(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());


        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserHistoryContract.FeedEntry.COLUMN_NAME_USER, username);
        values.put(UserHistoryContract.FeedEntry.COLUMN_NAME_LASTACCESS, formatter.format(date));

        // Insert the new row, returning the primary key value of the new row
        long idUserHistory = db.insert(UserHistoryContract.FeedEntry.TABLE_NAME, null, values);

        return idUserHistory;
    }

    public List getAllUserHistory() {

        UserHistoryDb dbHelper = new UserHistoryDb(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserHistoryContract.FeedEntry.COLUMN_NAME_USER,
                UserHistoryContract.FeedEntry.COLUMN_NAME_LASTACCESS
        };

        // Filter results WHERE "title" = 'My Title'
        //String selection = UserHistoryContract.FeedEntry.COLUMN_NAME_USER + " = ?";
        //String[] selectionArgs = { "Juancito" };
        String selection = null;
        String[] selectionArgs = null;

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserHistoryContract.FeedEntry.COLUMN_NAME_USER + " DESC";

        Cursor cursor = db.query(
                UserHistoryContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        //List items = new ArrayList<>();
        ArrayList<Users> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserHistoryContract.FeedEntry.COLUMN_NAME_USER));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserHistoryContract.FeedEntry.COLUMN_NAME_LASTACCESS));
            //items.add("Nombre: " + name + " Fecha: " + date);
            Users person = new Users();
            person.email=name;
            person.date=date;
            users.add(person);

        }
        cursor.close();

        return users;
    }
}
