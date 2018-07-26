package com.example.admin.videorecorddemo.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.videorecorddemo.R;
import com.example.admin.videorecorddemo.cons.VideoConfig;
import com.example.admin.videorecorddemo.adapter.FileterAdapter;
import com.example.admin.videorecorddemo.dialog.AudioMixSettingDialog;
import com.example.admin.videorecorddemo.utils.AppInnerDownLoader;
import com.example.admin.videorecorddemo.utils.DialogUtil;
import com.qiniu.pili.droid.shortvideo.PLBuiltinFilter;
import com.qiniu.pili.droid.shortvideo.PLDisplayMode;
import com.qiniu.pili.droid.shortvideo.PLShortVideoEditor;
import com.qiniu.pili.droid.shortvideo.PLShortVideoTrimmer;
import com.qiniu.pili.droid.shortvideo.PLVideoEditSetting;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/6/4.
 * 编辑视频
 */

public class VideoEditActivity extends BaseVideoActivity implements PLVideoSaveListener {
    public static final String FILEPATH = "FILE_PATH";
    @BindView(R.id.preview)
    GLSurfaceView glSurfaceView;
    @BindView(R.id.filter_reycler)
    RecyclerView mFilterReycler;
    @BindView(R.id.parent_relative)
    View mParentRelative;

    private String mFilePath;
    private PLShortVideoEditor mShortVideoEditor;
    private List<PLBuiltinFilter> mFilters;
    private FileterAdapter mAdapter;
    private PLShortVideoTrimmer mShortVideoTrimmer;
    private long mDurationMs;
    private AudioMixSettingDialog mAudioMixSettingDialog;
    private boolean mIsMixAudio = false;
    @Override
    protected void initData() {
        initVideoConfig();
        initRecyclerView();

        //添加水印
//        PLWatermarkSetting mWatermarkSetting = new PLWatermarkSetting();
//        mWatermarkSetting.setResourceId(R.mipmap.ic_launcher);
//        mWatermarkSetting.setPosition(0.01f, 0.75f);
//        mWatermarkSetting.setAlpha(128);
//        mShortVideoEditor.setWatermark(mWatermarkSetting);
        // 开始播放
        mShortVideoEditor.startPlayback();
        // 监听保存状态和结果
        mShortVideoEditor.setVideoSaveListener(this);
        initAudioMixSettingDialog();
    }

    private void initAudioMixSettingDialog() {
        mAudioMixSettingDialog = new AudioMixSettingDialog(this);
        // make dialog create +
        DialogUtil.showDialogCenter(this,mAudioMixSettingDialog);
        mAudioMixSettingDialog.dismiss();
        // make dialog create -
        mAudioMixSettingDialog.setOnAudioVolumeChangedListener(mOnAudioVolumeChangedListener);
    }

    private void initRecyclerView() {
        PLBuiltinFilter[] filterList = mShortVideoEditor.getBuiltinFilterList();
        mFilters = Arrays.asList(filterList);
        mFilterReycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new FileterAdapter(R.layout.item_activity_filter, mFilters);
        mFilterReycler.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mShortVideoEditor.setBuiltinFilter(mFilters.get(position).getName());
            }
        });
        mAudioMixSettingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mParentRelative.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 初始化视频配置
     */
    private void initVideoConfig() {
        mFilePath = getIntent().getStringExtra(FILEPATH);
        PLVideoEditSetting setting = new PLVideoEditSetting();
        // 视频源文件路径
        setting.setSourceFilepath(mFilePath);
        // 编辑后保存的目标文件路径
        setting.setDestFilepath(VideoConfig.RECORD_EDIT_FILE_PATH);
        // 编辑保存后，是否保留源文件
        setting.setKeepOriginFile(false);
        mShortVideoEditor = new PLShortVideoEditor(glSurfaceView, setting);
        mShortVideoEditor.setDisplayMode(PLDisplayMode.FULL);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_video_edit;
    }

    public static void actionStart(Activity activity, String filePath) {
        Intent intent = new Intent(activity, VideoEditActivity.class);
        intent.putExtra(FILEPATH, filePath);
        activity.startActivity(intent);
    }


    @OnClick({R.id.background_music, R.id.filter_video, R.id.clip_video, R.id.up_load,R.id.mul_mix,R.id.back_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_activity:
                finish();
                break;
            case R.id.background_music:
                AppInnerDownLoader.downLoadApk(VideoEditActivity.this, "https://douyindemo.oss-cn-hangzhou.aliyuncs.com/mp3/Thomas%20Prime%20-%20Sky%20High.mp3", "music");
                mShortVideoEditor.setAudioMixFile(Environment.getExternalStorageDirectory() + "/VersionChecker/music.mp3");
                mIsMixAudio = true;
                break;
            case R.id.mul_mix:
                if (mIsMixAudio) {
                    mAudioMixSettingDialog.show();
                    mParentRelative.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, "请先选择混音文件！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.filter_video:
                mFilterReycler.setVisibility(mFilterReycler.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);

                break;
            case R.id.clip_video:
                SelectFaceActivity.actionStart(this,mFilePath,VideoConfig.RECORD_EDIT_FILE_PATH);
//                mFrameListView.setVisibility(mFrameListView.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
                if (mFilterReycler.getVisibility()== View.VISIBLE) {
                    mFilterReycler.setVisibility(View.GONE);
                }
                break;
            case R.id.up_load:
                mShortVideoEditor.save();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShortVideoEditor.cancelSave();
    }

    @Override
    public void onSaveVideoSuccess(String s) {

    }

    @Override
    public void onSaveVideoFailed(int i) {

    }

    @Override
    public void onSaveVideoCanceled() {

    }

    @Override
    public void onProgressUpdate(float v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mShortVideoEditor.startPlayback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mShortVideoEditor.stopPlayback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShortVideoEditor.pausePlayback();
    }

    private AudioMixSettingDialog.OnAudioVolumeChangedListener mOnAudioVolumeChangedListener = new AudioMixSettingDialog.OnAudioVolumeChangedListener() {
        @Override
        public void onAudioVolumeChanged(int fgVolume, int bgVolume) {
            mShortVideoEditor.setAudioMixVolume(fgVolume / 100f, bgVolume / 100f);
        }
    };
}
