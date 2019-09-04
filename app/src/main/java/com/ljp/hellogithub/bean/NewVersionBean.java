package com.ljp.hellogithub.bean;

/**
 * 版本更新bean
 * Created by lijipei on 2016/12/19.
 */

public class NewVersionBean {

    private String repCode;//	返回码
    private String repMsg;//	返回消息

    private String code = "";
    private String desc = "";

    private NewVersionBean data;

    private int bv_version1 = 0;//	版本号
    private String bv_down_url;//	下载地址
    private String bv_desc;//	描述
    private String bv_upgrade;//	是否强制更新	1是  2不是
    private String bv_version_name;//版本名称

   /* private String flag;//	是否需要更新	1-强制更新 2-可以不更新 3-不需要更新  (自定义type:0--如果是0提示是安装的提示)
    private String downloadUrl;//	下载地址
    private String newVersion;//	最新版本
    private String newVersionName; //最新版本名称
    private String updateContent;//	更新内容*/

    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }

    public String getRepMsg() {
        return repMsg;
    }

    public void setRepMsg(String repMsg) {
        this.repMsg = repMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBv_version_name() {
        return bv_version_name;
    }

    public void setBv_version_name(String bv_version_name) {
        this.bv_version_name = bv_version_name;
    }

    public NewVersionBean getData() {
        return data;
    }

    public void setData(NewVersionBean data) {
        this.data = data;
    }

    public int getBv_version1() {
        return bv_version1;
    }

    public void setBv_version1(int bv_version1) {
        this.bv_version1 = bv_version1;
    }

    public String getBv_down_url() {
        return bv_down_url;
    }

    public void setBv_down_url(String bv_down_url) {
        this.bv_down_url = bv_down_url;
    }

    public String getBv_desc() {
        return bv_desc;
    }

    public void setBv_desc(String bv_desc) {
        this.bv_desc = bv_desc;
    }

    public String getBv_upgrade() {
        return bv_upgrade;
    }

    public void setBv_upgrade(String bv_upgrade) {
        this.bv_upgrade = bv_upgrade;
    }
}
