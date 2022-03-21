package com.jingwei.songhaibei.myapplication016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    String[] myTitles = {"百度", "京东", "天猫", "搜狗", "微软", "新浪", "淘宝"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.myFrame,
                new WebFragment()).commit();
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.myLayout);
        for (int i = 0; i < myTitles.length; i++) {
            FrameLayout myFrameLayout = new FrameLayout(this);
            myFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //自动生成myFrameLayout帧布局ID值
            myFrameLayout.setId(View.generateViewId());
            //动态添加myFrameLayout布局(选项卡的标签)
            myLayout.addView(myFrameLayout);
            TitleFragment myTitleFragment = new TitleFragment();
            Bundle myBundle = new Bundle();
            myBundle.putString("myTitle", myTitles[i]);
            //传递选项卡的标签
            myTitleFragment.setArguments(myBundle);
            getSupportFragmentManager().beginTransaction().add(
                    myFrameLayout.getId(), myTitleFragment).commit();
        }
    }
}