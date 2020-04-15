package com.example.travelsocialapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.User;
import com.example.travelsocialapp.util.IntentUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneE;
    private EditText passwordE;
    private Button loginB;
    private String url = "http://192.168.1.3:80/AndroidSever2/user_login.php";
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneE = (EditText)findViewById(R.id.phone);
        passwordE = (EditText)findViewById(R.id.password);
        loginB = (Button)findViewById(R.id.login);
        loginB.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login){

            LoginTest task = new LoginTest();
            task.execute(url);

//            Thread thread =new Thread(runnable);
//            thread.start();

        }else if(v.getId()==R.id.login_exit){ //点击关闭图标
            finish();
        }
    }

    //异步登录验证
    //参数：url地址
    class LoginTest extends AsyncTask<String,Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
//            String s = IntentUtil.produceMassage(C.login,getJsonStr());
//            Log.i("json",s);
//            String s2 =IntentUtil.sendPost(params[0],s);
//            Log.i("responce",s2);
//
//            if(s2.equals("success")){
//                return TRUE;
//            }
//            else return  FALSE;
            return true;//无条件登录成功
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) { //doInBackground完成
            super.onPostExecute(aBoolean);
            if(aBoolean==TRUE){
                showToast("登录成功");
                SharedPreferences.Editor sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
                sp.putInt("user_isLogin",1);//存储最近一次登录情况
                sp.apply();//
            }
            else{
                showToast("账号或密码错误");
                SharedPreferences.Editor sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
                sp.putInt("user_isLogin",0);//存储最近一次登录情况
                sp.apply();//
            }
        }
    }



    //生成自定义信息体
    //返回：信息体、
    private String getJsonStr() {
        String str = "";
        User user = new User(phoneE.getText().toString(),passwordE.getText().toString());
        return user.getLoginJsonString();
    }



    //没有使用model的时候
//    private String getJsonStr() {
//        String str = "";
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("phone",phoneE.getText().toString());
//            obj.put("password",passwordE.getText().toString());
//            str = obj.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }




    //    Runnable runnable =new Runnable() { //Runnable实验
//        @Override
//        public void run() {
//            String s = getJsonStr();
//            Log.i("json",s);
//            String s2 =IntentUtil.sendPost(url,s);
//            Log.i("responce",s2);
//        }
//    };

//    public static Map<String, String> parseJson(String jsonStr) //解析json 实验
//    {
//        Map<String,String> info=new HashMap<>();
//        try{
//            JSONObject jsonObject=new JSONObject(jsonStr);
//            String phone=jsonObject.getString("phone");
//            String password=jsonObject.getString("password");
//            info.put("phone",phone);
//            info.put("password",password);
//
//        }catch (JSONException js)
//        {
//            js.printStackTrace();
//            System.exit.print("ParseJson Error");
//        }
//        return info;
//    }



}
