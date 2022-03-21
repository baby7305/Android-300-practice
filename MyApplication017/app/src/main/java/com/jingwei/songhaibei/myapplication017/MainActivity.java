package com.jingwei.songhaibei.myapplication017;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost myTabhost = getTabHost();
        //加载TabHost布局
        LayoutInflater.from(this).inflate(R.layout.activity_main,
                myTabhost.getTabContentView(), true);
        //添加第一个标签页
        myTabhost.addTab(myTabhost.newTabSpec("tab1")
                .setIndicator("黎明之战").setContent(R.id.tab01));
        //添加第二个标签页
        myTabhost.addTab(myTabhost.newTabSpec("tab2")
                .setIndicator("权力之眼").setContent(R.id.tab02));
        //添加第三个标签页
        myTabhost.addTab(myTabhost.newTabSpec("tab3")
                .setIndicator("圆梦巨人").setContent(R.id.tab03));
    }
}