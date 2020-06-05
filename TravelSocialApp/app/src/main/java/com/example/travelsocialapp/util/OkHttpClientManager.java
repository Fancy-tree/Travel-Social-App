package com.example.travelsocialapp.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

//没有用到
//使用OkHttpClient 第三方库
public class OkHttpClientManager {
    private static final int TIMEOUT = 10;

    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
//        mOkHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
//        mOkHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
//        mOkHttpClient.setWriteTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

}
