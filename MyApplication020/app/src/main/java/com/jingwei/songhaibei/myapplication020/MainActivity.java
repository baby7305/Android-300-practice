package com.jingwei.songhaibei.myapplication020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PageView myPageView = (PageView) findViewById(R.id.myPageView);
        myPageView.setAdapter(new PageAdapter(this));
    }
}