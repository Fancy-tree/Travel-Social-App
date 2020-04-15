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
import com.example.travelsocialapp.util.AppUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;



//瀑布流Adapter
public class StaggeredGridAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<StaggeredGridAdapter.LinearViewHolder>{

    private Context mcontext;

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

        if(position % 2 !=0){ //奇数
            holder.travel_diary_show_bg_pictureI.setImageResource(R.drawable.example1);
        }else{//偶数
            holder.travel_diary_show_bg_pictureI.setImageResource(R.drawable.example2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mcontext,);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 30; //列表长度
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{//绑定控件
        ImageView travel_diary_show_bg_pictureI;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            travel_diary_show_bg_pictureI = (ImageView)itemView.findViewById(R.id.travel_diary_show_bg_picture);
            int SreenWidthpx = AppUtil.getSreenWidth(mcontext);

            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) travel_diary_show_bg_pictureI.getLayoutParams();
            params.width = SreenWidthpx/2 -AppUtil.dip2px(mcontext,10); //根据屏幕宽度设置图片宽度 接收单位为px
            travel_diary_show_bg_pictureI.setLayoutParams(params);

        }
    }


}
