package com.tranduythanh.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tranduythanh.bookstore.R;

import com.tranduythanh.model.Publisher;

public class AdvancedPublisherAdapter extends ArrayAdapter<Publisher> {
    Activity context;
    int resource;

    public AdvancedPublisherAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=context.getLayoutInflater().inflate(resource,null);
        TextView txtPublisherId=view.findViewById(R.id.txtPublisherId);
        TextView txtPublisherName=view.findViewById(R.id.txtPublisherName);




        Publisher publisher=getItem(position);
        txtPublisherId.setText(publisher.getPublisherId());
        txtPublisherName.setText(publisher.getPublisherName());

        return view;
    }
}