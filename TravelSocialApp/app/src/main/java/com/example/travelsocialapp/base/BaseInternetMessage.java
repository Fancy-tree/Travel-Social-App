package com.example.travelsocialapp.base;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


//网络传输的信息格式
public class BaseInternetMessage {
    private String code;
    private String message;
    private String resultStr;
    private Map<String, BaseModel> resultMap;
    private Map<String, ArrayList<? extends BaseModel>> resultList;

    public BaseInternetMessage() {
//        this.resultMap = new HashMap<String, BaseModel>();
//        this.resultList = new HashMap<String, ArrayList<? extends BaseModel>>();
    }


    public void setCode(String code) {
        this.code=code;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    public void setResult(String result) {
        this.resultStr = result;
    }


    //生成可发送信息
    //参数：标识码，信息体
    //返回：信息字符串
    public static String produceMassage(String head,String body){
        String result="";
        JSONObject obj = new JSONObject();
        try {
            obj.put("head",head);
            obj.put("body",body);
            result = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

    //解释服务器返回信息
    //参数：从服务器得到的JSON字符串
    //返回：BaseMessage对象
    public static BaseInternetMessage getMassage(String jsonStr){
        BaseInternetMessage message = new BaseInternetMessage();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
            if (jsonObject != null) {
                message.setCode(jsonObject.getString("code"));
                message.setMessage(jsonObject.getString("message"));
                message.setResult(jsonObject.getString("result"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }


}
