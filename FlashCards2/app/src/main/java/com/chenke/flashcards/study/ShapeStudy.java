package com.chenke.flashcards.study;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chenke.flashcards.R;
import com.chenke.flashcards.asynctask.RecordTask;

import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShapeStudy extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {
    private Toolbar tl_head;  //顶部工具栏

    private ImageButton horn;  //喇叭按钮
    private ImageView image_shape;  //图片内容
    private Button previous;  //上一个按钮
    private Button next;  //下一个按钮

    private TextToSpeech textToSpeech;  //tts对象

    private int i = 1;

    private TextView tvResult;
    private ProgressBar prbRate;

    //形状名称数组
    private String shapes[] = {
            "圆形","三角形","长方形","正方形","心形","五角星形",
            "球体","圆柱体","圆锥体"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);

        tvResult = findViewById(R.id.tv_result);
        prbRate = findViewById(R.id.prb_rate);
        prbRate.setMax(100);

        //查看本地是否有用户的进度信息
//        SharedPreferences sp = this.getSharedPreferences("record_shape", Context.MODE_PRIVATE);
//        rate = sp.getInt("rate", -1); //若无数据为-1

        //弹出对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否继续上次的学习？");

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
                //获取进度
                getRecord();


            }
        });
        builder.show();

        textToSpeech = new TextToSpeech(this,this);

        tl_head = findViewById(R.id.tl_head);
        //设置左边导航栏图标
        tl_head.setNavigationIcon(R.drawable.back);
        //设置标题
        tl_head.setTitle("形状学习");
        //设置标题颜色
        tl_head.setTitleTextColor(getResources().getColor(R.color.zfb));
        //替换系统自带的ActionBar
        setSupportActionBar(tl_head);
        //给导航图标设置监听器
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录学习进度
                saveRecord(i);
                finish();
            }
        });

        horn = findViewById(R.id.horn);
        image_shape = findViewById(R.id.image_shape);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);


        //注册监听
        horn.setOnClickListener(this);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);


    }

    //重写，文字转语音，需传入文字
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.horn:  //点击喇叭图标，实现文字转语音
                play();
                break;
            case R.id.previous:
                previous();  //切换图片，上一个
                break;
            case R.id.next:
                next();  //切换图片，下一个
                break;
        }
    }

    //播放语音
    private void play() {
        //获取当前文件名序号
        int j = i;
        j--;
        String str = shapes[j];


        Log.e("朗读：","调用播放");
        if (textToSpeech != null) {
            Log.e("进入：","正在播放");
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.setPitch(1.0f);
            //设定语速 ，默认1.0正常语速
            textToSpeech.setSpeechRate(1.0f);
            //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
            textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    //切换下一张图片
    private void next() {
        //先判断是否是最后一张，如果是给出提示
        if (i == 9) {
            //提示信息
            Toast.makeText(ShapeStudy.this, "这已经是最后一张图片了！",Toast.LENGTH_SHORT).show();
            return;
        }
        i++;
        String imageName = "shape0" + i;


        int resID = getResources().getIdentifier(imageName,"drawable","com.chenke.flashcards");
        Drawable image = getResources().getDrawable(resID);
        image_shape.setImageDrawable(image);

        Log.e("打印：","下一个");
    }

    //切换上一张图片
    private void previous() {
        //先判断是否是第一张，是的话给出提示
        if (i == 1){
            //提示信息
            Toast.makeText(ShapeStudy.this, "这已经是第一张图片了！",Toast.LENGTH_SHORT).show();
            return;
        }

        i--;
        String imageName = "shape0" + i;


        int resID = getResources().getIdentifier(imageName,"drawable","com.chenke.flashcards");
        Drawable image = getResources().getDrawable(resID);
        image_shape.setImageDrawable(image);
        Log.e("打印：","上一个");
    }


    /**
     * 初始化TextToSpeech引擎
     * @param status
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        //记录学习进度
        saveRecord(i);

        textToSpeech.stop();
        textToSpeech.shutdown();  //关闭，释放资源
        textToSpeech = null;
    }

    //保存学习进度
    public void saveRecord(int i) {
//        SharedPreferences sp = this.getSharedPreferences("record_shape", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt("rate",i);
//        editor.commit();//必须提交，否则保存不成功


        //获取用户电话号码
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String phone = sp.getString("phone","15227117202");

        //调用异步任务请求服务端
        RecordTask task = new RecordTask(ShapeStudy.this);
        String url = "http://192.168.124.24:8080//user/saveProgress?phone=" + phone + "&rate=" + i + "&type=pattern&module=1";
        task.execute(url);

    }

    //获取进度异步任务
    public class GetRecordTask extends AsyncTask<String, Integer, String> {

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
            for (; k <= 100; k+=9) {
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
            //取出i值，设置好图片
            if (Integer.parseInt(s) != -1) {
                //有数据，判断i的值
                String imageName = "shape0" + Integer.parseInt(s);
                int resID = getResources().getIdentifier(imageName,"drawable","com.chenke.flashcards");
                Drawable image = getResources().getDrawable(resID);
                //设置相应的图片
                image_shape.setImageDrawable(image);
                //把当前的值传给i
                i = Integer.parseInt(s);
            }


        }

    }

    //获取学习进度
    public void getRecord() {
        //获取用户电话号码
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String phone = sp.getString("phone","15227117202");

        //调用异步任务请求服务端
        GetRecordTask task = new GetRecordTask();
        String url = "http://192.168.124.24:8080//user/getProgress?phone=" + phone + "&type=pattern&module=1";
        task.execute(url);

    }


}
