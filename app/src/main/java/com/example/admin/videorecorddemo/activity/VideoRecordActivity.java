package com.example.admin.videorecorddemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.videorecorddemo.R;
import com.example.admin.videorecorddemo.adapter.FileterAdapter;
import com.example.admin.videorecorddemo.dialog.IOSStyleDialog;
import com.example.admin.videorecorddemo.utils.AppInnerDownLoader;
import com.example.admin.videorecorddemo.utils.DialogUtil;
import com.example.admin.videorecorddemo.utils.GetPathFromUri;
import com.example.admin.videorecorddemo.view.SectionProgressBar;
import com.example.admin.videorecorddemo.view.VideoRecordBtn;
import com.qiniu.pili.droid.shortvideo.PLAudioEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLBuiltinFilter;
import com.qiniu.pili.droid.shortvideo.PLCameraSetting;
import com.qiniu.pili.droid.shortvideo.PLDisplayMode;
import com.qiniu.pili.droid.shortvideo.PLFaceBeautySetting;
import com.qiniu.pili.droid.shortvideo.PLMicrophoneSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordStateListener;
import com.qiniu.pili.droid.shortvideo.PLShortVideoRecorder;
import com.qiniu.pili.droid.shortvideo.PLVideoEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.admin.videorecorddemo.cons.VideoConfig.*;

/**
 * Created by admin on 2018/6/4.
 */

public class VideoRecordActivity extends BaseVideoActivity implements PLRecordStateListener, PLVideoSaveListener {
    @BindView(R.id.sectionProgressBar)
    SectionProgressBar mSectionProgressBar;
    @BindView(R.id.preview)
    GLSurfaceView glSurfaceView;
    @BindView(R.id.record_btn)
    VideoRecordBtn mRecordBtn;
    @BindView(R.id.filter_reycler)
    RecyclerView mFilterReycler;
    @BindView(R.id.background_music)
    View mBackgroiundMusic;
    @BindView(R.id.local)
    View mLocal;
    @BindView(R.id.delete_before)
    View mDeleteBefore;
    @BindView(R.id.concat_video)
    View mConcatVideo;
    private PLShortVideoRecorder mShortVideoRecorder;
    private FileterAdapter mAdapter;
    private List<PLBuiltinFilter> mFilters;
    private PLRecordSetting mRecordSetting;
    private Stack<Long> mDurationRecordStack = new Stack();
    private IOSStyleDialog logOutDialog;
    private IOSStyleDialog finishDialog;

    @Override
    protected void initData() {
        //创建视频录制对象
        mShortVideoRecorder = new PLShortVideoRecorder();
        mShortVideoRecorder.setRecordStateListener(this);
        mConcatVideo.setEnabled(false);
        mDeleteBefore.setEnabled(false);
        initConfig();
        mSectionProgressBar.setFirstPointTime(MIN_TIME);
        mSectionProgressBar.setTotalTime(this, VIDEO_TIME);
        initRecycler();
        logOutDialog = new IOSStyleDialog(this);
        finishDialog = new IOSStyleDialog(this);
        finishDialog.setDialogFun("是否放弃刚拍摄的视频？");
    }

