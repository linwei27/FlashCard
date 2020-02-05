package com.chenke.flashcards;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chenke.flashcards.adapter.CopyPagerAdapter;
import com.chenke.flashcards.fragment.NumberFragment;
import com.chenke.flashcards.fragment.ShapeFragment;
import com.chenke.flashcards.util.MenuUtil;
import com.chenke.flashcards.util.PaletteView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TabSecondActivity extends AppCompatActivity implements OnTabSelectedListener , PaletteView.Callback, Handler.Callback {
    private ViewPager vp_content;  //翻页视图
    private TabLayout tab_title;  //标签视图
    private ArrayList<String> mTitleArray = new ArrayList<>();  //标题

    private View mUndoView;
    private View mRedoView;
    private View mPenView;
    private View mEraserView;
    private View mClearView;
    private PaletteView mPaletteView;
    private ProgressDialog mSaveProgressDlg;
    private static final int MSG_SAVE_SUCCESS = 1;
    private static final int MSG_SAVE_FAILED = 2;
    private Handler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabsecond);
        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);
        mTitleArray.add("数字临摹");
        mTitleArray.add("形状临摹");
        initTabLayout();
        initTabViewPager();

        //mPaletteView = findViewById(R.id.palette);
        //mPaletteView.setCallback(this);
        //mHandler = new Handler(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MSG_SAVE_FAILED);
        mHandler.removeMessages(MSG_SAVE_SUCCESS);
    }

    private void initSaveProgressDlg(){
        mSaveProgressDlg = new ProgressDialog(this);
        mSaveProgressDlg.setMessage("正在保存,请稍候...");
        mSaveProgressDlg.setCancelable(false);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_SAVE_FAILED:
                mSaveProgressDlg.dismiss();
                Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
                break;
            case MSG_SAVE_SUCCESS:
                mSaveProgressDlg.dismiss();
                Toast.makeText(this,"画板已保存",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }


    private static String saveImage(Bitmap bmp, int quality) {
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
        mUndoView.setEnabled(mPaletteView.canUndo());
        mRedoView.setEnabled(mPaletteView.canRedo());
    }


    //初始化标签布局
    private void initTabLayout() {
        tab_title = findViewById(R.id.tab_title);
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
        // 给tab_title添加标签选中监听器，该监听器默认绑定了翻页视图vp_content
        tab_title.addOnTabSelectedListener(this);
    }

    // 初始化标签翻页
    private void initTabViewPager() {

        //默认获取数字画板
        //mPaletteView = new NumberFragment().getpalette();

        vp_content = findViewById(R.id.vp_content);
        final CopyPagerAdapter adapter = new CopyPagerAdapter(
                getSupportFragmentManager(),mTitleArray);
        vp_content.setAdapter(adapter);


        //获取画板
        //NumberFragment numberFragment = (NumberFragment) adapter.currentFragment;
        //mPaletteView = numberFragment.getView().findViewById(R.id.palette);

        //获取numberFragment
        //NumberFragment numberFragment = (NumberFragment)adapter.getItem(0);
        int index = vp_content.getCurrentItem();
        NumberFragment numberFragment = (NumberFragment) vp_content.getAdapter().instantiateItem(vp_content,index);
        //获取画板
        mPaletteView = numberFragment.getpalette();
        Log.e("画板",mPaletteView.getClass().getName());
        //System.out.print(mPaletteView.getClass().getName().toString());


        vp_content.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Log.e("position",""+position);

                    //获取numberFragment
                    NumberFragment numberFragment = (NumberFragment)adapter.getItem(position);
                    //获取画板
                    mPaletteView = numberFragment.getpalette();
                    //Log.e("object",mPaletteView.getClass().toString());
                    //System.out.print(mPaletteView);
                } else {
                    Log.e("position",""+position);


                    //获取numberFragment
                    ShapeFragment shapeFragment = (ShapeFragment)adapter.getItem(position);
                    //Log.e("object",shapeFragment.getClass().toString());
                    //获取画板
                    mPaletteView = shapeFragment.getpalette();
                    //System.out.print(mPaletteView.getClass().getName().toString());
                    //Log.e("object",mPaletteView.getClass().toString());
                }
                tab_title.getTabAt(position).select();

            }
        });
    }


    //在标签选中时触发
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_content.setCurrentItem(tab.getPosition());
    }

    //在标签取消选中时触发
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    //在标签被重复选中时触发
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //显示菜单左侧的图标
        MenuUtil.setOverflowIconVisible(featureId,menu);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //从menu_home.xml中构建布局
        getMenuInflater().inflate(R.menu.menu_overflow,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {  //点击返回
            finish();
        } else if (id == R.id.menu_revoke) {
            mPaletteView.undo();
            Toast.makeText(this,"撤销",Toast.LENGTH_LONG).show();
        } else if (id == R.id.menu_clear) {
            mPaletteView.clear();
            Toast.makeText(this,"清除",Toast.LENGTH_LONG).show();
        } else if (id == R.id.menu_save) {
            if (mSaveProgressDlg == null) {
                initSaveProgressDlg();
            }
            mSaveProgressDlg.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bm = mPaletteView.buildBitmap();
                    String savedFile = saveImage(bm, 100);
                    if (savedFile != null) {
                        scanFile(TabSecondActivity.this, savedFile);
                        mHandler.obtainMessage(MSG_SAVE_SUCCESS).sendToTarget();
                    }else{
                        mHandler.obtainMessage(MSG_SAVE_FAILED).sendToTarget();
                    }
                }
            }).start();
            Toast.makeText(this,"保存",Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
