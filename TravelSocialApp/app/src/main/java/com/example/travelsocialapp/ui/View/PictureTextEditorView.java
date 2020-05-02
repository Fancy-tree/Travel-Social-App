package com.example.travelsocialapp.ui.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.URLUtil;
import android.widget.EditText;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.travelsocialapp.ui.TravelDiaryEditActivity;
import com.example.travelsocialapp.util.KeyBoardUtil;

import java.util.ArrayList;
import java.util.List;


//图文混排的EditText
@SuppressLint("AppCompatCustomView")
public class PictureTextEditorView extends EditText {
    private final String TAG = "PictureTextEditorView";
    private Context mContext;


    private List<String> mContentList;//EditText中所有文字与图片路径（不包含mBitmapTag ）
    public List<String> getmContentList() {
        return mContentList;
    }
    //为了方便存储
    private List<Integer> isBitmap=new ArrayList<Integer>();;//和mContentList一一对应，标记其内容是否为图片路径
    public List<Integer> getIsBitmap() {
        return isBitmap;
    }
    private List<Bitmap> BitmapList = new ArrayList<Bitmap>();//EditText中所有图片本身
    public List<Bitmap> getBitmapList() {
        return BitmapList;
    }



    public static final String mBitmapTag = "☆";
    private String mNewLineTag = "ஐ";

    public PictureTextEditorView(Context context) {
        super(context);
        init(context);
    }

    public PictureTextEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PictureTextEditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        getAllContent();
//        insertData();
    }

//    //初始化插入数据
//    private void insertData() {
//        if (mContentList.size() > 0) {
//            for (String str : mContentList) {
//                if (str.indexOf(mBitmapTag) != -1) {//判断是否是图片地址
//                    String path = str.replace(mBitmapTag, "");//还原地址字符串
//                    Bitmap bitmap = getSmallBitmap(path, 480, 800);
//                    //插入图片
//                    insertBitmap(path, bitmap);
//                } else {
//                    //插入文字
//                    SpannableString ss = new SpannableString(str);
//                    append(ss);
//                }
//            }
//        }
//    }
    //根据传入的参数，刷新视图
    public void refresh(List<String> ContentList,List<Integer> isBitmap,List<Bitmap> BitmapList){
        this.setText("");
        int count=0;//使用图片计数
        if (ContentList.size() > 0) {
            for (int i=0; i<ContentList.size();i++) {
                if(isBitmap.get(i)==0){
                    //是文本
                    String ss = ContentList.get(i);
                    ss = ss.replaceAll(mNewLineTag,"\n");//将换行符特殊符号还原
                    append(ss);

                }else {
                    //是图片
                    Bitmap bitmap = BitmapList.get(count);
                    count++;
                    insertBitmap(ContentList.get(i), bitmap,0);

                }
            }
        }

    }

    //用list的形式获取控件里所有的内容,存入自身属性中
    public void getAllContent() {
        BitmapFactory.Options options = new BitmapFactory.Options();//创建选项对象
        options.inJustDecodeBounds = true;//设置选项：只读取图像文件信息而不载入图像文件
        if (mContentList == null) {
            mContentList = new ArrayList<String>();
        }
//        String content = getText().toString();//不替换 换行符
        String content = getText().toString().replaceAll("\n", mNewLineTag);//换行符替换
//        String content = getText().toString().replaceAll("\n", ""); //去掉所有换行符
        if (content.length() > 0 && content.contains(mBitmapTag)) { //如果有图片
            String[] split = content.split("☆");//在指定位置分割字符
            mContentList.clear();
            isBitmap.clear();
            BitmapList.clear();
            for (String str : split) {
                mContentList.add(str);

                options.outHeight=0;//预设值
                BitmapFactory.decodeFile(str,options);
                if(options.outHeight==0){  //硬核判断，如果字符串不能加载有效图片，则它是文本，不是图片
                    isBitmap.add(0);//记录这个位置存放文本
                }else{
                    Log.i("1","1");
                    isBitmap.add(1);//记录这个位置存放图片
                    Bitmap bitmap = getSmallBitmap(str, 480, 800);
                    BitmapList.add(bitmap);
                }
            }
        } else{
            mContentList.clear(); //每次都是完全更新，而不是叠加
            mContentList.add(content);
            isBitmap.clear();
            isBitmap.add(0);//记录这个位置存放文本，不是图片

        }

    }



    //插入图片
    public void insertBitmap(String path) {
        Bitmap bitmap = getSmallBitmap(path, 480, 800);
        insertBitmap(path, bitmap,1);
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();//创建选项对象
        options.inJustDecodeBounds = true;//设置选项：只读取图像文件信息而不载入图像文件
        BitmapFactory.decodeFile(filePath, options);//读取图像文件信息存入option中

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);//设置缩小比例，例如3，将缩小为3/1

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);//载入图像文件
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();//获取手机屏幕尺寸
        int w_screen = dm.widthPixels;//屏幕像素宽
        int w_width = w_screen/3;
        int b_width = bitmap.getWidth();
        int b_height = bitmap.getHeight();
        int w_height = w_screen * b_height / b_width/3;//凑
//        int w_height = w_width * b_height / b_width;
        bitmap = Bitmap.createScaledBitmap(bitmap, w_width, w_height, false);//创建bitmap对象的时候再次缩放
        return bitmap;
    }

    //计算图片的缩放值
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);//原高度/需求高度=缩放比
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio; //取最小
        }
        return inSampleSize;
    }


     //插入图片
    private SpannableString insertBitmap(String path, Bitmap bitmap,int needNewLine) {
        Editable edit_text = getEditableText(); //Editable可变字符串类型，getEditableText()获取当前EditText内所有文字
        int index = getSelectionStart(); // 获取光标所在位置
        //定义换行符，使图片单独占一行 SpannableString是一种便于实现各种文本效果的类
        SpannableString newLine = new SpannableString("\n");
        if(needNewLine==1)edit_text.insert(index, newLine);//插入图片前换行
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        path = mBitmapTag + path + mBitmapTag; //在图片路径左右插入特殊字符
        SpannableString spannableString = new SpannableString(path);
        // 根据Bitmap对象创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
        // 用ImageSpan对象替换你指定的字符串（希望的图片，开始下标，结束下标，设置）Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括
        spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);//在末尾添加
        } else {
            edit_text.insert(index, spannableString);//在光标位置添加
        }
        if(needNewLine==1)edit_text.insert(index, newLine);//插入图片后换行
        return spannableString;
    }

    float oldY = 0;

    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Edit","Down");
                oldY = event.getY();
                requestFocus();//获得焦点
                break;
            case MotionEvent.ACTION_MOVE:
                float newY = event.getY();
                if (Math.abs(oldY - newY) > 20) {
                    clearFocus();//取消焦点
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }







}
