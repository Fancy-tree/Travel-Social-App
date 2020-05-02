package com.example.travelsocialapp.ui.View;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;

//没用上
public class SameDirectionScrollView extends ScrollView {

    public SameDirectionScrollView(Context context) {
        super(context);
    }

    public SameDirectionScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SameDirectionScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SameDirectionScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    //拦截住Move事件，防止子view滚动
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
//                if (){
//                    intercepted = true;
//                } else {
                    intercepted = false;
//                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }


        return intercepted;
//        return super.onInterceptTouchEvent(ev);
    }
}
