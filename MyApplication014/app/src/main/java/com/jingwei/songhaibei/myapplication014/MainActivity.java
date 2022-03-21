package com.jingwei.songhaibei.myapplication014;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout myTabLayout;
    private ViewPager myViewPager;
    private LayoutInflater myInflater;
    private List<String> myTitles = new ArrayList<String>();  //选项卡标题集合
    private List<View> myViews = new ArrayList<View>();       //选项卡页面集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        myTabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        InitView();
        //添加选项卡标题
        myTitles.add("京东");
        myTitles.add("天猫");
        myTitles.add("360");
        myTitles.add("搜狐");
        myTitles.add("百度");
        MyAdapter myAdapter = new MyAdapter(myViews, myTitles);
        myViewPager.setAdapter(myAdapter);          //给ViewPager设置适配器
        //将TabLayout和ViewPager关联起来
        myTabLayout.setupWithViewPager(myViewPager);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void InitView() {
        myInflater = LayoutInflater.from(this);
        String[] myWebsites = {"https://www.jd.com", "http://www.tmall.com",
                "http://www.360.cn", "http://www.sohu.com", "http://www.baidu.com"};
        for (int i = 1; i <= 5; i++) {
            View myView = myInflater.inflate(R.layout.myitem, null);
            WebView myWebView = (WebView) myView.findViewById(R.id.myWebView);
            myWebView.loadUrl(myWebsites[i - 1]);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new MyWebViewClient());
            myViews.add(myView);
        }
    }
}