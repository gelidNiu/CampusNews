package com.baibian.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

/**
 * Created by 邹特强 on 2017/8/13.
 */
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baibian.R;


public class ResponseActivity extends AppCompatActivity {
    private Button backButton = null;
    private TextView userId = null;
    private TextView textView0 = null;
    private TextView textView01 = null;
    private TextView textView02 = null;
    private TextView textView03 = null;
    private TextView textView04 = null;
    private TextView textView05 = null;
    private TextView textView06 = null;
    private TextView textView07 = null;
    private TextView textView08 = null;
    private TextView textView09 = null;
    private ImageView confirm = null;
    private TextView longTouch = null;
    private EditText respond_edt;
    private TextView wordNum_tv;
    private String response = "";
    private int selectCount = 0;
    private String userName = "";
    private String userIdString = "";
    private String discuss = "";
    private String explaintion = "";

    public static final int updataDiscuss = 1;
    public static final int textView01Selected = 10;
    public static final int textView01Unselected = 11;
    public static final int textView01ActionDown = 12;
    public static final int textView01Actionup = 13;
    public static final int textView02Selected = 20;
    public static final int textView02Unselected = 21;
    public static final int textView02ActionDown = 22;
    public static final int textView02Actionup = 23;
    public static final int textView03Selected = 30;
    public static final int textView03Unselected = 31;
    public static final int textView03ActionDown = 32;
    public static final int textView03Actionup = 33;
    public static final int textView04Selected = 40;
    public static final int textView04Unselected = 41;
    public static final int textView04ActionDown = 42;
    public static final int textView04Actionup = 43;
    public static final int textView05Selected = 50;
    public static final int textView05Unselected = 51;
    public static final int textView05ActionDown = 52;
    public static final int textView05Actionup = 53;
    public static final int textView06Selected = 60;
    public static final int textView06Unselected = 61;
    public static final int textView06ActionDown = 62;
    public static final int textView06Actionup = 63;
    public static final int textView07Selected = 70;
    public static final int textView07Unselected = 71;
    public static final int textView07ActionDown = 72;
    public static final int textView07Actionup = 73;
    public static final int textView08Selected = 80;
    public static final int textView08Unselected = 81;
    public static final int textView08ActionDown = 82;
    public static final int textView08Actionup = 83;
    public static final int textView09Selected = 90;
    public static final int textView09Unselected = 91;
    public static final int textView09ActionDown = 92;
    public static final int textView09Actionup = 93;
    private boolean isSelected01 = false;
    private boolean isSelected02 = false;
    private boolean isSelected03 = false;
    private boolean isSelected04 = false;
    private boolean isSelected05 = false;
    private boolean isSelected06 = false;
    private boolean isSelected07 = false;
    private boolean isSelected08 = false;
    private boolean isSelected09 = false;

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case updataDiscuss:
                    userId.setText(userIdString);
                    textView0.setText(discuss);
                    break;
                case textView01Selected:
                    System.out.println("___textView01selevtedBegin__1111");
                    textView01.setBackgroundResource(R.drawable.blue_background);
                    textView01.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    System.out.println("___textView01selevtedEnd__");
                    break;

                case textView01Unselected:
                    textView01.setBackgroundResource(R.drawable.black_rect);
                    textView01.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView01ActionDown:
                    longTouch.setText("对基本概念的叙述或理解有误；包括对于相近概念的混淆和概念范围的划分不当");
                    break;

                case textView01Actionup:
                    longTouch.setText("长按查看说明");
                    break;


                case textView02Selected:
                    textView02.setBackgroundResource(R.drawable.blue_background);
                    textView02.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    break;


                case textView02Unselected:
                    textView02.setBackgroundResource(R.drawable.black_rect);
                    textView02.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView02ActionDown:
                    longTouch.setText("将事情的前因后果倒置，将结果说成原因，将原因说成结果");
                    break;

                case textView02Actionup:
                    longTouch.setText("长按查看说明");
                    break;

                case textView03Selected:
                    textView03.setBackgroundResource(R.drawable.blue_background);
                    textView03.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    break;

