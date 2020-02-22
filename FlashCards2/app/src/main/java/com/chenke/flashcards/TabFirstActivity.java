package com.chenke.flashcards;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.chenke.flashcards.group.CircleMenuLayout;
import com.chenke.flashcards.study.ColorStudy;
import com.chenke.flashcards.study.NumberStudy;
import com.chenke.flashcards.study.ShapeStudy;



public class TabFirstActivity extends AppCompatActivity{
    private Button btn_number;  //数字按钮
    private Button btn_shape;   //形状按钮
    private Button btn_color;   //颜色按钮

    private CircleMenuLayout mCircleMenuLayout;  //圆形转盘
    private String[] mItemTexts = new String[] { "数字学习 ", "形状学习", "颜色学习",
            "数字学习", "形状学习", "颜色学习" };
    private int[] mItemImgs = new int[] { R.drawable.number1,
            R.drawable.shape1, R.drawable.color1,
            R.drawable.number2, R.drawable.shape2,
            R.drawable.color2 };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabfirst);
        mCircleMenuLayout = findViewById(R.id.menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                if (pos == 0 || pos == 3) {
                    onClickBtn_number(view);
                } else if (pos == 1 || pos == 4) {
                    onClickBtn_shape(view);
                } else if (pos == 2 || pos == 5) {
                    onClickBtn_color(view);
                }
                Toast.makeText(TabFirstActivity.this,"点击了元素",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(TabFirstActivity.this,"点击了中间",Toast.LENGTH_SHORT).show();
            }
        });


    }

    //数字学习
    private void onClickBtn_number(View view) {
        //处理逻辑
        Intent intent = new Intent(this, NumberStudy.class);
        startActivity(intent);
    }

    //形状学习
    private void onClickBtn_shape(View view) {
        //处理逻辑
        Intent intent = new Intent(this, ShapeStudy.class);
        startActivity(intent);
    }

    //颜色学习
    private void onClickBtn_color(View view) {
        //处理逻辑
        Intent intent = new Intent(this, ColorStudy.class);
        startActivity(intent);
    }


}
