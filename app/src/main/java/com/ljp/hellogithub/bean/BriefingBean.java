package com.ljp.hellogithub.bean;

/**
 * Created by lijipei on 2019/9/4.
 */

public class BriefingBean {
    //{"code":200,"data":{"orderNum":5,"receiveAmount":0,"totalAmount":0.01},"httpCode":"OK"}

    private String code;// 200,"data
    private String httpCode;
    private Data data;

    public class Data{
        private String orderNum;
        private String receiveAmount;
        private String totalAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
