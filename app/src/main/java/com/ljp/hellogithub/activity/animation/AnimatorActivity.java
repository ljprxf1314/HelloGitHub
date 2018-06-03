package com.ljp.hellogithub.activity.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/6/1
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class AnimatorActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.btn2)
    Button mBtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);

        //平移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBtn1, "translationX", 100);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
        //改变背景色动画
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(mBtn1, "backgroundColor",/*red*/0xffff8080,/*blue*/0xff8080ff);
        valueAnimator.setDuration(3000);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        //        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
        //动画集合
        AnimatorSet set = new AnimatorSet();

        @SuppressLint("ObjectAnimatorBinding")//弹性动画
                ObjectAnimator bounceAnimation = ObjectAnimator.ofFloat(
                mIvPic, "scale", 0.5f, 1.5f, 1);
        bounceAnimation.setInterpolator(new BounceInterpolator());
        bounceAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(
                    ValueAnimator animation) {
                float f = (Float) animation
                        .getAnimatedValue();
                mIvPic.setScaleX(f);
                mIvPic.setScaleY(f);
            }
        });

        set.playSequentially(
                ObjectAnimator.ofFloat(mIvPic, "translationX", 0, 100),
                ObjectAnimator.ofFloat(mIvPic, "translationY", 0, 100),
                ObjectAnimator.ofFloat(mIvPic, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(mIvPic, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(mIvPic, "rotation", 0, -90, 0),
                ObjectAnimator.ofFloat(mIvPic, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(mIvPic, "scaleY", 1, 0.5f, 1),
                ObjectAnimator.ofFloat(mIvPic, "alpha", 1, 0.25f, 1),
                bounceAnimation
        );
        set.setDuration(2000);//设置的时间是每个动画的执行时间
        set.start();

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.object01);
        animatorSet.setTarget(mBtn2);
        animatorSet.start();
    }
}
