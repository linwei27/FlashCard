package com.chenke.flashcards.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chenke.flashcards.bean.NumberInfo;
import com.chenke.flashcards.fragment.DynamicFragment;

import java.util.ArrayList;

public class PracPagerAdapter extends FragmentStatePagerAdapter {
    //声明一个题目队列
    private ArrayList<NumberInfo> mNumberList = new ArrayList<NumberInfo>();

    public PracPagerAdapter(FragmentManager fm,ArrayList<NumberInfo> numberList) {
        super(fm);
        mNumberList = numberList;
    }

    @Override
    public Fragment getItem(int i) {
        return DynamicFragment.newInstance(i,mNumberList.get(i).question,mNumberList.get(i).answer,
                mNumberList.get(i).errorList);
    }

    @Override
    public int getCount() {
        return mNumberList.size();
    }
}
