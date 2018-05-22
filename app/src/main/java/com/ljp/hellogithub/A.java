package com.ljp.hellogithub;

/**
 * Created by Administrator on 2018/5/2.
 */

public class A {

    public String str = "";

    public synchronized void a(){
        str = "a";
    }

    public synchronized void b(){
    }
}
