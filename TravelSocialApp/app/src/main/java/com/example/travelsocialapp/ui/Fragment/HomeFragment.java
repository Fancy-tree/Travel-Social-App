package com.example.travelsocialapp.ui.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.ui.Adapter.StaggeredGridAdapter;
import com.example.travelsocialapp.ui.View.SameDirectionScrollView;
import com.example.travelsocialapp.util.AppUtil;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        return view;
    }

    private RecyclerView mtravel_diary_showR;
    private ScrollView home_scrollS;
    private StaggeredGridAdapter staggeredGridAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staggeredGridAdapter=new StaggeredGridAdapter(getActivity());
        init(view);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void init(View view){
        home_scrollS = (ScrollView)view.findViewById(R.id.home_scroll);
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
            //TODO
            mtravel_diary_showR.setAdapter(staggeredGridAdapter);

        }

        //全局布局状态改变或者视图树中视图的*可见性*发生变化时会进行调用,不然获取的值都是0
        ViewTreeObserver viewTreeObserver = mtravel_diary_showR.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout() {
                if(canreflush==1)reflushshow(); //如果不设置canreflush，它会随时刷新，间隔很短，拖慢速度
            }
        });




    }

    private int canreflush=1;
//    刷新旅行日志栏,根据内容个数，改变瀑布流高度
    private void reflushshow(){
        staggeredGridAdapter.notifyDataSetChanged();

        //根据内容个数改变瀑布流高度，使得所有内容都能够显示在内
        int itemCount = staggeredGridAdapter.getItemCount();
        if (itemCount >0){
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mtravel_diary_showR.getLayoutParams();
            params.height = staggeredGridAdapter.getOneItemHeight()*(itemCount/2); //粗略计算总高度// 接收单位为px
            Log.i("now height",String.valueOf(params.height));
            if(params.height!=0){ //我不知道组件何时能获取到非0值，所以只能刷新到有效值为止
                mtravel_diary_showR.setLayoutParams(params);
                canreflush=0;
            }

        }

        staggeredGridAdapter.setOneItemHeight(0);//下一次重新计算总高度
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