                case textView03Unselected:
                    textView03.setBackgroundResource(R.drawable.black_rect);
                    textView03.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView03ActionDown:
                    longTouch.setText("论证中使用的资料有错误或不能论证其观点");
                    break;

                case textView03Actionup:
                    longTouch.setText("长按查看说明");
                    break;

                case textView04Selected:
                    textView04.setBackgroundResource(R.drawable.blue_background);
                    textView04.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    break;


                case textView04Unselected:
                    textView04.setBackgroundResource(R.drawable.black_rect);
                    textView04.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView04ActionDown:
                    longTouch.setText("对于推出结论的前提叙述或理解有误；或是所叙述的前提无法推出结论");
                    break;

                case textView04Actionup:
                    longTouch.setText("长按查看说明");
                    break;

                case textView05Selected:
                    textView05.setBackgroundResource(R.drawable.blue_background);
                    textView05.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    break;


                case textView05Unselected:
                    textView05.setBackgroundResource(R.drawable.black_rect);
                    textView05.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView05ActionDown:
                    longTouch.setText("仅仅以少数事例就得出一般性的结论");
                    break;

                case textView05Actionup:
                    longTouch.setText("长按查看说明");
                    break;

                case textView06Selected:
                    textView06.setBackgroundResource(R.drawable.blue_background);
                    textView06.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    break;


                case textView06Unselected:
                    textView06.setBackgroundResource(R.drawable.black_rect);
                    textView06.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView06ActionDown:
                    longTouch.setText("使用不恰当的类比得出不恰当的结论");
                    break;

                case textView06Actionup:
                    longTouch.setText("长按查看说明");
                    break;

                case textView07Selected:
                    textView07.setBackgroundResource(R.drawable.blue_background);
                    textView07.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    break;


                case textView07Unselected:
                    textView07.setBackgroundResource(R.drawable.black_rect);
                    textView07.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView07ActionDown:
                    longTouch.setText("将两件本没有因果关系的事情强行建立因果联系");
                    break;

                case textView07Actionup:
                    longTouch.setText("长按查看说明");
                    break;

                case textView08Selected:
                    textView08.setBackgroundResource(R.drawable.blue_background);
                    textView08.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    break;


                case textView08Unselected:
                    textView08.setBackgroundResource(R.drawable.black_rect);
                    textView08.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView08ActionDown:
                    longTouch.setText("..........");
                    break;

                case textView08Actionup:
                    longTouch.setText("长按查看说明");
                    break;

                case textView09Selected:
                    textView09.setBackgroundResource(R.drawable.blue_background);
                    textView09.setTextColor(Color.WHITE);
                    respond_edt.setText(response);
                    break;


                case textView09Unselected:
                    textView09.setBackgroundResource(R.drawable.black_rect);
                    textView09.setTextColor(Color.BLACK);
                    respond_edt.setText(response);
                    break;

                case textView09ActionDown:
                    longTouch.setText("在叙述过程中有与自己话语相矛盾的论证");
                    break;

