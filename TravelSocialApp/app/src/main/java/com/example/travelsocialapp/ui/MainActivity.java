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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.ui.Fragment.GuideMapFragment;
import com.example.travelsocialapp.ui.Fragment.HomeFragment;
import com.example.travelsocialapp.ui.Fragment.LocationFragment;
import com.example.travelsocialapp.ui.Fragment.UserFragment;
import com.example.travelsocialapp.util.AppUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener,LocationListener,UserFragment.ToOtherActivity {
    private LinearLayout tab_1L, tab_2L, tab_3L, tab_4L;
    private Fragment homeF= new HomeFragment(), locationF= new LocationFragment(), mapF=new GuideMapFragment(), userF=new UserFragment();
    private FrameLayout frameLayout1,frameLayout2,frameLayout3,frameLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      初始化下方菜单
        tab_1L = (LinearLayout) findViewById(R.id.tab_1);
        tab_2L = (LinearLayout) findViewById(R.id.tab_2);
        tab_3L = (LinearLayout) findViewById(R.id.tab_3);
        tab_4L = (LinearLayout) findViewById(R.id.tab_4);
        tab_1L.setOnClickListener(this);
        tab_2L.setOnClickListener(this);
        tab_3L.setOnClickListener(this);
        tab_4L.setOnClickListener(this);
//        绑定所有碎片容器
        frameLayout1 =(FrameLayout) findViewById(R.id.fragment1);
        frameLayout2 =(FrameLayout) findViewById(R.id.fragment2);
        frameLayout3 =(FrameLayout) findViewById(R.id.fragment3);
        frameLayout4 =(FrameLayout) findViewById(R.id.fragment4);
        //将所有碎片添加至activity，隐藏其他碎片，显示主页碎片
        getSupportFragmentManager().beginTransaction().add(R.id.fragment1, homeF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment2, locationF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment3, mapF).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment4, userF).commitAllowingStateLoss();
        frameLayout1.setVisibility(View.VISIBLE);
        frameLayout2.setVisibility(View.GONE);
        frameLayout3.setVisibility(View.GONE);
        frameLayout4.setVisibility(View.GONE);
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
        frameLayout1.setVisibility(View.GONE);
        frameLayout2.setVisibility(View.GONE);
        frameLayout3.setVisibility(View.GONE);
        frameLayout4.setVisibility(View.GONE);
        switch (v.getId()) {
            case R.id.tab_1:
                frameLayout1.setVisibility(View.VISIBLE);
                HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
                fragment.scrollToTop();//每次显示页面，都要重新将滚动条滚动到顶部
                if(AppUtil.isreleaseDiary==1){
                    fragment.reflushDiary();
                }
                break;
            case R.id.tab_2:
                frameLayout2.setVisibility(View.VISIBLE);
                break;
            case R.id.tab_3:
                frameLayout3.setVisibility(View.VISIBLE);
                break;
            case R.id.tab_4:
                frameLayout4.setVisibility(View.VISIBLE);
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        //登录信息确认
//        Log.i("login","登录信息确认");
        SharedPreferences sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
        int isLogin = sp.getInt("user_isLogin",0);//取出用户登录标记
        if(isLogin==1){
            //刷新用户页面登录信息
            reflashLoginInformation();

        }
//        定位
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
//                showToast("获取定位中");

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
        startActivityForResult(intent,2);//向登录页跳转
    }

    @Override
    public void toTravelDiaryActivity() {
        Intent intent = new Intent(this, TravelDiaryActivity.class);
        startActivity(intent);//向旅游日志页跳转
    }

    @Override
    public void toUserSettingMainActivity() {
        Intent intent = new Intent(this, UserSettingMainActivity.class);
        startActivityForResult(intent,1);//向用户设置页跳转
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){//登录状态
            if(resultCode==1){//登录成功
//                显示服务器返回的消息
                showToast(data.getExtras().getString("LoginMessageFromSever"));
                //    刷新用户页面登录信息
                reflashLoginInformation();

            }else if(resultCode==2){//注册状态（注册成功）
                // 显示服务器返回的消息
                showToast(data.getExtras().getString("SigninMessageFromSever"));
              //    刷新用户页面登录信息
                reflashLoginInformation();
            }else{
                Log.e("onActivityResult","Main Login 未定义状态");
            }
        }else if(requestCode==1){ //退出登录状态
            if(resultCode==1){//退出登录成功
                //显示服务器返回的消息
                showToast(data.getExtras().getString("LogoutMessageFromSever"));
                // 改变页面用户昵称
                UserFragment fragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.fragment4);
                assert fragment != null;
                fragment.setUserName("登录获得更多体验");
            }
        }
    }

//    刷新用户页面登录信息  昵称
    public void reflashLoginInformation()
    {
//      改变用户页面用户昵称
        SharedPreferences sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
        String userName = sp.getString("user_name","");//取出用户登录标记
        UserFragment fragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.fragment4);
        assert fragment != null;
        Log.i("Username",userName);
        fragment.setUserName(userName);
    }

}
