package com.example.travelsocialapp.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.base.BaseInternetMessage;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.TravelDiary;
import com.example.travelsocialapp.model.User;
import com.example.travelsocialapp.ui.Adapter.MyDiaryStaggeredGridAdapter;
import com.example.travelsocialapp.ui.Adapter.StaggeredGridAdapter;
import com.example.travelsocialapp.util.AppUtil;
import com.example.travelsocialapp.util.IntentUtil;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

public class TravelDiaryActivity extends BaseActivity implements View.OnClickListener {
    private ProgressBar progressBar;
    private RecyclerView travel_diary_my;
    private MyDiaryStaggeredGridAdapter myDiaryStaggeredGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary);
        findViewById(R.id.goto_write_diary).setOnClickListener(this);//写日志

        progressBar = findViewById(R.id.travel_diary_progress);

        myDiaryStaggeredGridAdapter = new MyDiaryStaggeredGridAdapter(TravelDiaryActivity.this);
        travel_diary_my = findViewById(R.id.travel_diary_my);
        travel_diary_my.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));//（列数/行数，竖直/水平）
        travel_diary_my.setAdapter(myDiaryStaggeredGridAdapter);
//      判断是否滚动到底部，显示更多
        travel_diary_my.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition,
                    int into[]={0,0};
                    int lastVisibleItem[] = manager.findLastCompletelyVisibleItemPositions(into);//瀑布流的返回值是数组，可能有多个最后一个的缘故
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向下滚动
                    if ((lastVisibleItem[0] == (totalItemCount - 1)||lastVisibleItem[1] == (totalItemCount - 1))&& isSlidingToLast) {
//                        showToast("last");
                        if(isLastDiary==0 && canReflush==1){
                            // 向服务器获取更多我的旅游日志信息（最多5个）
                            TravelDiaryActivity.InternetRequestMyReleaseDiary task = new TravelDiaryActivity.InternetRequestMyReleaseDiary();
                            task.execute(C.intentRequestMyReleaseDiaryUrl);
                        }
                        if(isLastDiary==1){
                            showToast("到底啦~");
//                            travel_diary_nomore.setVisibility(View.VISIBLE);
//                            travel_diary_nomore.setText("到底啦~");
                        }

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dy > 0) {
                    //大于0表示正在向下滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0表示停止或向上滚动
                    isSlidingToLast = false;
                }
            }
        });

        // 获取服务器旅游日志信息（最多5个）
        TravelDiaryActivity.InternetRequestMyReleaseDiary task = new TravelDiaryActivity.InternetRequestMyReleaseDiary();
        task.execute(C.intentRequestMyReleaseDiaryUrl);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goto_write_diary){//点击写日志将跳转到编辑日志界面
            Intent intent = new Intent(this, TravelDiaryEditActivity.class);
            startActivityForResult(intent,1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
    }

    ArrayList<TravelDiary> travelDiaries = new ArrayList<TravelDiary>();// 我的所有日志列表
    //异步请求获取我的日志
    //参数：url地址
    ProgressBar travel_diary_edit_progressBar;
    BaseInternetMessage baseInternetMessage = new BaseInternetMessage();//自己定义的用于通信格式转换的函数
    @SuppressLint("StaticFieldLeak")
    class InternetRequestMyReleaseDiary extends AsyncTask<String,Integer,Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            在加载状态时不能重复触发加载
            canReflush =0;
        }

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
//            Log.i("baseInternetMessage",baseInternetMessage.getCode());
            if(baseInternetMessage.getCode().equals(C.CODE_RequestMyDiarySuccess)){//接收成功代码
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
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) { //doInBackground完成
            super.onPostExecute(integer);
            if(integer==1){
                JSONObject messageResult = baseInternetMessage.getResultJsonObject();
                try {
                    int travelDiariesNum = messageResult.getInt("num");
                    labelNumber = labelNumber + travelDiariesNum;//更新显示到哪一个
                    int serial_number = messageResult.getInt("serial_number");
                    SharedPreferences.Editor sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
                    sp.putInt("serial_number",serial_number);//存储服务器需要的序号
                    sp.apply();
                    isLastDiary = messageResult.getInt("end_log");//是否包含最后一篇

                    JSONObject diarys= messageResult.getJSONObject("result_log");
                    for (int i=1;i<=travelDiariesNum;i++){
                        JSONObject oneDiary = diarys.getJSONObject(i+"");
                        TravelDiary tmp = new TravelDiary(TravelDiaryActivity.this);
                        tmp.refreshTravelDiaryFromIntentJsonObject(oneDiary,0);
                        travelDiaries.add(tmp);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("onPostExecute","Json格式错误");
                }


                myDiaryStaggeredGridAdapter.setTravelDiaries(travelDiaries);
                myDiaryStaggeredGridAdapter.notifyDataSetChanged();


            }else{
                if(integer==-1){
//                    网络错误 或 服务器返回信息错误
                    showToast("网络错误");
                }else{
                    showToast(baseInternetMessage.getMessage());
                }
            }
            progressBar.setVisibility(View.INVISIBLE);
            canReflush =1;
        }
    }

//    需要加载的下一篇日志序号
    int labelNumber = 1;
//    是否到加载到最后一篇
    int isLastDiary = 0;
//    是否可以加载更多
    int canReflush = 1;
    //获取发送的Json信息  请求获取我的旅行日志集合
    public String getJsonStrForIntent() {
        String result = "";
        JSONObject jsonObject = new JSONObject();
        SharedPreferences sp = getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
        String token = sp.getString("user_loginToken","");//取出用户登录凭证
        try {
            jsonObject.put("token",token);
            jsonObject.put("labelNumber",labelNumber);
            int serial_number = sp.getInt("serial_number",1);//取出服务器需要的序号
            jsonObject.put("serialNumber",serial_number);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("getJsonStrForIntent","Json格式错误");
        }
        result = BaseInternetMessage.produceInternetMassage(C.CODE_RequestMyDiary,jsonObject);
        return result;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){//编写旅行日志
            if(resultCode==1){//发布成功
//                通知首页刷新
                AppUtil.isreleaseDiary = 1;
//                从头开始刷新
                travelDiaries.clear();
                labelNumber=1;
                // 向服务器获取更多我的旅游日志信息（最多5个）
                TravelDiaryActivity.InternetRequestMyReleaseDiary task = new TravelDiaryActivity.InternetRequestMyReleaseDiary();
                task.execute(C.intentRequestMyReleaseDiaryUrl);
            }else {
                Log.e("onActivityResult","TravelDiaryShowDetailActivity 编写旅行日志页面返回未定义");
            }
        }

    }



}
