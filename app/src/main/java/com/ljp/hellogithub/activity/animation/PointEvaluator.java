package com.ljp.hellogithub.activity.animation;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/7/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Log.e("PointEvaluator","fraction:"+fraction);
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX()+fraction * (endPoint.getX()-startPoint.getX());
        float y = startPoint.getY()+fraction * (endPoint.getY()-startPoint.getY());
        Point point = new Point(x,y);
        return point;
    }
}
