package com.baibian.bean.jsonbean;

/**
 * Created by 邹特强 on 2017/8/20.
 */

import java.util.List;

/**
 * 论点完善界面对应的javabean类
 */
public class PerfectPage {
    private int thesisid;//原始论点id
    private List<PerfectThesis> perfectThesisList;//完善论点版本列表

    public int getThesisid() {
        return thesisid;
    }

    public void setThesisid(int thesisid) {
        this.thesisid = thesisid;
    }

    public List<PerfectThesis> getPerfectThesisList() {
        return perfectThesisList;
    }

    public void setPerfectThesisList(List<PerfectThesis> perfectThesisList) {
        this.perfectThesisList = perfectThesisList;
    }
}
