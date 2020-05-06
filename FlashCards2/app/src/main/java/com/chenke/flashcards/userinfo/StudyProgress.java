package com.chenke.flashcards.userinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenke.flashcards.R;
import com.chenke.flashcards.bean.ChartLabel;
import com.chenke.flashcards.util.ProgressCircleChart;
import com.chenke.flashcards.util.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class StudyProgress extends AppCompatActivity {

    private TextView tvResult;  //说明文字
    private ProgressBar prbRate;  //进度条

    private int number;  //进度
    private int pattern;  //形状
    private int color;  //颜色

    ProgressCircleChart pc;
    ProgressCircleChart pc1;
    ProgressCircleChart pc2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studyprog);

        tvResult = findViewById(R.id.tv_result);
        prbRate = findViewById(R.id.prb_rate);
        prbRate.setMax(100);

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
        pc = findViewById(R.id.progress_number);
        pc1 = findViewById(R.id.progress_shape);
        pc2 = findViewById(R.id.progress_color);

        //获取用户电话号码
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String phone = sp.getString("phone","15227117202");

        //调用后台接口，获取具体比例
        GetNumberTask task1 = new GetNumberTask();
        task1.execute("http://192.168.124.24:8080//user/getProgress?phone=" + phone + "&type=number&module=1");



        GetPatternTask task2 = new GetPatternTask();
        task2.execute("http://192.168.124.24:8080//user/getProgress?phone=" + phone + "&type=pattern&module=1");



        GetColorTask task3 = new GetColorTask();
        task3.execute("http://192.168.124.24:8080//user/getProgress?phone=" + phone + "&type=color&module=1");



        //初始化进度环
//        init(pc, 0.45f,"45%", "数字学习");
//        init(pc1, 0.67f,"67%", "形状学习");
//        init(pc2,0.78f,"78%","颜色学习");

    }



     //获取进度异步任务
    public class GetNumberTask extends AsyncTask<String, Integer, String> {

        private String result;

        @Override
        protected void onPreExecute() {
            tvResult.setText("开始获取进度！");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("请求参数",strings[0]);
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(strings[0])
                    .get()   //默认get请求
                    .build();
            Call call = okHttpClient.newCall(request);
            int k = 0;
            for (; k <= 100; k+=17) {
                publishProgress(k);
                try {
                    Thread.sleep(200);
                    Response response = call.execute();
                    result = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            prbRate.setProgress(values[0]);
            tvResult.setText("已完成 : " + values[0] + "%");
        }


        @Override
        protected void onPostExecute(String s) {
            //参数s是doInbackground的返回值
            Log.e("返回值",s);
            //result = s;
            tvResult.setText("获取进度完成！");
            init(pc,Integer.parseInt(s)/100f,Integer.parseInt(s) + "%","数字学习");
        }



    }

    //获取进度异步任务
    public class GetPatternTask extends AsyncTask<String, Integer, String> {

        private String result;

        @Override
        protected void onPreExecute() {
            tvResult.setText("开始获取进度！");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("请求参数",strings[0]);
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(strings[0])
                    .get()   //默认get请求
                    .build();
            Call call = okHttpClient.newCall(request);
            int k = 0;
            for (; k <= 100; k+=17) {
                publishProgress(k);
                try {
                    Thread.sleep(200);
                    Response response = call.execute();
                    result = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            prbRate.setProgress(values[0]);
            tvResult.setText("已完成 : " + values[0] + "%");
        }


        @Override
        protected void onPostExecute(String s) {
            //参数s是doInbackground的返回值
            Log.e("返回值",s);
            //result = s;
            tvResult.setText("获取进度完成！");

            float per = Float.parseFloat(String.format("%.2f",Integer.parseInt(s)/9f));

            DecimalFormat df1 = new DecimalFormat("0%");
            String rate = df1.format(Integer.parseInt(s)/9f);

            init(pc1, per,rate, "形状学习");
        }



    }



    //获取进度异步任务
    public class GetColorTask extends AsyncTask<String, Integer, String> {

        private String result;

        @Override
        protected void onPreExecute() {
            tvResult.setText("开始获取进度！");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("请求参数",strings[0]);
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(strings[0])
                    .get()   //默认get请求
                    .build();
            Call call = okHttpClient.newCall(request);
            int k = 0;
            for (; k <= 100; k+=17) {
                publishProgress(k);
                try {
                    Thread.sleep(200);
                    Response response = call.execute();
                    result = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            prbRate.setProgress(values[0]);
            tvResult.setText("已完成 : " + values[0] + "%");
        }


        @Override
        protected void onPostExecute(String s) {
            //参数s是doInbackground的返回值
            Log.e("返回值",s);
            //result = s;
            tvResult.setText("获取进度完成！");

            float per = Float.parseFloat(String.format("%.2f",Integer.parseInt(s)/7f));

            DecimalFormat df1 = new DecimalFormat("0%");
            String rate = df1.format(Integer.parseInt(s)/7f);

            init(pc2,per,rate,"颜色学习");

            //获取所有进度之后因隐藏进度条
            prbRate.setVisibility(prbRate.GONE);
            tvResult.setVisibility(tvResult.GONE);
        }



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
