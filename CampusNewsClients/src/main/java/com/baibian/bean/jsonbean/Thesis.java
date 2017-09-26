package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/19.
 */

/**
 * 每个论点的javabean类
 */
public class Thesis {
    private int tfromuid;//发表用户的id
    private int thesisid;//论点id
    private int tDebateid;//辩题id
    private String tdescription;//论点标题
    private int complete;//完善值
    private String tstate;//论点内容
    private boolean isAttention;//是否关注

    public int getTfromuid() {
        return tfromuid;
    }

    public void setTfromuid(int tfromuid) {
        this.tfromuid = tfromuid;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public int getThesisid() {
        return thesisid;
    }

    public void setThesisid(int thesisid) {
        this.thesisid = thesisid;
    }

    public String getTdescription() {
        return tdescription;
    }

    public int gettDebateid() {
        return tDebateid;
    }


    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }


    public void settDebateid(int tDebateid) {
        this.tDebateid = tDebateid;
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
}
