package com.example.travelsocialapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String mphoneNum; //用户电话号码
    private String mpassword; //用户密码

    public User(String phoneNum,String password){
        this.mphoneNum = phoneNum;
        this.mpassword = password;
    }



    //仅登录验证所需JsonString
    public String getLoginJsonString(){
        String str = "";
        JSONObject obj = new JSONObject();
        try {
            obj.put("phone", this.mphoneNum);
            obj.put("password",this.mpassword);
            str = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;

    }
}
