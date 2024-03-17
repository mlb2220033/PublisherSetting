package com.tranduythanh.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tranduythanh.bookstore.MyContactAdvancedActivity;
import com.tranduythanh.bookstore.R;
import com.tranduythanh.model.MyContact;

public class MyContactAdapter extends ArrayAdapter<MyContact> {
    Activity context;
    int resource;

    public MyContactAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View mycontact_item=inflater.inflate(resource, null);
        TextView txtContactName=mycontact_item.findViewById(R.id.txtContactName);
        TextView txtPhoneNumber=mycontact_item.findViewById(R.id.txtPhoneNumber);
        ImageView imgCall=mycontact_item.findViewById(R.id.imgCall);
        ImageView imgSms=mycontact_item.findViewById(R.id.imgSms);

        MyContact contact=getItem(position);
        txtContactName.setText(contact.getContactName());
        txtPhoneNumber.setText(contact.getPhoneNumber());

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyContactAdvancedActivity)context).makeDirectCal(contact);
            }
        });
        imgSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyContactAdvancedActivity)context).makeSms(contact);
            }
        });
        return mycontact_item;


    }
}
