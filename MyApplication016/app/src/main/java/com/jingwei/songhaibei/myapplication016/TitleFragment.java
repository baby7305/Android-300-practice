package com.jingwei.songhaibei.myapplication016;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TitleFragment extends Fragment {
    String[] myTitles = {"百度", "京东", "天猫", "搜狗", "微软", "新浪", "淘宝"};
    String[] myUrls = {"http://www.baidu.com", "http://www.jd.com",
            "http://www.tmall.com", "http://www.sougou.com", "http://www.microsoft.com",
            "http://www.sina.com.cn", "http://www.taobao.com"};
    String myTitle;
    TextView myTextView;
    //用于存储标题和地址集合
    List<String> myTitleList = new ArrayList(), myUrlList = new ArrayList();

    @Override
    public void setArguments(Bundle args) {
        myTitle = args.getString("myTitle");
    }

    @Override
    //动态创建标题
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myTextView = new TextView(inflater.getContext());
        myTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.
                LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        myTextView.setText(myTitle);
        myTextView.setTextSize(18);
        myTextView.setPadding(0, 25, 0, 25);
        myTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        return myTextView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < myTitles.length; i++) {                 //循环初始化数据
            myTitleList.add(myTitles[i]);
            myUrlList.add(myUrls[i]);
        }
        myTextView.setOnClickListener(new View.OnClickListener() {  //响应单击标签
            @Override
            public void onClick(View v) {
                //通过FragmentManager对象获取指定Fragment
                WebFragment myWebFragment =
                        (WebFragment) getFragmentManager().findFragmentById(R.id.myFrame);
                Bundle myBundle = new Bundle();
                //将与标题对应的超链接地址通过Bundle传入WebFragment
                myBundle.putString("myUrl",
                        myUrlList.get(myTitleList.indexOf(myTextView.getText())));
                myWebFragment.setArguments(myBundle);
                //重新加载页面
                myWebFragment.reloadView();
            }
        });
    }
}
