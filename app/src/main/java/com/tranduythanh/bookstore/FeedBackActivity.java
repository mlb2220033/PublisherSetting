package com.tranduythanh.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FeedBackActivity extends AppCompatActivity {
    EditText edtContentFeedBack;
    Button btnSubmitFeedBack;

    Intent previousIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        addViews();
        getDataFromPreviousActivity();
        addEvents();
    }

    private void addEvents() {
        btnSubmitFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=edtContentFeedBack.getText().toString();
                previousIntent.putExtra("CONTENT_FEEDBACK",content);
                setResult(2,previousIntent);
                finish();
            }
        });

    }

    private void getDataFromPreviousActivity() {
        previousIntent=getIntent();
    }

    private void addViews() {
        edtContentFeedBack=findViewById(R.id.edtContentFeedBack);
        btnSubmitFeedBack=findViewById(R.id.btnSubmitFeedBack);
    }
}