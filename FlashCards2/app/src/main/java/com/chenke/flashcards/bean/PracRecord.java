package com.chenke.flashcards.bean;

import android.widget.TextClock;

public class PracRecord {

    private String type;  //类型
    private String duration;  //时长
    private String startTime;   //开始时间
    private int imageId;  //配图id

    public PracRecord(String type, String duration, String startTime, int imageId) {
        this.type = type;
        this.duration = duration;
        this.startTime = startTime;
        this.imageId = imageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public PracRecord() {
    }



}
