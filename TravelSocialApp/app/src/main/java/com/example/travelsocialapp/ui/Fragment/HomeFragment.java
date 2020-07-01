package com.example.travelsocialapp.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseInternetMessage;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.TravelDiary;
import com.example.travelsocialapp.ui.Adapter.StaggeredGridAdapter;
import com.example.travelsocialapp.ui.View.MyScrollView;
import com.example.travelsocialapp.util.AppUtil;
import com.example.travelsocialapp.util.IntentUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Thread.sleep;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        return view;
    }

    private RecyclerView mtravel_diary_showR;
    private MyScrollView home_scrollS;
    private StaggeredGridAdapter staggeredGridAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staggeredGridAdapter=new StaggeredGridAdapter(getActivity());
        init(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    private void init(View view){
        home_scrollS = (MyScrollView)view.findViewById(R.id.home_scroll);
        //滚动到顶部,在创建的时候位置信息还不准确，方法失效，所以要避开view创建时段
        //post 其实并不是创建新的线程，所以不要包含复杂逻辑
        home_scrollS.post(new Runnable() {
            @Override
            public void run() {
                home_scrollS.scrollTo(0, 0);
            }
        });


        mtravel_diary_showR = view.findViewById(R.id.travel_diary_show);
        mtravel_diary_showR.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));//（列数/行数，竖直/水平）
        if(getActivity()!=null){
            mtravel_diary_showR.setAdapter(staggeredGridAdapter);
        }


        home_progress = view.findViewById(R.id.home_progress);
//        请求旅行日志
        // 获取服务器旅游日志信息（最多5个）
        HomeFragment.InternetRequestReleaseDiaryHome task = new HomeFragment.InternetRequestReleaseDiaryHome();
        task.execute(C.intentRequestReleaseDiaryHomeUrl);

//      判断是否滚动到底部，显示更多
        home_scrollS.setOnScrollViewListener(new MyScrollView.MyScrollViewListener() {
            @Override
            public void onTop() {

            }

            @Override
            public void onBottom() {
                if(isLastDiary==0 && canReflush==1){
                    // 向服务器获取更多我的旅游日志信息（最多5个）
                    HomeFragment.InternetRequestReleaseDiaryHome task = new HomeFragment.InternetRequestReleaseDiaryHome();
                    task.execute(C.intentRequestReleaseDiaryHomeUrl);
                }
                if(isLastDiary==1){
                    Toast.makeText(getActivity().getApplicationContext(),"到底啦~",Toast.LENGTH_SHORT).show();
//                            travel_diary_nomore.setVisibility(View.VISIBLE);
//                            travel_diary_nomore.setText("到底啦~");
                }
            }
        });


    }

    ArrayList<TravelDiary> travelDiaries = new ArrayList<TravelDiary>();// 我的所有日志列表
    //异步请求获取我的日志
    //参数：url地址
    ProgressBar home_progress;
    BaseInternetMessage baseInternetMessage = new BaseInternetMessage();//自己定义的用于通信格式转换的函数
    @SuppressLint("StaticFieldLeak")
    class InternetRequestReleaseDiaryHome extends AsyncTask<String,Integer,Integer> {
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
            if(baseInternetMessage.getCode().equals(C.CODE_RequestDiaryHomeSuccess)){//接收成功代码
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
            home_progress.setVisibility(View.VISIBLE);
            home_progress.setProgress(values[0]);
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
                    SharedPreferences.Editor sp = getActivity().getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE).edit();
                    sp.putInt("serial_number_home",serial_number);//存储服务器需要的序号
                    sp.apply();
                    isLastDiary = messageResult.getInt("end_log");//是否包含最后一篇

                    JSONObject diarys= messageResult.getJSONObject("result_log");
                    for (int i=1;i<=travelDiariesNum;i++){
                        JSONObject oneDiary = diarys.getJSONObject(i+"");
                        TravelDiary tmp = new TravelDiary(getActivity());
                        tmp.refreshTravelDiaryFromIntentJsonObject(oneDiary,0);
                        travelDiaries.add(tmp);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("onPostExecute","Json格式错误");
                }

                staggeredGridAdapter.setTravelDiaries(travelDiaries);
                staggeredGridAdapter.notifyDataSetChanged();

            }else{
                if(integer==-1){
//                    网络错误 或 服务器返回信息错误
                    if(getActivity()!=null){
                        Toast.makeText(getActivity().getApplicationContext(),"网络错误",Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e("getActivity()","null");
                    }

                }else{
                    if(getActivity()!=null){
                        Toast.makeText(getActivity().getApplicationContext(),baseInternetMessage.getMessage(),Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e("getActivity()","null");
                    }
                }
            }
            home_progress.setVisibility(View.INVISIBLE);
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
        if(getActivity()!=null){
            SharedPreferences sp = getActivity().getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
            String token = sp.getString("user_loginToken","");//取出用户登录凭证
            try {
                jsonObject.put("token",token);
                jsonObject.put("labelNumber",labelNumber);
                int serial_number = sp.getInt("serial_number_home",1);//取出服务器需要的序号
                jsonObject.put("serialNumber",serial_number);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("getJsonStrForIntent","Json格式错误");
            }
            result = BaseInternetMessage.produceInternetMassage(C.CODE_RequestDiaryHome,jsonObject);
        }else{
            Log.e("getJsonStrForIntent","getActivity=null");
        }
        return result;
    }



    //给activity调用
    public void scrollToTop(){
        //滚动到顶部,在创建的时候位置信息还不准确，方法失效，所以要避开view创建时段
        //post 其实并不是创建新的线程，所以不要包含复杂逻辑
        if(home_scrollS!=null)
        home_scrollS.post(new Runnable() {
            @Override
            public void run() {
                home_scrollS.scrollTo(0, 0);
            }
        });
    }

   //给activity调用,刷出最新日志
    public void reflushDiary(){
        if(AppUtil.isreleaseDiary==1){
            travelDiaries.clear();
            labelNumber=1;
            AppUtil.isreleaseDiary=0;
            // 向服务器获取更多我的旅游日志信息（最多5个）
            HomeFragment.InternetRequestReleaseDiaryHome task = new HomeFragment.InternetRequestReleaseDiaryHome();
            task.execute(C.intentRequestReleaseDiaryHomeUrl);

        }
    }

    public interface xxx{ //提供给activity实现，就可以在fragment里控制activity

    }

    @Override
    public void onAttach(@NonNull Context context) { //当与activity建立联系
        super.onAttach(context);
        //接口得到activity对象

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Home","resume");


    }

    @Override
    public void onDetach() { //当与activity断开联系
        super.onDetach();
    }

    @Override
    public void onDestroy() { //当frament销毁
        super.onDestroy();
        //取消异步任务
    }
}
