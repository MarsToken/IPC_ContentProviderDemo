package com.example.ipc_provider.bean;

import java.io.Serializable;

/**
 * Created by hp on 2019/10/29.
 */
public class Book implements Serializable {
    public int bookId;
    public String bookName;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
