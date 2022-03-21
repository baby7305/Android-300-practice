package com.jingwei.songhaibei.myapplication002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(new MyView(this));       //加载自定义View
        super.onCreate(savedInstanceState);
    }

    class MyView extends View {              //创建自定义View
        private Paint myPaint;
        EmbossMaskFilter myFilter;

        public MyView(Context context) {
            super(context);
            myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            myPaint.setColor(Color.RED);
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(64);
            myPaint.setTextSize(800);
            float[] myDirection = new float[]{1, 1, 1};
            float myLight = 0.05f;                  //设置环境光亮度
            float mySpecular = 5;                   //设置反射等级
            float myBlur = 13;                      //设置模糊级别
            //自定义浮雕滤镜
            myFilter = new EmbossMaskFilter(myDirection, myLight, mySpecular, myBlur);
            myPaint.setMaskFilter(myFilter);        //设置浮雕滤镜的画笔
        }

        @Override
        protected void onDraw(Canvas myCanvas) {
            super.onDraw(myCanvas);
            Display myDisplay = getWindowManager().getDefaultDisplay();
            int myWidth = myDisplay.getWidth();
            int myHeight = myDisplay.getHeight();
            //显示浮雕文本
            myCanvas.drawText("炫", myWidth / 10, myHeight / 2 + 70, myPaint);
        }
    }
}