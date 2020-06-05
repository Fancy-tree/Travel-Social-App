package com.example.travelsocialapp.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.ui.Fragment.GuideMapFragment;
import com.example.travelsocialapp.ui.Fragment.HomeFragment;
import com.example.travelsocialapp.ui.Fragment.LocationFragment;
import com.example.travelsocialapp.ui.Fragment.UserFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener,LocationListener,UserFragment.ToOtherActivity{
    private LinearLayout tab_1L, tab_2L, tab_3L, tab_4L;
    private Fragment homeF= new HomeFragment(), locationF= new LocationFragment(), mapF=new GuideMapFragment(), userF=new UserFragment();

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
        //将所有碎片添加至activity，隐藏其他碎片，显示主页碎片
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, locationF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, mapF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, userF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().hide(mapF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, homeF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().show(homeF).commitAllowingStateLoss();
        tab_1L.setSelected(true);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tab_1 || v.getId() == R.id.tab_2 || v.getId() == R.id.tab_3 || v.getId() == R.id.tab_4) {
            changeContainerView(v);
        }
    }

    //点击按钮切换碎片
    private void changeContainerView(View v) {
        tab_1L.setSelected(false);
        tab_2L.setSelected(false);
        tab_3L.setSelected(false);
        tab_4L.setSelected(false);
        v.setSelected(true);
        switch (v.getId()) {
            case R.id.tab_1:
                if (homeF == null) {
                    homeF = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment, homeF).commitAllowingStateLoss();
                }
                if (locationF != null)
                    getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
                if (mapF != null)
                    getSupportFragmentManager().beginTransaction().hide(mapF).commitAllowingStateLoss();
                if (userF != null)
                    getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
                if (homeF != null)
                    getSupportFragmentManager().beginTransaction().hide(homeF).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(homeF).commitAllowingStateLoss();
                HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                fragment.scrollToTop();//每次显示页面，都要重新将滚动条滚动到顶部

                break;
            case R.id.tab_2:
                if (locationF == null) {
                    locationF = new LocationFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment, locationF).commitAllowingStateLoss();
                }
                if (locationF != null)
                    getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
                if (mapF != null)
                    getSupportFragmentManager().beginTransaction().hide(mapF).commitAllowingStateLoss();
                if (userF != null)
                    getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
                if (homeF != null)
                    getSupportFragmentManager().beginTransaction().hide(homeF).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(locationF).commitAllowingStateLoss();

                break;
            case R.id.tab_3:
                if (mapF == null) {
                    mapF = new GuideMapFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment, mapF).commitAllowingStateLoss();
                }
                if (locationF != null)
                    getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
                if (userF != null)
                    getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
                if (homeF != null)
                    getSupportFragmentManager().beginTransaction().hide(homeF).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(mapF).commitAllowingStateLoss();

                break;
            case R.id.tab_4:
                if (userF == null) {
                    userF = new UserFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment, userF).commitAllowingStateLoss();
                }
                if (locationF != null)
                    getSupportFragmentManager().beginTransaction().hide(locationF).commitAllowingStateLoss();
                if (mapF != null)
                    getSupportFragmentManager().beginTransaction().hide(mapF).commitAllowingStateLoss();
                if (userF != null)
                    getSupportFragmentManager().beginTransaction().hide(userF).commitAllowingStateLoss();
                if (homeF != null)
                    getSupportFragmentManager().beginTransaction().hide(homeF).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(userF).commitAllowingStateLoss();
                break;

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        initWidget();
        startGPSLocation();

    }

    @Override
    protected void onPause() {
        super.onPause();
        locmgr.removeUpdates(this);
        locmgr = null;
        //LocationListener也要置null，但是我不会

    }


