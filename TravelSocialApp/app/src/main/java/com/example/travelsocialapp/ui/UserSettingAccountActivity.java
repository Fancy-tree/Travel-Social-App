package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.base.BaseInternetMessage;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.User;
import com.example.travelsocialapp.util.IntentUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UserSettingAccountActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_account);
        findViewById(R.id.user_setting_account_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.user_setting_account_logout){
            UserSettingAccountActivity.LogoutTest task = new UserSettingAccountActivity.LogoutTest();
            try {
                task.execute(C.intentLogoutUrl).get(4000, TimeUnit.MILLISECONDS);
            } catch (ExecutionException e) {
                showToast("链接ExecutionException");
                e.printStackTrace();
            } catch (InterruptedException e) {
                showToast("链接InterruptedException");
                e.printStackTrace();
            } catch (TimeoutException e) {
                showToast("链接超时");
                e.printStackTrace();
            };
        }

    }


    //异步退出登录请求
    //参数：url地址
    BaseInternetMessage baseInternetMessage = new BaseInternetMessage();//自己定义的用于通信格式转换的函数
    @SuppressLint("StaticFieldLeak")
    class LogoutTest extends AsyncTask<String,Void,Integer> {

        @Override
        protected Integer doInBackground(String... params) {
//            String s = IntentUtil.produceMassage(C.login,getJsonStr());
            String s = getJsonStr();
            Log.i("Client_say",s);
            String s2 = IntentUtil.sendPost(params[0],s);
            Log.i("Sever_responce",s2);

//            将收到的json字符串用于完善BaseInternetMessage对象信息
            try {
                baseInternetMessage = BaseInternetMessage.getInternetMassage(s2);
            } catch (JSONException e) {
                Log.i("BaseInternetMessage","getInternetMassage JSONException");
                e.printStackTrace();
                return  -1;
            }
//            Log.i("baseInternetMessage",baseInternetMessage.getCode());
            if(baseInternetMessage.getCode().equals(C.CODE_logoutSuccess)){//登录成功代码
                return 1;
            }else return  0;

        }

        @Override
        protected void onPostExecute(Integer integer) { //doInBackground完成
            super.onPostExecute(integer);
            if(integer==1){
                SharedPreferences.Editor sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
                sp.putInt("user_isLogin",0);//存储用户登录状态
                String token="";
                //将用户登录凭证清空
                sp.putString("user_loginToken",token);
                sp.apply();//

                Intent intent =  new Intent(UserSettingAccountActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("LogoutMessageFromSever",baseInternetMessage.getMessage());
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();

            }
            else{
                if(integer==-1){
//                    网络错误 或 服务器返回信息格式错误
                    showToast("网络错误");
                }else{
                    showToast(baseInternetMessage.getMessage());
                }
            }
        }
    }



    //生成自定义信息体
    //返回：信息体
    private String getJsonStr() {
        String result = "";

        JSONObject body= new JSONObject();
        SharedPreferences sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
        String user_loginToken = sp.getString("user_loginToken","");//取出用户token凭证
        try {
            body.put("token",user_loginToken);
        } catch (JSONException e) {
            Log.e("getJsonStr","JSONException");
            e.printStackTrace();
        }

        result = BaseInternetMessage.produceInternetMassage(C.CODE_logout,body);
        return result;
    }




}
