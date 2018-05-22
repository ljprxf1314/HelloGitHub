package com.ljp.hellogithub.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/3/5
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class HorizontalScrollViewEx extends ViewGroup {

    private static final String TAG = "HorizontalScrollViewEx";

    private int mChildrenSize;
    private int mChildWidth;//子view宽度
    private int mChildIndex;//子view下标

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;//速度监听

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 1.初始化需要的参数
     * 2.
     */
    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量
        int measuredWidth = 0;
        int measuredHeight = 0;
        final int childNum = getChildCount();
        Log.e(TAG,"childNum:"+childNum);
        measureChildren(widthMeasureSpec, heightMeasureSpec);//测量子view

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.e(TAG,"widthSpaceSize:"+widthSpaceSize);
        Log.e(TAG,"widthSpecMode:"+widthSpecMode);
        Log.e(TAG,"heightSpaceSize:"+heightSpaceSize);
        Log.e(TAG,"heightSpecMode:"+heightSpecMode);

        if (childNum == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize, childView.getMeasuredHeight());
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childNum;
            setMeasuredDimension(measuredWidth, heightSpaceSize);
        } else {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childNum;
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean b, int l, int i1, int i2, int i3) {
        //控件布局
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth,
                        childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;//默认不拦截
        int x = (int) ev.getX();//获取触摸屏幕的X坐标
        int y = (int) ev.getY();//获取触摸屏幕的Y坐标

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP://触摸屏幕时
                intercepted = false;//每次触摸是默认不拦截
                //isFinished:返回scroller是否已完成滚动。返回值：停止滚动返回true，否则返回false。
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();//终止滑动动画
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE://滑动
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;//左右滑动时,拦截事件进行处理
                } else {
                    intercepted = false;//上下滑动时不处理,将事件传递给listview
                }
                break;
            case MotionEvent.ACTION_DOWN://抬起时
                intercepted = false;
                break;
            default:
                break;
        }

        Log.d(TAG, "intercepted=" + intercepted);
        //记录最后滑动到的位置坐标
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_UP: {//触摸屏幕时
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{//滑动
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-deltaX, 0);
                break;}
            case MotionEvent.ACTION_DOWN:{//抬起
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);//计算控件在一秒内滑动的速率
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;}
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }*/

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
