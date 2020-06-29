package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;

public class UserSettingMainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_main);
        findViewById(R.id.user_setting_main_account).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.user_setting_main_account){
            Intent intent = new Intent(this, UserSettingAccountActivity.class);
            startActivityForResult(intent,1);//向用户账户设置页跳转

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){//退出登录状态
            if(resultCode==1){ //退出登录成功
//                显示服务器返回的消息
                Intent intent =  new Intent(UserSettingMainActivity.this,UserSettingMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("LogoutMessageFromSever",data.getExtras().getString("LogoutMessageFromSever"));
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();
            }else {
                Log.e("onActivityResult","UserSettingMainActivity 未退出登录成功");
            }
        }


    }


}
