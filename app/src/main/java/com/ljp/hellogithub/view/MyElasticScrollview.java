package com.ljp.hellogithub.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/3/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MyElasticScrollview extends ScrollView {

    private View innerView;
    private float y;

    private Rect normal = new Rect();
    private boolean animationFinish = true;

    public MyElasticScrollview(Context context) {
        super(context);
    }

    public MyElasticScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyElasticScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //当View中所有的子控件均被映射成xml后触发
        int childCount = getChildCount();
        if (childCount>0){
            innerView = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (innerView==null){
            return super.onTouchEvent(ev);
        }else{
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    y = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float preY = y == 0 ? ev.getY():y;
                    float nowY = ev.getY();
                    int detailY = (int)(preY - nowY);
                    Log.e("MyScrollview","detailY:"+detailY);
                    y = nowY;
                    //操作view进行拖动detailY的一半
                    if (isNeedMove()){//在顶部或底部
                        //布局改变位置之前，记录一下正常状态的位置
                        if (normal.isEmpty()) {
                            normal.set(innerView.getLeft(), innerView.getTop(), innerView.getRight(), innerView.getBottom());
                        }
                        //设置控件布局
                        innerView.layout(innerView.getLeft(), innerView.getTop() - detailY / 2, innerView.getRight(),
                                innerView.getBottom() - detailY / 2);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    y = 0;
                    //布局回滚到原来的位置
                    if (isNeedAnimation()) {
                        animation();
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void animation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, normal.top - innerView.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                innerView.clearAnimation();
                innerView.layout(normal.left, normal.top, normal.right, normal.bottom);
                normal.setEmpty();
                animationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        innerView.startAnimation(ta);
    }


    /**
     * 判断是否需要移动
     *
     * @return
     */
    private boolean isNeedMove() {
        int offset = getMeasuredHeight() - getHeight();//测量高度-屏幕显示宽度
        int scrollY = getScrollY();//Y轴的偏移量
        Log.e("zoubo", "getMeasuredHeight:" + innerView.getMeasuredHeight() + "----getHeight:" + getHeight());
        Log.e("zoubo", "offset:" + offset + "----scrollY:" + scrollY);
        if (scrollY==0 || offset==scrollY){//(在顶部||滑到底部)
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要回滚
     *
     * @return
     */
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }
}
