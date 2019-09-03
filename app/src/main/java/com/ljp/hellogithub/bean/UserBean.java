package com.ljp.hellogithub.bean;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/3/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class UserBean extends BaseBean implements Serializable {

//    {"code":200,"data":{"count":0,"logincode":"9001","merchantId":0,
    //            "name":"冀培","nickname":"冀培","shopAddress":"","shopCusTel":"","shopId":"100001180302112001457399101",
    //            "shopName":"开发人员专用测试店铺",
    //            "token":"7ae69e74044c4cfdb9c0b9d2d2763831","userTel":"","userid":"999999180307153347070299883"},
    //        "httpCode":"OK"}

    private String code = "";
    private String desc = "";

    private UserBean data;

    private String logincode;//账号
    private String merchantId;//
    private String name;//
    private String nickname;//
    private String shopAddress;//
    private String shopCusTel;//
    private String shopId;//
    private String shopName;//
    private String token;//
    private String userTel;//
    /** 会员id(memberid) */
    private String userid;//会员id

//    private String id;//员工id
    private String workid;//工作人员id
    private String avatar;
    /** 会员角色 1 管理员 2业务员 3会员 4运维人员 */
    private String usertype;
    private String orgid;//公司id
    private String carid;//车仓库id
    private String carid_nameref;//车仓库名称

    private String roleCode = "";//角色编码
    private String roleName = "";//角色名称

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getCarid_nameref() {
        return carid_nameref;
    }

    public void setCarid_nameref(String carid_nameref) {
        this.carid_nameref = carid_nameref;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public String getLogincode() {
        return logincode;
    }

    public void setLogincode(String logincode) {
        this.logincode = logincode;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopCusTel() {
        return shopCusTel;
    }

    public void setShopCusTel(String shopCusTel) {
        this.shopCusTel = shopCusTel;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
