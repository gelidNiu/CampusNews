package com.baibian.activity.user_drawer.users_information;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.baibian.R;
import com.baibian.base.MyBaseActivity;
import com.baibian.tool.ToastTools;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPortraitActivity extends MyBaseActivity implements View.OnClickListener{

    private static final int PERMISSION_CAMERA = 1;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    private static final int CHANGE_IMAGE = 1;
    private static final int FROM_CAMERA = 2;
    private static final int FROM_ALBUM = 3;

    private Handler imageLoadHandler;
    private Uri mUri = null;
    private Uri tempUri = null;
    private Bitmap bitmap = null;

    private CircleImageView userPortrait;
    private ImageView blurBackgroundView;

    final private String[] items = {"Scoop", "Capture", "Chosen from album"};
    final private static String fileName = "head_portrait";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_portrait);
        userPortrait = (CircleImageView) findViewById(R.id.user_portrait);
        blurBackgroundView = (ImageView) findViewById(R.id.blur_back_view);
        userPortrait.setOnClickListener(this);
        initFileOfUri();
        initUserPortraitInAdvance();

    }

    private void initFileOfUri() {
        File appDir = new File(Environment.getExternalStorageDirectory(), "LunDao");
        if (!appDir.exists()){
            appDir.mkdir();
        }
        File tempF = new File(appDir, "temp" + ".jpg");
        File f = new File(appDir, fileName + ".jpg");
        mUri = Uri.fromFile(f);
        tempUri = Uri.fromFile(tempF);
    }

    private void initUserPortraitInAdvance() {

        imageLoadHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case CHANGE_IMAGE:

                        if (bitmap != null) {
                            Log.d("Handler", "Handling1");
                            userPortrait.setImageBitmap(bitmap);
                            Bitmap tempBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                            blurBackgroundView.setImageBitmap(blur(tempBitmap, 25f, EditPortraitActivity.this));
//                        blurBackgroundView
                        }else {
                            ToastTools.ToastShow("请设置头像");
                        }
                        break;
                    case FROM_CAMERA:
                        Log.d("Handler", "Handling2");
//                        userPortrait.setImageBitmap(bitmap);
                        userPortrait.setImageBitmap((Bitmap) msg.obj);
                        break;
                    case FROM_ALBUM:
                        Log.d("Handler", "Handling3");
//                        userPortrait.setImageBitmap(bitmap);
                        userPortrait.setImageURI((Uri) msg.obj);
                }
            }
        };

        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = CHANGE_IMAGE;
                bitmap = getSaveImageShared(fileName);
                imageLoadHandler.sendMessage(msg);
                Log.d("thread_test", "Thread Testing");
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startCamera();
                }else {
                    ToastTools.ToastShow("You have just denied the permission request \n Accept it in your phone setting if you want");
                }
                break;
            case PERMISSION_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startAlbum();
                }else {
                    ToastTools.ToastShow("I won't say it again ...");
                }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_portrait:

                final ActionSheetDialog dialog = new ActionSheetDialog(EditPortraitActivity.this, items, v);
                dialog.isTitleShow(false).show();
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (items[position]){
                            case "Capture":
                                if (ContextCompat.checkSelfPermission(EditPortraitActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(EditPortraitActivity.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA);
                                }else {
                                    startCamera();
                                }
                                dialog.dismiss();
                                break;
                            case "Chosen from album":

                                if (ContextCompat.checkSelfPermission(EditPortraitActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(EditPortraitActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE);
                                }else {
                                    startAlbum();
                                }
                                dialog.dismiss();
                                break;
                            case "Scoop":
                                Intent forLarge = new Intent(EditPortraitActivity.this, FullscreenScoopActivity.class);
                                forLarge.putExtra("file_name", fileName);
                                startActivity(forLarge);
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                });

        }
    }

    private void startAlbum() {
        Intent intent1 = new Intent();
        intent1.setType("image/*");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent1,3);
    }

    private void startCamera() {
        Intent intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            /**
             * case 2 and 3 are used for accepting captures and photos from albums to set portrait
             */
            case Crop.REQUEST_CROP:
                userPortrait.setImageURI(mUri);
                new Thread(){
                    @Override
                    public void run() {
                        bitmap = getBitmapFromUri(mUri, EditPortraitActivity.this);
                        imageLoadHandler.sendEmptyMessage(CHANGE_IMAGE);
                        setSaveImageShared(bitmap, fileName);
                        sendBroadcastToChangeImage();
                    }
                }.start();
                break;
            case 2:
                if (data != null) {
                    final Bundle bundle = data.getExtras();
                    final Message camMeg = new Message();
                    if (bundle != null && !bundle.isEmpty()) {
                        bitmap = bundle.getParcelable("data");
                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                        Crop.of(uri, mUri).asSquare().start(this);
                    } else {
                        return;
                    }
                    /*
                    new Thread(){
                        @Override
                        public void run() {
                            camMeg.obj = bitmap;
                            camMeg.what = FROM_CAMERA;
                            imageLoadHandler.sendMessage(camMeg);
                            setSaveImageShared(bitmap, fileName);
                            sendBroadcastToChangeImage();

                        }
                    }.start();*/
                } else {
                    return;
                }
                break;
            case 3:
                if (data != null) {
                    final Uri uri = data.getData();
                    Crop.of(uri, mUri).asSquare().start(this);/*
                    new Thread(){
                        @Override
                        public void run() {
                            Message alMsg = new Message();
                            alMsg.what = FROM_ALBUM;
                            alMsg.obj = uri;
                            imageLoadHandler.sendMessage(alMsg);
                            bitmap = getBitmapFromUri(uri, EditPortraitActivity.this);
                            setSaveImageShared(bitmap, fileName);
                            sendBroadcastToChangeImage();
                        }
                    }.start();*/

                } else {
                    return;
                }
                break;

        }
    }
    /**
     * Notify that the image has been changed.
     */
    private void sendBroadcastToChangeImage() {
        Intent intent = new Intent();
        intent.setAction("com.baibian.image_change");
        sendBroadcast(intent);
    }

    public static Bitmap blur(Bitmap bitmap,float radius, Context context) {
        Bitmap output = Bitmap.createBitmap(bitmap); // 创建输出图片
        RenderScript rs = RenderScript.create(context); // 构建一个RenderScript对象
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); // 创建高斯模糊脚本
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap); // 创建用于输入的脚本类型
        Allocation allOut = Allocation.createFromBitmap(rs, output); // 创建用于输出的脚本类型
        gaussianBlue.setRadius(radius); // 设置模糊半径，范围0f<radius<=25f
        gaussianBlue.setInput(allIn); // 设置输入脚本类型
        gaussianBlue.forEach(allOut); // 执行高斯模糊算法，并将结果填入输出脚本类型中
        allOut.copyTo(output); // 将输出内存编码为Bitmap，图片大小必须注意
        if (Build.VERSION.SDK_INT < 23) {
            rs.destroy(); // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
        } else {
            RenderScript.releaseAllContexts();
        }
        return output;
    }

    public static void setSaveImageShared(Bitmap mBitmap, String fileName){
        File appDir = new File(Environment.getExternalStorageDirectory(), "LunDao");
        if (!appDir.exists()){
            appDir.mkdir();
        }
        File f = new File(appDir, fileName + ".jpg");
        if (f.exists()){
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap getSaveImageShared(String fileName){
        File appDir = new File(Environment.getExternalStorageDirectory(), "LunDao");
        if (!appDir.exists()){
            appDir.mkdir();
        }
        File f = new File(appDir, fileName + ".jpg");
        Bitmap mBitmap = null;
        try {
            FileInputStream stream = new FileInputStream(f);
            mBitmap = BitmapFactory.decodeStream(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBitmap;
    }
    public static Bitmap getBitmapFromUri(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
