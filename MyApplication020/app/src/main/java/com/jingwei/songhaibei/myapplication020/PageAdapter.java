package com.jingwei.songhaibei.myapplication020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PageAdapter extends BaseAdapter {
    Context myContext;
    Integer[] myImages = {R.mipmap.myimage12, R.mipmap.myimage34,
            R.mipmap.myimage56};  //总共三幅图像，代表六页

    public PageAdapter(Context context) {
        myContext = context;
    }

    @Override
    public int getCount() {
        return myImages.length;
    }

    @Override
    public Object getItem(int position) {
        return myImages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup myLayout;
        if (convertView == null)
            myLayout = (ViewGroup) LayoutInflater.from(myContext).inflate(R.layout.myitem, null);
        else myLayout = (ViewGroup) convertView;
        ImageView myImageView = (ImageView) myLayout.findViewById(R.id.myImageView);
        myImageView.setImageResource(myImages[position]);
        return myLayout;
    }
}
