package com.ljp.hellogithub.activity.annotation.reflect;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lijipei on 2019/8/22.
 */

public class ReflectClass {
    private final static String TAG = "peter.log.ReflectClass";

    private static final String pathClass = "com.ljp.hellogithub.activity.annotation.reflect.Book";

    // 创建对象
    public static void reflectNewInstance() {
        try {
            Class<?> classBook = Class.forName(pathClass);
            Object objectBook = classBook.newInstance();
            Book book = (Book) objectBook;
            book.setName("Android进阶之光");
            book.setAuthor("刘望舒");
            Log.e(TAG,book.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    //反射私有的构造方法
    public static void reflectPrivateConstructor(){
        try {
            Class<?> classBook = Class.forName(pathClass);
            Constructor<?> declaredConstructorBook = classBook.getDeclaredConstructor(String.class,String.class);
            declaredConstructorBook.setAccessible(true);
            Object objectBook = declaredConstructorBook.newInstance("android艺术开发探索","任玉刚");
            Book book = (Book) objectBook;
            book.toString();
            Log.e(TAG,book.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     反射私有属性
     */
    public static void reflectPrivateField() {
        try {
            Class<?> classBook = Class.forName(pathClass);
            Object objectBook = classBook.newInstance();
            Field fieldTag = classBook.getDeclaredField("TAG");
            fieldTag.setAccessible(true);
            String tag = (String) fieldTag.get(objectBook);
            Log.e(TAG,"reflectPrivateField tag = " + tag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     反射私有方法
     */
    public static void reflectPrivateMethod() {
        try {
            Class<?> classBook = Class.forName(pathClass);
            Method methodBook = classBook.getDeclaredMethod("declaredMethod",int.class);
            methodBook.setAccessible(true);
            Object objectBook = classBook.newInstance();
            String string = (String) methodBook.invoke(objectBook,0);

            Log.e(TAG,"reflectPrivateMethod string = " + string);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 获得系统Zenmode值
    public static int getZenMode() {
        int zenMode = -1;
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService", String.class);
            Object oNotificationManagerService = mGetService.invoke(null, Context.NOTIFICATION_SERVICE);
            Class<?> cINotificationManagerStub = Class.forName("android.app.INotificationManager$Stub");
            Method mAsInterface = cINotificationManagerStub.getMethod("asInterface",IBinder.class);
            Object oINotificationManager = mAsInterface.invoke(null,oNotificationManagerService);
            Method mGetZenMode = cINotificationManagerStub.getMethod("getZenMode");
            zenMode = (int) mGetZenMode.invoke(oINotificationManager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return zenMode;
    }

    // 关闭手机
    public static void shutDown() {
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService",String.class);
            Object oPowerManagerService = mGetService.invoke(null,Context.POWER_SERVICE);
            Class<?> cIPowerManagerStub = Class.forName("android.os.IPowerManager$Stub");
            Method mShutdown = cIPowerManagerStub.getMethod("shutdown",boolean.class,String.class,boolean.class);
            Method mAsInterface = cIPowerManagerStub.getMethod("asInterface",IBinder.class);
            Object oIPowerManager = mAsInterface.invoke(null,oPowerManagerService);
            mShutdown.invoke(oIPowerManager,true,null,true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

