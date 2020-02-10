package com.chenke.flashcards;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chenke.flashcards.constant.ImageList;
import com.chenke.flashcards.exercise.NumberExercise;
import com.chenke.flashcards.util.Utils;
import com.chenke.flashcards.widget.BannerPager;

public class TabThirdActivity extends AppCompatActivity implements BannerPager.BannerClickListener, View.OnClickListener {
    private RelativeLayout prac_number;  //数字练习
    private RelativeLayout prac_shape;  //形状练习
    private RelativeLayout prac_color;  //颜色练习

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabthird);

        // 从布局文件中获取名叫banner_pager的横幅轮播条
        BannerPager banner = findViewById(R.id.banner_pager);
        // 获取横幅轮播条的布局参数
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(this) * 250f / 640f);
        // 设置横幅轮播条的布局参数
        banner.setLayoutParams(params);
        // 设置横幅轮播条的广告图片队列
        banner.setImage(ImageList.getDefault());
        // 设置横幅轮播条的广告点击监听器
        banner.setOnBannerListener(this);
        banner.start();


        //获取控件
        prac_number = findViewById(R.id.prac_number);
        prac_shape = findViewById(R.id.prac_shape);
        prac_color = findViewById(R.id.prac_color);


        //注册监听器
        prac_number.setOnClickListener(this);
        prac_shape.setOnClickListener(this);
        prac_color.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        //注册上下文菜单
        registerForContextMenu(prac_number);
        registerForContextMenu(prac_shape);
        registerForContextMenu(prac_color);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //取消注册上下文菜单
        unregisterForContextMenu(prac_number);
        unregisterForContextMenu(prac_shape);
        unregisterForContextMenu(prac_color);
        super.onPause();
    }

    @Override
    public void onBannerClick(int position) {
        Toast.makeText(TabThirdActivity.this,"点击了图片"  ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prac_number:
                onClick_number(v);
                break;
            case R.id.prac_shape:
                onClick_shape(v);
                break;
            case R.id.prac_color:
                onClick_color(v);
                break;
        }
    }

    //颜色练习
    private void onClick_color(View v) {
        //Toast.makeText(TabThirdActivity.this,"颜色练习",Toast.LENGTH_SHORT).show();
        openContextMenu(v);
    }

    //形状练习
    private void onClick_shape(View v) {
        //Toast.makeText(TabThirdActivity.this,"形状练习",Toast.LENGTH_SHORT).show();
        openContextMenu(v);
    }

    //数字练习
    public void onClick_number(View v) {
        //Toast.makeText(TabThirdActivity.this,"数字练习",Toast.LENGTH_SHORT).show();
        //打开选项菜单
        openContextMenu(v);
    }

    //在上下文菜单的菜单界面创建时调用
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_prac,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_easy) {  //简单模式
            Intent intent = new Intent(this, NumberExercise.class);
            intent.putExtra("mode","easy");
            startActivity(intent);
            Toast.makeText(TabThirdActivity.this,"简单",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_medium) {  //中等模式
            Intent intent = new Intent(this, NumberExercise.class);
            intent.putExtra("mode","medium");
            startActivity(intent);
            Toast.makeText(TabThirdActivity.this,"中等",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_hard) {  //困难模式
            Intent intent = new Intent(this, NumberExercise.class);
            intent.putExtra("mode","hard");
            startActivity(intent);
            Toast.makeText(TabThirdActivity.this,"困难",Toast.LENGTH_SHORT).show();
        }
        return true;
    }


}
