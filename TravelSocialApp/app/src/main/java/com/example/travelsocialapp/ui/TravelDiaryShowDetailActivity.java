package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;

public class TravelDiaryShowDetailActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary_show_detail);
        findViewById(R.id.travel_diary_show_detail_user).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.travel_diary_show_detail_user){
            Intent intent = new Intent(this, OtherUserActivity.class);
            startActivity(intent);
        }
    }
}
