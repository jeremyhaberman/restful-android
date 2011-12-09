package com.jeremyhaberman.restfulandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.jeremyhaberman.restfulandroid.provider.Constants.NAME;
import static com.jeremyhaberman.restfulandroid.provider.Constants.TABLE_NAME;

public class ProfileData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "profiles.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Create a helper object for the Profile database
     */
    public ProfileData(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}

