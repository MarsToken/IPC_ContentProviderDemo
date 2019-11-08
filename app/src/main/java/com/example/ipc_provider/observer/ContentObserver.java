package com.example.ipc_provider.observer;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by hp on 2019/11/8.
 */
public class ContentObserver extends android.database.ContentObserver {
    private final String TAG = getClass().getName();
    private Handler mHandler;
    public static final int UPDATE_SIGN = 0x1;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public ContentObserver(Handler handler) {
        super(handler);
        mHandler = handler;
    }

    @Override
    public boolean deliverSelfNotifications() {
        Log.e(TAG, "deliverSelfNotifications");
        return super.deliverSelfNotifications();
    }

    /**
     * ContentProvider的内容发生变化时，触发该方法
     *
     * @param selfChange
     */
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.e(TAG, "onChange-------->");
    }

    /**
     * ContentProvider的内容发生变化时，触发该方法  这个与上方法相比多个Uri
     *
     * @param selfChange
     */
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.i(TAG, "onChange-------->" + uri);
        mHandler.sendEmptyMessage(UPDATE_SIGN);
    }
};
