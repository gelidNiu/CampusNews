package com.baibian.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.activity.MainActivity;
import com.baibian.tool.HttpTool;
import com.baibian.tool.ToastTools;
import com.baibian.tool.UI_Tools;
import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import cn.smssdk.gui.SMSReceiver;

/**
 * 注册界面的活动
 */
public class registerActivity extends Activity implements View.OnClickListener {
    //protected ImageView register_imageView;//头像部分
    protected EditText register_AccountEdit;//账号
    protected EditText register_password1;//第一次输入的密码
    protected EditText register_password2;//再次确认时输入的密码
    protected Button register_Button;//登录按钮
    protected Button visitor_mode_bt;//游客模式按钮-随便看看
    protected String account;//账号
    protected String password1;//第一次输入的密码
    protected String password2;//再次输入的密码
    private String phone;//电话号码
    private Response signInResponse;
    private Handler handler;
    private boolean isSignUpOK;
    private boolean isAccountHava;
    private String responseBody;
    private LinearLayout register_all_layout;
    private Button getCode_Button;
    private String vertificationCode;
    private EditText code;
    private MyCount mc;
    private boolean isPhoneNumValid;//电话号码是否有效
    private boolean isTouched;//标记发送验证码按钮是否被点击

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_layout);
        initView();

    }

    private void initView() {
        //  register_imageView = (ImageView) findViewById(R.id.register_imageView);
        register_AccountEdit = (EditText) findViewById(R.id.register_AccountEdit);
        register_password1 = (EditText) findViewById(R.id.register_first_passwords);
        //register_password2 = (EditText) findViewById(R.id.register_confirm_editText);
        register_Button = (Button) findViewById(R.id.register_Button);
        visitor_mode_bt = (Button) findViewById(R.id.visitor_mode_bt);
        register_all_layout = (LinearLayout) findViewById(R.id.register_all_layout);
        getCode_Button = (Button) findViewById(R.id.register_getCode);
        code = (EditText) findViewById(R.id.register_Code);
        mc = new MyCount(60000, 1000);//倒计时功能
        UI_Tools ui_tools = new UI_Tools();
        ui_tools.CancelFocusOne(this, register_all_layout, register_AccountEdit);
        register_Button.setOnClickListener(this);
        visitor_mode_bt.setOnClickListener(this);
        getCode_Button.setOnClickListener(this);
        //初始化sdk
        SMSSDK.initSDK(this, "1c56a693870cb", "2849dd663a585497395d6e5d8da70e2e");//俩个参数,Appkey,AppSecret
    }

    /**
     * SMS SDK调用,接受验证码
     */
    private void getVertificationCode1() {
        SMSSDK.initSDK(this, "1c56a693870cb", "2849dd663a585497395d6e5d8da70e2e");//俩个参数,Appkey,AppSecret
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
// 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    phone = (String) phoneMap.get("phone");//获取电话号码
                    account = phone;
// 提交用户信息（此方法可以不调用）
                    //    registerUser(country, phone);
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        System.out.println("验证成功");
                    }
                }
            }
        });
        registerPage.show(this);
        register_AccountEdit.setText(account);
    }

    //注册短信回调
    private void registerMessageCallback() {
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                //操作成功
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功，验证码正确

                    }else if(event==SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //已经获取验证码
                        Toast.makeText(registerActivity.this, "验证码已获取！", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //发送或接收验证码失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(registerActivity.this, "操作失败，请重新获取验证码", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ((Throwable) data).printStackTrace();
                }
                mc.onFinish();//停止计时
            }
        };
        SMSSDK.registerEventHandler(eh);//注册回调
    }

    /**
     * 获取用户输入的电话号码
     */
    private void getEditInformation() {
        phone = register_AccountEdit.getText().toString();
        account = register_AccountEdit.getText().toString();
        password1 = register_password1.getText().toString();
        vertificationCode = code.getText().toString().trim();//获取输入的验证码
    }

    //检测输入的手机号和密码是否有效
    private boolean checkInformation() {
        getEditInformation();
        if (account.length() != 11) {
            ToastTools.ToastShow(getString(R.string.please_account));
            return false;
        } else if (password1.length() > 16 || password1.length() < 6) {
            Toast.makeText(registerActivity.this, R.string.please6to16, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(vertificationCode)) {
            Toast.makeText(this, R.string.vercode_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*

     */
    public void onClick(View v) {
        switch (v.getId()) {
            //点击注册按钮
            case R.id.register_Button:
                if (checkInformation()) {
                    //用于接受登录、注册子线程的返回数据
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            signInResponse = (Response) msg.obj;
                            try {
                                responseBody = signInResponse.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            isSignUpOK = responseBody.startsWith("{\"user\"");
                            isAccountHava = responseBody.startsWith("{\"error\"");
                            if (isSignUpOK) {
                                vertificationCode = code.getText().toString().trim();
                                SMSSDK.submitVerificationCode("86", phone, vertificationCode);//发送验证码
                                System.out.println(isSignUpOK);
                                ToastTools.ToastShow(getString(R.string.signUp_OK));
                                //开启新线程，并将response传过去。
                                Intent intent1 = new Intent(registerActivity.this, MainActivity.class);
                                intent1.putExtra("response", String.valueOf(signInResponse));
                            }
                            //帐号已存在
                            else if (isAccountHava) {
                                ToastTools.ToastShow(getString(R.string.haved_account));
                            } else {
                                ToastTools.ToastShow(getString(R.string.timeout));
                            }
                        }
                    };
                    signUp(account, password1);
                    registerMessageCallback();//设置回调
                    SMSSDK.submitVerificationCode("86", phone, vertificationCode);
                }
                break;
            //点击随便看看按钮
            case R.id.visitor_mode_bt:
                //visitor_mode_bt.setTextColor(0xFF003399);
                //直接进入主界面
                Intent intent2 = new Intent(registerActivity.this, MainActivity.class);
                startActivity(intent2);
                finish();
                break;
            //点击获取验证码按钮
            case R.id.register_getCode:
                //第一次点击，获取验证码
                if(!isTouched) {
                    getEditInformation();//先获取用户输入的信息
                    if (phone.length() != 11) {
                        Toast.makeText(this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mc.start();//开始计时
                    registerMessageCallback();
                    SMSSDK.getVerificationCode("86", phone);//开始获取验证码
                    isTouched=true;
                }else{
                    getCode_Button.setText(R.string.reGetCode);
                    isTouched=false;
                }
                break;

        }
    }

    /*
* 注册方法
* */
    private Response signUp(String account, String password) {
        String user = HttpTool.getSignUpJson(account, password);
        Response response = sign("/api/signup", user);
        return response;
    }

    public Response sign(final String path, final String user) {
        final Response[] post = new Response[1];
        new Thread() {
            public void run() {
                System.out.println("123" + user);
                post[0] = HttpTool.post(user, path);
                Message message = Message.obtain();
                message.obj = post[0];
                handler.sendMessage(message);
            }
        }.start();
        return post[0];
    }
//计时器，用来倒计时
    class MyCount extends CountDownTimer {
        private String MillisUntilFinished;

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //在Ui线程中能够立即运行的线程
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getCode_Button.setText(R.string.reGetCode);
                    mc.cancel();
                }
            });
        }

        @Override
        public void onTick(long millisUntilFinished) {
            MillisUntilFinished = "" + millisUntilFinished / 1000;
            getCode_Button.setText("倒计时" + MillisUntilFinished + "秒");//

        }
    }

}
