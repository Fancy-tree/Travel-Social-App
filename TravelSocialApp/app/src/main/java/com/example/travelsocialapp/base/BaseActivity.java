package com.example.travelsocialapp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.util.AppUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        BaseApplication.addActivity(this);
    }
//    showToast
    public void showToast(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    //    隐藏软键盘
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

        //初始化并弹出等待上传对话框,没法关闭所以不用
        public void showWaitDialog(String message){
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_wait_update,null,false);
            final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
            TextView messageTV = view.findViewById(R.id.dialog_message);
            messageTV.setText(message);
//            Button btn_cancel_high_opion = view.findViewById(R.id.btn_cancel_high_opion);
//            Button btn_agree_high_opion = view.findViewById(R.id.btn_agree_high_opion);
//
//            btn_cancel_high_opion.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    SharedPreferencesUnitls.setParam(getApplicationContext(),"HighOpinion","false");
//                    //... To-do
//                    dialog.dismiss();
//                }
//            });
//
//            btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //... To-do
//                    dialog.dismiss();
//                }
//            });

            dialog.show();
            //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
            dialog.getWindow().setLayout((AppUtil.getSreenWidth(this)/4*3), LinearLayout.LayoutParams.WRAP_CONTENT);
        }



    // 设置屏幕背景变暗
    public void setScreenBgDarken() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
    }
//    设置屏幕背景变亮
    public void setScreenBgLight() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        lp.dimAmount = 1.0f;
        getWindow().setAttributes(lp);
    }


}
