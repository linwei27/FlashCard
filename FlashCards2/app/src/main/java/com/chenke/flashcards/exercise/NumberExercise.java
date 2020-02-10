package com.chenke.flashcards.exercise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chenke.flashcards.R;
import com.chenke.flashcards.adapter.PracPagerAdapter;
import com.chenke.flashcards.bean.NumberInfo;

import java.util.ArrayList;

public class NumberExercise extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;  //左上角返回图标
    private ImageView img_card;  //答题卡
    private ImageView img_time;  //时钟

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);

        //获取题目
        ArrayList<NumberInfo> numberList = NumberInfo.getNumberList();
        //构建适配器
        PracPagerAdapter adapter = new PracPagerAdapter(
                getSupportFragmentManager(),numberList);
        //获取翻页视图
        ViewPager vp_content = findViewById(R.id.vp_content);
        //注册适配器
        vp_content.setAdapter(adapter);
        //默认显示第一个视图
        vp_content.setCurrentItem(0);



        img_back = findViewById(R.id.img_back);  //返回按钮
        img_card = findViewById(R.id.img_card);  //答题卡
        img_time = findViewById(R.id.img_time);  //时钟


        //注册监听器
        img_back.setOnClickListener(this);
        img_card.setOnClickListener(this);
        img_time.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_card:
                //点击答题卡的操作
                //onClick_shape(v);
                break;
            case R.id.img_time:
                //点击时钟的操作
                //onClick_color(v);
                break;
        }

    }
}
