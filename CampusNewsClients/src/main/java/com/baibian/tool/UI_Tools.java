package com.baibian.tool;


import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

/**
 * �����������������һЩͨ�õ�UI���ֵĶ���
 */
public class UI_Tools {
    private List<ViewGroup> listViewGroup;
    private List<EditText> listEditText;
    public int i = 0;

    public UI_Tools() {

    }

    /**
     * �����������ʹ��EditText,,��������ط���ʱ��ȡ�����㲢���˳�����̡�������EditView�ĵط���Ӧ�õ������������
     * �����������������һ���ǻ���ڶ�����ȡ������Ҫ����ĵط������米��layout��listview֮��ģ�����������������һ��edittext��ֻ��Ҫ����һ���Ϳ��ԴﵽЧ��
     */
    public void CancelFocus(final Activity activity, final List<ViewGroup> listViewGroup, final EditText edittext) {
        System.out.print(listViewGroup.size());
        for (; i < listViewGroup.size(); i++) {
            listViewGroup.get(i)
                    .setFocusable(true);
            listViewGroup.get(i)
                    .setFocusableInTouchMode(true);
            listViewGroup.get(i)
                    .setFocusable(true);
            listViewGroup.get(i)
                    .setFocusableInTouchMode(true);
            listViewGroup.get(i)
                    .requestFocus();
            listViewGroup.get(i)
                    .setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            // TODO Auto-generated method stub
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                            //  imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//���д�����Ե�������̣�����������ط�û��ƨ��
                            imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);//���д������������
                            return false;
                        }
                    });

        }
    }
    //һ�����ֵ�ȡ������
    public void CancelFocusOne(final Activity activity, final ViewGroup viewGroup, final EditText edittext) {

            viewGroup
                    .setFocusable(true);
        viewGroup                    .setFocusableInTouchMode(true);
        viewGroup                    .setFocusable(true);
        viewGroup                    .setFocusableInTouchMode(true);
        viewGroup                    .requestFocus();
        viewGroup                    .setOnTouchListener(new View.OnTouchListener() {

                        public boolean onTouch(View v, MotionEvent event) {
                            // TODO Auto-generated method stub

                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                            //  imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//���д�����Ե�������̣�����������ط�û��ƨ��
                            imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);//���д������������
                            return false;
                        }
                    });


    }
}
