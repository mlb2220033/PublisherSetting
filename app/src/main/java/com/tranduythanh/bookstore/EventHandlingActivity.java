package com.tranduythanh.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EventHandlingActivity extends AppCompatActivity {

    Button btnChangeImage;
    ImageView imgStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_handling);
        addViews();
        addEvents();
    }

    private void addEvents() {
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgStudent.getTag()!=null)
                {
                    if(imgStudent.getTag().equals("man"))
                    {
                        imgStudent.setImageResource(R.mipmap.ic_woman);
                        imgStudent.setTag("woman");
                    }
                    else
                    {
                        imgStudent.setImageResource(R.mipmap.ic_man);
                        imgStudent.setTag("man");
                    }
                }
                else
                {
                    imgStudent.setImageResource(R.mipmap.ic_man);
                    imgStudent.setTag("man");
                }
            }
        });
    }

    private void addViews() {
        btnChangeImage=findViewById(R.id.btnChangeImage);
        imgStudent=findViewById(R.id.imgStudent);
    }

    public void openPhuongTrinhBac2Activity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, PhuongTrinhBac2Activity.class);
        startActivity(intent);
    }

    public void openCalendar2LunarActivity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, CalendarYear2LunarYearActivity.class);
        startActivity(intent);
    }

    public void openImageSlideShowActivity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, ImageSlideShowActivity.class);
        startActivity(intent);
    }

    public void openRuntimeViewActivity(View view) {
        Intent intent=new Intent(EventHandlingActivity.this, RuntimeViewActivity.class);
        startActivity(intent);
    }
}