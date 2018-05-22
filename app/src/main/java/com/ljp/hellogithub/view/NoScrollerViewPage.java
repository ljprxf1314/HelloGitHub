package com.ljp.hellogithub.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/3/2
 *     desc   : 禁止左右滑动的viewpage
 *     version: 1.0
 * </pre>
 */

public class NoScrollerViewPage extends ViewPager {

    public NoScrollerViewPage(Context context) {
        super(context);
    }

    public NoScrollerViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //不拦截事件进行处理,覆盖父类ViewPage的onInterceptTouchEvent处理方法
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
