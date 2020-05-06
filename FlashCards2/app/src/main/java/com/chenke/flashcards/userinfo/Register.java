package com.chenke.flashcards.userinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chenke.flashcards.MainActivity;
import com.chenke.flashcards.R;
import com.chenke.flashcards.util.TimeCount;
import com.chenke.flashcards.util.VeriftyCode;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button btn_verify;  //发送验证码按钮
    private Button btn_register_now;  //注册按钮

    private EditText edit_phone;  //手机号
    private EditText edit_verify;  //验证码

    private Toolbar tl_head;  //顶部工具栏

    private TimeCount time;  //重发验证码工具类

    private String result;  //返回结果

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tl_head = findViewById(R.id.tl_head);
        //设置左边导航栏图标
        tl_head.setNavigationIcon(R.drawable.back);
        //设置标题
        tl_head.setTitle("注册");
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


        btn_verify = findViewById(R.id.btn_verify);
        btn_register_now = findViewById(R.id.btn_register_now);
        edit_phone = findViewById(R.id.edit_phone);
        edit_verify = findViewById(R.id.edit_verify);


        time = new TimeCount(60000, 1000);
        //将按钮传给工具类
        time.btn_verify = btn_verify;

        btn_verify.setOnClickListener(this);
        btn_register_now.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verify:
                sendVerify(v);
                break;
            case R.id.btn_register_now:
                register_now(v);
                break;

        }
    }


    //注册接口
    private void register_now(View v) {
        //获取输入框内手机号
        String phone = edit_phone.getText().toString();
        //获取输入框内验证码
        String verify = edit_verify.getText().toString();
        //如果手机号或者验证码为空。给出提示：不允许登录
        if (phone.isEmpty() || verify.isEmpty()) {
            //提示信息
            Toast.makeText(Register.this, "手机号或验证码不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        //调用异步任务
        VerifyCodeTask task = new VerifyCodeTask();
        String url = "http://192.168.124.24:8080/user/judgeTestCode?verifyCode=" + verify + "&" + "phoneNumber=" + phone;
        task.execute(url);

    }

    //发送验证码接口
    private void sendVerify(View v) {
        String phone = edit_phone.getText().toString();
        if (phone.isEmpty()) {
            //提示信息
            Toast.makeText(Register.this, "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        time.start();

        //调用异步任务
        SendCodeTask task = new SendCodeTask();
        String url = "http://192.168.124.24:8080//user/getTestCode?phoneNumber=" + phone;
        task.execute(url);

    }

    //验证异步任务
    public class VerifyCodeTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.e("请求参数", strings[0]);
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(strings[0])
                    .get()   //默认get请求
                    .build();
            Call call = okHttpClient.newCall(request);

            try {
                Response response = call.execute();
                result = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            //参数s是doInbackground的返回值
            Log.e("返回值", s);
            if (!s.equals("success")) {
                Toast.makeText(Register.this, s, Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(Register.this, s, Toast.LENGTH_SHORT);
                //弹出对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setTitle("提示");
                builder.setMessage("注册成功，是否立即登录？");

                //设置否定按钮
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //返回到注册登录页
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        intent.putExtra("msg", "logout");
                        startActivity(intent);
                    }
                });

                //设置肯定按钮
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //返回到登录页
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    }
                });
                builder.show();

            }
        }


    }


    //发送验证码异步任务
    public class SendCodeTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.e("请求参数", strings[0]);
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(strings[0])
                    .get()   //默认get请求
                    .build();
            Call call = okHttpClient.newCall(request);

            try {
                Response response = call.execute();
                result = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            //参数s是doInbackground的返回值
            Log.e("返回值", s);
            if (!s.equals("success")) {
                Toast.makeText(Register.this, s, Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(Register.this, "发送验证码成功!", Toast.LENGTH_SHORT);

            }
        }


    }


}
