package com.jingwei.songhaibei.myapplication019;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int myCurrentColor = 0;
    final int[] myColors = new int[]{//定义一个颜色数组
            Color.rgb(122, 122, 0), Color.rgb(0, 122, 122), Color.rgb(122, 0, 0),
            Color.rgb(255, 255, 0), Color.rgb(0, 255, 255), Color.rgb(255, 0, 0)};
    //此处的6个ID是在activity_main.xml文件中添加的TextView的ID
    final int[] myIDs = new int[]{R.id.myView01, R.id.myView02, R.id.myView03,
            R.id.myView04, R.id.myView05, R.id.myView06};
    TextView[] myViews = new TextView[myIDs.length];
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {   //表明消息来自本程序所发送
            if (msg.what == 0x123) {
                for (int i = 0; i < myIDs.length; i++) {
                    myViews[i].setBackgroundColor(myColors[(i + myCurrentColor) % myIDs.length]);
                }
                myCurrentColor++;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < myIDs.length; i++) {
            myViews[i] = (TextView) findViewById(myIDs[i]);
        }
        new Timer().schedule(new TimerTask() {  //定义线程周期性地改变控件的背景颜色
            @Override
            public void run() {
                //发送空消息通知系统改变6个TextView组件的背景色
                myHandler.sendEmptyMessage(0x123);
            }
        }, 0, 300);
    }
}