//    进行定位
//    实际上GPS是很难（很慢，信号不好）取得定位信息的，这里通常使用网络定位
    private TextView tab_2_locationT;
    private LocationManager locmgr;//定位管理对象
    private Criteria mCriteria = new Criteria();//定位选项

    private void initWidget() {
        tab_2_locationT = (TextView)findViewById(R.id.tab_2_location);
        locmgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 设置定位精确度 Criteria.ACCURACY_COARSE 比较粗略， Criteria.ACCURACY_FINE 则比较精细
        mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否需要海拔信息 Altitude
        mCriteria.setAltitudeRequired(true);
        // 设置是否需要方位信息 Bearing
        mCriteria.setBearingRequired(true);
        // 设置是否允许运营商收费
        mCriteria.setCostAllowed(true);
        // 设置对电源的需求
        mCriteria.setPowerRequirement(Criteria.POWER_LOW);
    }


    @SuppressLint({"MissingPermission", "SetTextI18n"}) //屏蔽没有权限报错,??
    public void startGPSLocation() {
        //获取系统定位管理器
        locmgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取当前可用的 最佳定位提供者（规格条件，是返回已启用的提供者）
        assert locmgr != null;
        String provider = locmgr.getBestProvider(mCriteria, true);

        if(provider==null){
            showToast("已关闭定位");
        }else{
//            showToast(provider);
            //定位提供者每次获得新的数据就通知程序（定位提供者，间隔x毫秒通知，超过x米通知，监听对象）
            locmgr.requestLocationUpdates(provider, 300, 1, this);

            Location location = locmgr.getLastKnownLocation(provider);
            if(location!=null){  //检查Location对象是否为null
                try {
                    List<Address> address = translationLocation(location);
                    //                检查 地址是否为空
                    if(!address.isEmpty()){
                        String tmp = "";
                        for(int i=0;i<address.get(0).getMaxAddressLineIndex();i++){
                            tmp = tmp +" "+ address.get(0).getAddressLine(i);
                        }

                        Log.i("Location000：",tmp);
                        tmp = getCity(tmp);
                        tab_2_locationT.setText(tmp);
//                        showToast("定位启动");
                    }else { //地址为空
                        showToast("解析地址失败");
                    }

                } catch (IOException e) {
                    showToast("定位失败");
                    e.printStackTrace();
                    return;
                }
            }else {
                showToast("获取定位中");

            }

        }

    }

    //检测定位字符串中
    public String getCity(String location){

        String city = "暂无城市";
        if(location.contains("重庆")){
            city = "重庆";
        }

        return  city;
    }


//    地址解析(Location对象) 返回中文地址
    public   List<Address> translationLocation(Location location) throws IOException {
        double height,weidu,jingdu,speed;
        height = location.getAltitude();//获取高度（米）
        weidu = location.getLatitude();//获取纬度
        jingdu = location.getLongitude();//获取经度
        speed = location.getSpeed();//获取速度（米/秒）

        Log.i("location",String.valueOf(weidu));
        Log.i("location",String.valueOf(jingdu));

        //(上下文，语言（取得系统默认语言）)
        Geocoder geo = new Geocoder(this,Locale.getDefault());
        List<Address> address = null;

            //(经度，纬度，返回结果数)如果无法定位会抛出错误
            address =  geo.getFromLocation(weidu,jingdu,1);

            if(address.isEmpty()){
//                showToast("no address");
                Log.i("location", "no address ");
            }else {
//                showToast("has address");
                Log.i("location", "has address ");
            }
           

//        showToast("定位中");
        return address;
    }

    //定位监听
    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(Location location) {
//        showToast("定位改变");

        if(location!=null) {  //检查Location对象是否为null
            List<Address> address = null;
            try {
                address = translationLocation(location);
            } catch (IOException e) {
                showToast("定位失败");
                e.printStackTrace();
                return;
            }
//                检查 地址是否为空
            if (!address.isEmpty()) {

                String tmp = "";
//                    for(int i=0;i<address.get(0).getMaxAddressLineIndex();i++){
//                        tmp = tmp +" "+ address.get(0).getAddressLine(i);
//                    }
                tmp = getCity(address.get(0).getAddressLine(0));
                tab_2_locationT.setText(tmp);
//                showToast("定位启动");
            } else {
                showToast("获取定位失败");
            }
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

//    UserFragment 接口，跳转登录界面
    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,0);//向登录页跳转
    }

    @Override
    public void toTravelDiaryActivity() {
        Intent intent = new Intent(this, TravelDiaryActivity.class);
        startActivity(intent);//向旅游日志页跳转
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){//登录状态
            if(resultCode==0){
//                显示服务器返回的消息
                showToast(data.getExtras().getString("LoginMessageFromSever"));
            }else if(resultCode==1){//注册状态
                // 显示服务器返回的消息
                showToast(data.getExtras().getString("SigninMessageFromSever"));
            }else{
                Log.e("onActivityResult","Main Login 未定义状态");
            }
        }




    }

}
