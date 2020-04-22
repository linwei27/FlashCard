package com.chenke.flashcards.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chenke.flashcards.R;
import com.chenke.flashcards.util.PaletteView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShapeFragment extends Fragment implements PaletteView.Callback, Handler.Callback, View.OnClickListener{
    protected View mView;
    public static Context mContext;


    public static PaletteView mPaletteView;
    public static ProgressDialog mSaveProgressDlg;
    public static final int MSG_SAVE_SUCCESS = 1;
    public static final int MSG_SAVE_FAILED = 2;
    public static Handler mHandler;

    private int i = 1;

    private ImageView img_shape;  //顶部图形图片
    private Button btn_previous;  //上一个按钮
    private Button btn_next;  //下一个按钮



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        mView = inflater.inflate(R.layout.fragment_shape,container,false);
        mPaletteView = mView.findViewById(R.id.palette);

        //获取控件
        img_shape = mView.findViewById(R.id.shape);
        btn_previous = mView.findViewById(R.id.previous);
        btn_next = mView.findViewById(R.id.next);

        //注册监听器
        btn_previous.setOnClickListener(this);
        btn_next.setOnClickListener(this);


        mPaletteView.setCallback(this);
        return mView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPaletteView.setCallback(this);
        mHandler = new Handler(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_SAVE_FAILED:
                mSaveProgressDlg.dismiss();
                Toast.makeText(mContext,"保存失败",Toast.LENGTH_SHORT).show();
                break;
            case MSG_SAVE_SUCCESS:
                mSaveProgressDlg.dismiss();
                Toast.makeText(mContext,"画板已保存",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    public static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }


    public static String saveImage(Bitmap bmp, int quality) {
        if (bmp == null) {
            return null;
        }
        File appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (appDir == null) {
            return null;
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.flush();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public void onUndoRedoStatusChanged() {
        Log.e("className",mPaletteView.getClass().getName());
        //mUndoView.setEnabled(mPaletteView.canUndo());
        //mRedoView.setEnabled(mPaletteView.canRedo());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MSG_SAVE_FAILED);
        mHandler.removeMessages(MSG_SAVE_SUCCESS);
    }

    public static void initSaveProgressDlg(){
        mSaveProgressDlg = new ProgressDialog(mContext);
        mSaveProgressDlg.setMessage("正在保存,请稍候...");
        mSaveProgressDlg.setCancelable(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous:
                //切换数字
                previous();
                //清除画板
                mPaletteView.clear();
                break;
            case R.id.next:
                next();
                mPaletteView.clear();
                break;
        }
    }



    //切换下一张图片
    private void next() {
        //先判断是否是最后一张，如果是给出提示
        if (i == 9) {
            //提示信息
            Toast.makeText(mContext, "这已经是最后一张图片了！",Toast.LENGTH_SHORT).show();
            return;
        }
        i++;
        String imageName = "shape0" + i;


        int resID = getResources().getIdentifier(imageName,"drawable","com.chenke.flashcards");
        Drawable image = getResources().getDrawable(resID);
        img_shape.setImageDrawable(image);

        Log.e("打印：","下一个");
    }

    //切换上一张图片
    private void previous() {
        //先判断是否是第一张，是的话给出提示
        if (i == 1){
            //提示信息
            Toast.makeText(mContext, "这已经是第一张图片了！",Toast.LENGTH_SHORT).show();
            return;
        }

        i--;
        String imageName = "shape0" + i;

        int resID = getResources().getIdentifier(imageName,"drawable","com.chenke.flashcards");
        Drawable image = getResources().getDrawable(resID);
        img_shape.setImageDrawable(image);
        Log.e("打印：","上一个");
    }



}
