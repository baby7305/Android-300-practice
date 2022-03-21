package com.jingwei.songhaibei.myapplication015;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class TabFragment extends Fragment {
    private String myTitle;
    String[] myMovies = {"亲密的陌生人", "善意的谎言", "光荣岁月"};

    public void setTitle(String title) {
        this.myTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ImageView myImageView = new ImageView(getContext());
        myImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (myTitle.contains(myMovies[0])) {
            myImageView.setImageResource(R.mipmap.myimage1);
        } else if (myTitle.contains(myMovies[1])) {
            myImageView.setImageResource(R.mipmap.myimage2);
        } else if (myTitle.contains(myMovies[2])) {
            myImageView.setImageResource(R.mipmap.myimage3);
        }
        return myImageView;
    }
}
