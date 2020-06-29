package com.example.travelsocialapp.base;

//备注：
//共享空间  存储现状

// 名字：user_setting
//city: 最近一次用户选择的城市名String

// 名字: travel_social_app_sharedPreferences
//user_isLogin: 用户是否登录Int
//user_loginToken：用户登录凭证String
//user_name:用户昵称String

// serial_number:显示我的旅行日志时，服务器需要的序号Int

import java.util.ArrayList;

//常量
public final class C {

    //发送服务器信息 code
    public static final String CODE_login	= "101";//请求登录验证
    public static final String CODE_signin	= "102";//请求注册验证
    public static final String CODE_logout	= "103";//请求退出登录

    public static final String CODE_ReleaseDiary	= "201";//请求发布旅行日志
    public static final String CODE_RequestMyDiary	= "202";//请求获取我的旅行日志

//    服务器返回code
    public static final String CODE_loginSuccess= "102";//登录成功
    public static final String CODE_signinSuccess= "104";//注册成功
    public static final String CODE_logoutSuccess= "108";//注册成功

    public static final String CODE_ReleaseDiarySuccess	= "202";//请求发布旅行日志成功
    public static final String CODE_RequestMyDiarySuccess	= "204";//请求获取我的旅行日志成功

//    服务器地址
    public static final String intentUrl = "http://192.168.43.132:80";
//      public static final String intentUrl = "http://172.20.10.5:80";
//    示例
//    private String url = "http://172.20.10.2/Travel2/tp5/public/index.php";
//    private String url = "http://192.168.1.5:80/Travel2/tp5/public?login='sdfa'";

//    注册登录服务地址
    public static final String intentLoginUrl = intentUrl+"/Travel2/tp5/index";
    public static final String intentSigninUrl = intentUrl+"/Travel2/tp5/api_login";
    public static final String intentLogoutUrl = intentUrl+"/Travel2/tp5/exit";

//    发布旅行日志地址
    public static final String intentReleaseDiaryUrl = intentUrl+"/Travel2/tp5/re_travel_log";//发布旅行日志
    public static final String intentRequestMyReleaseDiaryUrl = intentUrl+"/Travel2/tp5/out_travel_log";//请求我的旅行日志



    //默认通用城市列表
    public static final ArrayList<String> city = new ArrayList<String>(){
        //生成匿名内部内进行初始化
        {
            add("南京");
            add("北京");
            add("上海");
        }
    };



}
