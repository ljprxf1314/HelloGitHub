package com.ljp.hellogithub.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/3/2
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MySlideDemo1View extends View {

    public MySlideDemo1View(Context context) {
        super(context);
    }

    public MySlideDemo1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MySlideDemo1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }
}
