package com.ljp.hellogithub.activity.annotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.annotation.reflect.ReflectClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lijipei on 2019/8/22.
 */
@AhView(R.layout.activity_annotation_main)
public class AnnotationMainActivity extends FragmentActivity {

    @BindView(R.id.btn_reflect)
    Button mBtnReflect;

    @SuppressWarnings("deprecation")  //忽略警告
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(InjectHelper.injectObject(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ButterKnife.bind(this);

        TestAnnotation testAnnotation = new TestAnnotation();
        testAnnotation.say();
        testAnnotation.speak();
    }

    @OnClick(R.id.btn_reflect)
    public void onViewClicked() {
        ReflectClass.reflectNewInstance();//反射创建对象
        ReflectClass.reflectPrivateConstructor();//反射私有构造方法
        ReflectClass.reflectPrivateField();
        ReflectClass.reflectPrivateMethod();

        Log.e("ReflectClass"," zenmode = " + ReflectClass.getZenMode());
     }
}
