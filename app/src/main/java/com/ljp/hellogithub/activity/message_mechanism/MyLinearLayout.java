package com.ljp.hellogithub.activity.message_mechanism;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2020/3/22.
 */

public class MyLinearLayout extends LinearLayout {

    private boolean isInterceptClick = false;//是否禁用控件的所有点击

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isInterceptClick){//不拦截
            return super.dispatchTouchEvent(ev);
        }else{//拦截
            return true;
        }
    }

    /**
     * 是否拦截所有点击操作,true拦截,false不拦截
     * @param interceptClick
     */
    public void setInterceptClick(boolean interceptClick) {
        isInterceptClick = interceptClick;
    }
}
