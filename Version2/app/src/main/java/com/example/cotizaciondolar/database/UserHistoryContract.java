package com.example.cotizaciondolar.database;

import android.provider.BaseColumns;

public final class UserHistoryContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserHistoryContract() {
    }/* Inner class that defines the table contents */

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "UserHistory";
        public static final String COLUMN_NAME_USER = "User";
        public static final String COLUMN_NAME_LASTACCESS = "LastAccess";
    }
}
