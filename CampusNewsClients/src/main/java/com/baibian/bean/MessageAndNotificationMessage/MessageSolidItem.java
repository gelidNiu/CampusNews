package com.baibian.bean.MessageAndNotificationMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2017/8/28.
 */

public class MessageSolidItem implements MultipleTypes {

    private List<MessageItemItemItem> mData = new ArrayList<>();

    public MessageSolidItem(List<MessageItemItemItem> data){
        mData = data;
    }

    public MessageSolidItem(){}

    @Override
    public int getType() {
        return 2;
    }

    public List<MessageItemItemItem> getmData() {
        return mData;
    }

    public void setmData(List<MessageItemItemItem> mData) {
        this.mData = mData;
    }
}
