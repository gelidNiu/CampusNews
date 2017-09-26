package com.baibian.fragment.forums;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baibian.R;

/**
 * 论点内容碎片
 */
public class PerfectArgumentFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private TextView publish_tv;//发表按钮
    private EditText myArgument_edt;//我的版本论点
    private EditText original_argument_edt;
    private ForegroundColorSpan add_foregroundColorSpan;
    private ForegroundColorSpan delete_foregroundColorSpan;
    private ForegroundColorSpan background_foregroundColorSpan;
    private SpannableStringBuilder myArgument_spannableString;
    private SpannableStringBuilder original_spannableString;
    private Editable original_editable;
    private Editable change_editable;

@Override
public void onCreate(Bundle onSavedInstanceState)
{
    setRetainInstance(true);
    super.onCreate(onSavedInstanceState);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_perfect_argument, container, false);
        initView();
        return mView;
    }

    public void initView() {
        publish_tv = (TextView) mView.findViewById(R.id.publish_tv);
        myArgument_edt = (EditText) mView.findViewById(R.id.myArgument_edt);
        myArgument_edt.setText(R.string.choose_exist_objection);
        myArgument_edt.addTextChangedListener(new ChangeEdtWatcher());
        original_argument_edt=(EditText)mView.findViewById(R.id.original_argument_edt);
        original_argument_edt.setText(myArgument_edt.getText());//复制内容
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish_tv:
                publish();
                break;//发表信息逻辑
        }
    }
    private class ChangeEdtWatcher implements TextWatcher {
        private int end;//记录编辑后的光标位置
        private int start;//输入框光标位置
        private int index;
        private int beforeNum;
        private int afterNum;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            index = start;//获取编辑起始位置
            this.start = myArgument_edt.getSelectionStart();
            beforeNum=s.length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            end = myArgument_edt.getSelectionEnd();
            afterNum=s.length();
           if (end < start) {
                /**
                 * 删除的逻辑
                 */
             deleteWord();
           } else addWord(s);
        }

        /**
         * *添加问文字的逻辑
         */
        private void addWord(Editable s) {
            add_foregroundColorSpan = new ForegroundColorSpan(Color.rgb(255,152,0));
            background_foregroundColorSpan = new ForegroundColorSpan(Color.rgb(71,186,254));
            myArgument_spannableString = new SpannableStringBuilder(myArgument_edt.getText());
            myArgument_spannableString.setSpan(add_foregroundColorSpan, index, myArgument_edt.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            myArgument_edt.removeTextChangedListener(this);//移除监听
            myArgument_edt.setText(myArgument_spannableString);//所有能改变edittext内容的代码在这里出现都会吹出现无限循环的现象！！
            myArgument_edt.addTextChangedListener(this);//添加监听，防止死循环
            myArgument_edt.setSelection(end);//设置光标在上次编辑位置
            original_editable = original_argument_edt.getEditableText();//original_editable必须也实时刷新，否则会出现bug，，，，因为editable内容改变后就不是同一个editable，所以insert方法就无效了
            original_editable.insert(index, s.toString().substring(index, end));//插入右边文本添加的文字
            original_spannableString = new SpannableStringBuilder(original_argument_edt.getText());
            original_spannableString.setSpan(background_foregroundColorSpan, index, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//将增加部分文字颜色变为背景色
            original_argument_edt.setText(original_spannableString);
            myArgument_spannableString = null;//消除对象引用，防止内存泄漏，下同
            original_spannableString = null;

        }

        /**
         * *删除文字的逻辑
         */
        private void deleteWord() {
            if((beforeNum-afterNum)>1)
            {
                start=end+beforeNum-afterNum;
            }
            delete_foregroundColorSpan = new ForegroundColorSpan(Color.rgb(229,28,35));
            change_editable = myArgument_edt.getEditableText();//change_editable不可以保存字体的颜色，检测说明spannableString才可以显示文本的颜色等信息去，其它如string，editable对象都无法显示颜色
            myArgument_edt.removeTextChangedListener(this);
            change_editable.insert(end, original_argument_edt.getText().toString().substring(end, start));//插入刚删除的文本
            myArgument_spannableString = new SpannableStringBuilder(myArgument_edt.getText());
            myArgument_spannableString.setSpan(delete_foregroundColorSpan, end, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            myArgument_edt.setText(myArgument_spannableString);
            myArgument_edt.addTextChangedListener(this);
            myArgument_edt.setSelection(end);
            myArgument_spannableString = null;
        }
        private void cutWord()
        {

        }
    }

    /**
     * 向后端提交完善后端的信息，需要提交的信息，有1.整个异议item的所有部件信息2.修改后的论点内容
     */
    public void publish() {

    }

}
