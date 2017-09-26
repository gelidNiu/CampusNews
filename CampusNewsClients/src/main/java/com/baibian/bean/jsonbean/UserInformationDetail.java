package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/19.
 */

import java.util.List;

/**
 * 每个用户的信息的javaBean类
 */
public class UserInformationDetail {
    private int id;//用户id
    private String backgroundImageUrl;//背景图片对应url
    private String userPortraitImageUrl;//用户头像对应url
    private boolean isAttention;//是否关注
    private String nickname;//用户昵称
    private String userSignature;//用户个性签名
    private int like;//用户获赞数
    private int attention;//用户受关注数
    private int fansAmounts;//用户粉丝数
    private String gender;//用户性别
    private int age;//用户年龄
    private String constellation;//用户星座
    private String schoolName;//用户学校
    private String locations;//用户住址
    private String honors;//用户获得的勋章
    private String educations;//用户的学历
    private int level;//用户的等级
    private int debateTopic;//用户辩题数
    private int thesis;//用户论点数
    private int presentations;//用户发言数
    private String birthday;//用户生日
    private int mobile;//用户手机号码
    private int emailNumber;//用户邮箱

    private List<PeriodicalItem> periodicalItemList;//分享的期刊集合

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getUserPortraitImageUrl() {
        return userPortraitImageUrl;
    }

    public void setUserPortraitImageUrl(String userPortraitImageUrl) {
        this.userPortraitImageUrl = userPortraitImageUrl;
    }

    public int getThesis() {
        return thesis;
    }

    public void setThesis(int thesis) {
        this.thesis = thesis;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getPresentations() {
        return presentations;
    }

    public void setPresentations(int presentations) {
        this.presentations = presentations;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFansAmounts() {
        return fansAmounts;
    }

    public void setFansAmounts(int fansAmounts) {
        this.fansAmounts = fansAmounts;
    }

    public int getEmailNumber() {
        return emailNumber;
    }

    public String getEducations() {
        return educations;
    }

    public void setEducations(String educations) {
        this.educations = educations;
    }

    public void setEmailNumber(int emailNumber) {
        this.emailNumber = emailNumber;
    }

    public int getDebateTopic() {
        return debateTopic;
    }

    public void setDebateTopic(int debateTopic) {
        this.debateTopic = debateTopic;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public List<PeriodicalItem> getPeriodicalItemList() {
        return periodicalItemList;
    }

    public void setPeriodicalItemList(List<PeriodicalItem> periodicalItemList) {
        this.periodicalItemList = periodicalItemList;
    }
}
