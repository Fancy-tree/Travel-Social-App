package com.example.travelsocialapp.ui;

import android.annotation.SuppressLint;
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
import com.example.travelsocialapp.base.BaseInternetMessage;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.User;
import com.example.travelsocialapp.util.AppUtil;
import com.example.travelsocialapp.util.IntentUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneE;
    private EditText passwordE;
    private Button loginB;

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneE = (EditText)findViewById(R.id.phone);
        passwordE = (EditText)findViewById(R.id.password);
        loginB = (Button)findViewById(R.id.login);
        loginB.setOnClickListener(this);
        findViewById(R.id.login_tosigin).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login){//点击登录
            if(!AppUtil.isMobileNO(phoneE.getText().toString())){
                showToast("手机号格式错误");
            }else if(passwordE.getText().toString().equals("")){
                showToast("密码为空");
            }else if(passwordE.getText().toString().contains(" ")){
                showToast("密码含有空格");
            }else{
                LoginTest task = new LoginTest();
                try {
                    task.execute(C.intentLoginUrl).get(4000, TimeUnit.MILLISECONDS);
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

//            Thread thread =new Thread(runnable);
//            thread.start();
            }
        }else if(v.getId()==R.id.login_tosigin){ //点击注册
            Intent intent = new Intent(this, SignInActivity.class);
            startActivityForResult(intent,0);
        }else if(v.getId()==R.id.login_exit){ //点击关闭图标
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        如果用户已登录，强制退出Activity
//        SharedPreferences geteditorsp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
//        int isLogin =geteditorsp.getInt("user_isLogin",0);
//        if(isLogin==1){
//            finish();
//        }
    }

    //异步登录验证
    //参数：url地址
    BaseInternetMessage baseInternetMessage = new BaseInternetMessage();//自己定义的用于通信格式转换的函数
    @SuppressLint("StaticFieldLeak")
    class LoginTest extends AsyncTask<String,Void,Integer> {

        @Override
        protected Integer doInBackground(String... params) {
//            String s = IntentUtil.produceMassage(C.login,getJsonStr());
            String s = getJsonStr();
            Log.i("Client_say",s);
            String s2 =IntentUtil.sendPost(params[0],s);
            Log.i("Sever_responce",s2);

//            将收到的json字符串用于完善BaseInternetMessage对象信息
            try {
                baseInternetMessage = BaseInternetMessage.getInternetMassage(s2);
            } catch (JSONException e) {
                Log.i("BaseInternetMessage","getInternetMassage JSONException");
                e.printStackTrace();
                return  -1;
            }
            Log.i("baseInternetMessage",baseInternetMessage.getCode());
            if(baseInternetMessage.getCode().equals(C.CODE_loginSuccess)){//登录成功代码
                return 1;
            }else return  0;

        }

        @Override
        protected void onPostExecute(Integer integer) { //doInBackground完成
            super.onPostExecute(integer);
            if(integer==1){
                int flag = 0;
                SharedPreferences.Editor sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
                sp.putInt("user_isLogin",1);//存储用户登录状态
                String token="";
                try {//存储用户登录凭证
                    token  = baseInternetMessage.getResultJsonObject().getString("token");
                    sp.putString("user_loginToken",token);
                } catch (JSONException e) {
                    flag = 0;
                    Log.e("SharedPreferences","用户登录凭证Json格式错误");
                    e.printStackTrace();
                }
                Log.i("SharedPreferences","user_loginToken: "+token);
                sp.apply();//
                flag=1;

                if(flag==1){
                    Intent intent =  new Intent(LoginActivity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("LoginMessageFromSever",baseInternetMessage.getMessage());
                    intent.putExtras(bundle);
                    setResult(0, intent);
                    finish();
                }

            }
            else{
                if(integer==-1){
//                    网络错误 或 服务器返回信息错误
                    showToast("网络错误");
                }else{
                    showToast(baseInternetMessage.getMessage());
                }
                SharedPreferences.Editor sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
                sp.putInt("user_isLogin",0);//存储用户登录状态
                sp.apply();//
            }
        }
    }



    //生成自定义信息体
    //返回：信息体、
    private String getJsonStr() {
        String result = "";
        User user = new User(phoneE.getText().toString(),passwordE.getText().toString());
        result = BaseInternetMessage.produceInternetMassage(C.CODE_login,user.getLoginJsonObject());
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){//注册状态
            if(resultCode==0){
//                显示服务器返回的消息
                Intent intent =  new Intent(LoginActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SigninMessageFromSever",data.getExtras().getString("SigninMessageFromSever"));
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();
            }else {
                Log.e("onActivityResult","LoginActivity 未注册成功");
            }
        }


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
