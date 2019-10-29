package com.example.ipc_provider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hp on 2019/10/29.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String db_name = "book.db";
    public static final String DB_NAME_BOOK = "book";
    public static final String DB_NAME_USER = "user";

    public DbOpenHelper(Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("tag", "dbOpenHelper,onCreate!");
        db.execSQL("create table " + DB_NAME_BOOK + "(bookId INTEGER PRIMARY KEY AUTOINCREMENT,bookName TEXT)");
        db.execSQL("create table " + DB_NAME_USER + "(userId INTEGER PRIMARY KEY AUTOINCREMENT,userName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
