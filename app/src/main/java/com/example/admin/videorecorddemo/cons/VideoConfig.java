package com.example.admin.videorecorddemo.cons;

import android.os.Environment;

import com.qiniu.pili.droid.shortvideo.PLCameraSetting;
import com.qiniu.pili.droid.shortvideo.PLVideoEncodeSetting;

/**
 * Created by admin on 2018/6/4.
 */

public class VideoConfig {

    /**************                          录制视频配置                                      ***********/
    public static final String VIDEO_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/ShortVideo/";      //文件存储的路径
    public static final String RECORD_FILE_PATH = VIDEO_STORAGE_DIR + "record.mp4";             //视频合成以后文件的路径
    public static final String RECORD_EDIT_FILE_PATH = VIDEO_STORAGE_DIR + "record_edit.mp4";             //视频合成以后文件的路径

    public static final PLCameraSetting.CAMERA_FACING_ID CAMERA_SWITCH = PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT;//前后摄像头
    public static final PLCameraSetting.CAMERA_PREVIEW_SIZE_RATIO RATIO_SIZE = PLCameraSetting.CAMERA_PREVIEW_SIZE_RATIO.RATIO_16_9;//视频文件比例
    public static final PLCameraSetting.CAMERA_PREVIEW_SIZE_LEVEL PREVIEWSIZELEVEL
            = PLCameraSetting.CAMERA_PREVIEW_SIZE_LEVEL.PREVIEW_SIZE_LEVEL_480P;     //预览的像素
    public static final PLVideoEncodeSetting.VIDEO_ENCODING_SIZE_LEVEL ENCODINGSIZELEVEL
            = PLVideoEncodeSetting.VIDEO_ENCODING_SIZE_LEVEL.VIDEO_ENCODING_SIZE_LEVEL_480P_3;  //录屏的像素

    public static final int BITRATE = 1000 * 1024;      //编码码率
    public static final int FBS = 30;               //帧率
    public static final int VIDEO_TIME = 30 * 1000;     //视频最大长度
    public static final int MIN_TIME = 5 * 1000;     //视频最小长度

    /**************                          录制视频配置                                      ***********/


}
