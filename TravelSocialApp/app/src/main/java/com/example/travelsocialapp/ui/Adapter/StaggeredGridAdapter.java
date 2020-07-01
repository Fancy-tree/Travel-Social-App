package com.example.travelsocialapp.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.TravelDiary;
import com.example.travelsocialapp.ui.TravelDiaryShowDetailActivity;
import com.example.travelsocialapp.util.AppUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;



//瀑布流Adapter
public class StaggeredGridAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<StaggeredGridAdapter.LinearViewHolder>{

    private Context mcontext;
    private ArrayList<TravelDiary> travelDiaries=new ArrayList<TravelDiary>();// 我的所有日志列表
    public void setTravelDiaries(ArrayList<TravelDiary> travelDiaries) { this.travelDiaries = travelDiaries; }

    //内容的高度
    private ArrayList<Integer> oneItemHeight=new ArrayList<Integer>();
    public ArrayList<Integer> getOneItemHeight() {
        return oneItemHeight;
    }
    public void setOneItemHeight(ArrayList<Integer> allItemHeight) {
        this.oneItemHeight = allItemHeight;
    }

    public StaggeredGridAdapter(Context context){
        this.mcontext = context;
    }

    @NonNull
    @Override
    public StaggeredGridAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate:XML布局资源被解析并转换成View对象,  每个布局的样子
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_recyclerview_travel_diary_show,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {//操作控件

        String title = travelDiaries.get(position).getMtitle();
        holder.travel_diary_show_title.setText(title);

//        我也不知道为什么，这么写瀑布流就不会错乱太严重
        if(!(travelDiaries.get(position).getMbgimgUrl()).equals(holder.travel_diary_show_bg_pictureI.getTag())) {
            holder.travel_diary_show_bg_pictureI.setTag(travelDiaries.get(position).getMbgimgUrl());
            ImageLoader.getInstance().displayImage(C.intentUrl+travelDiaries.get(position).getMbgimgUrl(), holder.travel_diary_show_bg_pictureI);
        }
        oneItemHeight.add(holder.travel_diary_show_father_linearlayout.getHeight());
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        // 图片高度随机设置  1:1 或 3:4
        int max=10,min=1;
        int random = (int) (Math.random()*(max-min)+min);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) holder.travel_diary_show_bg_pictureI.getLayoutParams();
        int height = params.height;
        int width = params.width;
        if(random<5){
            params.height =width; //根据屏幕宽度设置图片宽度 接收单位为px
        }else{
            params.height =width*4/3;
        }
        holder.travel_diary_show_bg_pictureI.setLayoutParams(params);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, TravelDiaryShowDetailActivity.class);
                mcontext.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return travelDiaries.size(); //列表长度
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

            travel_diary_show_title = itemView.findViewById(R.id.travel_diary_show_title);
            travel_diary_show_user_name = itemView.findViewById(R.id.travel_diary_show_user_name);

            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) travel_diary_show_bg_pictureI.getLayoutParams();
            params.width = SreenWidthpx/2 -AppUtil.dip2px(mcontext,10); //根据屏幕宽度设置图片宽度 接收单位为px
            travel_diary_show_bg_pictureI.setLayoutParams(params);

        }
    }




}
