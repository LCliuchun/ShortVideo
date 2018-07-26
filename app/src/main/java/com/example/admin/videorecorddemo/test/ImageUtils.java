package com.example.admin.videorecorddemo.test;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.videorecorddemo.R;

/**
 * Created by lin on 2017/3/21.
 * 操作图片的工具类
 */

public class ImageUtils {
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context) // 指定Context
                .load(url)// 指定图片的URL
                .centerCrop().into(imageView);//指定显示图片的ImageView
    }
}
