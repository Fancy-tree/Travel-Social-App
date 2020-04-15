package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.ui.Fragment.GuideMapFragment;
import com.example.travelsocialapp.ui.Fragment.HomeFragment;
import com.example.travelsocialapp.ui.Fragment.LocationFragment;
import com.example.travelsocialapp.ui.Fragment.UserFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout tab_1L, tab_2L, tab_3L, tab_4L;
    private Fragment homeF,locationF,mapF,userF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab_1L = (LinearLayout) findViewById(R.id.tab_1);
        tab_2L = (LinearLayout) findViewById(R.id.tab_2);
        tab_3L = (LinearLayout) findViewById(R.id.tab_3);
        tab_4L = (LinearLayout) findViewById(R.id.tab_4);
        tab_1L.setOnClickListener(this);
        tab_2L.setOnClickListener(this);
        tab_3L.setOnClickListener(this);
        tab_4L.setOnClickListener(this);
        homeF=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,homeF).commitAllowingStateLoss();
        tab_1L.setSelected(true);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tab_1 || v.getId()==R.id.tab_2 || v.getId()==R.id.tab_3  || v.getId()==R.id.tab_4) {
            changeContainerView(v);
        }
    }

    private void changeContainerView(View v) {
        tab_1L.setSelected(false);
        tab_2L.setSelected(false);
        tab_3L.setSelected(false);
        tab_4L.setSelected(false);
        v.setSelected(true);
        switch (v.getId()){
            case R.id.tab_1:
                if(homeF==null) {
                    homeF = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment, homeF).commitAllowingStateLoss();
                }
                if(locationF!=null)getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
                if(mapF!=null)getSupportFragmentManager().beginTransaction().hide(mapF).commitAllowingStateLoss();
                if(userF!=null)getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
                if(homeF!=null)getSupportFragmentManager().beginTransaction().hide(homeF).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(homeF).commitAllowingStateLoss();


                break;
            case R.id.tab_2:
                if(locationF==null){
                    locationF=new LocationFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment,locationF).commitAllowingStateLoss();
                }
                if(locationF!=null)getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
                if(mapF!=null)getSupportFragmentManager().beginTransaction().hide(mapF).commitAllowingStateLoss();
                if(userF!=null)getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
                if(homeF!=null)getSupportFragmentManager().beginTransaction().hide(homeF).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(locationF).commitAllowingStateLoss();

                break;
            case R.id.tab_3:
                if(mapF==null){
                    mapF=new GuideMapFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment,mapF).commitAllowingStateLoss();
                }
                if(locationF!=null)getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
                if(userF!=null)getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
                if(homeF!=null)getSupportFragmentManager().beginTransaction().hide(homeF).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(mapF).commitAllowingStateLoss();

                break;
            case R.id.tab_4:
                if(userF==null){
                    userF=new UserFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment,userF).commitAllowingStateLoss();
                }
                if(locationF!=null)getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
                if(mapF!=null)getSupportFragmentManager().beginTransaction().hide(mapF).commitAllowingStateLoss();
                if(userF!=null)getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
                if(homeF!=null)getSupportFragmentManager().beginTransaction().hide(homeF).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(userF).commitAllowingStateLoss();
                break;

        }



    }
}
