package com.ljp.hellogithub.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.util.UIUtils;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/3/2
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MySlideDemo1View extends View {

    private static final String TAG = "MySlideDemo1View";
    private Paint paint,paint2;
    private Scroller mScroller;
    //分别记录上次滑动的坐标
    private int mLastX = 0;

    public MySlideDemo1View(Context context) {
        super(context);
        init();
    }

    public MySlideDemo1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySlideDemo1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(UIUtils.getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);//抗锯齿
        paint2 = new Paint();
        paint2.setColor(UIUtils.getColor(R.color.white));
        paint2.setTextSize(20);
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(paint);
        canvas.drawText("我移动了",100,100,paint2);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){//滑动未结束,终止滑动
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                log("delta:"+deltaX);
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                smoothScrollBy(0, 0);
                break;
            default:
                break;
        }

        mLastX = x;

        return true;
    }

    private void smoothScrollBy(int dx, int dy){
        mScroller.startScroll(0, 0, dx, 0, 500);
        invalidate();
        log("smoothScrollBy");
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            log("computeScroll进来了");
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        log("computeScroll");
    }

    private void log(String str) {
        Log.e(TAG,str);
    }
}
