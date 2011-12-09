package com.jeremyhaberman.restfulandroid.provider;

import android.net.Uri;

public class Constants {

    public static final String TABLE_NAME = "profiles";
    public static final String AUTHORITY = "com.jeremyhaberman.restfulandroid.profiles";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    // Columns in the Profiles database
    public static final String NAME = "name";
}
