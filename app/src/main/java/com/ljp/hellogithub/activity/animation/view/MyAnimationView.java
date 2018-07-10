package com.ljp.hellogithub.activity.animation.view;

import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.ljp.hellogithub.activity.animation.Point;
import com.ljp.hellogithub.activity.animation.PointEvaluator;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/7/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MyAnimationView extends View {

    private static final float RADIUS = 50f;

    private Point currentPoint;//点

    private Paint mPaint;

    //1.ValueAnimator的ofFloat()和ofInt()方法:分别用于对浮点型和整型的数据进行动画操作的
    //2.但实际上ValueAnimator中还有一个ofObject()方法，是用于对任意对象进行动画操作的,
    //但是相比于浮点型或整型数据，对象的动画操作明显要更复杂一些，因为系统将完全无法知道如何从初始对象过度到结束对象，
    // 因此这个时候我们就需要实现一个自己的TypeEvaluator来告知系统如何进行过度。

    public MyAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }


    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.x;
        float y = currentPoint.y;
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();
    }
}
