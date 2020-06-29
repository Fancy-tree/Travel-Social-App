package com.example.travelsocialapp.ui.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.ui.Adapter.ChatLinearAdapter;
import com.example.travelsocialapp.ui.Adapter.GuideMapContentLinearAdapter;
import com.example.travelsocialapp.ui.Adapter.GuideMapUpMenuLinearAdapter;
import com.example.travelsocialapp.ui.Adapter.StaggeredGridAdapter;
import com.example.travelsocialapp.ui.ChatActivity;

public class GuideMapFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_map,null);
        return view;
    }

    private RecyclerView guide_map_one_day_travel_guide;
    private GuideMapUpMenuLinearAdapter guideMapUpMenuLinearAdapter;
    private RecyclerView guide_map_one_day_travel_content;
    private GuideMapContentLinearAdapter guideMapContentLinearAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity()!=null){
//            精选一日游  上菜单
            LinearLayoutManager ms= new LinearLayoutManager(getActivity());
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
            guide_map_one_day_travel_guide = view.findViewById(R.id.guide_map_one_day_travel_guide);
            guide_map_one_day_travel_guide.setLayoutManager(ms);
            guideMapUpMenuLinearAdapter = new GuideMapUpMenuLinearAdapter(getActivity());
            guide_map_one_day_travel_guide.setAdapter(guideMapUpMenuLinearAdapter);
//            精选一日游 内容
            LinearLayoutManager ms2= new LinearLayoutManager(getActivity());
            ms2.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
            guide_map_one_day_travel_content = view.findViewById(R.id.guide_map_one_day_travel_content);
            guide_map_one_day_travel_content.setLayoutManager(ms2);
            guideMapContentLinearAdapter = new GuideMapContentLinearAdapter(getActivity());
            guide_map_one_day_travel_content.setAdapter(guideMapContentLinearAdapter);

        }else{
            Log.e("GuideMapFragment","getActivity()=null");
        }

    }
}
