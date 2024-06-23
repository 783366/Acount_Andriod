package com.example.account;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Account.class}, version = 1, exportSchema = false)
public abstract class AccountRoomDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();

    private static AccountRoomDatabase INSTANCE;

    static AccountRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AccountRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                            AccountRoomDatabase.class, "account_database")
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

