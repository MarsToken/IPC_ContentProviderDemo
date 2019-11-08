package com.example.ipc_provider.observer;

import com.example.ipc_provider.bean.Book;

import java.util.List;

/**
 * Created by hp on 2019/11/8.
 */
public interface IOnBookChangedListener {
    void onBookChanged(List<Book> book);
}
