package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.base.BaseInternetMessage;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.TravelDiary;
import com.example.travelsocialapp.ui.View.PictureTextEditorView;
import com.example.travelsocialapp.util.ImgUtil;
import com.example.travelsocialapp.util.IntentUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class TravelDiaryShowDetailMyActivity extends BaseActivity {

    private TravelDiary travelDiary;
    private TextView travel_diary_show_detail_title;
    private PictureTextEditorView travel_diary_show_detail_content;
    private ImageView travel_diary_show_detail_bg_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary_show_detail_my);
//      初始化travelDiary
        travelDiary=new TravelDiary(TravelDiaryShowDetailMyActivity.this);
//      绑定
        travel_diary_show_detail_progressBar = (ProgressBar)findViewById(R.id.travel_diary_show_detail_progressBar);
        travel_diary_show_detail_title = (TextView) findViewById(R.id.travel_diary_show_detail_title);
        travel_diary_show_detail_bg_img = (ImageView)findViewById(R.id.travel_diary_show_detail_bg_img);
        travel_diary_show_detail_content = (PictureTextEditorView) findViewById(R.id.travel_diary_show_detail_content);
//        设置不可编辑
        travel_diary_show_detail_content.setFocusable(false);
        travel_diary_show_detail_content.setFocusableInTouchMode(false);


        //异步请求获取我的一篇日志
        TravelDiaryShowDetailMyActivity.InternetRequestMyReleaseDiaryOne task = new TravelDiaryShowDetailMyActivity.InternetRequestMyReleaseDiaryOne();
        task.execute(C.intentRequestMyReleaseDiaryOne);
    }


    //异步请求获取我的一篇日志
    //参数：url地址
    private ProgressBar travel_diary_show_detail_progressBar;
    BaseInternetMessage baseInternetMessage = new BaseInternetMessage();//自己定义的用于通信格式转换的函数
    @SuppressLint("StaticFieldLeak")
    class InternetRequestMyReleaseDiaryOne extends AsyncTask<String,Integer,Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            for(int i=0;i<30;i++){
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    Log.e("Sleep","wrong");
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            String s = getJsonStrForIntent();

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
            if(baseInternetMessage.getCode().equals(C.CODE_RequestMyDiaryOneSuccess)){//接收成功代码
                for(int i=30;i<100;i++){
                    try {
                        sleep(5);
                    } catch (InterruptedException e) {
                        Log.e("Sleep","wrong");
                        e.printStackTrace();
                    }
                    publishProgress(i);
                }
                return 1;
            }else return  0;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            travel_diary_show_detail_progressBar.setVisibility(View.VISIBLE);
            travel_diary_show_detail_progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) { //doInBackground完成
            super.onPostExecute(integer);
            if(integer==1){
                JSONObject messageResult = baseInternetMessage.getResultJsonObject();
                try {
                    travelDiary.refreshTravelDiaryFromIntentJsonObject(messageResult,1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("onPostExecute","Json格式错误");
                }
               //子线程加载并显示图片与文字
                TravelDiaryShowDetailMyActivity.InternetRequestMyReleaseDiaryOneImg task = new TravelDiaryShowDetailMyActivity.InternetRequestMyReleaseDiaryOneImg();
                task.execute();

            }else{
                if(integer==-1){
//                    网络错误 或 服务器返回信息错误
                    showToast("网络错误");
                }else{
                    showToast(baseInternetMessage.getMessage());
                }
            }
            travel_diary_show_detail_progressBar.setVisibility(View.INVISIBLE);
        }
    }

    Bitmap bg_img;//日志背景图
    List<Bitmap> bitmapList=new ArrayList<Bitmap>();  //日志正文所有图片
    class InternetRequestMyReleaseDiaryOneImg extends AsyncTask<Void,Integer,Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            bg_img= ImageLoader.getInstance().loadImageSync(C.intentUrl+travelDiary.getMbgimgUrl());
            for(int i=0;i<travelDiary.getmPictureUrl().size();i++){
                bitmapList.add(ImageLoader.getInstance().loadImageSync(C.intentUrl+travelDiary.getmPictureUrl().get(i)));
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if(integer==1){
//                Log.i("Img", ImgUtil.bitmapImagetoString(bitmapList.get(0)));
//                travel_diary_show_detail_bg_img.setImageBitmap(bitmapList.get(0));
                travel_diary_show_detail_title.setText(travelDiary.getMtitle());
                travel_diary_show_detail_bg_img.setImageBitmap(bg_img);
                travel_diary_show_detail_content.refresh(travelDiary.getmContentList(),travelDiary.getMisBitmap(),bitmapList);
            }
        }
    }

    //获取发送的Json信息  请求获取我的一篇旅行日志
    public String getJsonStrForIntent() {
        String result = "";
        JSONObject jsonObject = new JSONObject();

        SharedPreferences sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
        String token = sp.getString("user_loginToken","");//取出用户凭证

        // 获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        String lid = intent.getStringExtra("lid");

        try {
            jsonObject.put("token",token);
            jsonObject.put("lid",lid);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("getJsonStrForIntent","Json格式错误");
        }

        result = BaseInternetMessage.produceInternetMassage(C.CODE_RequestMyDiaryOne,jsonObject);
        return result;
    }
}
