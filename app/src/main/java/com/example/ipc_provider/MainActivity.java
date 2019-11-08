package com.example.ipc_provider;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ipc_provider.bean.Book;
import com.example.ipc_provider.observer.ContentObserverHelper;
import com.example.ipc_provider.observer.IOnBookChangedListener;
import com.example.ipc_provider.provider.BookContentProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int checkResult = checkCallingOrSelfPermission("com.example.ipc_provider.provider.BookContentProvider");
        if (checkResult == PackageManager.PERMISSION_DENIED) {
            Log.e(TAG, "onBind failed,permission deny!");
            return;
        }
        //Uri uri = Uri.parse("content://com.example.ipc_provider.provider.BookContentProvider");//authorities
        ContentObserverHelper.getInstance().init(this, new IOnBookChangedListener() {
            @Override
            public void onBookChanged(List<Book> books) {
                for (int i = 0; i < books.size(); i++) {
                    Log.e(TAG, books.get(i).toString());
                }
            }
        });
        List<Book> books = ContentObserverHelper.getInstance().getBook();
        for (int i = 0; i < books.size(); i++) {
            Log.e(TAG, books.get(i).toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ContentObserverHelper.getInstance().unregisterContentObserver();
    }

    public void onClick(View view) {
        //insert
        ContentValues values = new ContentValues();
        values.put("bookId", 6);
        values.put("bookName", "testBook");
        Uri uri = BookContentProvider.BOOK_CONTENT_URI;
        getContentResolver().insert(uri, values);
    }
}
