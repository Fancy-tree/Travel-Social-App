package com.example.travelsocialapp.ui.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;


//自定义滚动条，实现监听
public class MyScrollView extends ScrollView  {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    MyScrollViewListener myScrollViewListener;
    boolean isTop = false;
    boolean isBottom = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isTop = false;
                    break;
                case 1:
                    isBottom = false;
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (myScrollViewListener != null) {
            if (getScrollY() == 0) {
                if (!isTop) {
                    isTop = true;
                    mHandler.sendEmptyMessageDelayed(0, 200);
                    myScrollViewListener.onTop();
                }
            } else {
                View contentView = getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() == (getScrollY() + getHeight())) {
                    if (!isBottom) {
                        isBottom = true;
                        mHandler.sendEmptyMessageDelayed(1, 200);
                        myScrollViewListener.onBottom();
                    }

                }
            }
        }

    }

    /**
     * 自定义滑动事件的监听接口
     */
    public interface MyScrollViewListener {

        void onTop(); // 滑动到顶部


        void onBottom();// 滑动到底部
    }

    public void setOnScrollViewListener(MyScrollViewListener myScrollViewListener) {
        this.myScrollViewListener = myScrollViewListener;
    }


    private int downX;
    private int downY;
    private int mTouchSlop;

//    @Override
//    public boolean isGetTop() {
//        if (getScrollY() <= 0)
//            return true;
//        else
//            return false;
//    }
//
//    @Override
//    public boolean isGetBottom() {
//        if (getChildCount() == 0) {
//            return true;
//        }
//        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
//            return true;
//        else
//            return false;
//    }

//    惯性滑动
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }


}