                case textView09Actionup:
                    longTouch.setText("长按查看说明");
                    break;


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_activity_layout);
        initData();
        respond_edt = (EditText) findViewById(R.id.respond_edt);
        wordNum_tv = (TextView) findViewById(R.id.wordNum_tv);
        respond_edt.addTextChangedListener(new WordNumLimitTextWatcher(respond_edt, 100, wordNum_tv));

        backButton = (Button) findViewById(R.id.backButton);
        userId = (TextView) findViewById(R.id.userId);
        textView0 = (TextView) findViewById(R.id.textView0);
        longTouch = (TextView) findViewById(R.id.longTouch);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        userId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        textView01 = (TextView) findViewById(R.id.textview01);
        textView01.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message12 = new Message();
                                message12.what = textView01ActionDown;
                                handler.sendMessage(message12);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message13 = new Message();
                                message13.what = textView01Actionup;
                                handler.sendMessage(message13);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("___textView01selevtedrunnabkle__");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected01) {
                            if (selectCount != 1) {
                                isSelected01 = true;
                                response = "概念有误。";
                                selectCount = 1;
                                System.out.println("___textView01selevtedCount__");
                                Message message10 = new Message();
                                message10.what = textView01Selected;
                                handler.sendMessage(message10);
                            }
                        } else {
                            isSelected01 = false;
                            response = "";
                            selectCount = 0;
                            Message message11 = new Message();
                            message11.what = textView01Unselected;
                            handler.sendMessage(message11);
                        }

                    }
                }).start();

            }
        });


        textView02 = (TextView) findViewById(R.id.textview02);
        textView02.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message22 = new Message();
                                message22.what = textView02ActionDown;
                                handler.sendMessage(message22);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message23 = new Message();
                                message23.what = textView02Actionup;
                                handler.sendMessage(message23);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected02) {
                            if (selectCount != 1) {
                                isSelected02 = true;
                                response = "因果颠倒。";
                                selectCount = 1;
                                Message message20 = new Message();
                                message20.what = textView02Selected;
                                handler.sendMessage(message20);
                            }
                        } else {
                            isSelected01 = false;
                            response = "";
                            selectCount = 0;
                            Message message21 = new Message();
                            message21.what = textView02Unselected;
                            handler.sendMessage(message21);
                        }

                    }
                }).start();

            }
        });


        textView03 = (TextView) findViewById(R.id.textview03);
        textView03.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message32 = new Message();
                                message32.what = textView03ActionDown;
                                handler.sendMessage(message32);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message33 = new Message();
                                message33.what = textView03Actionup;
                                handler.sendMessage(message33);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected03) {
                            if (selectCount != 1) {
                                isSelected03 = true;
                                response = "资料有误。";
                                selectCount = 1;
                                Message message30 = new Message();
                                message30.what = textView03Selected;
                                handler.sendMessage(message30);
                            } else {
                            }

                        } else {
                            isSelected03 = false;
                            response = "";
                            selectCount = 0;
                            Message message31 = new Message();
                            message31.what = textView03Unselected;
                            handler.sendMessage(message31);
                        }
                    }
                }).start();
            }
        });


        textView04 = (TextView) findViewById(R.id.textview04);
        textView04.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message42 = new Message();
                                message42.what = textView04ActionDown;
                                handler.sendMessage(message42);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message43 = new Message();
                                message43.what = textView04Actionup;
                                handler.sendMessage(message43);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected04) {
                            if (selectCount != 1) {
                                isSelected04 = true;
                                response = "前提有误。";
                                selectCount = 1;
                                Message message40 = new Message();
                                message40.what = textView04Selected;
                                handler.sendMessage(message40);
                            } else {
                            }

                        } else {
                            isSelected04 = false;
                            response = "";
                            selectCount = 0;
                            Message message41 = new Message();
                            message41.what = textView04Unselected;
                            handler.sendMessage(message41);
                        }
                    }
                }).start();
            }
        });


        textView05 = (TextView) findViewById(R.id.textview05);
        textView05.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message52 = new Message();
                                message52.what = textView05ActionDown;
                                handler.sendMessage(message52);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message53 = new Message();
                                message53.what = textView05Actionup;
                                handler.sendMessage(message53);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected05) {
                            if (selectCount != 1) {
                                isSelected05 = true;
                                response = "以偏概全";
                                selectCount = 1;
                                Message message50 = new Message();
                                message50.what = textView05Selected;
                                handler.sendMessage(message50);
                            } else {
                            }

                        } else {
                            isSelected05 = false;
                            response = "";
                            selectCount = 0;
                            Message message51 = new Message();
                            message51.what = textView05Unselected;
                            handler.sendMessage(message51);
                        }
                    }
                }).start();
            }
        });


        textView06 = (TextView) findViewById(R.id.textview06);
        textView06.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message62 = new Message();
                                message62.what = textView06ActionDown;
                                handler.sendMessage(message62);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message63 = new Message();
                                message63.what = textView06Actionup;
                                handler.sendMessage(message63);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected06) {
                            if (selectCount != 1) {
                                isSelected06 = true;
                                response = "类比不当";
                                selectCount = 1;
                                Message message60 = new Message();
                                message60.what = textView06Selected;
                                handler.sendMessage(message60);
                            } else {
                            }

                        } else {
                            isSelected06 = false;
                            response = "";
                            selectCount = 0;
                            Message message61 = new Message();
                            message61.what = textView06Unselected;
                            handler.sendMessage(message61);
                        }
                    }
                }).start();
            }
        });


        textView07 = (TextView) findViewById(R.id.textview07);
        textView07.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message72 = new Message();
                                message72.what = textView07ActionDown;
                                handler.sendMessage(message72);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message73 = new Message();
                                message73.what = textView07Actionup;
                                handler.sendMessage(message73);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView07.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected07) {
                            if (selectCount != 1) {
                                isSelected07 = true;
                                response = "因果无关";
                                selectCount = 1;
                                Message message70 = new Message();
                                message70.what = textView07Selected;
                                handler.sendMessage(message70);
                            } else {
                            }

                        } else {
                            isSelected07 = false;
                            response = "";
                            selectCount = 0;
                            Message message71 = new Message();
                            message71.what = textView07Unselected;
                            handler.sendMessage(message71);
                        }
                    }
                }).start();
            }
        });


        textView08 = (TextView) findViewById(R.id.textview08);
        textView08.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message82 = new Message();
                                message82.what = textView08ActionDown;
                                handler.sendMessage(message82);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message83 = new Message();
                                message83.what = textView08Actionup;
                                handler.sendMessage(message83);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected08) {
                            if (selectCount != 1) {
                                isSelected08 = true;
                                response = "举例不当。";
                                selectCount = 1;
                                Message message80 = new Message();
                                message80.what = textView08Selected;
                                handler.sendMessage(message80);
                            } else {
                            }

                        } else {
                            isSelected08 = false;
                            response = "";
                            selectCount = 0;
                            Message message81 = new Message();
                            message81.what = textView08Unselected;
                            handler.sendMessage(message81);
                        }
                    }
                }).start();
            }
        });


        textView09 = (TextView) findViewById(R.id.textview09);
        textView09.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message92 = new Message();
                                message92.what = textView09ActionDown;
                                handler.sendMessage(message92);
                            }
                        }).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message93 = new Message();
                                message93.what = textView09Actionup;
                                handler.sendMessage(message93);
                            }
                        }).start();
                        break;
                }
                return false;
            }
        });
        textView09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isSelected09) {
                            if (selectCount != 1) {
                                isSelected09 = true;
                                response = "自我矛盾。";
                                selectCount = 1;
                                Message message90 = new Message();
                                message90.what = textView09Selected;
                                handler.sendMessage(message90);
                            } else {
                            }

                        } else {
                            isSelected09 = false;
                            response = "";
                            selectCount = 0;
                            Message message91 = new Message();
                            message91.what = textView09Unselected;
                            handler.sendMessage(message91);
                        }
                    }
                }).start();
            }
        });

        confirm = (ImageView) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

//TODO:待对接

    private void initData() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userIdString = intent.getStringExtra("userId");
        discuss = intent.getStringExtra("discuss");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message0 = new Message();
                message0.what = updataDiscuss;
                handler.sendMessage(message0);

            }
        }).start();
    }


    private class WordNumLimitTextWatcher implements TextWatcher {

        private EditText mEditText;

        private int limitNum;

        private int selectionStart;

        private int selectionEnd;

        private int currentWordNum;

        private TextView wordNumTv;


        private WordNumLimitTextWatcher(EditText mEditText, int limitNum, TextView wordNumTv) {

            this.mEditText = mEditText;

            this.limitNum = limitNum;

            this.wordNumTv = wordNumTv;


        }


        @Override

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            currentWordNum = s.length();

        }


        @Override

        public void afterTextChanged(Editable s) {

            wordNumTv.setText(currentWordNum + "/" + limitNum);

            selectionStart = mEditText.getSelectionStart();

            selectionEnd = mEditText.getSelectionEnd();

            if (currentWordNum > limitNum)

            {

                s.delete(selectionStart - 1, selectionEnd);

                mEditText.setText(s);

                selectionEnd = mEditText.getSelectionEnd();

                mEditText.setSelection(selectionEnd);

            }

        }


    }

}
