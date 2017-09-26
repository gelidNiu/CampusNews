package com.baibian.bean.MessageAndNotificationMessage;

/**
 * Created by Ellly on 2017/8/28.
 */

public class MessageDetailItem implements MultipleTypes {

    public enum QuestionCurrentState{
         SOLVED, DELETED, DEFAULT
    }
    private static int ID = 0;
    private String userPortraitUrl;
    private String userName;
    private String questionCotent;
    private String responseContent;
    private String uploadTime;
    private QuestionCurrentState currentState;


    public String getMessageDetailType(){
        return "回复了您的发言";
    }

    @Override
    public int getType() {
        return 0;
    }
}
