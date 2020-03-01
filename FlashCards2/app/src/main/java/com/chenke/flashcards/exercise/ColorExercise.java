package com.chenke.flashcards.exercise;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenke.flashcards.R;
import com.chenke.flashcards.adapter.ColorPracAdapter;
import com.chenke.flashcards.adapter.PracPagerAdapter;
import com.chenke.flashcards.bean.NumberInfo;
import com.chenke.flashcards.fragment.ColorPracFragment;
import com.chenke.flashcards.fragment.DynamicFragment;
import com.chenke.flashcards.util.Utils;
import com.codingending.popuplayout.PopupLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ColorExercise extends AppCompatActivity implements View.OnClickListener {


    private ImageView img_back;  //左上角返回图标
    private ImageView img_card;  //答题卡
    private ImageView img_time;  //时钟

    private TextView text_time;  //计时

    private ViewPager vp_content;  //viewpager

    //答题卡按钮
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;


    private int relen = 0;

    private int drelen = 60;  //倒计时60秒
    Timer timer = new Timer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);

        //获取计时控件
        text_time = findViewById(R.id.text_time);

        //获取模式名称
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");

        //困难模式倒计时
        if (mode.contains("困难")) {
            //倒计时
            timer.schedule(task, 1000, 1000);
        } else {
            //其它模式计时
            handler.postDelayed(runnable, 1000);
        }


        //获取题目
        ArrayList<NumberInfo> numberList = NumberInfo.getNumberList();
        //构建适配器
        ColorPracAdapter adapter = new ColorPracAdapter(
                getSupportFragmentManager(), numberList, mode);
        //获取翻页视图
        vp_content = findViewById(R.id.vp_content);
        //注册适配器
        vp_content.setAdapter(adapter);
        //设置限制页面数，解决不保存fragment状态的问题
        vp_content.setOffscreenPageLimit(10);
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


    //倒计时
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text_time.setText("" + drelen);
                    drelen--;
                    if (drelen < 0) {
                        timer.cancel();
                        //时间结束，弹出对话框，要求交卷
                        //弹出对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(ColorExercise.this);
                        builder.setTitle("提示");
                        builder.setMessage("时间结束，是否提交？");

                        //设置否定按钮
                        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //禁用答题卡
                                img_card.setEnabled(false);
                                Toast.makeText(ColorExercise.this,"时间已用完，不能提交答案。",Toast.LENGTH_LONG).show();
                            }
                        });

                        //设置肯定按钮
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //跳转到练习报告页面
                                Intent intent = new Intent(ColorExercise.this, NumberReport.class);
                                startActivity(intent);
                            }
                        });
                        builder.show();
                    }
                }
            });
        }
    };

    //计时
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relen++;
            text_time.setText("" + relen);
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_card:
                //点击答题卡的操作
                onClick_card(v);
                break;
            case R.id.img_time:
                //点击时钟的操作
                //onClick_color(v);
                break;

        }

    }

    //初始化数组
    public static void initArray(int a[]) {
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
    }

    //检查数组是否包含0（是否已答完）
    public boolean isall() {
        for (int i = 0; i < DynamicFragment.selectArray.length; i++) {
            if (DynamicFragment.selectArray[i] == 0) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        initArray(ColorPracFragment.selectArray);
    }

    //获取答题卡布局
    public View getCardView() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_card, null);

        //获取题目
        btn1 = view.findViewById(R.id.sub1);
        btn2 = view.findViewById(R.id.sub2);
        btn3 = view.findViewById(R.id.sub3);
        btn4 = view.findViewById(R.id.sub4);
        btn5 = view.findViewById(R.id.sub5);
        btn6 = view.findViewById(R.id.sub6);
        btn7 = view.findViewById(R.id.sub7);
        btn8 = view.findViewById(R.id.sub8);
        btn9 = view.findViewById(R.id.sub9);
        btn10 = view.findViewById(R.id.sub10);

        //button数组
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(0, btn1);
        buttons.add(1, btn2);
        buttons.add(2, btn3);
        buttons.add(3, btn4);
        buttons.add(4, btn5);
        buttons.add(5, btn6);
        buttons.add(6, btn7);
        buttons.add(7, btn8);
        buttons.add(8, btn9);
        buttons.add(9, btn10);

        //判断题目是否被选中
        //1,获取list
        int arr[] = ColorPracFragment.selectArray;
        //2,遍历数组
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                //如果被选中，更改题号的颜色
                buttons.get(i).setBackground(getResources().getDrawable(R.drawable.btn_circle4));
                buttons.get(i).setTextColor(getResources().getColor(R.color.white));
            }
        }
        return view;
    }

    //点击答题卡，从底部弹出答题卡视图
    private void onClick_card(View v) {
        //获取答题卡布局
        View parent = getCardView();
        //答题卡关闭图标
        ImageView img_close = parent.findViewById(R.id.close);
        //提交按钮
        Button btn_submit = parent.findViewById(R.id.btn_submit);
        //初始化布局
        final PopupLayout popupLayout = PopupLayout.init(ColorExercise.this, parent);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.close:  //关闭
                        popupLayout.dismiss();
                        break;
                    case R.id.btn_submit:  //提交答案
                        Toast.makeText(ColorExercise.this, "提交", Toast.LENGTH_SHORT).show();
                        //提交答案先检查是否全部答完
                        if (isall()) {  //如果做完
                            //跳转到练习报告页面
                            Intent intent = new Intent(ColorExercise.this, NumberReport.class);
                            startActivity(intent);
                        } else {  //如果未做完
                            //弹出对话框
                            AlertDialog.Builder builder = new AlertDialog.Builder(ColorExercise.this);
                            builder.setTitle("提示");
                            builder.setMessage("你还有未做的习题，是否提交？");

                            //设置否定按钮
                            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            //设置肯定按钮
                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转到练习报告页面
                                    Intent intent = new Intent(ColorExercise.this, NumberReport.class);
                                    startActivity(intent);

                                }
                            });
                            builder.show();
                        }
                        break;
                    case R.id.sub1:
                        vp_content.setCurrentItem(0, false);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub2:
                        vp_content.setCurrentItem(1, false);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub3:
                        vp_content.setCurrentItem(2);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub4:
                        vp_content.setCurrentItem(3);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub5:
                        vp_content.setCurrentItem(4);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub6:
                        vp_content.setCurrentItem(5);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub7:
                        vp_content.setCurrentItem(6);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub8:
                        vp_content.setCurrentItem(7);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub9:
                        vp_content.setCurrentItem(8);
                        popupLayout.dismiss();
                        break;
                    case R.id.sub10:
                        vp_content.setCurrentItem(9);
                        popupLayout.dismiss();
                        break;

                }
            }
        };
        //注册监听
        img_close.setOnClickListener(clickListener);
        btn_submit.setOnClickListener(clickListener);
        btn1.setOnClickListener(clickListener);
        btn2.setOnClickListener(clickListener);
        btn3.setOnClickListener(clickListener);
        btn4.setOnClickListener(clickListener);
        btn5.setOnClickListener(clickListener);
        btn6.setOnClickListener(clickListener);
        btn7.setOnClickListener(clickListener);
        btn8.setOnClickListener(clickListener);
        btn9.setOnClickListener(clickListener);
        btn10.setOnClickListener(clickListener);

        //不设置圆角
        popupLayout.setUseRadius(false);
        //获取屏幕高度(像素)
        int height = Utils.getScreenHeight(ColorExercise.this);
        //获取屏幕密度
        float density = Utils.getScreenDensity(ColorExercise.this);
        //计算屏幕高度（dp）
        int screenHeight = (int) (height / density);
        //设置弹出窗高度
        popupLayout.setHeight(screenHeight, true);
        //从底部弹出
        popupLayout.show(PopupLayout.POSITION_BOTTOM);
    }



}
