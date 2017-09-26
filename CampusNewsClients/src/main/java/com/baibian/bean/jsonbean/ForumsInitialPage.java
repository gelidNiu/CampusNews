package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/16.
 */

/**
 * gson解析论阁初始页时的json数据的javaBean类
 */
public class ForumsInitialPage {
    private int topicid;//论点主题id
    private String describtion;//论点主题标题
    private int view;//浏览量
    private int like;//点赞数
    private String content;//论点主题描述
    private boolean islike;//点赞状态
    private boolean isAttention;//关注状态

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDecribtion(String decribtion) {
        this.describtion = decribtion;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIslike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }
}
