package com.tranduythanh.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Publisher implements Serializable {
    private String publisherId;
    private String publisherName;
    private ArrayList<Book>books;
    private ArrayList<Publisher>publishersby;



    public Publisher() {
        books=new ArrayList<>();
        publishersby=new ArrayList<>();
    }

    public Publisher(String publisherId, String publisherName) {
        this();
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
    

    @NonNull
    @Override
    public String toString() {
        String msg=publisherId+"-"+publisherName;
        return msg;
    }


    public void setPublishers(ArrayList<Publisher> publishersby) {
        this.publishersby=publishersby;
    }
}
