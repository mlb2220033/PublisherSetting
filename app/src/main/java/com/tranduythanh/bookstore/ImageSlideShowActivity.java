package com.tranduythanh.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageSlideShowActivity extends AppCompatActivity {
    ImageView imgSmall1;
    ImageView imgSmall2;
    ImageView imgBig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide_show);
        addViews();
        addEvents();
    }

    private void addEvents() {
        imgSmall1.setOnClickListener(new MyClassEvent());
        imgSmall2.setOnClickListener(new MyClassEvent());
    }

    private void addViews() {
        imgSmall1=findViewById(R.id.imgSmall1);
        imgSmall2=findViewById(R.id.imgSmall2);
        imgBig=findViewById(R.id.imgBig);
    }

    class MyClassEvent implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            if(view.equals(imgSmall1))
            {
                imgBig.setImageResource(R.mipmap.ic_book_big1);
            }
            else if(view.equals(imgSmall2))
            {
                imgBig.setImageResource(R.mipmap.ic_book_big2);
            }
        }
    }

}