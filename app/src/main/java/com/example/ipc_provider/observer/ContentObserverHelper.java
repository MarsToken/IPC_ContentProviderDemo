package com.example.ipc_provider.observer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ConfigurationHelper;
import android.util.Log;

import com.example.ipc_provider.bean.Book;
import com.example.ipc_provider.provider.BookContentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2019/11/8.
 */
public class ContentObserverHelper {
    private static final String TAG = "MainActivity";
    private Uri uri = BookContentProvider.BOOK_CONTENT_URI;
    private ContentObserver mContentObserver;
    private Context mContext;
    private IOnBookChangedListener mListener;
    private static volatile ContentObserverHelper mContentObserverHelper;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == ContentObserver.UPDATE_SIGN) {
                Log.e(TAG, "数据发生变化");
                if (null != mListener) {
                    mListener.onBookChanged(getBook());
                }
            }
            return false;
        }
    });

    private ContentObserverHelper() {

    }

    public static ContentObserverHelper getInstance() {
        if (null == mContentObserverHelper) {
            synchronized (ConfigurationHelper.class) {
                if (null == mContentObserverHelper) {
                    mContentObserverHelper = new ContentObserverHelper();
                }
            }
        }
        return mContentObserverHelper;
    }

    private void registerContentObserver() {
        mContext.getContentResolver().registerContentObserver(uri, false, mContentObserver);
    }

    private void setListener(IOnBookChangedListener listener) {
        mListener = listener;
    }

    public ContentObserverHelper init(Context context, IOnBookChangedListener listener) {
        mContentObserver = new ContentObserver(mHandler);
        mContext = context;
        registerContentObserver();
        setListener(listener);
        return this;
    }

    public void unregisterContentObserver() {
        mContext.getContentResolver().unregisterContentObserver(mContentObserver);
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public List<Book> getBook() {
        List<Book> list = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(uri, new String[]{"bookId", "bookName"}, null, null, null);
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.bookId = cursor.getInt(0);
            book.bookName = cursor.getString(1);
            list.add(book);
            //Log.e("tag", "query book:" + book.toString());
        }
        Log.e("tag", "finished!");
        cursor.close();
        return list;
    }
}
