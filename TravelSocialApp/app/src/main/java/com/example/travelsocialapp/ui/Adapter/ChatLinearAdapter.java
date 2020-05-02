package com.example.travelsocialapp.ui.Adapter;

import android.content.Context;
import android.util.Log;
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

public class ChatLinearAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ChatLinearAdapter.LinearViewHolder> {
    private Context mcontext;
    private List<ChatMessage> mdatas = new ArrayList<>();

    public ChatLinearAdapter(Context context){
        this.mcontext = context;
    }

    //给adapter添加数据
    public void addDataToAdapter(ChatMessage msg) {
        mdatas.add(msg);
    }

    @NonNull
    @Override
    public ChatLinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_recyclerview_chat_message,parent,false));//inflate:XML布局资源被解析并转换成View对象,  每个布局的样子;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatLinearAdapter.LinearViewHolder holder, int position) {//操作控件
        ChatMessage tmpmsg = mdatas.get(position);
        if(tmpmsg.getType()==0){
            holder.chat_rightL.setVisibility(View.VISIBLE);
            holder.chat_leftL.setVisibility(View.GONE);
            holder.chat_right_msgT.setText(tmpmsg.getMassage());

        }else{
            holder.chat_leftL.setVisibility(View.VISIBLE);
            holder.chat_rightL.setVisibility(View.GONE);
            holder.chat_left_msgT.setText(tmpmsg.getMassage());

        }

//        Log.i("msg",mdatas.get(position).getMassage());


    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{//绑定控件
        private TextView chat_left_msgT;
        private TextView chat_right_msgT;
        private LinearLayout chat_rightL, chat_leftL;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_left_msgT  = (TextView)itemView.findViewById(R.id.chat_left_msg);
            chat_right_msgT =(TextView)itemView.findViewById(R.id.chat_right_msg);
            chat_rightL = (LinearLayout)itemView.findViewById(R.id.chat_right);
            chat_leftL = (LinearLayout)itemView.findViewById(R.id.chat_left);
        }

    }
}
