package com.chenke.flashcards.userinfo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chenke.flashcards.R;
import com.chenke.flashcards.bean.ChartLabel;
import com.chenke.flashcards.util.ProgressCircleChart;
import com.chenke.flashcards.util.Utils;

import java.util.ArrayList;


public class StudyProgress extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studyprog);

        Toolbar tl_head = findViewById(R.id.tl_head);
        //设置左边导航栏图标
        tl_head.setNavigationIcon(R.drawable.back);
        //设置标题
        tl_head.setTitle("学习进度");
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

        //获取进度环
        ProgressCircleChart pc = findViewById(R.id.progress_number);
        ProgressCircleChart pc1 = findViewById(R.id.progress_shape);
        ProgressCircleChart pc2 = findViewById(R.id.progress_color);

        //调用后台接口，获取具体比例

        //初始化进度环
        init(pc, 0.45f,"45%", "数字学习");
        init(pc1, 0.67f,"67%", "形状学习");
        init(pc2,0.78f,"78%","颜色学习");

    }

    private void init(ProgressCircleChart pc,float per,String rate,String name) {
        pc.setPer(per);
        pc.setLabelSpace(Utils.dip2px(this,8));
        ArrayList<ChartLabel> chartLabels = new ArrayList<>();
        chartLabels.add(new ChartLabel(rate,Utils.dip2px(this,35), Color.BLUE));
        chartLabels.add(new ChartLabel(name,Utils.dip2px(this,24),Color.BLACK));
        pc.setLabelList(chartLabels);
    }
}
