package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/19.
 */

/**
 * 个人中心用户关注的论点
 */
public class ConcernedThesis {
    private int thesisid;//论点id
    private String tPublishtime;//论点发表时间
    private String tdescription;//论点内容
    private int tLike;//点赞数
    private int tAttention;//关注数

    public int getThesisid() {
        return thesisid;
    }

    public void setThesisid(int thesisid) {
        this.thesisid = thesisid;
    }

    public String gettPublishtime() {
        return tPublishtime;
    }

    public void settPublishtime(String tPublishtime) {
        this.tPublishtime = tPublishtime;
    }

    public String getTdescription() {
        return tdescription;
    }

    public void setTdescription(String tdescription) {
        this.tdescription = tdescription;
    }

    public int gettLike() {
        return tLike;
    }

    public void settLike(int tLike) {
        this.tLike = tLike;
    }

    public int gettAttention() {
        return tAttention;
    }

    public void settAttention(int tAttention) {
        this.tAttention = tAttention;
    }
}
