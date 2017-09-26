package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/20.
 */

/**
 * 每个异议对应的javabean类
 */
public class Objection {
    private String nickname;//用户昵称
    private String Icon;//用户头像url
    private String describtion;//标题
    private String content;//内容
    private String publishtime;//发表时间

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }
}
