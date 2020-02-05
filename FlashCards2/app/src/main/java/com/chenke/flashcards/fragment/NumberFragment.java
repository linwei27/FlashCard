package com.chenke.flashcards.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chenke.flashcards.R;
import com.chenke.flashcards.util.PaletteView;


public class NumberFragment extends Fragment {
    protected View mView;
    protected Context mContext;

    protected PaletteView mPaletteView;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        mView = inflater.inflate(R.layout.fragment_number,container,false);
        mPaletteView = mView.findViewById(R.id.palette);
        Log.e("中华人民共和国",mPaletteView.getClass().getName());
        return mView;
    }

    //获取画板控件
    public PaletteView getpalette() {
        //View view = View.inflate(mContext,R.layout.fragment_number,null);
        //绑定布局
        //View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_number,null);
        //View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_number,null);

        //mPaletteView = view.findViewById(R.id.palette);
        return mPaletteView;
    }




}
