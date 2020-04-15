package com.example.travelsocialapp.ui.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.ui.Adapter.StaggeredGridAdapter;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        return view;
    }

    private RecyclerView mtravel_diary_showR;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtravel_diary_showR = view.findViewById(R.id.travel_diary_show);
        mtravel_diary_showR.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));//（列数/行数，竖直/水平）
        if(getActivity()!=null){
            //TODO
            mtravel_diary_showR.setAdapter(new StaggeredGridAdapter(getActivity()));

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
    public void onDetach() { //当与activity断开联系
        super.onDetach();
    }

    @Override
    public void onDestroy() { //当frament销毁
        super.onDestroy();
        //取消异步任务
    }
}
