package com.example.travelsocialapp.base;

//备注：
//共享空间  存储现状
// 名字：user_setting
//city: 最近一次用户选择的城市名

import java.util.ArrayList;

//常量
public final class C {

    //发送服务器信息 code
    public static final String CODE_login	= "101";//请求登录验证
    public static final String CODE_signin	= "102";//请求注册验证

//    服务器返回code
    public static final String CODE_loginSuccess= "102";//登录成功
    public static final String CODE_signinSuccess= "104";//登录成功

//    服务器地址
    public static final String intentUrl = "http://192.168.43.25:80";
//    示例
//    private String url = "http://172.20.10.2/Travel2/tp5/public/index.php";
//    private String url = "http://192.168.1.5:80/Travel2/tp5/public?login='sdfa'";

//    注册登录服务地址
    public static final String intentLoginUrl = intentUrl+"/Travel2/tp5/public/index.php/index/";
    public static final String intentSigninUrl = intentUrl+"/Travel2/tp5/public/index.php/index/index/login";




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
