package com.example.travelsocialapp.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IntentUtil {
    //post传送信息  并 得到服务器返回信息
    //参数：url地址，信息体
    //返回：服务器返回的字符串
    public static String sendPost(String url, String data){
        //System.exit.print(data);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl = null;
        try {
            realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            //这个设置比较重要，设置http请求的数据类型以及编码格式，因为这里使用json来传递数据，所以这一设置是json.
            conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
            // 设置接收类型否则返回415错误//此处为暴力方法设置接受所有类型，以此来防范返回415;
//            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("accept", "application/json");
            // 设置通用的请求属性（？）
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //类型，长度,都会出现权限报错
//            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//            conn.setRequestProperty("Content-Length", data.length()+"");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置post方式和最迟等待时间
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("POST");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(data);  // 向服务端输出参数
            // flush输出流的缓冲
            out.flush();
            //写完数据后，获取服务器响应代码，200 OK //404 页面找不到// 503服务器内部错误
            int code = conn.getResponseCode();
            if(code==200){
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null)
                {
                    result +=  line;
                }
            }else{
                Log.i("Login","登录服务器响应异常！ 出错代码："+code);
                //todo ui通知用户
            }
        } catch (MalformedURLException e) {
            Log.i("Login","登录格式不正确的 url 异常！");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("Login","登录发送POST请求出现异常！");
            e.printStackTrace();
        }finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                Log.i("Login","登录关闭IO流出现异常！");
                ex.printStackTrace();
            }
        }
        return result;

    }





//关于post与get
//    1、get方式的安全性较Post方式要差些，包含e79fa5e98193e4b893e5b19e31333365656532机密信息的话，建议用Post数据提交方式；
//
//2、在做数据查询时，建议用Get方式；而在做数据添加、修改或删除时，建议用Post方式；
//



}
