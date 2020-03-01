package com.chenke.flashcards.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chenke.flashcards.bean.NumberInfo;
import com.chenke.flashcards.fragment.ColorPracFragment;

import java.util.ArrayList;

public class ColorPracAdapter extends FragmentStatePagerAdapter {
    //声明一个题目队列
    private ArrayList<NumberInfo> mNumberList = new ArrayList<NumberInfo>();
    //模式
    private String mode;

    public ColorPracAdapter(FragmentManager fm, ArrayList<NumberInfo> numberList, String mode) {
        super(fm);
        this.mode = mode;
        mNumberList = numberList;

    }


    @Override
    public Fragment getItem(int i) {
        return ColorPracFragment.newInstance(mode,i,mNumberList.get(i).question,mNumberList.get(i).answer,
                mNumberList.get(i).errorList);
    }

    @Override
    public int getCount() {
        return mNumberList.size();
    }
}
