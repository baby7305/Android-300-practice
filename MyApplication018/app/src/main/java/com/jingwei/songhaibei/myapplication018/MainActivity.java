package com.jingwei.songhaibei.myapplication018;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public TextView myTextView;
    public static int x = 0;
    public static int y = 0;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                AbsoluteLayout.LayoutParams params =
                        new AbsoluteLayout.LayoutParams(256, 256, x++ % 756, y++ % 756);
                myTextView.setLayoutParams(params);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView = (TextView) findViewById(R.id.myTextView);
        new Timer().schedule(new TimerTask() {           //周期性地改变控件的坐标
            @Override
            public void run() {
                myHandler.sendEmptyMessage(0x123);
            }
        }, 0, 10);
    }
}