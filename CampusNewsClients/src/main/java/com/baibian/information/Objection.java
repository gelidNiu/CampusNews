package com.baibian.information;

/**
 * Created by 邹特强 on 2017/8/2.
 */

/**
 * 异议的信息类
 */
public class Objection {
    private String objectionPerson;//异议者的名字
    private String time;//异议发表时间
    private String title;//异议标题
    private String content;//异议的具体内容

    private void set(String objectionPerson, String time, String title, String content) {
        this.objectionPerson = objectionPerson;
        this.time = time;
        this.title = title;
        this.content = content;
    }

    private String getObjectionPerson() {
        return this.objectionPerson;
    }

    private String getTime() {
        return this.time;
    }

    private String getTitle() {
        return this.title;
    }

    private String getContent() {
        return this.content;
    }
}

