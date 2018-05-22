package com.ljp.hellogithub.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.nineoldandroids.view.ViewHelper;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/2/28
 *     desc   : 拖动滑动view
 *     version: 1.0
 * </pre>
 */

public class MySlideView extends View {

    private static final String TAG = "TestButton";
    private int mScaledTouchSlop;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public MySlideView(Context context) {
        super(context);
    }

    public MySlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext())
                .getScaledTouchSlop();
        Log.e(TAG, "sts:" + mScaledTouchSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取控件距离屏幕左上角的距离
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        Log.e(TAG,"rawX:"+x+"--rawY:"+y);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://接触屏幕
                break;
            case MotionEvent.ACTION_MOVE://滑动屏幕
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.e(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
                int translationX = (int) ViewHelper.getTranslationX(this) + deltaX;
                int translationY = (int) ViewHelper.getTranslationY(this) + deltaY;
                Log.e(TAG,ViewHelper.getTranslationX(this)+"");
                Log.e(TAG, "move, translationX:" + translationX + " translationY:" + translationY);
                ViewHelper.setTranslationX(this, translationX);
                ViewHelper.setTranslationY(this, translationY);
                break;
            case MotionEvent.ACTION_UP://离开屏幕
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        Log.e(TAG,"mLastX:"+mLastX+"--mLastY:"+mLastY);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
    }
}
