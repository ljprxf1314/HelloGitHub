package com.ljp.hellogithub.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by Administrator on 2019/7/21.
 */

@SuppressLint("AppCompatCustomView")
public class MyView extends TextView implements View.OnTouchListener {
    private Context context;
    private Scroller scroller;
    public MyView(Context context) {
        super(context);
        initDataa(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDataa(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDataa(context);
    }

    private void initDataa(Context context) {
        this.context = context;
        this.setOnTouchListener(this);
        scroller = new Scroller(context);
    }

    private void smoothScrollTo(int destX, int destY) {
        int scrollX = this.getScrollX();
        int scrollY = this.getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        scroller.startScroll(scrollX, scrollY, deltaX, deltaY, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            this.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                smoothScrollTo(-(int)event.getX(),-(int)event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                smoothScrollTo(-(int)event.getX(),-(int)event.getY());
                break;
        }
        return true;
    }
}
