package com.jingwei.songhaibei.myapplication001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        //创建线性布局管理器
        LinearLayout myLinearLayout = new LinearLayout(this);
        //加载线性布局管理器
        super.setContentView(myLinearLayout);
        //在线性布局管理器中垂直布局控件
        myLinearLayout.setOrientation(LinearLayout.VERTICAL);
        //创建TextView控件
        final TextView myTextView = new TextView(this);
        //设置TextView控件的文本
        myTextView.setText("Hello,World!");
        //设置TextView控件的字体大小
        myTextView.setTextSize(20);
        //创建Button控件
        Button myButton = new Button(this);
        //设置Button控件的文本
        myButton.setText("刷新内容");
        //设置Button控件的字体大小
        myButton.setTextSize(20);
        //为Button控件添加Click单击事件响应方法
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextView.setText("Hello,Android炫酷应用实例集锦!\n当前日期：" + new java.util.Date());
            }
        });
        //在线性布局管理器中添加TextView控件
        myLinearLayout.addView(myTextView);
        //在线性布局管理器中添加Button控件
        myLinearLayout.addView(myButton);
    }
}