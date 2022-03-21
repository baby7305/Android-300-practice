package com.jingwei.songhaibei.myapplication020;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Scroller;


public class PageView extends FrameLayout {
    static int myWidth, myHeight;
    //两条贝赛尔曲线对应的路径对象
    Path myPath1 = new Path(), myPath2 = new Path();
    //触摸点和页脚点
    static PointF myTouchPoint = new PointF(), myCorner = new PointF();
    //两条贝赛尔曲线关键点
    static PointF myBezierStart1 = new PointF(), myBezierControl1 = new PointF(),
            myBezierVertex1 = new PointF(), myBezierEnd1 = new PointF(),
            myBezierStart2 = new PointF(), myBezierControl2 = new PointF(),
            myBezierVertex2 = new PointF(), myBezierEnd2 = new PointF();
    static float myMiddleX, myMiddleY, myDegrees, mTouchToCornerDis;
    Matrix myMatrix;
    Paint myPaint;
    Scroller myScroller;
    boolean isAnimated = false;
    View myCurrentView, nextViewTranscript;//当前PageView和卷曲部分PageView
    Context myContext;
    BaseAdapter myAdapter = null;
    //子视图(PageView)数量和当前显示索引值
    int myCurrentPosition = -1, myItemCount = 0;

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        myContext = context;
        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.FILL);
        myMatrix = new Matrix();
        myScroller = new Scroller(myContext);
        myTouchPoint.set(0, 0);                //初始化触摸点坐标
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        myWidth = getWidth();
        myHeight = getHeight();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        calcPoints(isAnimated, myWidth);       //计算贝塞尔曲线关键点坐标
        super.dispatchDraw(canvas);
        //绘制卷起部分
        if (myItemCount > 1) drawFoldPart(canvas, nextViewTranscript);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (child.equals(myCurrentView)) drawCurrentPage(canvas, child, myPath1);
        else drawNextPage(canvas, child);              //绘制下一页
        return true;
    }

    public void drawCurrentPage(Canvas canvas, View child, Path path) {//绘制当前页
        myPath1.reset();                                   //重置路径对象
        myPath1.moveTo(myBezierStart1.x, myBezierStart1.y);//移至起始点
        myPath1.quadTo(myBezierControl1.x, myBezierControl1.y,
                myBezierEnd1.x, myBezierEnd1.y);            //绘制第一条贝赛尔曲线
        myPath1.lineTo(myTouchPoint.x, myTouchPoint.y);    //与触摸点连接
        //与第二条贝赛尔曲线结束点连接
        myPath1.lineTo(myBezierEnd2.x, myBezierEnd2.y);
        myPath1.quadTo(myBezierControl2.x, myBezierControl2.y,
                myBezierStart2.x, myBezierStart2.y);        //反向绘制第二条贝塞尔曲线
        myPath1.lineTo(myCorner.x, myCorner.y);            //与页脚点连接
        myPath1.close();
        canvas.save();
        canvas.clipPath(path, Region.Op.XOR);
        child.draw(canvas);
        canvas.restore();
    }

    public void drawNextPage(Canvas canvas, View child) { //绘制下一页
        myPath2.reset();                                     //重置路径对象
        myPath2.moveTo(myBezierStart1.x, myBezierStart1.y);  //移动起始点
        myPath2.lineTo(myBezierVertex1.x, myBezierVertex1.y);//与第一条曲线顶点连接
        myPath2.lineTo(myBezierVertex2.x, myBezierVertex2.y);//与第二条曲线顶点连接
        myPath2.lineTo(myBezierStart2.x, myBezierStart2.y);  //与第二条曲线起点连接
        myPath2.lineTo(myCorner.x, myCorner.y);              //与页脚点连接
        myPath2.close();
        myDegrees = (float) Math.toDegrees(Math.atan2(myBezierControl1.x - myCorner.x,
                myBezierControl2.y - myCorner.y));//计算翻页角度
        canvas.save();
        canvas.clipPath(myPath1);
        canvas.clipPath(myPath2, Region.Op.INTERSECT);
        child.draw(canvas);
        canvas.rotate(myDegrees, myBezierStart1.x, myBezierStart1.y);
        canvas.restore();
    }

    public void drawFoldPart(Canvas canvas, View view) {    //绘制卷起页背面
        myPath2.reset();
        myPath2.moveTo(myBezierVertex2.x, myBezierVertex2.y);
        myPath2.lineTo(myBezierVertex1.x, myBezierVertex1.y);
        myPath2.lineTo(myBezierEnd1.x, myBezierEnd1.y);
        myPath2.lineTo(myTouchPoint.x, myTouchPoint.y);
        myPath2.lineTo(myBezierEnd2.x, myBezierEnd2.y);
        myPath2.close();
        canvas.save();
        canvas.clipPath(myPath1);
        canvas.clipPath(myPath2, Region.Op.INTERSECT);
        float rotateDegrees = (float) Math.toDegrees(Math.PI / 2 +
                Math.atan2(myBezierControl2.y - myTouchPoint.y,
                        myBezierControl2.x - myTouchPoint.x)); //计算旋转角度
        if (myCorner.y == 0) rotateDegrees -= 180;
        myMatrix.reset();                                      //重置矩阵对象
        //变换矩阵
        myMatrix.setPolyToPoly(new float[]{Math.abs(myWidth - myCorner.x),
                myCorner.y}, 0, new float[]{myTouchPoint.x, myTouchPoint.y}, 0, 1);
        //旋转矩阵
        myMatrix.postRotate(rotateDegrees, myTouchPoint.x, myTouchPoint.y);
        canvas.save();
        canvas.concat(myMatrix);
        view.draw(canvas);
        canvas.restore();
        canvas.rotate(myDegrees, myBezierStart1.x, myBezierStart1.y);
        canvas.restore();
    }

    public void computeScroll() {
        super.computeScroll();
        if (myScroller.computeScrollOffset()) {
            myTouchPoint.set(myScroller.getCurrX(), myScroller.getCurrY());
            postInvalidate();
        }
        if (isAnimated && myScroller.isFinished()) {     //绘制最后一幅图像
            isAnimated = false;
            if (DragToRight((int) myCorner.x)) myCurrentPosition -= 1;
            else myCurrentPosition += 1;
            myCurrentView = myAdapter.getView(myCurrentPosition, myCurrentView, null);
            myTouchPoint.set(0, 0);
            myCorner.set(0, 0);
            postInvalidate();
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        myAdapter = adapter;
        myItemCount = myAdapter.getCount();
        myCurrentView = null;
        nextViewTranscript = null;
        removeAllViews();
        if (myItemCount != 0) {
            myCurrentPosition = 0;
            myCurrentView = myAdapter.getView(myCurrentPosition, null, null);
            addView(myCurrentView);
            if (myItemCount > 1) {
                nextViewTranscript = myAdapter.getView(myCurrentPosition + 1, null, null);
                addView(nextViewTranscript);
            }
        } else myCurrentPosition = -1;
        myTouchPoint.set(0, 0);
        myCorner.set(0, 0);
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (myAdapter != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (myItemCount == 0) return false;
                myScroller.abortAnimation();
                calcCornerXY(event.getX(), event.getY());
                if (DragToRight((int) myCorner.x)) {
                    if (myCurrentPosition == 0) return false;
                    nextViewTranscript = myAdapter.getView(myCurrentPosition - 1,
                            nextViewTranscript, null);
                } else {
                    if (myCurrentPosition == myItemCount - 1) return false;
                    nextViewTranscript = myAdapter.getView(myCurrentPosition + 1,
                            nextViewTranscript, null);
                }
                isAnimated = false;
                myTouchPoint.set(event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                myTouchPoint.set(event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (canDragOver()) {
                    isAnimated = true;
                    startAnimation(300, myScroller);
                } else myTouchPoint.set(myCorner.x - .09f, myCorner.y - .09f);
            }
            postInvalidate();
            return true;
        } else return false;
    }

    //求解直线P1、P2和直线P3、P4的交点坐标，一次函数通式： y=ax+b
    public static PointF getCross(PointF P1, PointF P2, PointF P3, PointF P4) {
        PointF myPoint = new PointF();
        float a1 = (P2.y - P1.y) / (P2.x - P1.x);
        float b1 = ((P1.x * P2.y) - (P2.x * P1.y)) / (P1.x - P2.x);
        float a2 = (P4.y - P3.y) / (P4.x - P3.x);
        float b2 = ((P3.x * P4.y) - (P4.x * P3.y)) / (P3.x - P4.x);
        myPoint.x = (b2 - b1) / (a1 - a2);
        myPoint.y = a1 * myPoint.x + b1;
        return myPoint;
    }

    public static boolean DragToRight(int cornerX) { //是否从左边翻向右边
        if (cornerX > 0) return false;
        return true;
    }

    //启动动画
    public static void startAnimation(int delayMillis, Scroller myScroller) {
        int dx;     //dx:水平方向滑动的距离，负值会使滚动向左
        int dy;     //dy:垂直方向滑动的距离，负值会使滚动向上
        if (myCorner.x > 0) dx = (int) (-myTouchPoint.x + 1);
        else dx = (int) (myWidth - myTouchPoint.x - 1);
        if (myCorner.y > 0) dy = (int) (myHeight - myTouchPoint.y - 1);
        else dy = (int) (1 - myTouchPoint.y);          //防止myTouch.y最终变为0
        myScroller.startScroll((int) myTouchPoint.x,
                (int) myTouchPoint.y, dx, dy, delayMillis);
    }

    //计算中点坐标、贝塞尔起始点和控制点坐标
    public static void calcPoints(boolean isAnimated, int myWidth) {
        myMiddleX = (myTouchPoint.x + myCorner.x) / 2;
        myMiddleY = (myTouchPoint.y + myCorner.y) / 2;
        myBezierControl1.x = myMiddleX - (myCorner.y - myMiddleY)
                * (myCorner.y - myMiddleY) / (myCorner.x - myMiddleX);
        myBezierControl1.y = myCorner.y;
        myBezierControl2.x = myCorner.x;
        myBezierControl2.y = myMiddleY - (myCorner.x - myMiddleX)
                * (myCorner.x - myMiddleX) / (myCorner.y - myMiddleY);
        myBezierStart1.x = myBezierControl1.x -
                (myCorner.x - myBezierControl1.x) / 3;
        myBezierStart1.y = myCorner.y;
        if (!isAnimated) {//这里做限制,使得手指滑动过程中视图不能翻过中间线
            if (myCorner.x == 0 && myBezierStart1.x > myWidth / 2) {   //向右滑动时
                float f1 = Math.abs(myCorner.x - myTouchPoint.x);
                float f2 = myWidth / 2 * f1 / myBezierStart1.x;
                myTouchPoint.x = Math.abs(myCorner.x - f2);
                float f3 = Math.abs(myCorner.x - myTouchPoint.x)
                        * Math.abs(myCorner.y - myTouchPoint.y) / f1;
                myTouchPoint.y = Math.abs(myCorner.y - f3);
                myMiddleX = (myTouchPoint.x + myCorner.x) / 2;
                myMiddleY = (myTouchPoint.y + myCorner.y) / 2;
                myBezierControl1.x = myMiddleX - (myCorner.y - myMiddleY)
                        * (myCorner.y - myMiddleY) / (myCorner.x - myMiddleX);
                myBezierControl1.y = myCorner.y;
                myBezierControl2.x = myCorner.x;
                myBezierControl2.y = myMiddleY - (myCorner.x - myMiddleX)
                        * (myCorner.x - myMiddleX) / (myCorner.y - myMiddleY);
                myBezierStart1.x = myBezierControl1.x
                        - (myCorner.x - myBezierControl1.x) / 2;
            }
            if (myCorner.x == myWidth && myBezierStart1.x < myWidth / 2) {   //向左滑动时
                myBezierStart1.x = myWidth - myBezierStart1.x;
                float f1 = Math.abs(myCorner.x - myTouchPoint.x);
                float f2 = myWidth / 2 * f1 / myBezierStart1.x;
                myTouchPoint.x = Math.abs(myCorner.x - f2);
                float f3 = Math.abs(myCorner.x - myTouchPoint.x)
                        * Math.abs(myCorner.y - myTouchPoint.y) / f1;
                myTouchPoint.y = Math.abs(myCorner.y - f3);
                myMiddleX = (myTouchPoint.x + myCorner.x) / 2;
                myMiddleY = (myTouchPoint.y + myCorner.y) / 2;
                myBezierControl1.x = myMiddleX - (myCorner.y - myMiddleY)
                        * (myCorner.y - myMiddleY) / (myCorner.x - myMiddleX);
                myBezierControl1.y = myCorner.y;
                myBezierControl2.x = myCorner.x;
                myBezierControl2.y = myMiddleY - (myCorner.x - myMiddleX)
                        * (myCorner.x - myMiddleX) / (myCorner.y - myMiddleY);
                myBezierStart1.x = myBezierControl1.x
                        - (myCorner.x - myBezierControl1.x) / 2;
            }
        }
        myBezierStart2.x = myCorner.x;
        myBezierStart2.y = myBezierControl2.y -
                (myCorner.y - myBezierControl2.y) / 2;
        mTouchToCornerDis = (float) Math.hypot((myTouchPoint.x -
                myCorner.x), (myTouchPoint.y - myCorner.y));
        myBezierEnd1 = getCross(myTouchPoint,
                myBezierControl1, myBezierStart1, myBezierStart2);
        myBezierEnd2 = getCross(myTouchPoint,
                myBezierControl2, myBezierStart1, myBezierStart2);
        myBezierVertex1.x = (myBezierStart1.x +
                2 * myBezierControl1.x + myBezierEnd1.x) / 4;
        myBezierVertex1.y = (2 * myBezierControl1.y +
                myBezierStart1.y + myBezierEnd1.y) / 4;
        myBezierVertex2.x = (myBezierStart2.x +
                2 * myBezierControl2.x + myBezierEnd2.x) / 4;
        myBezierVertex2.y = (2 * myBezierControl2.y +
                myBezierStart2.y + myBezierEnd2.y) / 4;
    }

    public static boolean canDragOver() {
        if (mTouchToCornerDis > myWidth / 10) return true;
        return false;
    }

    //计算拖拽点对应的拖拽脚(角落)
    public static void calcCornerXY(float x, float y) {
        if (x <= myWidth / 2) myCorner.x = 0;
        else myCorner.x = myWidth;
        if (y <= myHeight / 2) myCorner.y = 0;
        else myCorner.y = myHeight;
    }
}
