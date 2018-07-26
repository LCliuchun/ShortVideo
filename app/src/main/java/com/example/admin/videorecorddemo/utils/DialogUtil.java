package com.example.admin.videorecorddemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * Created by ASUS on 2017/9/1.
=======
/**
 * Created by xianglei on 2018/3/19.
>>>>>>> origin/master
 */

public class DialogUtil {
    public static void showDialogCenter(Context context,Dialog dialog) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);      //获取应用程序显示区域描述大小和密度的度量值
        dialog.show();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = dm.widthPixels;
        attributes.height = dm.heightPixels;
        attributes.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(attributes);
    }
}
