package com.example.my_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "news.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "news";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ABSTRACT = "abstract";
    public static final String COLUMN_ARTICLE = "article";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_TIP = "tip";

    public NewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_ABSTRACT + " TEXT,"
                + COLUMN_ARTICLE + " TEXT,"
                + COLUMN_IMAGE_PATH + " TEXT,"
                + COLUMN_TIP + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
