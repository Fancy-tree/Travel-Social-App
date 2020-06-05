package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.ui.Adapter.SearchCityLinearAdapter;

public class SearchCityActivity extends BaseActivity {
    private RecyclerView recycleViewR;
    private SearchCityLinearAdapter searchCityLinearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        recycleViewR = (RecyclerView)findViewById(R.id.search_city_recyclerview);
        recycleViewR.setLayoutManager(new LinearLayoutManager(SearchCityActivity.this));
        searchCityLinearAdapter = new SearchCityLinearAdapter(SearchCityActivity.this, new OnItemClickListener());
        recycleViewR.setAdapter(searchCityLinearAdapter);

    }




    // 用于searchCityLinearAdapter接口回调监听
    private class OnItemClickListener implements SearchCityLinearAdapter.OnItemClickListener {
        @Override
        public void onClick(String city) {
//            showToast(pos);
            SharedPreferences.Editor puteditorsp = getSharedPreferences("user_setting",MODE_PRIVATE).edit();
            puteditorsp.putString("city",city);//存储最近一次选择的城市
            puteditorsp.apply();//立即提交
            finish();
        }
    }
}
