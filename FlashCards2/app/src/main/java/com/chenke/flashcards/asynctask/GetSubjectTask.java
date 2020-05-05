package com.chenke.flashcards.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.chenke.flashcards.bean.NumberInfo;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetSubjectTask extends AsyncTask<String, Integer, NumberInfo> {

    private NumberInfo numberInfo;
    private Context mContext;

    public GetSubjectTask() {
    }


//    public GetSubjectTask(NumberInfo mContext) {
//        this.mContext = mContext;
//    }

    @Override
    protected void onPreExecute() {
        //Toast.makeText(mContext,"开始下载",Toast.LENGTH_LONG);
    }

    @Override
    protected NumberInfo doInBackground(String... strings) {
        Log.e("请求参数",strings[0]);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(strings[0])
                .get()   //默认get请求
                .build();
        Call call = okHttpClient.newCall(request);


        try {
            Response response = call.execute();
            String responseData = response.body().string();
            Log.e("responseData",responseData);
            Gson gson = new Gson();
            numberInfo = gson.fromJson(responseData, NumberInfo.class);
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
//                String responseData = response.body().string();
//                Log.e("responseData",responseData);
//                Gson gson = new Gson();
//                numberInfo = gson.fromJson(responseData, NumberInfo.class);
//            }
//        });

        return numberInfo;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(NumberInfo numberInfo) {
        super.onPostExecute(numberInfo);
    }


}
