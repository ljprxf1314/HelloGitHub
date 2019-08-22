package com.ljp.hellogithub.activity.annotation;

/**
 * Created by lijipei on 2019/8/22.
 * 注入布局文件帮助类
 * 使用反射机制获取注解的文件,将布局注入到setContentView中
 */

public class InjectHelper {

    public static int injectObject(Object handler) throws Exception {
        Class<?> classType  = handler.getClass();
        //classType.isAnnotation();判断是否是注解类
        AhView contentView = classType.getAnnotation(AhView.class);
        if (contentView!=null){
            try {
                return contentView.value();
            }catch (Throwable t){
                throw new Exception("No injection layout");
            }
        }else{
            throw new Exception("No injection layout");
        }
    }


}
