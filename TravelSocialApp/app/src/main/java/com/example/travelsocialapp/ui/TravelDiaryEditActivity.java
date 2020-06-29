package com.example.travelsocialapp.ui;

import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.base.BaseActivity;
import com.example.travelsocialapp.base.BaseInternetMessage;
import com.example.travelsocialapp.base.C;
import com.example.travelsocialapp.model.TravelDiary;
import com.example.travelsocialapp.ui.View.PictureTextEditorView;
import com.example.travelsocialapp.util.AppUtil;
import com.example.travelsocialapp.util.IntentUtil;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static java.lang.Thread.sleep;


public class TravelDiaryEditActivity extends BaseActivity implements View.OnClickListener{
    private Uri imgUri;//创建uri对象
    private PictureTextEditorView travel_diary_editE;
    private EditText travel_diary_titleE;
    private ImageView travel_diary_bg_picture;
    private FrameLayout travel_diary_bg_frameLayoutF;
    private Button  travel_diary_change_bg_pictureB,travel_diary_bg_goneB;
    private ScrollView travel_diary_scrollS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary_edit);
        init();
    }

    private void init(){
        travel_diary_editE= (PictureTextEditorView)findViewById(R.id.travel_diary_edit);
        travel_diary_titleE = (EditText)findViewById(R.id.travel_diary_title);
        travel_diary_bg_picture = (ImageView)findViewById(R.id.travel_diary_bg_picture);
        travel_diary_bg_frameLayoutF = (FrameLayout)findViewById(R.id.travel_diary_bg_frameLayout);
        travel_diary_change_bg_pictureB = (Button)findViewById(R.id.travel_diary_change_bg_picture);
        travel_diary_bg_goneB = (Button)findViewById(R.id.travel_diary_bg_gone);
        travel_diary_scrollS = (ScrollView)findViewById(R.id.travel_diary_scroll);
        travel_diary_edit_progressBar = (ProgressBar)findViewById(R.id.travel_diary_edit_progressBar);

        findViewById(R.id.open_photo_album).setOnClickListener(this);
        findViewById(R.id.save_diary).setOnClickListener(this);
        findViewById(R.id.continue_diary).setOnClickListener(this);
        findViewById(R.id.sys_to_topic).setOnClickListener(this);
        findViewById(R.id.hide_keyboard).setOnClickListener(this);
        findViewById(R.id.travel_diary_change_bg_picture).setOnClickListener(this);
        findViewById(R.id.travel_diary_bg_gone).setOnClickListener(this);
        findViewById(R.id.release_diary).setOnClickListener(this);

        //加长EditText，使EditText获得焦点时，滚动条能够自动向下一点，使标题与背景不那么挡文字
//        int SreenHeightpx = AppUtil.getSreenHeight(this);
//        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) travel_diary_editE.getLayoutParams();
//        params.height = SreenHeightpx -AppUtil.dip2px(this,500); //根据屏幕高度设置内容编辑区域 接收单位为px
//        travel_diary_editE.setLayoutParams(params);


//测试用
//        travel_diary_editE.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                Log.i("EditActivity", travel_diary_editE.getmContentList().toString());
////                Log.i("EditActivity", travel_diary_editE.getIsBitmap().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//
//
//        });


    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.open_photo_album){ //打开相册
            openPhotoAlbum(v,101);
        }else if(v.getId()==R.id.save_diary){//保存草稿
            saveRecentDiary();
        }else if(v.getId()==R.id.continue_diary){//读取草稿
            LoadRecentDiary();
        }else if(v.getId()==R.id.sys_to_topic){//同步话题

        }else if(v.getId()==R.id.hide_keyboard){ //隐藏键盘
            hideInput(); //当用户点击时隐藏软键盘
        }else if(v.getId()==R.id.travel_diary_change_bg_picture){//更换背景
            openPhotoAlbum(v,102);
        }else if(v.getId()==R.id.travel_diary_bg_gone){//隐藏&显示
            changeVisibility();
        }else if(v.getId()==R.id.release_diary){//发布
//            AppUtil.setReleaseDiaryJsonString(getJsonStrForIntent());
//            saveRecentDiary();
            if(travel_diary_titleE.getText().toString().equals("") ){
                showToast("标题为空");
            }else if(tmpbgbitmap==null){
                showToast("背景为空");
            } else{
                hideInput(); //隐藏软键盘
                TravelDiaryEditActivity.ReleaseDiary task = new TravelDiaryEditActivity.ReleaseDiary();
                task.execute(C.intentReleaseDiaryUrl);
            }
//            Intent intent =  new Intent(TravelDiaryEditActivity.this,TravelDiaryActivity.class);
//            setResult(1, intent);
        }
    }

    //隐藏标题功能，便于编辑正文
    int visibilityflag = 1;
    public void changeVisibility(){
        visibilityflag = ~visibilityflag; //取反
        if(visibilityflag==1){
            travel_diary_bg_picture.setVisibility(View.VISIBLE);
            travel_diary_titleE.setVisibility(View.VISIBLE);
            travel_diary_change_bg_pictureB.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) travel_diary_bg_frameLayoutF.getLayoutParams();
            params.height = AppUtil.dip2px(this,160);//强行设置，如果高度设计变化，这里也要改   参数单位不同需要转换
            travel_diary_bg_frameLayoutF.setLayoutParams(params);
            travel_diary_bg_goneB.setText("隐藏");

        }else {
            travel_diary_bg_picture.setVisibility(View.GONE);
            travel_diary_titleE.setVisibility(View.GONE);
            travel_diary_change_bg_pictureB.setVisibility(View.GONE);
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) travel_diary_bg_frameLayoutF.getLayoutParams();
            params.height =AppUtil.dip2px(this,30);;//强行设置，如果高度设计变化，这里也要改   参数单位不同需要转换
            travel_diary_bg_frameLayoutF.setLayoutParams(params);
            travel_diary_bg_goneB.setText("显示");

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Activity","Down");
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                hideInput(); //当用户点击时隐藏软键盘
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