    private void initRecycler() {
        PLBuiltinFilter[] filterList = mShortVideoRecorder.getBuiltinFilterList();
        mFilters = Arrays.asList(filterList);
        mFilterReycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new FileterAdapter(R.layout.item_activity_filter, mFilters);
        mFilterReycler.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRecordBtn.setOnViewPressedListener(onPressedListener);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mShortVideoRecorder.setBuiltinFilter(mFilters.get(position).getName());
            }
        });
        logOutDialog.setOnCommitClickListener(new IOSStyleDialog.OnCommitClickListener() {
            @Override
            public void onCommitClick() {
                mShortVideoRecorder.deleteLastSection();
                logOutDialog.dismiss();
            }
        });
        finishDialog.setOnCommitClickListener(new IOSStyleDialog.OnCommitClickListener() {
            @Override
            public void onCommitClick() {
                finishDialog.dismiss();
                try {
                    mShortVideoRecorder.deleteAllSections();
                } catch (Exception e) {
                    finish();
                }
                finish();
            }
        });
    }


    /**
     * 初始化视频录制配置
     */
    private void initConfig() {
        PLCameraSetting cameraSetting = new PLCameraSetting();
        cameraSetting.setCameraId(CAMERA_SWITCH);
        cameraSetting.setCameraPreviewSizeRatio(RATIO_SIZE);
        cameraSetting.setCameraPreviewSizeLevel(PREVIEWSIZELEVEL);
// 麦克风采集选项
        PLMicrophoneSetting microphoneSetting = new PLMicrophoneSetting();
        microphoneSetting.setPtsOptimizeEnabled(false);

// 视频编码选项
        PLVideoEncodeSetting videoEncodeSetting = new PLVideoEncodeSetting(this);
        videoEncodeSetting.setEncodingSizeLevel(ENCODINGSIZELEVEL); // 1920*1080
        videoEncodeSetting.setEncodingBitrate(BITRATE); // 1000kbps
        videoEncodeSetting.setEncodingFps(FBS);
        videoEncodeSetting.setHWCodecEnabled(true); // true:硬编 false:软编

// 音频编码选项
        PLAudioEncodeSetting audioEncodeSetting = new PLAudioEncodeSetting();
        audioEncodeSetting.setHWCodecEnabled(true);
        audioEncodeSetting.setChannels(1);
// 美颜选项
        PLFaceBeautySetting faceBeautySetting = new PLFaceBeautySetting(1.0f, 0.5f, 0.5f);
// 录制选项
        mRecordSetting = new PLRecordSetting();
        mRecordSetting.setMaxRecordDuration(VIDEO_TIME);
        mRecordSetting.setVideoCacheDir(VIDEO_STORAGE_DIR);
        mRecordSetting.setVideoFilepath(RECORD_FILE_PATH);
        mRecordSetting.setDisplayMode(PLDisplayMode.FULL);
        mShortVideoRecorder.prepare(glSurfaceView, cameraSetting, microphoneSetting,
                videoEncodeSetting, audioEncodeSetting, faceBeautySetting, mRecordSetting);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_video_record;
    }

    @Override
    public void onReady() {
    }

    @Override
    public void onError(int i) {
        Log.e("RECORD_VIDEO", "onError");
    }

    @Override
    public void onDurationTooShort() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoRecordActivity.this, "该视频段太短了", Toast.LENGTH_SHORT).show();
                mSectionProgressBar.removeLastBreakPoint();
            }
        });
    }


    private long mSectionBeginTSMs;

    @Override
    public void onRecordStarted() {
        Log.e("RECORD_VIDEO", "onRecordStarted");
        mSectionBeginTSMs = System.currentTimeMillis();
        mSectionProgressBar.setCurrentState(SectionProgressBar.State.START);
    }

    @Override
    public void onRecordStopped() {
        Log.e("RECORD_VIDEO", "onRecordStopped");
        long totalDurationMs = (System.currentTimeMillis() - mSectionBeginTSMs) + (mDurationRecordStack.isEmpty() ? 0 : mDurationRecordStack.peek());
        mDurationRecordStack.push(totalDurationMs);
        mSectionProgressBar.addBreakPointTime(totalDurationMs);
        mSectionProgressBar.setCurrentState(SectionProgressBar.State.PAUSE);
    }

    @Override
    public void onSectionIncreased(long incDuration, long totalDuration, int sectionCount) {
        onSectionCountChanged(sectionCount, totalDuration);
    }

    /**
     * 回退后的回调
     *
     * @param decDuration   删除掉的时间
     * @param totalDuration 剩余的总时间
     * @param sectionCount  视频的数量
     */
    @Override
    public void onSectionDecreased(long decDuration, long totalDuration, int sectionCount) {
        Log.e("RECORD_VIDEO", "onSectionDecreased");
        mSectionProgressBar.removeLastBreakPoint();
        mDurationRecordStack.pop();
        onSectionCountChanged(sectionCount, totalDuration);
    }

    @Override
    public void onRecordCompleted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoRecordActivity.this, "已达到拍摄总时长", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private VideoRecordBtn.OnViewPressedListener onPressedListener = new VideoRecordBtn.OnViewPressedListener() {
        @Override
        public void onPressed(boolean onPressed) {
            if (onPressed) {
                //按住录制
                mShortVideoRecorder.beginSection();
            } else {
                mShortVideoRecorder.endSection();
            }
        }
    };

    private void onSectionCountChanged(final int count, final long totalTime) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDeleteBefore.setEnabled(count > 0);
                mConcatVideo.setEnabled(totalTime >= (MIN_TIME));
                mLocal.setEnabled(totalTime <= 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShortVideoRecorder.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShortVideoRecorder.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShortVideoRecorder.destroy();
    }

    @OnClick({R.id.delete_before, R.id.concat_video, R.id.filter_video, R.id.background_music, R.id.switch_camera, R.id.local, R.id.back_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.concat_video:
                mShortVideoRecorder.concatSections(this);
                break;
            case R.id.delete_before:
                DialogUtil.showDialogCenter(this, logOutDialog);
                break;
            case R.id.filter_video:
                mFilterReycler.setVisibility(mFilterReycler.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.background_music:
                //https://douyindemo.oss-cn-hangzhou.aliyuncs.com/mp3/Thomas%20Prime%20-%20Sky%20High.mp3
                AppInnerDownLoader.downLoadApk(VideoRecordActivity.this, "https://douyindemo.oss-cn-hangzhou.aliyuncs.com/mp3/Thomas%20Prime%20-%20Sky%20High.mp3", "music");
                mShortVideoRecorder.setMusicFile(Environment.getExternalStorageDirectory() + "/VersionChecker/music.mp3");
                break;
            case R.id.switch_camera:
                mShortVideoRecorder.switchCamera();
                break;
            case R.id.local:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 0);
                break;
            case R.id.back_activity:
                if (mDurationRecordStack.isEmpty()) {
                    finish();
                } else {
                    DialogUtil.showDialogCenter(this, finishDialog);
                }
                break;
        }
    }

    /**
     * 视频合成成功
     *
     * @param filePath
     */
    @Override
    public void onSaveVideoSuccess(String filePath) {
        VideoEditActivity.actionStart(this, filePath);
    }

    /**
     * 视频合成失败
     *
     * @param i
     */
    @Override
    public void onSaveVideoFailed(int i) {

    }

    @Override
    public void onSaveVideoCanceled() {

    }

    /**
     * 视频进度更新
     *
     * @param v
     */
    @Override
    public void onProgressUpdate(float v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String selectedFilepath = GetPathFromUri.getPath(this, data.getData());
            if (selectedFilepath != null && !"".equals(selectedFilepath)) {
                VideoEditActivity.actionStart(this, selectedFilepath);
            }
        } else {
            finish();
        }
    }

}
