package com.chenke.flashcards.bean;

public class Subject {
    private String id;  //序号
    private NumberInfo value;  //题目信息


    public Subject(String id, NumberInfo value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NumberInfo getValue() {
        return value;
    }

    public void setValue(NumberInfo value) {
        this.value = value;
    }
}
