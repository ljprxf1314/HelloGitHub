package com.ljp.hellogithub.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lijipei on 2019/9/3.
 */

public class RequestLoginBean {
    @SerializedName("logincode")
    public String logincode;
    @SerializedName("pwd")
    public String pwd;

    @Override
    public String toString() {
        return "RequestLoginBean{" +
                "logincode='" + logincode + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
