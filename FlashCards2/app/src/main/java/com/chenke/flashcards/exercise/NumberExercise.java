package com.chenke.flashcards.exercise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chenke.flashcards.R;
import com.chenke.flashcards.adapter.PracPagerAdapter;
import com.chenke.flashcards.bean.NumberInfo;
import com.chenke.flashcards.bean.Subject;
import com.chenke.flashcards.fragment.DynamicFragment;
import com.chenke.flashcards.util.Utils;
import com.codingending.popuplayout.PopupLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NumberExercise extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;  //左上角返回图标
    private ImageView img_card;  //答题卡
    private ImageView img_time;  //时钟

    private TextView text_time;  //计时

    private ViewPager vp_content;  //viewpager

    private String mode;  //模式

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



    private TextView tvResult;
    private ProgressBar prbRate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);

        //获取进度条和说明文字
        tvResult = findViewById(R.id.tv_result);
        prbRate = findViewById(R.id.prb_rate);
        prbRate.setMax(100);


        //获取计时控件
        text_time = findViewById(R.id.text_time);

        //获取模式名称
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        //请求地址
        String url = "http://192.168.124.24:8080//number/getNumQuestion?grade=1";

        if (mode.contains("中等")) {
            url = "http://192.168.124.24:8080//number/getNumQuestion?grade=2";
        } else if (mode.contains("困难")) {
            url = "http://192.168.124.24:8080//number/getNumQuestion?grade=3";
        }

        //调用异步任务获取题目
        GetSubjectTask subjectTask = new GetSubjectTask();
        subjectTask.execute(url);




        img_back = findViewById(R.id.img_back);  //返回按钮
        img_card = findViewById(R.id.img_card);  //答题卡
        img_time = findViewById(R.id.img_time);  //时钟

        //注册监听器
        img_back.setOnClickListener(this);
        img_card.setOnClickListener(this);
        img_time.setOnClickListener(this);


    }


    //获取题目异步任务
    public class GetSubjectTask extends AsyncTask<String, Integer, ArrayList<NumberInfo>> {

        private ArrayList<NumberInfo> list = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            tvResult.setText("开始获取题目！");
        }

        @Override
        protected ArrayList<NumberInfo> doInBackground(String... strings) {
            //Log.e("请求参数",strings[0]);
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(strings[0])
                    .get()   //默认get请求
                    .build();
            Call call = okHttpClient.newCall(request);


            int j = 0;
            for (; j < 100; j+=8) {

                publishProgress(j);

                try {
                    Thread.sleep(200);  //延时模拟慢动作
                    Response response = call.execute();

                    String responseData = response.body().string();
                    //Log.e("msg",responseData);

                    Gson gson = new Gson();

                    //对于List，反序列化时必须提供它的Type，通过Gson提供的TypeToken<T>.getType()方法可以定义当前List的Type
                    Type numberListType = new TypeToken<ArrayList<Subject>>() {
                    }.getType();

                    ArrayList<Subject> subjects = gson.fromJson(responseData, numberListType);

                    //Log.e("msgsss",subjects.get(0).getValue().getQuestion());


                    for (int i = 0; i < subjects.size(); i++) {
                        //将题目信息添加到list列表
                        list.add(subjects.get(i).getValue());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return list;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            prbRate.setProgress(values[0]);
            tvResult.setText("正在获取题目，已完成 : " + values[0] + "%");
        }

        @Override
        protected void onPostExecute(ArrayList<NumberInfo> numberInfos) {
            tvResult.setText("题目获取完成！");

            //获取到题目之后隐藏进度条等信息
            prbRate.setVisibility(prbRate.GONE);
            tvResult.setVisibility(tvResult.GONE);



            //困难模式倒计时
            if (mode.contains("困难")) {
                //倒计时
                timer.schedule(task, 1000, 1000);
            } else {
                //其它模式计时
                handler.postDelayed(runnable, 1000);
            }

            //构建适配器
            PracPagerAdapter adapter = new PracPagerAdapter(
                    getSupportFragmentManager(), numberInfos, mode);
            //获取翻页视图
            vp_content = findViewById(R.id.vp_content);
            //注册适配器
            vp_content.setAdapter(adapter);
            //设置限制页面数，解决不保存fragment状态的问题
            vp_content.setOffscreenPageLimit(10);
            //默认显示第一个视图
            vp_content.setCurrentItem(0);



        }


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
                        AlertDialog.Builder builder = new AlertDialog.Builder(NumberExercise.this);
                        builder.setTitle("提示");
                        builder.setMessage("时间结束，是否提交？");

                        //设置否定按钮
                        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //禁用答题卡
                                img_card.setEnabled(false);
                                Toast.makeText(NumberExercise.this,"时间已用完，不能提交答案。",Toast.LENGTH_LONG).show();
                            }
                        });

                        //设置肯定按钮
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //跳转到练习报告页面
                                Intent intent = new Intent(NumberExercise.this, NumberReport.class);
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
        initArray(DynamicFragment.selectArray);
    }

    //获取答题卡布局
    public View getCardView() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_card, null);

        //获取答题卡mode
        TextView textView = view.findViewById(R.id.text_mode);
        //给答题卡设置mode
        textView.setText(mode);

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
        int arr[] = DynamicFragment.selectArray;
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
        final PopupLayout popupLayout = PopupLayout.init(NumberExercise.this, parent);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.close:  //关闭
                        popupLayout.dismiss();
                        break;
                    case R.id.btn_submit:  //提交答案
                        Toast.makeText(NumberExercise.this, "提交", Toast.LENGTH_SHORT).show();
                        //提交答案先检查是否全部答完
                        if (isall()) {  //如果做完
                            //跳转到练习报告页面
                            Intent intent = new Intent(NumberExercise.this, NumberReport.class);
                            startActivity(intent);
                        } else {  //如果未做完
                            //弹出对话框
                            AlertDialog.Builder builder = new AlertDialog.Builder(NumberExercise.this);
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
                                Intent intent = new Intent(NumberExercise.this, NumberReport.class);
                                startActivity(intent);

                                }
                            });

                            //对话框依附activity，弹出之前判断activity是否在活动状态
                            if (!isFinishing()) {
                                builder.show();
                            }
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
        int height = Utils.getScreenHeight(NumberExercise.this);
        //获取屏幕密度
        float density = Utils.getScreenDensity(NumberExercise.this);
        //计算屏幕高度（dp）
        int screenHeight = (int) (height / density);
        //设置弹出窗高度
        popupLayout.setHeight(screenHeight, true);
        //从底部弹出
        popupLayout.show(PopupLayout.POSITION_BOTTOM);
    }


}
