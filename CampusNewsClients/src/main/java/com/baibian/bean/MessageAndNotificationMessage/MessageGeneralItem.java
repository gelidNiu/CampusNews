package com.baibian.bean.MessageAndNotificationMessage;

/**
 * Created by Ellly on 2017/8/28.
 */

public class MessageGeneralItem implements MultipleTypes {
    public enum MessageGeneralCurrentState{
        BEGAN, NEW_EMERGED, USER_ATTEND, USER_FOCUSED
    }
    private MessageGeneralCurrentState currentState;
    private String userName;
    private String title;

    public int getMessageGeneralType(){
        return currentState.ordinal();
    }

    @Override
    public int getType() {
        return 1;
    }
}
