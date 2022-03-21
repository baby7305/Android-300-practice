package com.jingwei.songhaibei.myapplication014;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyAdapter extends PagerAdapter {
    private List<View> myViews;
    private List<String> myTitles;

    public MyAdapter(List<View> mViewList, List<String> titles) {
        this.myViews = mViewList;
        myTitles = titles;
    }

    @Override
    public int getCount() {
        return myViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(myViews.get(position));      //添加选项卡
        return myViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(myViews.get(position));    //删除选项卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return myTitles.get(position);
    }
}
