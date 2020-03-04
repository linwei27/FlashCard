package com.chenke.flashcards.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.chenke.flashcards.R;
import com.chenke.flashcards.bean.PracRecord;
import com.chenke.flashcards.util.CircleImageView;

import java.util.List;

public class PracRecordAdapter extends ArrayAdapter {
    private final int resourceId;


    public PracRecordAdapter( Context context, int resource, List<PracRecord> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        PracRecord pracRecord = (PracRecord) getItem(position);  //获取当前练习记录实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);  //实例化一个对象
        CircleImageView imageView = view.findViewById(R.id.image);   //图片
        TextView type = view.findViewById(R.id.text_type);  //类型
        TextView startTime = view.findViewById(R.id.text_startTime);  //开始时间
        TextView duration = view.findViewById(R.id.text_duration);  //时长

        imageView.setImageResource(pracRecord.getImageId());
        type.setText(pracRecord.getType());
        startTime.setText(pracRecord.getStartTime());
        duration.setText(pracRecord.getDuration());

        return view;
    }


    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }




}
