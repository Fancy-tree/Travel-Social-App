package com.example.travelsocialapp.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.ui.MainActivity;
import com.example.travelsocialapp.ui.TravelDiaryShowDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchCityLinearAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<SearchCityLinearAdapter.LinearViewHolder> {
    private Context mcontext;
    private List<String> mdatas = C.city;
    private OnItemClickListener mListerner;


    public SearchCityLinearAdapter(Context context,OnItemClickListener listerner){
        this.mcontext = context;
        this.mListerner = listerner;
    }

    @NonNull
    @Override
    public SearchCityLinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_recyclerview_search_city,parent,false));//inflate:XML布局资源被解析并转换成View对象,  每个布局的样子;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {
        holder.item_search_city_textT.setText(mdatas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListerner.onClick(mdatas.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder{//绑定控件
        private TextView item_search_city_textT;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            item_search_city_textT = (TextView)itemView.findViewById(R.id.item_search_city_text);
        }

    }


    // 外部接口回调监听
    public interface OnItemClickListener {
        void onClick(String city);
    }
}
