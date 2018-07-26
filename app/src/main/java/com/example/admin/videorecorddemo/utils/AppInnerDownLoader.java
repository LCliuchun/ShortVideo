package com.example.admin.videorecorddemo.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 更新APK
 */

public class AppInnerDownLoader {
    public final static String SD_FOLDER = Environment.getExternalStorageDirectory() + "/VersionChecker/";
    public static AlertDialog sAlertDialog;

    /**
     * 从服务器中下载APK
     */
    @SuppressWarnings("unused")
    public static void downLoadApk(final Activity mContext, final String url, final String appName) {
        try {
            final ProgressDialog pd; // 进度条对话框
            pd = new ProgressDialog(mContext);
            pd.setCancelable(false);// 必须一直下载完，不可取消
            pd.show();
            downloadFile(url, appName, pd);
            //                    sleep(3000);
//            installApk(mContext, file);
            // 结束掉进度条对话框

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从服务器下载最新更新文件
     *
     * @param path 下载路径
     * @param pd
     * @return
     * @throws Exception
     */
    private static void downloadFile(final String path, final String appName, final ProgressDialog pd) {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5000);
                        // 获取到文件的大小
                        pd.setMax(conn.getContentLength());
                        InputStream is = conn.getInputStream();
                        String fileName = SD_FOLDER
                                + appName + ".mp3";
                        File file = new File(fileName);
                        // 目录不存在创建目录
                        if (!file.getParentFile().exists())
                            file.getParentFile().mkdirs();
                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024 * 5];
                        int len;
                        int total = 0;
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            total += len;
                            // 获取当前下载量
                            pd.setProgress(total);
                            pd.setProgressNumberFormat(bytes2kb(total) + "/" + bytes2kb(conn.getContentLength()));
                        }
                        fos.close();
                        bis.close();
                        is.close();
                        pd.dismiss();
                    } catch (Exception e) {

                    }

                }
            }.start();

        }
    }

    /**
     * 获取应用程序版本（versionName）
     *
     * @return 当前应用的版本号
     */

    public static int getLocalVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            //Log.e(TAG, "获取应用程序版本失败，原因：" + e.getMessage());
            return 1;
        }

        return info.versionCode;
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }
}
