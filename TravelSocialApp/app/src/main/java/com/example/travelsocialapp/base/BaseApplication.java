package com.example.travelsocialapp.base;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

//统一管理所有Activity
//暂时没用到
public class BaseApplication extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();
    private static BaseApplication instance;
    private BaseApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //单例模式中获取唯一的MyApplication实例
    public static BaseApplication getInstance() {
        if(null == instance) {
            instance = new BaseApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity)  {
        activityList.add(activity);
    }


    //遍历所有Activity并finish,
    public void exit() {
        for(Activity activity:activityList) {
            activity.finish();
        }
        activityList.clear();
    }


}
