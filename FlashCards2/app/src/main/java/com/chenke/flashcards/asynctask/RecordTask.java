package com.chenke.flashcards.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecordTask extends AsyncTask<String,Integer,String> {

    private Context mContext;
    private String result = "waiting";


    public RecordTask(Context mContext) {
        this.mContext = mContext;
    }

    public RecordTask() {
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

        try {
            Response response = call.execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Log.e("msg","请求失败");
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
////                    Log.e("msg",response.body().string());
//                result = response.body().string();
//            }
//        });

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        //参数s是doInbackground的返回值
        Log.e("返回值",s);

        if (s.equals("success")) {
            Toast.makeText(mContext,"保存进度成功！",Toast.LENGTH_LONG).show();
        } else if (s.equals("failed")){
            Toast.makeText(mContext,"保存进度失败！",Toast.LENGTH_LONG).show();
        }

    }
}
