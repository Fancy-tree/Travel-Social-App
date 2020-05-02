package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;

public class TravelDiaryActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary);
        findViewById(R.id.goto_write_diary).setOnClickListener(this);//写日志

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goto_write_diary){//点击写日志将跳转到编辑日志界面
            Intent intent = new Intent(this, TravelDiaryEditActivity.class);
            startActivity(intent);
        }

    }
}
