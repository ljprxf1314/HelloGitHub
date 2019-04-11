package com.ljp.hellogithub.activity.rxjava.http;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2019/4/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class LoginRequest {

    private String logincode;
    private String pwd;

    public String getLogincode() {
        return logincode;
    }

    public void setLogincode(String logincode) {
        this.logincode = logincode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
