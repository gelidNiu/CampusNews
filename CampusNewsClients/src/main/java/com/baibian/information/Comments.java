package com.baibian.information;

/**
 * Created by Light on 2017/8/14.
 */

/**
 * 评论信息类
 */
public class Comments {
    private String date;
    private boolean is_solved;
    private String user_name;
    private String title;
    private String content;
    private int ImageId;

    public Comments(String date, boolean is_solved, String user_name, String title, String content, int imageId) {
        this.date = date;
        this.is_solved = is_solved;
        this.user_name = user_name;
        this.title = title;
        this.content = content;
        ImageId = imageId;
    }

    public int getImageId() {
        return ImageId;
    }

    public String getDate() {
        return date;
    }

    public boolean getIsSolved() {
        return is_solved;
    }

    public String getUserName() {
        return user_name;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

