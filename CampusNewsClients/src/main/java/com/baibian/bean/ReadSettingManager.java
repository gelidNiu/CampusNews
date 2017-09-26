package com.baibian.bean;


import com.baibian.R;
import com.baibian.tool.SharedPreUtils;

/**
 * 阅读器的配置管理
 */

public class ReadSettingManager {
    public static final int READ_BG_DEFAULT = 0;
    public static final int READ_BG_1 = 1;
    public static final int READ_BG_2 = 2;
    public static final int READ_BG_3 = 3;
    public static final int READ_BG_4 = 4;
    public static final int NIGHT_MODE = 5;

    public static final String SHARED_READ_TEXT_SIZE = "shared_read_text_size";
    public static final String SHARED_READ_NIGHT_MODE = "shared_night_mode";
    public static final String SHARED_READ_BACKGROUND = "shared_background";



    //单例模式:确保某一个类只有一个实例，而且自行实例化并向整个系统提供这个实例。
    //这样做有以下几个优点:
    //1.对于那些比较耗内存的类，只实例化一次可以大大提高性能，尤其是在移动开发中。
    //2.保持程序运行的时候该中始终只有一个实例存在内存中
    private static volatile ReadSettingManager sInstance;
    //用volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最的值

    private SharedPreUtils sharedPreUtils;
    public static ReadSettingManager getInstance(){
        if (sInstance == null){
            synchronized (ReadSettingManager.class){
                if (sInstance == null){
                    sInstance = new ReadSettingManager();
                }
            }
        }
        return sInstance;
    }
    //synchronized 关键字，代表这个方法加锁,相当于不管哪一个线程（例如线程A），运行到这个方法时,
    // 都要检查有没有其它线程B（或者C、 D等）正在用这个方法(或者该类的其他同步方法)，
    // 有的话要等正在使用synchronized方法的线程B（或者C 、D）运行完这个方法后再运行此线程A,没有的话,锁定调用者,然后直接运行。



    private ReadSettingManager(){
        sharedPreUtils = SharedPreUtils.getInstance();
    }




    public void setTextSize(int textSize){
        sharedPreUtils.putInt(SHARED_READ_TEXT_SIZE,textSize);
    }


    public int getTextSize(){
        return sharedPreUtils.getInt(SHARED_READ_TEXT_SIZE, 16);
    }


    public void setNightMode(boolean isNight){
        sharedPreUtils.putBoolean(SHARED_READ_NIGHT_MODE,isNight);
    }

    public boolean isNightMode(){
        return sharedPreUtils.getBoolean(SHARED_READ_NIGHT_MODE, false);
    }

    public void setBackground(int background){
        sharedPreUtils.putInt(SHARED_READ_BACKGROUND,background);
    }
    public int getBackground(){
        return sharedPreUtils.getInt(SHARED_READ_BACKGROUND, R.color.white_dark);
    }



//    public void setReadBackground(int theme){
//        sharedPreUtils.putInt(SHARED_READ_BG,theme);
//    }


//    public int getReadBgTheme(){
//        return sharedPreUtils.getInt(SHARED_READ_BG, READ_BG_DEFAULT);
//    }
    


//    public void setFullScreen(boolean isFullScreen){
//        sharedPreUtils.putBoolean(SHARED_READ_FULL_SCREEN,isFullScreen);
//    }
//
//    public boolean isFullScreen(){
//        return sharedPreUtils.getBoolean(SHARED_READ_FULL_SCREEN,false);
//    }
}
