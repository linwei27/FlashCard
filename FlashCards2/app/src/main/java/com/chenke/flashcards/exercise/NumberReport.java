package com.chenke.flashcards.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chenke.flashcards.MainActivity;
import com.chenke.flashcards.R;
import com.chenke.flashcards.TabThirdActivity;
import com.chenke.flashcards.bean.NumberInfo;

import java.util.ArrayList;

public class NumberReport extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private Button btn_count;  //答对题目数

    private Bundle bundle;  //包裹

    private int count = 0;  //答对数量

    private String shapeName[] = {
            "圆形","三角形","长方形","正方形","心形","五角星",
            "球体","圆柱体","圆锥体"
    };

    //颜色数组
    private String colorName[] = {
            "红色",
            "橙色",
            "黄色",
            "绿色",
            "蓝色",
            "青色",
            "紫色"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pracreport);

        btn_count = findViewById(R.id.btn_count);

        img_back = findViewById(R.id.img_back);

        img_back.setOnClickListener(this);

        Intent intent = getIntent();
        //获取标识
        String TAG = intent.getStringExtra("TAG");
        //获取数据包
        bundle = intent.getExtras();
        //获取答案数组
        String a[] = bundle.getStringArray("answerList");
        //获取用户的答案
        int b[] = bundle.getIntArray("pickList");

        if (TAG.equals("Number")) {  //判断数字练习

            for (int i = 0; i < b.length; i++) {
                //数字直接判断
                if (b[i] == Integer.parseInt(a[i])) {
                    count++;
                }
            }

            btn_count.setText(count + "");

        } else if (TAG.equals("Shape")) {  //判断形状
            for (int i = 0; i < b.length; i++) {

                if (a[i].equals(shapeName[b[i]])){
                    count++;
                }

            }


        } else if (TAG.equals("Color")) {  //判断颜色练习
            for (int i = 0; i < b.length; i++) {

                if (a[i].equals(colorName[b[i]])) {
                    count++;
                }
            }
        }

        //改变答对数量值
        btn_count.setText(count + "");


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                Intent intent = new Intent(NumberReport.this, MainActivity.class);
                intent.putExtra("msg","finish");
                startActivity(intent);
                break;
        }

    }

}
