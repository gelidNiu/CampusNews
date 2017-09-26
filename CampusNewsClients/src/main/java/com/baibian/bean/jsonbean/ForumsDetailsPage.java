package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/19.
 */

import java.util.List;

/**
 * 辩题详情即论点页面
 */
public class ForumsDetailsPage {
    private List<Thesis> thesisList;//论点列表
    private int topicid;//辩题id
    private String describtion;//辩题内容
    private int like;//点赞数
    private boolean isAttention;//是否关注

    public List<Thesis> getThesisList() {
        return thesisList;
    }

    public void setThesisList(List<Thesis> thesisList) {
        this.thesisList = thesisList;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }
}
