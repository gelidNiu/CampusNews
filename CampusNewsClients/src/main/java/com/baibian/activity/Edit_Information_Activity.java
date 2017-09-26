package com.baibian.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.tool.HttpTool;
import com.baibian.tool.LinearLayout_Inflaterable;
import com.baibian.tool.ToastTools;
import com.baibian.tool.UI_Tools;

import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * �༭������Ϣ��activity
 */

public class Edit_Information_Activity extends Activity implements View.OnClickListener {
    private ImageView edit_information_back;//������Ϣ���ؼ�
    private LinearLayout edit_information_all_layout;//ȫ����
    private List<String> data;//��������Դ
    private EditText user_name_edittext;
    private int i;
    private LinearLayout house_LinearLayout;
    private LinearLayout advantage_LinearLayout;
    private View add_house_layout;
    private View add_advantage_layout;
    LinearLayout_Inflaterable house_linearlayout_inflaterable;
    LinearLayout_Inflaterable advantage_linearlayout_inflaterable;
    private TextView complete;
    private Response informationResponse;
    private Handler handler;
    private String responseString;
    private String newString = "";
    private final int EDIT_REQUEST = 1;
    private Intent mIntent;
    private CheckBox female_checkbox;
    private CheckBox male_checkbox;
    private String gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_informationlayout);

        initview();

        data = new ArrayList<String>();
//        for (int i = 0; i < 5; i++) {
//            int index = i + 1;
//            data.add("����" + index);
//        }

        house_linearlayout_inflaterable = new LinearLayout_Inflaterable(this, house_LinearLayout, add_house_layout, data, 10
        );
        house_linearlayout_inflaterable.initlayout();
        advantage_linearlayout_inflaterable = new LinearLayout_Inflaterable(this, advantage_LinearLayout, add_advantage_layout, data, 10);
        advantage_linearlayout_inflaterable.initlayout();
        UI_Tools ui_tools = new UI_Tools();
        ui_tools.CancelFocusOne(this, edit_information_all_layout, user_name_edittext);
        edit_information_back.setOnClickListener(this);
        complete.setOnClickListener(this);
        initResult(newString);

    }

    private void initResult(String newString) {
        mIntent = new Intent();
//        Log.d("����ȥǰ",result_usersString);
        // ���ý���������д���
        this.setResult(EDIT_REQUEST, mIntent);
    }

    private void initview() {
        edit_information_back = (ImageView) findViewById(R.id.edit_information_back);
        edit_information_all_layout = (LinearLayout) findViewById(R.id.edit_information_all_layout);
        user_name_edittext = (EditText) findViewById(R.id.user_name_edittext);
        house_LinearLayout = (LinearLayout) findViewById(R.id.house_LinearLayout);
        add_house_layout = (View) findViewById(R.id.add_house_layout);
        add_advantage_layout = (View) findViewById(R.id.add_advantage_layout);
        advantage_LinearLayout = (LinearLayout) findViewById(R.id.advantage_LinearLayout);
        female_checkbox = (CheckBox) findViewById(R.id.female_checkbox);
        male_checkbox = (CheckBox) findViewById(R.id.male_checkbox);
        complete = (TextView) findViewById(R.id.complete);//���textview
        Intent intent = getIntent();
        responseString = intent.getStringExtra("responseString");
        initcheckbox();
        parseJSONObject(responseString);

    }

    private void initcheckbox() {
        male_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked ) {
                    female_checkbox.setChecked(false);
                    gender="male";
                }
            }
        });
        female_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked ) {
                    male_checkbox.setChecked(false);
                    gender="female";
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_information_back://?????????
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.complete:
                //��Ȼ��������  ��ô��Ҫ�޸���Ϣ��
                String user_name = user_name_edittext.getText().toString();
                String params = "{ \"user\": {\"mobile\": \"" + "13000000001" + "\",\"gender\": \"" + gender + "\",\"experience\": " + "[]" + ",\"educations\": " + "[\"456466\",\"456\"]" + ",\"locations\": " + "[]" + ",\"nickname\": \"" + user_name + "\",\"birthday\": " + "1491835565" + "}}";
                Log.d("������Ϣ", params);
                CompleteInformaion(params);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            default:
                break;
        }
    }

    private void CompleteInformaion(String params) {
        SharedPreferences preferences = getSharedPreferences("usersimformation", MODE_PRIVATE);
        String id = preferences.getString("id", "");//??????????????id
        String path = "/api/users/" + id;
        Log.d("id22", id);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                informationResponse = (Response) msg.obj;
                String string = "";
                if (informationResponse.code() == 200) {
                    try {
                        string = informationResponse.body().string();//??????
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    string = ChangeToJSON(string);
                    Log.d("changedInformation",string);
                    ToastTools.ToastShow("Edit Succeed");
                    //??????????????response???????
                } else {
                    ToastTools.ToastShow(getString(R.string.timeout));
                }
            }
        };
        putin(path, params);
    }

    private Response putin(String path, String params) {
        Log.d("idpath2", path);
        Response response = PutUserInformation(path, params);
//        System.out.println("response" + response);
//        System.out.println("responsecode" + response.body());

        return response;
    }

    public Response PutUserInformation(final String path, final String params) {
        final Response[] post = new Response[1];
        new Thread() {
            public void run() {
                SharedPreferences preferences = getSharedPreferences("usersimformation", MODE_PRIVATE);
                String auth_token = preferences.getString("auth_token", "");//??????????????id
                post[0] = HttpTool.doPutOkHttpResponse(path, params, auth_token);
                Message message = Message.obtain();
                message.obj = post[0];
                handler.sendMessage(message);
            }
        }.start();
        return post[0];
    }

    private void parseJSONObject(String josnData) {
        Log.d("josndata", josnData);
        try {
            JSONArray jsonArray = new JSONArray(josnData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nickname = "";
                nickname = jsonObject.getString("nickname");
                user_name_edittext.setText(nickname);
                gender=jsonObject.getString("gender");
                Log.d("gender",gender);
                if (gender.equalsIgnoreCase("male")){
                    male_checkbox.setChecked(true);
                }else if (gender.equalsIgnoreCase("female")){
                    female_checkbox.setChecked(true);
                }
                Log.d("nickname", nickname);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String ChangeToJSON(String string) {
        string = string.replace("{\"user\":", "");
        string = string.replace("}}", "}");
        string = "[" + string + "]";
        return string;
    }

}
