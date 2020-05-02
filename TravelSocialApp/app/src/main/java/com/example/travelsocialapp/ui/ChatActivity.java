package com.example.travelsocialapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.model.ChatMessage;
import com.example.travelsocialapp.ui.Adapter.ChatLinearAdapter;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText chat_msgE;
    private ScrollView chat_scrollS;
    private RecyclerView recycleViewR;
    private ChatLinearAdapter chatLinearAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chat_msgE = (EditText)findViewById(R.id.chat_msg);
//        chat_scrollS = (ScrollView)findViewById(R.id.chat_scroll);
        findViewById(R.id.chat_send_msg).setOnClickListener(this);

        recycleViewR = (RecyclerView)findViewById(R.id.recycler_view_chat);
        recycleViewR.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        chatLinearAdapter = new ChatLinearAdapter(ChatActivity.this);
        recycleViewR.setAdapter(chatLinearAdapter);

//        chatLinearAdapter.addDataToAdapter(new ChatMessage(0,"hi"));
//        chatLinearAdapter.addDataToAdapter(new ChatMessage(1,"la"));

    }

    @Override
    public void onClick(View v) {
        int n = (int)(Math.random()*2);//1-2
        if(v.getId()==R.id.chat_send_msg){
            chatLinearAdapter.addDataToAdapter(new ChatMessage(n-1,chat_msgE.getText().toString()));
            chatLinearAdapter.notifyDataSetChanged();//刷新视图
//            chat_scrollS.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部，其实并不能，有一小段距离的bug


        }

    }
}
