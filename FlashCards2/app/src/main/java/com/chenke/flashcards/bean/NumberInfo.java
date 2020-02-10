package com.chenke.flashcards.bean;

import java.util.ArrayList;

public class NumberInfo {
    public String question;  //题目
    public String answer;  //答案内容
    public int errorList [];  //错误选项

    public NumberInfo() {

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int[] getErrorList() {
        return errorList;
    }

    public void setErrorList(int[] errorList) {
        this.errorList = errorList;
    }

    public NumberInfo(String question, String answer, int[] errorList) {
        this.question = question;
        this.answer = answer;
        this.errorList = errorList;
    }

    //获取题目，请求服务端
    public static ArrayList<NumberInfo> getNumberList() {
        NumberInfo n1 = new NumberInfo();
        n1.setQuestion("请选出听到的数字");
        n1.setAnswer("1");
        int a[] = {1,2,3,4};
        n1.setErrorList(a);
        NumberInfo n2 = new NumberInfo();
        n2.setQuestion("请选出听到的数字");
        n2.setAnswer("2");
        int b[] = {1,2,3,4};
        n2.setErrorList(a);

        ArrayList<NumberInfo> numberInfoArrayList = new ArrayList<>();
        numberInfoArrayList.add(n1);
        numberInfoArrayList.add(n2);
        return numberInfoArrayList;
    }

}