//    发布日志 获得Json信息
    public String getJsonStrForIntent(){
        //获得当前组件框中的内容
        travel_diary_editE.getAllContent();
//        Log.i("EditActivity", travel_diary_editE.getmContentList().toString());
//        Log.i("EditActivity", travel_diary_editE.getIsBitmap().toString());
        List<String> ContentList = travel_diary_editE.getmContentList();
        List<Bitmap> BitmapList = travel_diary_editE.getBitmapList();
        List<Integer> isBitmap= travel_diary_editE.getIsBitmap();
        TravelDiary travelDiary =
                new TravelDiary(TravelDiaryEditActivity.this,ContentList,BitmapList,isBitmap,travel_diary_titleE.getText().toString(),tmpbgbitmap);
//        travel_diary_bg_picture.setDrawingCacheEnabled(false);//防止travel_diary_bg_picture.getDrawingCache()返回相同图片

        Log.i("2",travelDiary.getDiaryJsonStringForIntent(this));

        return travelDiary.getDiaryJsonStringForIntent(this);
    }

    //异步发布日志
    //参数：url地址
    ProgressBar travel_diary_edit_progressBar;
    BaseInternetMessage baseInternetMessage = new BaseInternetMessage();//自己定义的用于通信格式转换的函数
    @SuppressLint("StaticFieldLeak")
    class ReleaseDiary extends AsyncTask<String,Integer,Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            for(int i=0;i<10;i++){
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    Log.e("Sleep","wrong");
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            String s = getJsonStrForIntent();
//            String s = AppUtil.getReleaseDiaryJsonString();

            for(int i=10;i<30;i++){
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    Log.e("Sleep","wrong");
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            Log.i("Client_say",s);
            String s2 = IntentUtil.sendPost(params[0],s);
            Log.i("Sever_responce",s2);


//            将收到的json字符串用于完善BaseInternetMessage对象信息
            try {
                baseInternetMessage = BaseInternetMessage.getInternetMassage(s2);
            } catch (JSONException e) {
                Log.i("BaseInternetMessage","getInternetMassage JSONException");
                e.printStackTrace();
                return  -1;
            }
//            Log.i("baseInternetMessage",baseInternetMessage.getCode());
            if(baseInternetMessage.getCode().equals(C.CODE_ReleaseDiarySuccess)){//发布成功代码
                // 模拟通知进度条
                for(int i=30;i<100;i++){
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        Log.e("Sleep","wrong");
                        e.printStackTrace();
                    }
                    publishProgress(i);
                }
                publishProgress(100);
                return 1;
            }else return  0;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            travel_diary_edit_progressBar.setVisibility(View.VISIBLE);
            travel_diary_edit_progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) { //doInBackground完成
            super.onPostExecute(integer);
            if(integer==1){
                showToast(baseInternetMessage.getMessage());
                Intent intent =  new Intent(TravelDiaryEditActivity.this,TravelDiary.class);
                setResult(1, intent);
                finish();
            }
            else{
                if(integer==-1){
//                    网络错误 或 服务器返回信息错误
                    showToast("网络错误");
                }else{
                    showToast(baseInternetMessage.getMessage());
                }
            }
            travel_diary_edit_progressBar.setVisibility(View.GONE);
        }
    }

    //将当前内容存储到sd卡
    public void saveRecentDiary(){
        //获得当前组件框中的内容
        travel_diary_editE.getAllContent();
//        Log.i("EditActivity", travel_diary_editE.getmContentList().toString());
//        Log.i("EditActivity", travel_diary_editE.getIsBitmap().toString());
        List<String> ContentList = travel_diary_editE.getmContentList();
        List<Bitmap> BitmapList = travel_diary_editE.getBitmapList();
        List<Integer> isBitmap= travel_diary_editE.getIsBitmap();
        //创建一个travelDiary对象，将组件中的内容按一定格式交给它
//        travel_diary_bg_picture.setDrawingCacheEnabled(true);//防止travel_diary_bg_picture.getDrawingCache()返回null
        if(tmpbgbitmap==null)istmpbgbitmapnull=1;
        TravelDiary travelDiary =
                new TravelDiary(TravelDiaryEditActivity.this,ContentList,BitmapList,isBitmap,travel_diary_titleE.getText().toString(),tmpbgbitmap);
//        travel_diary_bg_picture.setDrawingCacheEnabled(false);//防止travel_diary_bg_picture.getDrawingCache()返回相同图片
        Log.i("1",travelDiary.getDiaryJsonString());
//       调用方法，将自身持有的信息存入sd卡
        if(travelDiary.saveRecentDiaryToSDCard()==0){
            showToast("保存失败");
        }else{
            showToast("保存成功");
        }
    }

    //将sd卡里的内容加载回来
    public void  LoadRecentDiary(){
//        创建一个空白的travelDiary对象
        TravelDiary travelDiary = new TravelDiary(TravelDiaryEditActivity.this);
//        从sd卡中读取相应的值
        travelDiary.getRecentDiaryFromSDCard();
        Log.i("2",travelDiary.getDiaryJsonString());
//        将内容以一定格式交给组件，进行刷新组件
        travel_diary_editE.refresh(travelDiary.getmContentList(),travelDiary.getMisBitmap(),travelDiary.getmBitmapList());
        travel_diary_titleE.setText(travelDiary.getMtitle());
        travel_diary_bg_picture.setImageBitmap(travelDiary.getMbgimg());
        if(istmpbgbitmapnull==1){  //修复保存时没有设置背景图，加载时显示白板bug
            travel_diary_bg_picture.setBackgroundColor(getResources().getColor(R.color.undefine_picture_gray));
            istmpbgbitmapnull=0;

        }

    }





