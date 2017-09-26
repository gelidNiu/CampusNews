package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/19.
 */

/**
 * 用户的粉丝信息
 */
public class InformationFans {
    private int id;//粉丝id
    private String userPortraitUrl;//粉丝头像
    private String userName;//粉丝昵称
    private String userSignature;//粉丝个性签名
    private boolean isFocused;//是否关注粉丝
    private int level;//粉丝等级
    private String honors;//粉丝获得勋章

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getUserPortraitUrl() {
        return userPortraitUrl;
    }

    public void setUserPortraitUrl(String userPortraitUrl) {
        this.userPortraitUrl = userPortraitUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHonors() {
        return honors;
    }

    public void setHonors(String honors) {
        this.honors = honors;
    }
}
