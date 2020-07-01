package com.example.travelsocialapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.travelsocialapp.base.BaseInternetMessage;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.ui.TravelDiaryEditActivity;
import com.example.travelsocialapp.util.ImgUtil;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//记录旅行日志所有的内容
//提供一些打包，保存，读取操作
public class TravelDiary {
    private String mtitle;//日志标题
    private Bitmap mbgimg;//日志背景
    private String mbgimgUrl;//日志背景地址
    public String getMbgimgUrl() { return mbgimgUrl; }

    private String lid="";//服务器标识
    public String getLid() { return lid; }

    private List<String> mContentList; //日志正文所有内容
    private List<Bitmap> mBitmapList;  //日志正文所有图片
    private List<Integer> misBitmap;  //日志正文内容项是否为图片

    private List<String> mPictureUrl=new ArrayList<String>();//从服务器获取所有图片的url
    public List<String> getmPictureUrl() { return mPictureUrl; }
    private String mIncompletePictureSaveSDCardUri;//草稿图片暂存sd卡 文件夹地址
    private String mIncompleteTextSaveSDCardUri;//草稿文本信息暂存sd卡 文件夹地址


    //提供内容版初始化
    public TravelDiary(Context context, List<String> ContentList, List<Bitmap> BitmapList, List<Integer> isBitmap,String title,Bitmap bgimg){
        this.mContentList = ContentList;
        this.mBitmapList = BitmapList;
        this.misBitmap = isBitmap;
        this.mtitle = title;
        this.mbgimg = bgimg;
        mIncompletePictureSaveSDCardUri = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() +"/traveldiary/saveIncomplete";
        mIncompleteTextSaveSDCardUri = context.getExternalFilesDir("Documents").toString()+"/traveldiary/saveIncomplete";

    }
    //空白初始化
    public  TravelDiary(Context context){
        mContentList =new ArrayList<String>();
        misBitmap = new ArrayList<Integer>();
        mBitmapList =new ArrayList<Bitmap>();

        mIncompletePictureSaveSDCardUri = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() +"/traveldiary/saveIncomplete";
        mIncompleteTextSaveSDCardUri = context.getExternalFilesDir("Documents").toString()+"/traveldiary/saveIncomplete";
    }

    public String getMtitle() { return mtitle; }
    public List<String> getmContentList() {
        return mContentList;
    }
    public List<Bitmap> getmBitmapList() {
        return mBitmapList;
    }
    public List<Integer> getMisBitmap() {
        return misBitmap;
    }
    public Bitmap getMbgimg() { return mbgimg; }


//    解析服务器数据: 刷新此对象(参数的json对象存储了完整的一个日志信息)
    public void refreshTravelDiaryFromIntentJsonObject(JSONObject jsonObject,int hasContentList) throws JSONException {
        this.mContentList.clear();
        this.mBitmapList.clear();
        this.misBitmap.clear();
        if(hasContentList==1){
            JSONObject result = jsonObject;
            this.mtitle=result.getString("title");
//        this.mbgimg = ImgUtil.stringToBitmapImage(result.getString("titleimg"));
            this.mbgimgUrl = result.getString("titleimg");
            int contentListlLength = result.getInt("length");
            if(contentListlLength!=0){
                JSONObject contentList = result.getJSONObject("contentList");
                int imgCount = 0;//图片计数
                for(int i=0;i<contentListlLength;i++){
                    JSONObject itemin = new JSONObject();
                    itemin=contentList.getJSONObject(i+1+"");
                    if(itemin.getString("type").equals("0")){
//                如果是文本
                        mContentList.add(itemin.getString("content"));
                        misBitmap.add(0);
                    }else{
                        //如果是图片
                        mContentList.add("img");
                        mPictureUrl.add(itemin.getString("content"));
                        misBitmap.add(1);
                    }
                }
            }

        }else {
//            只读取标题和背景图
            JSONObject result = jsonObject;
            this.mtitle=result.getString("title");
//        this.mbgimg = ImgUtil.stringToBitmapImage(result.getString("titleimg"));
            this.mbgimgUrl = result.getString("titleimg");
            this.lid = result.getString("lid");
        }

    }


//    发送服务器：  得到能够发送给服务器的完整日志所有信息的Json字符串
    public String getDiaryJsonStringForIntent(Context context)
    {
        String result = "";
        int imgCount = 0;//图片下标计数
        JSONArray ContentList = new JSONArray();
        for(int i=0;i<mContentList.size();i++){
            JSONObject item = new JSONObject();
            if(misBitmap.get(i)==1){
//                如果是图片
                try {
                    JSONObject itemin = new JSONObject();
                    itemin.put("content",ImgUtil.bitmapImagetoString(mBitmapList.get(imgCount)));
                    itemin.put("type","1");
                    item.put(i+1+"",itemin);
                } catch (JSONException e) {
                    Log.e("TravelDiary","getDiaryJsonStringForIntent ContentList to Json wrong");
                    e.printStackTrace();
                }
                imgCount++;

            }else{
//                如果是文字
                try {
                    JSONObject itemin = new JSONObject();
                    itemin.put("content",mContentList.get(i));
                    itemin.put("type","0");
                    item.put(i+1+"",itemin);
                } catch (JSONException e) {
                    Log.e("TravelDiary","getDiaryJsonStringForIntent ContentList to Json wrong");
                    e.printStackTrace();
                }
            }
            ContentList.put(item);
        }

        JSONObject forResult = new JSONObject();
        SharedPreferences sp = context.getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
        String user_loginToken = sp.getString("user_loginToken","");//取出用户登录标记
        try {
            forResult.put("token",user_loginToken);
            forResult.put("title",mtitle);
            if(mbgimg!=null){
                forResult.put("titleimg",ImgUtil.bitmapImagetoString(mbgimg));
            }else{
                forResult.put("titleimg","");
            }
            forResult.put("contentList",ContentList);
        } catch (JSONException e) {
            Log.e("TravelDiary","getDiaryJsonStringForIntent ContentList to Json wrong");
            e.printStackTrace();
        }

        result = BaseInternetMessage.produceInternetMassage(C.CODE_ReleaseDiary,forResult);
        return result;
    }


