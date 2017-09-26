package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/20.
 */
/**
 * 完善的论点版本对应的javabean类
 */
public class PerfectThesis {
    private int id;//该完善论点的id
    private int tfromuid;//发表该完善版本用户的id
    private String tdescription;//标题
    private String tstate;//论点内容
    private String originalThesis;//原始论点内容
    private String nickname;//用户昵称
    private int like;//点赞数
    private String describtion;//完善理由标题
    private String content;//完善理由内容

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTfromuid() {
        return tfromuid;
    }

    public void setTfromuid(int tfromuid) {
        this.tfromuid = tfromuid;
    }

    public String getTdescription() {
        return tdescription;
    }

    public void setTdescription(String tdescription) {
        this.tdescription = tdescription;
    }

    public String getTstate() {
        return tstate;
    }

    public void setTstate(String tstate) {
        this.tstate = tstate;
    }

    public String getOriginalThesis() {
        return originalThesis;
    }

    public void setOriginalThesis(String originalThesis) {
        this.originalThesis = originalThesis;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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
}
