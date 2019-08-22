package com.ljp.hellogithub.activity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lijipei on 2019/8/22.
 * 通过注解方式加载布局文件
 */
@Target(ElementType.TYPE)   //作用在类、接口、枚举、注解类型
@Retention(RetentionPolicy.RUNTIME)  //运行编译时也保存注解
public @interface AhView {

    int value();
}
