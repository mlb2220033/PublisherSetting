package com.tranduythanh.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tranduythanh.bookstore.R;
import com.tranduythanh.model.Book;

public class BookAdapter extends ArrayAdapter<Book> {
    Activity context;
    int resource;
    public BookAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=context.getLayoutInflater().inflate(resource,null);
        TextView txtBookId=view.findViewById(R.id.txtPublisherId);
        TextView txtBookName=view.findViewById(R.id.txtPublisherName);
        TextView txtUnitPrice=view.findViewById(R.id.txtUnitPrice);

        Book book=getItem(position);
        txtBookId.setText(book.getBookId());
        txtBookName.setText(book.getBookName());
        txtUnitPrice.setText(book.getUnitPrice()+"");

        return view;
    }
}
