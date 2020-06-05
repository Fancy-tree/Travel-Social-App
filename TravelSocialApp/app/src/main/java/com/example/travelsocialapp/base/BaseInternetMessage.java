package com.example.travelsocialapp.base;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


//网络传输的信息格式
public class BaseInternetMessage {
    private String code;
    private String message;
    private JSONObject resultJsonObject;

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

    public void setResultJsonObject(JSONObject result) {
        this.resultJsonObject = result;
    }

    public String getCode() { return this.code; }

    public String getMessage() { return this.message; }

    public JSONObject getResultJsonObject() { return this.resultJsonObject; }


    //生成可发送信息
    //参数：标识码，信息体
    //返回：信息字符串
    public static String produceInternetMassage(String code,JSONObject body){
        String result="";
        JSONObject obj = new JSONObject();
        try {
            obj.put("code",code);
            obj.put("result",body);
            result = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

    //解释服务器返回信息
    //参数：从服务器得到的JSON字符串
    //返回：BaseMessage对象
    public  static  BaseInternetMessage getInternetMassage(String jsonStr) throws JSONException {
        BaseInternetMessage message = new BaseInternetMessage();
        JSONObject jsonObject = null;

            jsonObject = new JSONObject(jsonStr);
            if (jsonObject != null) {
                message.setCode(jsonObject.getString("code"));
//                Log.i("BaseInternetMessage",message.getCode());
                message.setMessage(jsonObject.getString("message"));
                message.setResultJsonObject(jsonObject.getJSONObject("result"));

            }


        return message;
    }





}
