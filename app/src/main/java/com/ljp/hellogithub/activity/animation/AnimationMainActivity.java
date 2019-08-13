package com.ljp.hellogithub.activity.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/5/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class AnimationMainActivity extends BaseActivity {

    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;
    @BindView(R.id.button5)
    Button mButton5;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.button6)
    Button mButton6;
    @BindView(R.id.button7)
    Button mButton7;
    @BindView(R.id.center1)
    View mCenter1;
    @BindView(R.id.iv_share_weixin)
    ImageView mIvShareWeixin;
    @BindView(R.id.iv_share_sina)
    ImageView mIvShareSina;
    @BindView(R.id.center2)
    View mCenter2;
    @BindView(R.id.iv_share_pengyouquan)
    ImageView mIvSharePengyouquan;
    @BindView(R.id.iv_share_sms)
    ImageView mIvShareSms;
    @BindView(R.id.center3)
    View mCenter3;
    @BindView(R.id.iv_share_qq)
    ImageView mIvShareQq;
    @BindView(R.id.iv_share_copy)
    ImageView mIvShareCopy;
    @BindView(R.id.center4)
    View mCenter4;
    @BindView(R.id.iv_share_qqzone)
    ImageView mIvShareQqzone;
    @BindView(R.id.iv_share_close)
    ImageView mIvShareClose;
    @BindView(R.id.rl_share_more)
    RelativeLayout mRlShareMore;

    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);
        ButterKnife.bind(this);
        mAnimationDrawable = (AnimationDrawable) mImageView.getBackground();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        mImageView.setAnimation(animation);

    }

    @OnClick({R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.imageView, R.id.button6,
            R.id.button7,R.id.iv_share_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button2:
                startActivity(AnimatorActivity.class);
                break;
            case R.id.button3:
                startActivity(LayoutAnimationActivity.class);
                overridePendingTransition(R.anim.animation_main_enter, R.anim.animation_main_exit);
                break;
            case R.id.button4:
                mAnimationDrawable.start();
                break;
            case R.id.button5:
                mAnimationDrawable.stop();
                break;
            case R.id.button6:
                startActivity(AnimatorTwoActivity.class);
                break;
            case R.id.imageView:
                break;
            case R.id.button7://分享动画
                if (mRlShareMore.getVisibility() == View.INVISIBLE) {
                    mRlShareMore.setVisibility(View.VISIBLE);
                    startOpenAnimation(true);
                } else {
                    startOpenAnimation(false);
                }
                break;
            case R.id.iv_share_close:
                startOpenAnimation(false);
                break;
        }
    }

    private void startOpenAnimation(final boolean b) {
        if (b) {
            if (mIvShareWeixin.getVisibility() == View.INVISIBLE) {
                mIvShareWeixin.setVisibility(View.VISIBLE);
                startTranstateAnimation(mIvShareWeixin, true, 2);
            }
        } else {
            mRlShareMore.setEnabled(false);
            startTranstateAnimation(mIvShareWeixin, false, 2);
            startTranstateAnimation(mIvSharePengyouquan, false, 2);
            startTranstateAnimation(mIvShareQq, false, 2);
            startTranstateAnimation(mIvShareQqzone, false, 2);
            //
            startTranstateAnimation(mIvShareSina, false, 4);
            startTranstateAnimation(mIvShareSms, false, 4);
            startTranstateAnimation(mIvShareCopy, false, 4);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0f);
            alphaAnimation.setDuration(600);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRlShareMore.setEnabled(true);
                    mRlShareMore.setVisibility(View.INVISIBLE);
                    mIvShareWeixin.setVisibility(View.INVISIBLE);
                    mIvSharePengyouquan.setVisibility(View.INVISIBLE);
                    mIvShareQq.setVisibility(View.INVISIBLE);
                    mIvShareQqzone.setVisibility(View.INVISIBLE);
                    mIvShareSina.setVisibility(View.INVISIBLE);
                    mIvShareSms.setVisibility(View.INVISIBLE);
                    mIvShareCopy.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mRlShareMore.startAnimation(alphaAnimation);
        }
    }

    private void startTranstateAnimation(final View v, boolean open, int position) {
        if (open) {
            Log.e("startTranstateAnimation", "Open.getY()=" + mIvShareClose.getY() + ",v.getY()=" + v.getY());
            ObjectAnimator animator = ObjectAnimator.ofFloat(v, "y",
                    mIvShareClose.getY(),
                    v.getY() - UIUtils.dp2px(20),
                    v.getY() + UIUtils.dp2px(10),
                    v.getY()).setDuration(100);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (v.getId() == mIvShareWeixin.getId()) {
                        if (mIvSharePengyouquan.getVisibility() == View.INVISIBLE) {
                            mIvSharePengyouquan.setVisibility(View.VISIBLE);
                            startTranstateAnimation(mIvSharePengyouquan, true, 3);
                        }
                    } else if (v.getId() == mIvSharePengyouquan.getId()) {
                        if (mIvShareQq.getVisibility() == View.INVISIBLE) {
                            mIvShareQq.setVisibility(View.VISIBLE);
                            startTranstateAnimation(mIvShareQq, true, 3);
                        }
                    } else if (v.getId() == mIvShareQq.getId()) {
                        if (mIvShareQqzone.getVisibility() == View.INVISIBLE) {
                            mIvShareQqzone.setVisibility(View.VISIBLE);
                            startTranstateAnimation(mIvShareQqzone, true, 2);
                        }
                    } else if (v.getId() == mIvShareQqzone.getId()) {
                        if (mIvShareSina.getVisibility() == View.INVISIBLE) {
                            mIvShareSina.setVisibility(View.VISIBLE);
                            startTranstateAnimation(mIvShareSina, true, 4);
                        }
                    } else if (v.getId() == mIvShareSina.getId()) {
                        if (mIvShareSms.getVisibility() == View.INVISIBLE) {
                            mIvShareSms.setVisibility(View.VISIBLE);
                            startTranstateAnimation(mIvShareSms, true, 4);
                        }
                    } else if (v.getId() == mIvShareSms.getId()) {
                        if (mIvShareCopy.getVisibility() == View.INVISIBLE) {
                            mIvShareCopy.setVisibility(View.VISIBLE);
                            startTranstateAnimation(mIvShareCopy, true, 4);
                        }
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mIvShareClose.setEnabled(true);
        } else {
            mIvShareClose.setEnabled(false);
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_PARENT, 0.4f);
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setDuration(500);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillAfter(false);
            v.startAnimation(animationSet);
        }
    }
}
