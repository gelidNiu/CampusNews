package com.baibian.fragment.forums;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;

/**
 * 完善理由碎片
 */
public class PerfectReasonFragment extends Fragment implements View.OnClickListener{
    private EditText sketch_argument_edt;//����˵���۵������
    private EditText explain_argument_edt;//�����۵������
    private TextView sketch_argument_wordNum_tv;//��¼�������������
    private TextView explain_argument_wordNum_tv;//��¼˵�����������
    private ImageView argument_back_img;
    private int sketchWordNum=0;//����˵�����������
    private int explainWordNum=0;//�����۵����������
    private int selectionStart=0;//�༭�����ʼλ��
    private int selectionEnd=0;//�༭���ĩβλ��
    private SpannableString spannableString;
    private View mView;
    private Context mActivity;
    private TextView reason_confirm_tv;
    private ChooseTvClickListener chooseTvClickListener;

    /**
     * 接口，方便perfectArgumentActivity回调，间接实现activity对fragment控件的监听
     */
    public interface ChooseTvClickListener{
        void onChooseTvClick();
    }
    public void setChooseTvClickListener(ChooseTvClickListener chooseTvClickListener)
    {
        this.chooseTvClickListener=chooseTvClickListener;
    }
    @Override
    public void onCreate(Bundle onSavedInstanceState)
    {
        System.out.println("perfectReasonFragment onCreate1");
        setRetainInstance(true);
        super.onCreate(onSavedInstanceState);
    }
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mActivity=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("perfectReasonFragment onCreate");
        mView=inflater.inflate(R.layout.fragment_perfect_reason, container, false);
        initView();
        return mView;
    }
    private void initView()
    {
        sketch_argument_edt=(EditText)mView.findViewById(R.id.sketch_argument_edt);
        explain_argument_edt=(EditText)mView.findViewById(R.id.explain_argument_edt);
        sketch_argument_wordNum_tv=(TextView)mView.findViewById(R.id.sketch_wordNum_tv);
        explain_argument_wordNum_tv=(TextView)mView.findViewById(R.id.explain_wordNum_tv);
        reason_confirm_tv=(TextView)mView.findViewById(R.id.reason_confirm_tv);
        argument_back_img=(ImageView)mView.findViewById(R.id.argument_back_img);
        argument_back_img.setOnClickListener(this);
        reason_confirm_tv.setOnClickListener(this);
        /*
        **���������߼�
         */
        sketch_argument_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sketchWordNum=s.length();//ʵʱ��¼���������
            }
            @Override
            public void afterTextChanged(Editable s) {
                sketch_argument_wordNum_tv.setText(s.length()+"/38");
                selectionStart=sketch_argument_edt.getSelectionStart();
                selectionEnd=sketch_argument_edt.getSelectionEnd();
                if(sketchWordNum>38)
                {
                    s.delete(selectionStart-1,selectionEnd);//��������ɾ��������ԭ��δ��ȫ���
                    sketch_argument_edt.setText(s);
                    selectionEnd=sketch_argument_edt.getSelectionEnd();
                    sketch_argument_edt.setSelection(selectionEnd);
                }
            }
        });
        /*
        **���������߼�
         */
        explain_argument_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                explainWordNum=s.length();//ʵʱ��¼���������
            }
            //�мǲ����������������̫���ʱ����������Ῠ��
            @Override
            public void afterTextChanged(Editable s) {
                explain_argument_wordNum_tv.setText(s.length()+"/100");
                selectionStart=sketch_argument_edt.getSelectionStart();
                selectionEnd=sketch_argument_edt.getSelectionEnd();
                if(explainWordNum>100)
                {
                    s.delete(selectionStart-1,selectionEnd);//��������ɾ��������ԭ��δ��ȫ���
                    explain_argument_edt.setText(s);
                    selectionEnd=explain_argument_edt.getSelectionEnd();
                    sketch_argument_edt.setSelection(selectionEnd);
                }
            }
        });

    }
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.argument_back_img:
                getActivity().finish();
                break;
            case R.id.reason_confirm_tv:
                System.out.println("A");
                if(chooseTvClickListener!=null)
                {
                    System.out.println("B");
                    chooseTvClickListener.onChooseTvClick();//调用activity方法，让activity处理
                }
                break;

        }
    }

}
