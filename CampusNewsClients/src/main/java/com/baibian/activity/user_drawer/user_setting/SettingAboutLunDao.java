package com.baibian.activity.user_drawer.user_setting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.baibian.R;
import com.baibian.base.MyBaseActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class SettingAboutLunDao extends MyBaseActivity {

    private static final int LOAD_LOGO = 0;
    ImageView imageView;
    Handler handler;
    private Bitmap toProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about_lun_dao);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case LOAD_LOGO:
//                        imageView.setImageDrawable(ContextCompat.getDrawable(SettingAboutLunDao.this, R.drawable.logo4));

                        imageView.setImageResource(R.drawable.small_logo);
                        break;
                }
                super.handleMessage(msg);
            }
        };
        imageView = (ImageView) findViewById(R.id.setting_a_l_d_logo);
        handler.sendEmptyMessageDelayed(LOAD_LOGO, 100);
    }
    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
