package com.example.travelsocialapp.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class GuideMapUpMenuLinearAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<GuideMapUpMenuLinearAdapter.LinearViewHolder> {
    private Context mcontext;

    public GuideMapUpMenuLinearAdapter(Context context){
        this.mcontext = context;
    }


    @NonNull
    @Override
    public GuideMapUpMenuLinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate:XML布局资源被解析并转换成View对象,  每个布局的样子;
        return  new GuideMapUpMenuLinearAdapter.LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_recyclerview_guide_map_up_menu,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuideMapUpMenuLinearAdapter.LinearViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }



    class LinearViewHolder extends RecyclerView.ViewHolder{//绑定控件

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);

        }

    }
}
