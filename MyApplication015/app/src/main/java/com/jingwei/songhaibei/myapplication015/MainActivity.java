package com.jingwei.songhaibei.myapplication015;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    String[] myTitle = {"亲密的陌生人", "善意的谎言", "光荣岁月"};
    String[] myData = {"亲密的陌生人", "善意的谎言", "光荣岁月"};
    TabLayout myTabLayout;
    ViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        myTabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        myViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return myTitle[position % myTitle.length];  //此方法负责管理选项卡标签
            }

            @Override
            public Fragment getItem(int position) {         //创建Fragment并返回
                TabFragment myTabFragment = new TabFragment();
                myTabFragment.setTitle(myData[position % myTitle.length]);
                return myTabFragment;
            }

            @Override
            public int getCount() {
                return myTitle.length;
            }
        });
        myTabLayout.setupWithViewPager(myViewPager);  //将ViewPager关联TabLayout
        myTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myViewPager.setCurrentItem(tab.getPosition());          //切换ViewPager
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}