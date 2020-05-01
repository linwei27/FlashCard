package com.chenke.flashcards.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VeriftyCode extends AppCompatActivity {

    private OkHttpClient okHttpClient;

    public static String result = "waiting for result";  //返回结果


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //发送验证码
    public void sendVeriftyCode(final String phone) {
        //实例化Okhttpclient对象
        okHttpClient = new OkHttpClient();
        Log.e("phone",phone);
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("http://192.168.124.24:8080//user/getTestCode?phoneNumber=" + phone)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("msg","请求失败");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.e("msg",response.body().string());
                    }
                });
            }
        }.start();
        //Log.e("msg","发送成功");

    }

    //验证信息
    public String checkCode(final String code, final String phone) {
        //实例化Okhttpclient对象
        okHttpClient = new OkHttpClient();
        Log.e("发送端code",code);

        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("http://192.168.124.24:8080/user/judgeTestCode?verifyCode=" + code + "&" + "phoneNumber=" + phone)
                        .build();
                Call call = okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("msg","请求失败");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        result = response.body().string();
                    }
                });


            }
        }.start();
        //Log.e("msg","发送成功");
        return result;
    }


}
