package com.chenke.flashcards.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenke.flashcards.R;
import com.chenke.flashcards.util.RandomSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ColorPracFragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener  {
    protected View mView;  //视图对象
    protected Context mContext;  //上下文对象
    private int position;  //位置
    private String question;  //问题
    private String answer;  //答案
    private String errList[];  //错误选项

    private String ttString;  //播放答案还是题干

    private String mode;  //模式名称

    private ImageButton horn;  //喇叭按钮

    private TextToSpeech textToSpeech;  //tts对象

    private TextView order;  //题号

    public static int[] selectArray = new int[10];  //选择数组

    public String finalarr[];


    //选项
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;


    //用户选项
    private ImageView t_A;
    private ImageView t_B;
    private ImageView t_C;
    private ImageView t_D;

    //颜色数组
    private String colorName[] = {
            "红色",
            "橙色",
            "黄色",
            "绿色",
            "蓝色",
            "青色",
            "紫色",
    };

    //颜色值数组
    private int colorValue[] = {
            Color.RED,
            Color.rgb(255,97,0),
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.CYAN,
            Color.rgb(128,0,128)
    };


    public static ColorPracFragment newInstance(String mode,int position,String question, String answer, String errList[]) {
        ColorPracFragment fragment = new ColorPracFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mode",mode);
        bundle.putInt("position", position);
        bundle.putString("question",question);
        bundle.putString("answer",answer);
        bundle.putStringArray("errList",errList);
        fragment.setArguments(bundle);  //包裹塞给碎片
        return fragment;
    }

    /**
     * 颜色下标转换
     */
    public int convertColor(String color) {
        int j = 0;
        for (int i=0; i < 7; i++) {
            if (color.equals(colorName[i])) {
                j = i;
                break;
            }
        }
        return j;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();  //获取活动页面的上下文
        if (getArguments() != null) {
            mode = getArguments().getString("mode");
            position = getArguments().getInt("position");
            question = getArguments().getString("question");
            answer = getArguments().getString("answer");
            errList = getArguments().getStringArray("errList");
        }

        //根据布局文件生成视图对象
        mView = inflater.inflate(R.layout.fragment_colorprac,container,false);

        //new一个tts对象
        textToSpeech = new TextToSpeech(mContext,this);
        //获取模式
        TextView text_mode = mView.findViewById(R.id.text_mode);
        text_mode.setText(mode);

        //如果是简单模式（数字，形状，图形），播放答案
        if (mode.contains("简单")) {
            ttString = answer;
        } else { //否则播放问题
            ttString = question;
        }

        //上面的先保留
        //ttString = colorName[Integer.parseInt(answer)];


        //获取选项
        t_A = mView.findViewById(R.id.text_A);
        t_B = mView.findViewById(R.id.text_B);
        t_C = mView.findViewById(R.id.text_C);
        t_D = mView.findViewById(R.id.text_D);

        //合并数组
        List list = new ArrayList(Arrays.asList(errList));
        list.addAll(Arrays.asList(answer));
        String[] arr = new String[list.size()];
        list.toArray(arr);

        //随机打乱数组顺序，构成选项
        finalarr = RandomSort.changePosition(arr);


        t_A.setBackgroundColor(colorValue[convertColor(finalarr[0])]);
        t_B.setBackgroundColor(colorValue[convertColor(finalarr[1])]);
        t_C.setBackgroundColor(colorValue[convertColor(finalarr[2])]);
        t_D.setBackgroundColor(colorValue[convertColor(finalarr[3])]);

//        t_A.setText(finalarr[0]);
//        t_B.setText(finalarr[1]);
//        t_C.setText(finalarr[2]);
//        t_D.setText(finalarr[3]);


        //获取喇叭
        horn = mView.findViewById(R.id.horn);
        //注册监听
        horn.setOnClickListener(this);

        //获取4个选项图标
        btnA = mView.findViewById(R.id.btn_A);
        btnB = mView.findViewById(R.id.btn_B);
        btnC = mView.findViewById(R.id.btn_C);
        btnD = mView.findViewById(R.id.btn_D);

        //注册监听
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


        //获取题号
        order = mView.findViewById(R.id.text_order);
        //设置题号
        int temp = position;
        order.setText(temp + 1 + "");

        //将控件值设为指定值
        return mView;
    }

    //点击更改选项颜色
    public void changeOption(View view) {
        //取消选中所有
        btnA.setSelected(false);
        btnB.setSelected(false);
        btnC.setSelected(false);
        btnD.setSelected(false);
        //选择指定标签
        view.setSelected(true);

        //设置当前选中为蓝色，其它为黑色
        if (view == btnA) {
            //Toast.makeText(getActivity(),"用户的答案" + t_A.getText(),Toast.LENGTH_SHORT).show();
            btnA.setBackground(getResources().getDrawable(R.drawable.btn_circle4));
            btnA.setTextColor(getResources().getColor(R.color.white));
            btnB.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnB.setTextColor(getResources().getColor(R.color.blue));
            btnC.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnC.setTextColor(getResources().getColor(R.color.blue));
            btnD.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnD.setTextColor(getResources().getColor(R.color.blue));

        } else if (view == btnB) {
           // Toast.makeText(getActivity(),"用户的答案" + t_B.getText(),Toast.LENGTH_SHORT).show();
            btnB.setBackground(getResources().getDrawable(R.drawable.btn_circle4));
            btnB.setTextColor(getResources().getColor(R.color.white));
            btnC.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnC.setTextColor(getResources().getColor(R.color.blue));
            btnD.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnD.setTextColor(getResources().getColor(R.color.blue));
            btnA.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnA.setTextColor(getResources().getColor(R.color.blue));

        } else if (view == btnC) {
            //Toast.makeText(getActivity(),"用户的答案" + t_C.getText(),Toast.LENGTH_SHORT).show();
            btnC.setBackground(getResources().getDrawable(R.drawable.btn_circle4));
            btnC.setTextColor(getResources().getColor(R.color.white));
            btnD.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnD.setTextColor(getResources().getColor(R.color.blue));
            btnA.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnA.setTextColor(getResources().getColor(R.color.blue));
            btnB.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnB.setTextColor(getResources().getColor(R.color.blue));
        } else if (view == btnD) {
            //Toast.makeText(getActivity(),"用户的答案" + t_D.getText(),Toast.LENGTH_SHORT).show();
            btnD.setBackground(getResources().getDrawable(R.drawable.btn_circle4));
            btnD.setTextColor(getResources().getColor(R.color.white));
            btnA.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnA.setTextColor(getResources().getColor(R.color.blue));
            btnB.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnB.setTextColor(getResources().getColor(R.color.blue));
            btnC.setBackground(getResources().getDrawable(R.drawable.btn_circle3));
            btnC.setTextColor(getResources().getColor(R.color.blue));
        }

    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this.mContext, "数据丢失或不支持", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.horn) {
            play(ttString);
        } else if (id == R.id.btn_A) {
            changeOption(v);
            selectArray[position] = convertColor(finalarr[0]);  //代表已选择,将答案塞进去
        } else if (id == R.id.btn_B) {
            changeOption(v);
            selectArray[position] = convertColor(finalarr[1]);  //代表已选择,将答案塞进去
        } else if (id == R.id.btn_C) {
            changeOption(v);
            selectArray[position] = convertColor(finalarr[2]);
        } else if (id == R.id.btn_D) {
            changeOption(v);
            selectArray[position] = convertColor(finalarr[3]);
        }
    }

    //播放语音
    private void play(String str) {
        if (textToSpeech != null) {
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.setPitch(1.0f);
            //设定语速 ，默认1.0正常语速
            textToSpeech.setSpeechRate(1.0f);
            //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
            textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
        }

    }
}
