package com.ljp.hellogithub.activity.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

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

    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);
        ButterKnife.bind(this);
        mAnimationDrawable = (AnimationDrawable) mImageView.getBackground();

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_item);
        mImageView.setAnimation(animation);

    }

    @OnClick({R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.imageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button2:
                startActivity(AnimatorActivity.class);
                break;
            case R.id.button3:
                startActivity(LayoutAnimationActivity.class);
                overridePendingTransition(R.anim.animation_main_enter,R.anim.animation_main_exit);
                break;
            case R.id.button4:
                mAnimationDrawable.start();
                break;
            case R.id.button5:
                mAnimationDrawable.stop();
                break;
            case R.id.imageView:
                break;
        }
    }
}
