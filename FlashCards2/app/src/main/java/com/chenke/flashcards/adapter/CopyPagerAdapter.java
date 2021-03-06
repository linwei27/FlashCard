package com.chenke.flashcards.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenke.flashcards.R;
import com.chenke.flashcards.fragment.NumberFragment;
import com.chenke.flashcards.fragment.ShapeFragment;
import com.chenke.flashcards.util.PaletteView;

import java.util.ArrayList;

public class CopyPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray;  //标题文字队列

    public Fragment currentFragment;

    public CopyPagerAdapter(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    //获取指定位置的碎片
    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new NumberFragment();
        } else if (i == 1) {
            return new ShapeFragment();
        }
        return new NumberFragment();
    }

    //获取画板控件


    //获取碎片的个数
    @Override
    public int getCount() {
        return mTitleArray.size();
    }

    // 获得指定碎片页的标题文本
    public CharSequence getPageTitle(int position) {
        return mTitleArray.get(position);
    }

}