// 打开相册，取出图片，裁剪图片，显示出来---------------------------
    //唤出相册
    public void openPhotoAlbum(View v,int requsetCode){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);//动作设为选取内容
        it.setType("image/*");//设置选取的媒体类型为:所有类型的图片
        startActivityForResult(it,requsetCode);
    }

    //处理Intent返回结果
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == 101){
            //相册图片路径不同版本有不同取法
            // 判断手机系统版本号
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4及以上系统使用这个方法处理图片
                travel_diary_editE.insertBitmap(handleImageOnKitKat(data));
            }
            else {
                // 4.4以下系统使用这个方法处理图片
                travel_diary_editE.insertBitmap(handleImageBeforeKitKat(data));

            }

        }else if(resultCode == Activity.RESULT_OK && requestCode == 102){

            //相册图片路径不同版本有不同取法
            // 判断手机系统版本号
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4及以上系统使用这个方法处理图片
//                displayImage(handleImageOnKitKat(data));
                // 裁剪图片
                Uri imagePathUri = Uri.fromFile(new File(handleImageOnKitKat(data)));
                photoClip(imagePathUri);
            }
            else {
                // 4.4以下系统使用这个方法处理图片
//                displayImage(handleImageBeforeKitKat(data));
                // 裁剪图片
                Uri imagePathUri = Uri.fromFile(new File(handleImageBeforeKitKat(data)));
                photoClip(imagePathUri);
            }

        }else if(requestCode == 1 ){
            //在这里获得了剪裁后的Bitmap对象，可以用于上传

//          tmpbgbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), tmpClipImgPath);
//            显示并压缩并暂存图片
            displayImage(tmpClipImgPath);


        }else{
            showToast("没选择照片");

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);

        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath; // 返回图片路径
    }

    private String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    Bitmap tmpbgbitmap;//暂存bgimg，便于其他函数使用（其实是强行修正某bug）
    int istmpbgbitmapnull=0;//tmpbgbitmap是否为空
    //    输入图片路径，压缩并暂存图片,在imgview上展示图片
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            int iw,ih,vw,vh;
            BitmapFactory.Options option = new BitmapFactory.Options();//创建选项对象
            option.inJustDecodeBounds = true; //设置选项：只读取图像文件信息而不载入图像文件
            BitmapFactory.decodeFile(imagePath,option);//读取图像文件信息存入option中

            iw = option.outWidth;//图像宽度
            ih = option.outHeight;//图像高度
            vw = travel_diary_bg_picture.getWidth();//获取ImageView宽度
            vh = travel_diary_bg_picture.getHeight();//获取ImageView高度

