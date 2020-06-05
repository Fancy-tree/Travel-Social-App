package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.logicalAnd;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneE;
    private EditText passwordE;
    private EditText passwordE2;
    private Button signB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        phoneE = (EditText)findViewById(R.id.signin_phone);
        passwordE = (EditText)findViewById(R.id.signin_password);
        passwordE2 = (EditText)findViewById(R.id.signin_password2);
        signB = (Button)findViewById(R.id.signin_signin);
        signB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signin_signin){
            if(!AppUtil.isMobileNO(phoneE.getText().toString())){
                showToast("手机号格式错误");
            }else if(passwordE.getText().toString().equals("")){
                showToast("密码为空");
            }else if(passwordE.getText().toString().contains(" ")){
                showToast("密码不能有空格");
            }else if(!passwordE.getText().toString().equals(passwordE2.getText().toString())){
                showToast("密码两次输入不一致");
            }else{
                SignInActivity.SigninTest task = new SignInActivity.SigninTest();
                try {
                    task.execute(C.intentSigninUrl).get(4000, TimeUnit.MILLISECONDS);
                } catch (ExecutionException e) {
                    showToast("链接ExecutionException");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    showToast("链接InterruptedException");
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    showToast("链接超时");
                    e.printStackTrace();
                }
            }


        }else if(v.getId()==R.id.signin_back){ //点击关闭图标
            finish();
        }

    }

    //异步注册验证
    //参数：url地址
     BaseInternetMessage baseInternetMessage = new BaseInternetMessage();//自己定义的用于通信格式转换的函数
    @SuppressLint("StaticFieldLeak")
    class SigninTest extends AsyncTask<String,Void, Integer> {

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

            if(baseInternetMessage.getCode().equals(C.CODE_signinSuccess)){//注册成功代码
                return 1;
            }else return  0;
        }

        @Override
        protected void onPostExecute(Integer integer) { //doInBackground完成
            super.onPostExecute(integer);
            if(integer==1){

                SharedPreferences.Editor sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
                sp.putInt("user_isLogin",1);//存储用户登录状态
                String token="";
                try {//存储用户登录凭证
                    token = baseInternetMessage.getResultJsonObject().getString("token");
                    sp.putString("user_loginToken",token);
                    Log.i("SharedPreferences","user_loginToken: "+token);
                } catch (JSONException e) {
                    Log.e("SharedPreferences","用户注册凭证Json格式错误");
                    e.printStackTrace();
                }
                sp.apply();//
                Intent intent =  new Intent(SignInActivity.this,LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SigninMessageFromSever",baseInternetMessage.getMessage());
                intent.putExtras(bundle);
                setResult(0, intent);
//                showToast(baseInternetMessage.getMessage());
                finish();
            }
            else{
                if(integer==-1){
                    // 网络错误 或 服务器返回信息错误
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
        result = BaseInternetMessage.produceInternetMassage(C.CODE_signin,user.getLoginJsonObject());
        return result;

    }


}
