package com.example.ipc_provider;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ipc_provider.bean.Book;
import com.example.ipc_provider.provider.BookContentProvider;

import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sayHello(null);
        int checkResult = checkCallingOrSelfPermission("com.example.ipc_provider.provider.BookContentProvider");
        if (checkResult == PackageManager.PERMISSION_DENIED) {
            Log.e(TAG, "onBind failed,permission deny!");
            return;
        }
        //Uri uri = Uri.parse("content://com.example.ipc_provider.provider.BookContentProvider");//authorities
        Uri uri = BookContentProvider.BOOK_CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put("bookId", 6);
        values.put("bookName", "testBook");
        getContentResolver().insert(uri, values);
        Cursor cursor = getContentResolver().query(uri, new String[]{"bookId", "bookName"}, null, null, null);
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.bookId = cursor.getInt(0);
            book.bookName = cursor.getString(1);
            Log.e("tag", "query book:" + book.toString());
        }
        Log.e("tag", "finished!");
        cursor.close();

//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);

    }

    private void sayHello(@NonNull String hello) {
        System.out.println(hello);
    }
}
