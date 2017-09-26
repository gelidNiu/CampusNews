package com.baibian.view.LunBa;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.baibian.R;


public class AddSubtractView extends RelativeLayout implements View.OnClickListener, TextWatcher {

    private Button mAddButton;
    private Button mSubtractButton;
    private EditText mFigureEditText;
    private int mCount = 1;
    private int MAX_COUNT = 20;
    private OnCountChangeListener mListener;

    public int getmCount() {
        return mCount;
    }

    public void setMAX_COUNT(int MAX_COUNT) {
        this.MAX_COUNT = MAX_COUNT;
    }

    public int getMAX_COUNT() {
        return MAX_COUNT;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        /**
         *删到空时默认最低值为1
         */
        if (s.toString().isEmpty()){
            mFigureEditText.setText(String.valueOf(1));
            mFigureEditText.setSelection(1);
            return;
        }
        mCount = Integer.valueOf(s.toString());
        /**
         * 当增加到超过最大值时默认设置为MAX_COUNT
         * 并将光标移动到最后
         */
        if (mCount > MAX_COUNT){
            mFigureEditText.setText(String.valueOf(MAX_COUNT));
            mFigureEditText.setSelection(String.valueOf(MAX_COUNT).length());
        }
        if (mListener != null){
            mListener.onCountChange(mCount);
        }
    }

    /**
     * 数值变化时的监听器
     */
    public interface OnCountChangeListener{
        void onCountChange(int count);
    }
    public AddSubtractView(Context context) {
        super(context);
        initView();
    }


    public AddSubtractView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AddSubtractView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setOnCountChangeListener(OnCountChangeListener listener){
        mListener = listener;
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.activity_periodical_detail_buy_popup_layout_add_subtract_view, this);
        mAddButton = (Button) findViewById(R.id.add_btn);
        mSubtractButton = (Button) findViewById(R.id.subtract_btn);
        mFigureEditText = (EditText) findViewById(R.id.figure_view);
        mAddButton.setOnClickListener(this);
        mSubtractButton.setOnClickListener(this);
        mFigureEditText.addTextChangedListener(this);
        mFigureEditText.setSelection(mFigureEditText.getText().toString().length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                ++mCount;
                if (mCount <= MAX_COUNT) {
                    changeCount(mCount);
                }
                mFigureEditText.clearFocus();
                break;
            case R.id.subtract_btn:
                if (mCount > 0) {
                    --mCount;
                    changeCount(mCount);
                }
                mFigureEditText.clearFocus();
                break;
        }
    }

    public int getMaxCount(){
        return MAX_COUNT;
    }
    public void changeCount(int count) {
        String content = String.valueOf(count);
        if (count <= MAX_COUNT && count > 0) {
            mFigureEditText.setText(content);
            mFigureEditText.setSelection(content.length());
            if (mListener != null){
                mListener.onCountChange(count);
            }
        }
    }
}
