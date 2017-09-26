package com.baibian.information;

/**
 * Created by 邹特强 on 2017/7/25.
 */
/*
**为每个论点建立的信息类
 */
public class ForumsArgument {
    private String title;//标题
    private String content;//论点具体内容
    private int perfect_num;
    public void setArgument(String title,String content,int perfect_num)
    {
        this.title=title;
        this.content=content;
        this.perfect_num=perfect_num;
    }
    public String getTitle()
    {
        return title;
    }
    public String getContent()
    {
        return content;
    }
    public int getPerfectNum()
    {
        return perfect_num;
    }

}
