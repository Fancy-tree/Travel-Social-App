package com.example.travelsocialapp.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.TravelDiary;
import com.example.travelsocialapp.ui.TravelDiaryShowDetailActivity;
import com.example.travelsocialapp.ui.TravelDiaryShowDetailMyActivity;
import com.example.travelsocialapp.util.AppUtil;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.transform.Result;

import static android.content.Context.MODE_PRIVATE;

public class MyDiaryStaggeredGridAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<MyDiaryStaggeredGridAdapter.LinearViewHolder>{
    private Context mcontext;

    private ArrayList<TravelDiary> travelDiaries=new ArrayList<TravelDiary>();// 我的所有日志列表
    public void setTravelDiaries(ArrayList<TravelDiary> travelDiaries) { this.travelDiaries = travelDiaries; }

    public MyDiaryStaggeredGridAdapter(Context context){
        this.mcontext = context;

    }


    @NonNull
    @Override
    public MyDiaryStaggeredGridAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyDiaryStaggeredGridAdapter.LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_recyclerview_travel_diary_show,parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyDiaryStaggeredGridAdapter.LinearViewHolder holder, final int position) {
        String title = travelDiaries.get(position).getMtitle();
        holder.travel_diary_show_title.setText(title);

//        我也不知道为什么，这么写瀑布流就不会错乱太严重
        if(!(travelDiaries.get(position).getMbgimgUrl()).equals(holder.travel_diary_show_bg_pictureI.getTag())) {
            holder.travel_diary_show_bg_pictureI.setTag(travelDiaries.get(position).getMbgimgUrl());
            ImageLoader.getInstance().displayImage(C.intentUrl+travelDiaries.get(position).getMbgimgUrl(), holder.travel_diary_show_bg_pictureI);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, TravelDiaryShowDetailMyActivity.class);
                intent.putExtra("lid",travelDiaries.get(position).getLid());
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return travelDiaries.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{//绑定控件
        ImageView travel_diary_show_bg_pictureI;
        LinearLayout travel_diary_show_father_linearlayout;
        TextView travel_diary_show_title;
        TextView travel_diary_show_user_name;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);

            travel_diary_show_father_linearlayout = (LinearLayout)itemView.findViewById(R.id.travel_diary_show_father_linearlayout);

            travel_diary_show_bg_pictureI = (ImageView)itemView.findViewById(R.id.travel_diary_show_bg_picture);
            int SreenWidthpx = AppUtil.getSreenWidth(mcontext);

            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) travel_diary_show_bg_pictureI.getLayoutParams();
            params.width = SreenWidthpx/2 -AppUtil.dip2px(mcontext,10); //根据屏幕宽度设置图片宽度 接收单位为px
            travel_diary_show_bg_pictureI.setLayoutParams(params);

            travel_diary_show_title = itemView.findViewById(R.id.travel_diary_show_title);

            // 显示用户昵称
            SharedPreferences sp = mcontext.getSharedPreferences("travel_social_app_sharedPreferences",MODE_PRIVATE);
            String username = sp.getString("user_name","");//取出用户昵称
            travel_diary_show_user_name = itemView.findViewById(R.id.travel_diary_show_user_name);
            travel_diary_show_user_name.setText(username);

        }
    }

}
