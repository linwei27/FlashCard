package com.chenke.flashcards.userinfo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chenke.flashcards.R;
import com.chenke.flashcards.adapter.PracRecordAdapter;
import com.chenke.flashcards.bean.PracRecord;
import com.chenke.flashcards.util.MyListViewUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PracRecords extends AppCompatActivity implements MyListViewUtils.LoadListener {
    private List<PracRecord> list = new ArrayList<>();  //数据源列表

    private MyListViewUtils listViewUtils;  //自定义的listview

    private PracRecordAdapter adapter;  //适配器

    private Toolbar tl_head;  //顶部工具栏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pracrecord);

        tl_head = findViewById(R.id.tl_head);
        //设置左边导航栏图标
        tl_head.setNavigationIcon(R.drawable.back);
        //设置标题
        tl_head.setTitle("练习记录");
        //设置标题颜色
        tl_head.setTitleTextColor(getResources().getColor(R.color.zfb));
        //替换系统自带的ActionBar
        setSupportActionBar(tl_head);
        //给导航图标设置监听器
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initPracRecord();  //初始化数据，正式用应该从服务端获取
        adapter = new PracRecordAdapter(PracRecords.this, R.layout.pracrecord_item, list);
        //获取listview
        listViewUtils = findViewById(R.id.listview);
        listViewUtils.setInteface(this);
        //设置监听器
        listViewUtils.setAdapter(adapter);

    }

    //时间戳转年月日
    public String getCurrentTime(Long value) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        String time = format.format(new Date(value * 1000L));
        return time;
    }

    private void initPracRecord() {
        PracRecord record1 = new PracRecord("数字练习","4分钟",getCurrentTime(1583299732L),R.drawable.rnumber);
        list.add(record1);
        PracRecord record2 = new PracRecord("数字练习","12分钟",getCurrentTime(1583299732L),R.drawable.rnumber);
        list.add(record2);
        PracRecord record3 = new PracRecord("颜色练习","5分钟",getCurrentTime(1583299732L),R.drawable.rcolor);
        list.add(record3);
        PracRecord record4 = new PracRecord("数字练习","23分钟",getCurrentTime(1583299732L),R.drawable.rnumber);
        list.add(record4);
        PracRecord record5 = new PracRecord("颜色练习","13分钟",getCurrentTime(1583299732L),R.drawable.rcolor);
        list.add(record5);
        PracRecord record6 = new PracRecord("数字练习","17分钟",getCurrentTime(1583299732L),R.drawable.rnumber);
        list.add(record6);
        PracRecord record7 = new PracRecord("形状练习","16分钟",getCurrentTime(1583299732L),R.drawable.rshape);
        list.add(record7);
        PracRecord record8 = new PracRecord("形状练习","5分钟",getCurrentTime(1583299732L),R.drawable.rshape);
        list.add(record8);
        PracRecord record9 = new PracRecord("数字练习","8分钟",getCurrentTime(1583299732L),R.drawable.rnumber);
        list.add(record9);
        PracRecord record10 = new PracRecord("形状练习","45分钟",getCurrentTime(1583299732L),R.drawable.rshape);
        list.add(record10);
        PracRecord record11 = new PracRecord("数字练习","45分钟",getCurrentTime(1583299732L),R.drawable.rnumber);
        list.add(record11);
        PracRecord record12 = new PracRecord("数字练习","45分钟",getCurrentTime(1583299732L),R.drawable.rnumber);
        list.add(record12);
    }

    // 实现PullLoad接口
    @Override
    public void PullLoad() {
        // 设置延时三秒获取时局，用于显示加载效果
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // 这里处理请求返回的结果（这里使用模拟数据）
                PracRecord record20 = new PracRecord("数字练习模拟下拉刷新","45分钟",getCurrentTime(1583299732L),R.drawable.rcolor);
                list.add(0,record20);
                initPracRecord();
                // 更新数据
                adapter.notifyDataSetChanged();
                // 加载完毕
                listViewUtils.loadComplete();
            }
        }, 3000);  //延时3秒，模拟获取数据

    }

    // 实现onLoad接口
    @Override
    public void onLoad() {
        // 设置延时三秒获取时局，用于显示加载效果
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // 这里处理请求返回的结果（这里使用模拟数据）
                PracRecord record21 = new PracRecord("练习模拟上拉加载更多","45分钟",getCurrentTime(1583299732L),R.drawable.rcolor);
                list.add(record21);
                // 更新数据
                adapter.notifyDataSetChanged();
                // 加载完毕
                listViewUtils.loadComplete();
            }
        }, 3000);
    }



}
