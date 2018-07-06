package com.ljp.hellogithub.activity.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.iv_switch)
    ImageView mIvSwitch;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.cl)
    ConstraintLayout mCl;

    private boolean isCartOut = false;//true:调出仓库 false:调入仓库
    //动画插值器
    OvershootInterpolator mOvershootInterpolator = new OvershootInterpolator();

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

        //xml文件定义属性动画
        //        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.object01);
        //        animatorSet.setTarget(mBtn2);
        //        animatorSet.start();

        //给属性添加动画
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第二种自定义包装类
                //                ViewWrapper viewWrapper = new ViewWrapper(mBtn2);
                //                ObjectAnimator.ofInt(viewWrapper,"width",500).setDuration(5000).start();
                //第三种使用valueAnimator方法
                performAnimate(mBtn2, mBtn2.getWidth(), 500);
            }
        });
    }

    @OnClick({R.id.iv_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_switch:
                ObjectAnimator oaLeft;
                ObjectAnimator oaRight;
                //动画集合
                AnimatorSet setSwitch = new AnimatorSet();
                setSwitch.playSequentially(
                        ObjectAnimator.ofFloat(mIvSwitch, "rotation", 0, 180));
                setSwitch.setDuration(1000);//设置的时间是每个动画的执行时间
                setSwitch.start();

                if (isCartOut) {
                    Log.e("translation切换", "left" + mTvLeft.getLeft());
                    oaLeft = ObjectAnimator.ofFloat(mTvLeft, "translationX", mTvLeft.getLeft());
                    oaRight = ObjectAnimator.ofFloat(mTvRight, "translationX", mTvLeft.getLeft());
                    isCartOut = false;
                } else {
                    Log.e("translation切换", "left" + (mTvRight.getLeft() - mTvLeft.getLeft()) + "--right" +
                            (mTvLeft.getLeft() - mTvRight.getLeft()));
                    oaLeft = ObjectAnimator.ofFloat(mTvLeft, "translationX", (mTvRight.getLeft() - mTvLeft.getLeft()));
                    oaRight = ObjectAnimator.ofFloat(mTvRight, "translationX", (mTvLeft.getLeft() - mTvRight.getLeft()));
                    isCartOut = true;
                }
                oaLeft.setInterpolator(mOvershootInterpolator);
                oaLeft.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Log.e("translation结束", "left:" + mTvLeft.getLeft() + "--right" + mTvRight.getLeft());
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        Log.e("translation开始", "left:" + mTvLeft.getLeft() + "--right" + mTvRight.getLeft());
                    }
                });
                oaLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {

                    }
                });
                oaLeft.setDuration(1000).start();

                oaRight.setInterpolator(mOvershootInterpolator);
                oaRight.setDuration(1000).start();
                break;
        }
    }

    private static class ViewWrapper {
        private View mTrager;

        public ViewWrapper(View trager) {
            mTrager = trager;
        }

        public void setWidth(int width) {
            mTrager.getLayoutParams().width = width;
            mTrager.requestLayout();
        }

        public int getWidth() {
            return mTrager.getLayoutParams().width;
        }
    }


    private void performAnimate(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            // 持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // 获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                Log.d("performAnimate", "current value: " + currentValue);

                // 获得当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = animator.getAnimatedFraction();
                Log.d("performAnimate", "fraction value: " + fraction);
                // 直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.setDuration(5000).start();
    }
}
