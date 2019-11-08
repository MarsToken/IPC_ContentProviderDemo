package com.example.ipc_provider.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.ipc_provider.db.DbOpenHelper;

/**
 * Created by hp on 2019/9/17.
 */
public class BookContentProvider extends ContentProvider {
    private static final String TAG = "BookContentProvider";
    public static final String AUTHORITY = "com.example.ipc_provider.provider.BookContentProvider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    public static final int BOOK_URI_CODE = 1;
    public static final int USER_URI_CODE = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;

    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.e(TAG, "onCreate=" + Thread.currentThread().getName());
        mContext = getContext();
        mSQLiteDatabase = new DbOpenHelper(mContext).getWritableDatabase();
        mSQLiteDatabase.execSQL("delete from " + DbOpenHelper.DB_NAME_BOOK);
        mSQLiteDatabase.execSQL("delete from " + DbOpenHelper.DB_NAME_USER);
        mSQLiteDatabase.execSQL("insert into book values(1,'java');");
        mSQLiteDatabase.execSQL("insert into book values(2,'android');");
        mSQLiteDatabase.execSQL("insert into book values(3,'ios');");
        return mSQLiteDatabase != null;
    }

    @androidx.annotation.Nullable
    @Override
    public Cursor query(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String[] projection,
                        @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs,
                        @androidx.annotation.Nullable String sortOrder) {
        Log.e(TAG, "query=" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            try {
                throw new IllegalAccessException("Unsupported Uri:" + uri);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mSQLiteDatabase.query(tableName, projection, selection, selectionArgs, null, sortOrder, null);
    }

    @androidx.annotation.Nullable
    @Override
    public String getType(@androidx.annotation.NonNull Uri uri) {
        Log.e(TAG, "getType=" + Thread.currentThread().getName());
        return null;
    }

    @androidx.annotation.Nullable
    @Override
    public Uri insert(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values) {
        Log.e(TAG, "insert=" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            try {
                throw new IllegalAccessException("Unsupported Uri:" + uri);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mSQLiteDatabase.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String selection,
                      @androidx.annotation.Nullable String[] selectionArgs) {
        Log.e(TAG, "delete=" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            try {
                throw new IllegalAccessException("Unsupported Uri:" + uri);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        int count = mSQLiteDatabase.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values,
                      @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
        Log.e(TAG, "update=" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            try {
                throw new IllegalAccessException("Unsupported Uri:" + uri);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        int row = mSQLiteDatabase.update(tableName, values, selection, selectionArgs);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.DB_NAME_BOOK;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.DB_NAME_USER;
                break;
            default:
                break;
        }
        return tableName;
    }
}