    //保存至sd卡： 将diary的文本信息（包括mContentList，misBitmap）   生成一条Json字符串
    public String getDiaryJsonString(){
        String str = "";
        //遍历mContentList 将所有项以string格式放入JSONArray
        JSONArray ContentList = new JSONArray();
        for(int i =0;i<mContentList.size();i++){
            try {
                JSONObject item = new JSONObject();
                item.put(String.valueOf(i),mContentList.get(i)); //value:String
                ContentList.put(item);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("TravelDiary","getDiaryJsonString ContentList to Json wrong");
            }
        }
        //遍历misBitmap 将所有项以string格式放入JSONArray
        JSONArray isBitmap = new JSONArray();
        for(int i =0;i<misBitmap.size();i++){
            try {
                JSONObject item = new JSONObject();
                item.put(String.valueOf(i),misBitmap.get(i));//value:Integer
                isBitmap.put(item);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("TravelDiary","misBitmap to Json wrong");
            }
        }


        String result = "";
        try {
            JSONObject TravelDiary = new JSONObject();
            TravelDiary.put("title",mtitle);
            TravelDiary.put("ContentList",ContentList);
            TravelDiary.put("isBitmap",isBitmap);
            result = TravelDiary.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("TravelDiary","TravelDiary to Json wrong");
        }
        return result;

    }


