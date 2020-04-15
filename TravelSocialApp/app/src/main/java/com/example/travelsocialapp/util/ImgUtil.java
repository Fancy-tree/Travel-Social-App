package com.example.travelsocialapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImgUtil {

    //没有用到
    //将bitmap图片 转化为string
    public static String bitmapImagetoString(Bitmap bitmap){
        //ByteArrayOutputStream字节数组输出流...字节数组输出流在内存中创建一个字节数组缓冲区，所有发送到输出流的数据保存在该字节数组缓冲区中
        ByteArrayOutputStream stream = new ByteArrayOutputStream();//32字节（默认大小）的缓冲区
        //压缩图片，压缩的是存储大小，即你放到disk上的大小，内存中bitmap大小还是不变（处理图片格式，质量，流）
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] b = stream.toByteArray();//创建一个新分配的字节数组。数组的大小和当前输出流的大小，内容是当前输出流的拷贝。
        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    //暂时没有用到
    //将String 转化为 bitmap图片
    public static Bitmap stringToBitmapImage(String s) {
        try {
            Bitmap bitmap = null;
            byte[] b = Base64.decode(s, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }
}
