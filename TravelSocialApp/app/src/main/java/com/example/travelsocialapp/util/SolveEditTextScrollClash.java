package com.example.travelsocialapp.util;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

//edittext和ScrollView冲突问题
//暂时没有用到
public class SolveEditTextScrollClash implements View.OnTouchListener {

    private EditText editText;

    public SolveEditTextScrollClash(EditText editText) {
        this.editText = editText;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //触摸的是EditText而且当前EditText能够滚动则将事件交给EditText处理。否则将事件交由其父类处理
        if ((view.getId() == editText.getId() && canVerticalScroll(editText))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);//父布局禁用拦截事件功能
            if (event.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向能否够滚动
     * @param editText  须要推断的EditText
     * @return  true：能够滚动   false：不能够滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }
}