    //保存至sd卡： 解析符合格式存储TravelDiary的JsonString,重置对象文本信息，（包括mContentList和misBitmap）
    private void reCreateTravelDiaryText(String jsonString){
        mContentList.clear();
        misBitmap.clear();
        if(!jsonString.equals("")){
            try {
                JSONObject jsonObject=new JSONObject(jsonString);

                JSONArray ContentList = jsonObject.getJSONArray("ContentList");
                for(int i =0;i<ContentList.length();i++){
                    try {
                        JSONObject item = ContentList.getJSONObject(i);
                        this.mContentList.add(item.getString(String.valueOf(i)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("TravelDiary","Json to ContentList wrong");
                    }
                }

                JSONArray isBitmap = jsonObject.getJSONArray("isBitmap");
                for(int i =0;i<isBitmap.length();i++){
                    try {
                        JSONObject item = isBitmap.getJSONObject(i);
                        this.misBitmap.add(item.getInt(String.valueOf(i)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("TravelDiary","Json to ContentList wrong");
                    }
                }

                String  title=jsonObject.getString("title");
                this.mtitle = title;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Log.i("TravelDiary","recreate fail: jsonstring = null");
        }


    }




    //将草稿存至SD卡,包括文字和图片
    public int saveRecentDiaryToSDCard(){
//        String pictureSavePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();//$rootDir/Andorid/data/包名/Picture
//        pictureSavePath = pictureSavePath+"/traveldiary/saveIncomplete";//便于Picture下文件分类
//        Log.i("path",pictureSavePath);
        String pictureSavePath;
        File f = null;
        f = new File(mIncompletePictureSaveSDCardUri);
        if(!f.exists()){  //先创建文件夹 ，文件夹和文件不能混合创建！
            f.mkdirs(); //f.mkdir()只能创建1层文件夹，f.mkdirs()才能创建多层文件夹！
        }
        FileOutputStream fOut =null;
        //将所有图片依次存入SD卡
        for(int i =0;i<mBitmapList.size()+1;i++){ //+1 多存一张背景图
//            Log.i("for",String.valueOf(i));
            if (i == 0) { //存入背景图 0.jpg
                pictureSavePath = mIncompletePictureSaveSDCardUri+"/"+i+".jpg";//以下标为文件名//String用+  是很耗时的

            }else{//存入文本中的图 1.jpg 2.jpg...
                pictureSavePath = mIncompletePictureSaveSDCardUri+"/"+i+".jpg";
            }
            // 新建文件
            f = new File(pictureSavePath); //默认没有此文件则创建，有则覆盖
            // 新建文件输出流
            try {
                fOut = new FileOutputStream(f,false);//从文件开头开始覆盖

                if (i == 0) { //存入背景图
                    if(mbgimg!=null)mbgimg.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                }else{//存入文本中的图
                    // 将bitmap压缩至文件输出流
                    mBitmapList.get(i-1).compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                }
                fOut.flush();
            } catch (FileNotFoundException e) {
                Log.i("fout","null");
                e.printStackTrace();
                return 0;
            } catch (IOException e) {
                Log.i("fout","IOException");
                e.printStackTrace();
                return 0;
            }finally {
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

        }

        int count = 1;//计算图片名
        for(int i=0;i<misBitmap.size();i++){
            if(misBitmap.get(i)==1){
                mContentList.set(i,mIncompletePictureSaveSDCardUri+"/"+count+".jpg"); //将所存地址同步改变为sd卡内图片地址
                count++;
            }
        }

//        pictureSavePath = context.getExternalFilesDir("Documents").toString();////$rootDir/Andorid/data/包名/Documents
//        pictureSavePath = pictureSavePath+"/traveldiary/saveIncomplete";//便于Documents下文件分类
        pictureSavePath=mIncompleteTextSaveSDCardUri;
        String text = getDiaryJsonString(); //得到本文的Json
        f = new File(pictureSavePath);
        if(!f.exists()){  //先创建文件夹 ，文件夹和文件不能混合创建！
            f.mkdirs(); //f.mkdir()只能创建1层文件夹，f.mkdirs()才能创建多层文件夹！
        }
        fOut =null;
        pictureSavePath = pictureSavePath+"/incompleteTraveldiary.txt";
        f = new File(pictureSavePath);//创建文件
        try {
            fOut = new FileOutputStream(f);
            fOut.write(text.getBytes()); //写入二进制流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }finally {
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 1;
    }


    //从sd卡读取草稿，重置TravelDiary对象,（包括mContentList，mBitmapList，misBitmap）
    public void getRecentDiaryFromSDCard(){
        String textSavePath = mIncompleteTextSaveSDCardUri+"/incompleteTraveldiary.txt";
        String JsonStr ="";
        File f = new File(textSavePath);
        FileInputStream fread = null;

        try {
            fread = new FileInputStream(f);
            byte[] bytes = new byte[1024];//相当于缓存，万一读一个很大的文件，内存会不够的
            int n = 0;//实际读取到的字节数
			while((n=fread.read(bytes))!=-1){//循环读取，先读bytes大小，再读bytes大小...//read返回实际读到的字符数，读到文件末尾会返回-1
                JsonStr = new String(bytes,0,n);//把字节转string
             }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fread.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reCreateTravelDiaryText(JsonStr);
        mBitmapList.clear();//图片清空
        int count =0;//统计共有几张图
        for(int i=0;i<misBitmap.size();i++){
            if(misBitmap.get(i)==1) count++;
        }

        String pictureSavePath = mIncompletePictureSaveSDCardUri;
        File f2 = null;
        FileInputStream fread2 = null;
        for(int i=0;i<count+1;i++){
            try {
                if(i==0){ //取出背景图
                    f2 = new File(pictureSavePath+"/"+i+".jpg"); //0.jpg
                    fread2 = new FileInputStream(f2);
                    Bitmap bitmap  = BitmapFactory.decodeStream(fread2);
                    mbgimg = bitmap;
                }else {
                    f2 = new File(pictureSavePath+"/"+(i)+".jpg"); //1.jpg 2.jpg
                    fread2 = new FileInputStream(f2);
                    Bitmap bitmap  = BitmapFactory.decodeStream(fread2);
                    mBitmapList.add(bitmap);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }


    }






}
