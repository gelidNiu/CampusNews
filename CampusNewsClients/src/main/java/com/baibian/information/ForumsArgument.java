package com.baibian.information;

/**
 * Created by ����ǿ on 2017/7/25.
 */
/*
**Ϊÿ���۵㽨������Ϣ��
 */
public class ForumsArgument {
    private String title;//����
    private String content;//�۵��������
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
