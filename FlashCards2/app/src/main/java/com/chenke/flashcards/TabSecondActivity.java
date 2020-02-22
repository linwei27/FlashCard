package com.chenke.flashcards;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chenke.flashcards.adapter.CopyPagerAdapter;
import com.chenke.flashcards.fragment.NumberFragment;
import com.chenke.flashcards.fragment.ShapeFragment;
import com.chenke.flashcards.util.MenuUtil;
import com.chenke.flashcards.util.NoScrollViewPager;
import com.chenke.flashcards.util.PaletteView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TabSecondActivity extends AppCompatActivity implements OnTabSelectedListener {
    private NoScrollViewPager vp_content;  //翻页视图
    private TabLayout tab_title;  //标签视图
    private ArrayList<String> mTitleArray = new ArrayList<>();  //标题

    private int index = 0;


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

        vp_content = findViewById(R.id.vp_content);
        final CopyPagerAdapter adapter = new CopyPagerAdapter(
                getSupportFragmentManager(),mTitleArray);
        vp_content.setAdapter(adapter);
        vp_content.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                index = position;
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
            if (index == 0) {
                NumberFragment.mPaletteView.undo();
            } else if (index == 1) {
                ShapeFragment.mPaletteView.undo();
            }
            Toast.makeText(this,"撤销",Toast.LENGTH_LONG).show();
        } else if (id == R.id.menu_clear) {
            if (index == 0) {
                NumberFragment.mPaletteView.clear();
            } else if (index == 1) {
                ShapeFragment.mPaletteView.clear();
            }
            Toast.makeText(this,"清除",Toast.LENGTH_LONG).show();
        } else if (id == R.id.menu_save) {
            if (index == 0) {
                if (NumberFragment.mSaveProgressDlg == null) {
                    NumberFragment.initSaveProgressDlg();
                }
                NumberFragment.mSaveProgressDlg.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bm = NumberFragment.mPaletteView.buildBitmap();
                        String savedFile = NumberFragment.saveImage(bm, 100);
                        if (savedFile != null) {
                            NumberFragment.scanFile(NumberFragment.mContext, savedFile);
                            NumberFragment.mHandler.obtainMessage(NumberFragment.MSG_SAVE_SUCCESS).sendToTarget();
                        }else{
                            NumberFragment.mHandler.obtainMessage(NumberFragment.MSG_SAVE_FAILED).sendToTarget();
                        }
                    }
                }).start();

            } else if (index == 1) {
                if (ShapeFragment.mSaveProgressDlg == null) {
                    ShapeFragment.initSaveProgressDlg();
                }
                ShapeFragment.mSaveProgressDlg.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bm = ShapeFragment.mPaletteView.buildBitmap();
                        String savedFile = ShapeFragment.saveImage(bm, 100);
                        if (savedFile != null) {
                            ShapeFragment.scanFile(ShapeFragment.mContext, savedFile);
                            ShapeFragment.mHandler.obtainMessage(ShapeFragment.MSG_SAVE_SUCCESS).sendToTarget();
                        }else{
                            ShapeFragment.mHandler.obtainMessage(ShapeFragment.MSG_SAVE_FAILED).sendToTarget();
                        }
                    }
                }).start();
            }
            Toast.makeText(this,"保存",Toast.LENGTH_LONG).show();
        }

        return true;
    }



}