//            int scaleFactor  = Math.min(iw/vw,ih/vh);
            int scaleFactor = 4;
            option.inJustDecodeBounds = false;//关闭设置选项
            option.inSampleSize = scaleFactor;//设置缩小比例，例如3，将缩小为3/1

            tmpbgbitmap = BitmapFactory.decodeFile(imagePath,option);
            travel_diary_bg_picture.setImageBitmap(tmpbgbitmap);

        }
        else {
            showToast("failed to get image");
        }
    }

//    裁剪图片(使用android自带裁剪工具)
    String tmpClipImgPath;
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例,0:0自由比例
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        intent.putExtra("scale", true);
        // outputX outputY 是裁剪图片宽高 不设置即为原始值
//        intent.putExtra("outputX", 400);
//        intent.putExtra("outputY", 300);
        //设置了true的话直接返回bitmap，可能会很占内存
        intent.putExtra("return-data", false);
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //设置输出的地址
        String imagePath  =getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() +"/traveldiary/savetmpClip.jpg";
        Uri uriout = Uri.fromFile(new File(imagePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriout);
        tmpClipImgPath = imagePath;
        //不启用人脸识别
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 1);
    }




}


//通过相册获得图片，展示在imageview
//public class TravelDiaryEditActivity extends BaseActivity implements View.OnClickListener{
//    Uri imgUri;//创建uri对象
//    ImageView imv;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_travel_diary_edit);
//        imv = (ImageView)findViewById(R.id.imageView);
//        findViewById(R.id.button).setOnClickListener(this);
//    }
//
//    //唤出相册
//    public void onGet(View v){
//        Intent it = new Intent(Intent.ACTION_GET_CONTENT);//动作设为选取内容
//        it.setType("image/*");//设置选取的媒体类型为:所有类型的图片
//        startActivityForResult(it,101);
//
//    }
//
//    //处理Intent返回结果（将刚拍的照片显示到ImageView）
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == Activity.RESULT_OK && requestCode == 101){
//
//            //相册图片路径不同版本有不同取法
//            // 判断手机系统版本号
//            if (Build.VERSION.SDK_INT >= 19) {
//                // 4.4及以上系统使用这个方法处理图片
//                handleImageOnKitKat(data);
//            }
//            else {
//                // 4.4以下系统使用这个方法处理图片
//                handleImageBeforeKitKat(data);
//            }
//
//        }else{
//            showToast("没拍到照片");
//
//        }
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void handleImageOnKitKat(Intent data) {
//        String imagePath = null;
//        Uri uri = data.getData();
//        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
//
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            // 如果是document类型的Uri，则通过document id处理
//            String docId = DocumentsContract.getDocumentId(uri);
//            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                String id = docId.split(":")[1]; // 解析出数字格式的id
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            }
//            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                imagePath = getImagePath(contentUri, null);
//            }
//        }
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是content类型的Uri，则使用普通方式处理
//            imagePath = getImagePath(uri, null);
//        }
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是file类型的Uri，直接获取图片路径即可
//            imagePath = uri.getPath();
//        }
//        displayImage(imagePath); // 根据图片路径显示图片
//    }
//
//    private void handleImageBeforeKitKat(Intent data) {
//        Uri uri = data.getData();
//        String imagePath = getImagePath(uri, null);
//        displayImage(imagePath);
//    }
//
//
//    private String getImagePath(Uri uri, String selection) {
//        String path = null;
//        // 通过Uri和selection来获取真实的图片路径
//        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//
//    private void displayImage(String imagePath) {
//        if (imagePath != null) {
//            int iw,ih,vw,vh;
//            BitmapFactory.Options option = new BitmapFactory.Options();//创建选项对象
//            option.inJustDecodeBounds = true; //设置选项：只读取图像文件信息而不载入图像文件
//            BitmapFactory.decodeFile(imagePath,option);//读取图像文件信息存入option中
//
//            iw = option.outWidth;//图像宽度
//            ih = option.outHeight;//图像高度
//            vw = imv.getWidth();//获取ImageView宽度
//            vh = imv.getHeight();//获取ImageView高度
//
//            int scaleFactor  = Math.min(iw/vw,ih/vh);
//            option.inJustDecodeBounds = false;//关闭设置选项
//            option.inSampleSize = scaleFactor;//设置缩小比例，例如3，将缩小为3/1
//
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath,option);
//            imv.setImageBitmap(bitmap);
//        }
//        else {
//            showToast("failed to get image");
//        }
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        if(v.getId()==R.id.button){
//            onGet(v);
//        }
//    }
//}


//使用相机拍照，存储，显示图片
//public class TravelDiaryEditActivity extends BaseActivity implements View.OnClickListener{
//    Uri imgUri;//创建uri对象
//    ImageView imv;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_travel_diary_edit);
//        imv = (ImageView)findViewById(R.id.imageView);
//        findViewById(R.id.button).setOnClickListener(this);
//    }
//
//    //唤出相机
//    public void onGet(View v){
//        ///storage/emulated/0/Android/data/com.example.travelsocialapp/files/Pictures/1586606242418.jpg
//        String path = TravelDiaryEditActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();//$rootDir/Andorid/data/包名/picture
//        String fname = System.currentTimeMillis() + ".jpg";//利用当前时间组合出不重复的文件名
//        imgUri = Uri.parse("file://"+path+"/"+fname);//定义uri对象
////        Log.i("uri",imgUri.toString());
//        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        it.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);//传入目录，它会帮你存到里面去
//        startActivityForResult(it,100);
//
//    }
//
//    //处理Intent返回结果（将刚拍的照片显示到ImageView）
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == Activity.RESULT_OK && requestCode == 100){
//// //           Bundle extras = data.getExtras(); //将Intent的附加数据转为Bundle对象
//// //          Bitmap bmp =(Bitmap) extras.get("data");//将intent对象传来的bitmap格式数据取出
////            Bitmap bmp = BitmapFactory.decodeFile(imgUri.getPath());//读取指定路径文件，并转换为bitmap对象
////            ImageView imv = (ImageView)findViewById(R.id.imageView);
////            imv.setImageBitmap(bmp);
//            showImg();
//        }else{
//            showToast("没拍到照片");
//
//        }
//    }
//
//    //小尺寸从sd卡载入图片，避免储存溢出
//    void showImg(){
//        int iw,ih,vw,vh;
//        BitmapFactory.Options option = new BitmapFactory.Options();//创建选项对象
//        option.inJustDecodeBounds = true; //设置选项：只读取图像文件信息而不载入图像文件
//        BitmapFactory.decodeFile(imgUri.getPath(),option);//读取图像文件信息存入option中
//        iw = option.outWidth;//图像宽度
//        ih = option.outHeight;//图像高度
//        vw = imv.getWidth();//获取ImageView宽度
//        vh = imv.getHeight();//获取ImageView高度
//
//        int scaleFactor  = Math.min(iw/vw,ih/vh);
//        option.inJustDecodeBounds = false;//关闭设置选项
//        option.inSampleSize = scaleFactor;//设置缩小比例，例如3，将缩小为3/1
//
//        Bitmap bmp = BitmapFactory.decodeFile(imgUri.getPath(),option);//真正载入文件
//        imv.setImageBitmap(bmp);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v.getId()==R.id.button){
//            onGet(v);
//        }
//    }
//}

