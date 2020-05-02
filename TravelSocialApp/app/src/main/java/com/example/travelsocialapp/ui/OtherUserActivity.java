package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.ui.Adapter.ViewPageAdapter;
import com.example.travelsocialapp.ui.Fragment.UserTravelDiaryShowFragment;
import com.example.travelsocialapp.ui.Fragment.UserTravelGuideMapShowFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT;
import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class OtherUserActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        viewPager = (ViewPager) findViewById(R.id.ViewPager);
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        findViewById(R.id.other_user_chart).setOnClickListener(this);

        initViewPager();
    }

    private void initViewPager() {
        //创建List集合
        fragments = new ArrayList<>();
        //添加到fragments集合里
        fragments.add(new UserTravelDiaryShowFragment());
        fragments.add(new UserTravelGuideMapShowFragment());
        //创建适配器
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //把fragment添加到adapter
        adapter.setFragments(fragments);
        //吧adapter添加到viewPager
        viewPager.setAdapter(adapter);

        //tabLayouut有几个创建几个
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        //是tabLayout和viewPager  关联同步一下
        tabLayout.setupWithViewPager(viewPager);
        //设置创建的名字
        tabLayout.getTabAt(0).setText("旅游日志");
        tabLayout.getTabAt(1).setText("路线图");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.other_user_chart){
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);

        }

    }
}
