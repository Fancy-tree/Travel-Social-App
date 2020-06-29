package com.example.travelsocialapp.ui.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.ui.TravelDiaryActivity;
import com.example.travelsocialapp.ui.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,null);
        return view;
    }
    private TextView userName;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//findViewById是有上下文的，默认是在Activity的主布局中，当在一些主布局的View中，子布局中比如dialog中，就要用view.findViewById(),才行。要不然报空指针错误

        view.findViewById(R.id.travel_diary).setOnClickListener(this);//旅行日志
        view.findViewById(R.id.user_head).setOnClickListener(this);//头像
        view.findViewById(R.id.user_setting).setOnClickListener(this);//设置
        userName = view.findViewById(R.id.user_name);//用户昵称

//        初始化用户信息

    }

    @Override
    public void onClick(View v) {
        if(getActivity()!=null){ //防止空指针错误
            SharedPreferences sp = getActivity().getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
            int isLogin = sp.getInt("user_isLogin",0);//取出用户登录标记
            if(isLogin==0){ //如果用户没有登录，所有在此绑定的点击事件都将唤出登录页面
                ToOtherActivity toOtherActivity = (ToOtherActivity)getActivity();
                toOtherActivity.toLoginActivity();

            }else{//用户已登录
                if(v.getId()==R.id.travel_diary){
                    ToOtherActivity toOtherActivity = (ToOtherActivity)getActivity();
                    toOtherActivity.toTravelDiaryActivity();
                }
                if(v.getId()==R.id.user_head){//用户头像
                //   测试用
//
//                    SharedPreferences.Editor sp2 = getActivity().getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
//                    sp2.putInt("user_isLogin",0);//存储用户登录状态
//                    setUserName("登录获得更多体验");
//                    sp2.apply();//

                }else if(v.getId()==R.id.user_setting){
                    ToOtherActivity toOtherActivity = (ToOtherActivity)getActivity();
                    toOtherActivity.toUserSettingMainActivity();

                }

            }

        }

    }

//    跳转页面接口给Activity
    public interface ToOtherActivity
    {
        abstract public void toLoginActivity();
        abstract public void toTravelDiaryActivity();
        abstract public void toUserSettingMainActivity();
    }
//    给Activity调用
    public void setUserName(String name){
        userName.setText(name);
    }
}
