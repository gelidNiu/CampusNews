package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/19.
 */

/**
 * 个人中心用户关注的辩题
 */
public class ConcernedDebateTopic {
    private int topicId;//辩题id
    private String describtion;//辩题标题
    private String content;//辩题内容
    private String publishTime;//辩题发表时间
    private int like;//辩题点赞数
    private int attention;//辩题关注数

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }


    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }


}
