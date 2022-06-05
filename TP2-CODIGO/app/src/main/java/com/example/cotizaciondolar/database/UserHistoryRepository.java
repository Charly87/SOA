package com.example.cotizaciondolar.database;

import static com.example.cotizaciondolar.database.UserHistoryContract.FeedEntry.COLUMN_NAME_LASTACCESS;
import static com.example.cotizaciondolar.database.UserHistoryContract.FeedEntry.COLUMN_NAME_USER;
import static com.example.cotizaciondolar.database.UserHistoryContract.FeedEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.cotizaciondolar.models.entities.UserLoginHistory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserHistoryRepository {

    private final Context context;

    public UserHistoryRepository(Context context) {
        this.context = context;
    }

    public long insertUserHistory(String username) {
        UserHistoryDb dbHelper = new UserHistoryDb(context);
        // Trae el repository en modo escritura
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC-3"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        String formattedDate = zonedDateTime.format(formatter);

        // Crea mapa de valores donde las claves son los nombres de las columnas de la bd
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USER, username);
        values.put(COLUMN_NAME_LASTACCESS, formattedDate);

        // Inserta la fila y devuelve el valor de la primary key
        return db.insert(TABLE_NAME, null, values);
    }

    public List<UserLoginHistory> getAllUserHistory() {
        UserHistoryDb dbHelper = new UserHistoryDb(context);
        // Trae el repository en modo lectura
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Especifica las columnas que se incorporaran en el SELECT en la query
        String[] projection = {
                BaseColumns._ID,
                COLUMN_NAME_USER,
                COLUMN_NAME_LASTACCESS
        };

        // Orden de seleccion
        String sortOrder =
                COLUMN_NAME_USER + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<UserLoginHistory> userLoginHistories = new ArrayList<>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME_USER));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME_LASTACCESS));

            UserLoginHistory userLoginHistory = new UserLoginHistory(name, date);
            userLoginHistories.add(userLoginHistory);
        }

        cursor.close();
        return userLoginHistories;
    }
}
