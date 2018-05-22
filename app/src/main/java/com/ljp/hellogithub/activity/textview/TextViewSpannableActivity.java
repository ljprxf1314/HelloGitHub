package com.ljp.hellogithub.activity.textview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.CommonWebActivity;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextViewSpannableActivity extends BaseActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv5)
    TextView mTv5;
    @BindView(R.id.tv6)
    TextView mTv6;
    @BindView(R.id.tv7)
    TextView mTv7;
    @BindView(R.id.tv8)
    TextView mTv8;
    @BindView(R.id.tv9)
    TextView mTv9;
    @BindView(R.id.tv10)
    TextView mTv10;
    @BindView(R.id.tv11)
    TextView mTv11;
    @BindView(R.id.tv12)
    TextView mTv12;
    @BindView(R.id.tv13)
    TextView mTv13;

    private int startIndex = 0;//起始下标
    private int endIndex = 1;//终止下标
    private RelativeSizeSpan relativeSizeSpan;//改变文字大小
    private SpannableString animationSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        ButterKnife.bind(this);

        //了解SpannableString的使用
        //        flags属性就有意思了，共有四种属性：
        //        Spanned.SPAN_INCLUSIVE_EXCLUSIVE  从起始下标到终了下标，包括起始下标
        //        Spanned.SPAN_INCLUSIVE_INCLUSIVE  从起始下标到终了下标，同时包括起始下标和终了下标
        //        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE  从起始下标到终了下标，但都不包括起始下标和终了下标
        //        Spanned.SPAN_EXCLUSIVE_INCLUSIVE  从起始下标到终了下标，包括终了下标

        //1.ForegroundColorSpan:设置文字颜色
        SpannableString span = new SpannableString("设置文字的前背景为淡蓝色");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        span.setSpan(colorSpan, 9, span.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv1.setText(span);

        //2.BackgroundColorSpan:设置文字背景
        SpannableString span2 = new SpannableString("设置文字的前背景为淡绿色");
        BackgroundColorSpan backgroundColorSpan;
        backgroundColorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        //        backgroundColorSpan = new BackgroundColorSpan(getResources().getDrawable(R.drawable.bg_text));
        span2.setSpan(backgroundColorSpan, 9, span.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv2.setText(span2);

        //3.RelativeSizeSpan:设置文字相对大小，在TextView原有的文字大小的基础上，相对设置文字大小
        SpannableString span3 = new SpannableString("万丈高楼平地起");
        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan3 = new RelativeSizeSpan(1.2f);
        span3.setSpan(sizeSpan1, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        span3.setSpan(sizeSpan2, 2, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        span3.setSpan(sizeSpan3, 4, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mTv3.setText(span3);

        //4.StrikethroughSpan:给文本添加横线
        SpannableString span4 = new SpannableString("为文字设置删除线");
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        span4.setSpan(strikethroughSpan, 5, span4.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv4.setText(span4);

        //5.UnderlineSpan:设置下划线
        SpannableString span5 = new SpannableString("为文字设置下滑线");
        UnderlineSpan underlineSpan = new UnderlineSpan();
        span5.setSpan(underlineSpan, 5, span5.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv5.setText(span5);

        //6.SuperscriptSpan:设置文字上标
        SpannableString span6 = new SpannableString("为文字设置上标");
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        span6.setSpan(superscriptSpan, 5, span6.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv6.setText(span6);

        //7.SubscriptSpan:设置文字下标
        SpannableString span7 = new SpannableString("为文字设置上标");
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        span7.setSpan(subscriptSpan, 5, span7.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv7.setText(span7);

        //8.StyleSpan:为文字设置风格（粗体、斜体），和TextView属性textStyle类似
        SpannableString span8 = new SpannableString("为文字设置粗体、斜体风格");
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);
        span8.setSpan(styleSpan_B, 5, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        span8.setSpan(styleSpan_I, 8, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv8.setText(span8);

        //9.ImageSpan:设置文本表情
        SpannableString span9 = new SpannableString("在文本中添加表情(表情)");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, 42, 42);//设置边界
        ImageSpan imageSpan = new ImageSpan(drawable);
        span9.setSpan(imageSpan, 6, 8, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv9.setText(span9);

        //10.ClickableSpan:设置可点击的文本，设置这个属性的文本可以相应用户点击事件，
        // 至于点击事件用户可以自定义，就像效果图显示一样，用户可以实现点击跳转页面的效果
        SpannableString span10 = new SpannableString("为文字设置点击事件");
        MyClickableSpan clickableSpan = new MyClickableSpan("http://www.baidu.com");
        span10.setSpan(clickableSpan, 5, span10.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv10.setMovementMethod(LinkMovementMethod.getInstance());//可以支持对TextView的内容滑动
        mTv10.setHighlightColor(Color.parseColor("#36969696"));
        mTv10.setText(span10);

        //11.URLSpan:
        SpannableString span11 = new SpannableString("为文字设置超链接");
        URLSpan urlSpan = new URLSpan("https://www.jianshu.com/u/dbae9ac95c78");
        span11.setSpan(urlSpan, 5, span11.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv11.setMovementMethod(LinkMovementMethod.getInstance());
        mTv11.setHighlightColor(Color.parseColor("#36969696"));
        mTv11.setText(span11);

        //除此之外，还有MaskFilterSpan可以实现模糊和浮雕效果，
        // RasterizerSpan可以实现光栅效果
        // SpannableStringBuilder:可以实现字符串的拼接

        //1.练习
        animationSpan = new SpannableString("看,这里的文字会动哦!");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        animationSpan.setSpan(foregroundColorSpan, 0, animationSpan.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv12.setText(animationSpan);
        relativeSizeSpan = new RelativeSizeSpan(1.6f);
        handler.sendEmptyMessage(1);

        //2.练习
        SpannableString spannableString = new SpannableString("共累计签到23次");
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        RelativeSizeSpan relativeSizeSpan1 = new RelativeSizeSpan(1.8f);
        spannableString.setSpan(foregroundColorSpan1,5,7,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan1,5,7,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv13.setText(spannableString);

    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                animationSpan.setSpan(relativeSizeSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                mTv12.setText(animationSpan);
                handler.postDelayed(runnable, 300);
            }
            return false;
        }
    });

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startIndex++;
            endIndex++;
            if (startIndex == animationSpan.length()) {
                startIndex = 0;
                endIndex = 1;
            }
            //运行在线程当中
            handler.sendEmptyMessage(1);
        }
    };

    @OnClick({R.id.tv1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                break;
        }
    }

    public class MyClickableSpan extends ClickableSpan {

        private String content;

        public MyClickableSpan(String content) {
            this.content = content;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            //            super.updateDrawState(ds);
            ds.setUnderlineText(false);//控制是否让可点击文本显示下划线，false是不显示下划线
        }

        @Override
        public void onClick(View view) {
            //            注意：使用ClickableSpan的文本如果想真正实现点击作用，
            //            必须为TextView设置setMovementMethod方法，否则没有点击相应，
            //            至于setHighlightColor方法则是控制点击是的背景色。
            Intent intent = new Intent(TextViewSpannableActivity.this, CommonWebActivity.class);
            intent.putExtra("urlStr", content);
            startActivity(intent);
        }
    }
}
