package com.chenke.flashcards.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenke.flashcards.R;
import com.chenke.flashcards.util.PaletteView;

public class ShapeFragment extends Fragment {
    protected View mView;
    protected Context mContext;


    private PaletteView mPaletteView;  //画板控件


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_shape,container,false);
        mPaletteView = mView.findViewById(R.id.palette);
        return mView;
    }

    //获取画板控件
    public PaletteView getpalette() {
        //View view = View.inflate(this.getContext(),R.layout.fragment_number,null);
        //绑定布局
        //View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_number,null);
        //mPaletteView = view.findViewById(R.id.palette);
        return mPaletteView;
    }
}
