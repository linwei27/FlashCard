package com.chenke.flashcards.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.chenke.flashcards.MainActivity;
import com.chenke.flashcards.R;
import com.chenke.flashcards.TabThirdActivity;

public class NumberReport extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pracreport);

        img_back = findViewById(R.id.img_back);

        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                Intent intent = new Intent(NumberReport.this, MainActivity.class);
                intent.putExtra("msg","finish");
                startActivity(intent);
                break;
        }

    }

}
