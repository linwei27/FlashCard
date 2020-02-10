package com.chenke.flashcards.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenke.flashcards.R;

public class DynamicFragment extends Fragment {
    protected View mView;  //视图对象
    protected Context mContext;  //上下文对象
    private int position;  //位置
    private String question;  //问题
    private String answer;  //答案
    private int errList[];  //错误选项

    public static DynamicFragment newInstance(int position,String question, String answer, int errList[]) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("question",question);
        bundle.putString("answer",answer);
        bundle.putIntArray("errList",errList);
        fragment.setArguments(bundle);  //包裹塞给碎片
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();  //获取活动页面的上下文
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            question = getArguments().getString("question");
            answer = getArguments().getString("answer");
            errList = getArguments().getIntArray("errList");
        }

        //根据布局文件生成视图对象
        mView = inflater.inflate(R.layout.fragment_practice,container,false);
        //将控件值设为指定值
        return mView;
    }
}
