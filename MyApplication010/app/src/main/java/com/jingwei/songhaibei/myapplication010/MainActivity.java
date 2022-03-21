package com.jingwei.songhaibei.myapplication010;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout myLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
    }
    public void myBtnLeftClick(View v) {//响应单击按钮“靠左对齐”
        //设置线性布局管理器靠左对齐
        myLinearLayout.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
    }

    public void myBtnCenterClick(View v) {//响应单击按钮“居中对齐”
        //设置线性布局管理器居中对齐
        myLinearLayout.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
    }

    public void myBtnRightClick(View v) {//响应单击按钮“靠右对齐”
        //设置线性布局管理器靠右对齐
        myLinearLayout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    }
}