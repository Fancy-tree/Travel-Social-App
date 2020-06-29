package com.example.travelsocialapp.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

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

            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP请求正文的流。
//            conn.setChunkedStreamingMode(51200); // 128K
            // 不使用缓存
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置post方式和最迟等待时间
            conn.setConnectTimeout(5000);
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

//    post多张图片上传
//    private static final String TAG = "uploadFile";
//    private static final int TIME_OUT = 10*10000000;   //超时时间
//    private static final String CHARSET = "utf-8"; //设置编码
//    public static final String SUCCESS="1";
//    public static final String FAILURE="0";
//    /**
//     * android上传文件到服务器
//     * @param file  需要上传的文件
//     * @param RequestURL  请求的rul
//     * @return  返回响应的内容
//     */
//    public static String uploadFile(File file,String RequestURL)
//    {
//        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
//        String PREFIX = "--" , LINE_END = "\r\n";
//        String CONTENT_TYPE = "multipart/form-data";   //内容类型
//
//        try {
//            URL url = new URL(RequestURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(TIME_OUT);
//            conn.setConnectTimeout(TIME_OUT);
//            conn.setDoInput(true);  //允许输入流
//            conn.setDoOutput(true); //允许输出流
//            conn.setUseCaches(false);  //不允许使用缓存
//            conn.setRequestMethod("POST");  //请求方式
//            conn.setRequestProperty("Charset", CHARSET);  //设置编码
//            conn.setRequestProperty("connection", "keep-alive");
//            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
//            if(file!=null)
//            {
//                /**
//                 * 当文件不为空，把文件包装并且上传
//                 */
//                OutputStream outputSteam=conn.getOutputStream();
//                //DataOutputStream数据输出流允许应用程序以与机器无关方式将Java基本数据类型写到底层输出流。
//                DataOutputStream dos = new DataOutputStream(outputSteam);
////                StringBuffer 和 StringBuilder 类的对象能够被多次的修改，并且不产生新的未使用对象,StringBuffer线程安全
//                StringBuffer sb = new StringBuffer();
//                sb.append(PREFIX);
//                sb.append(BOUNDARY);
//                sb.append(LINE_END);
//                /**
//                 * 这里重点注意：
//                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
//                 * filename是文件的名字，包含后缀名的   比如:abc.png
//                 */
//
//                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
//                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
//                sb.append(LINE_END);
//                dos.write(sb.toString().getBytes());
//                InputStream is = new FileInputStream(file);
//                byte[] bytes = new byte[1024];
//                int len = 0;
//                while((len=is.read(bytes))!=-1)
//                {
//                    dos.write(bytes, 0, len);
//                }
//                is.close();
//                dos.write(LINE_END.getBytes());
//                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
//                dos.write(end_data);
//                dos.flush();
//                /**
//                 * 获取响应码  200=成功
//                 * 当响应成功，获取响应的流
//                 */
//                int res = conn.getResponseCode();
//                Log.e(TAG, "response code:"+res);
//                if(res==200)
//                {
//                    return SUCCESS;
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return FAILURE;
//    }








}
