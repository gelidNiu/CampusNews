package com.baibian.bean.MessageAndNotificationMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2017/8/31.
 */

public class MessageChatItemContent {

    public static List<MessageChatItem> mData = new ArrayList<>();
    public static int COUNT = 15;
    public static void addItem(MessageChatItem item){
        mData.add(item);
    }

    static {
        for (int i = 0; i < COUNT; i++){
            addItem(new MessageChatItem());
        }
    }

    public static class MessageChatItem{
        private String userPortraitUrl;
        private String userName;
        private String date;
        private String userSignature;
        public boolean isNew;
        public boolean isVIP;
    }
}
