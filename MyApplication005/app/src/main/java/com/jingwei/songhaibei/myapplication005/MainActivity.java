package com.jingwei.songhaibei.myapplication005;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    GridLayout myGridLayout;
    //定义字符串数组，保存16个按钮的标题
    String[] myTitle = new String[]{"7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            ".", "0", "=", "+"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myGridLayout = (GridLayout) findViewById(R.id.myGridLayout);
        for (int i = 0; i < myTitle.length; i++) {
            Button myButton = new Button(this);
            myButton.setText(myTitle[i]);
            //设置该按钮的字号大小
            myButton.setTextSize(36);
            //指定该按钮所在的行号,2表示从第3行开始计数
            GridLayout.Spec myRow = GridLayout.spec(i / 4 + 2);
            //指定该按钮所在的列号
            GridLayout.Spec myColumn = GridLayout.spec(i % 4);
            GridLayout.LayoutParams myParams =
                    new GridLayout.LayoutParams(myRow, myColumn);
            myGridLayout.addView(myButton, myParams);
        }
    }
}