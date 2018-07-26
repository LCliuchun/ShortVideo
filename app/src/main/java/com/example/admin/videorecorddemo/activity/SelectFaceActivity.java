package com.example.admin.videorecorddemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.videorecorddemo.R;
import com.example.admin.videorecorddemo.adapter.FaceSelectAdapter;
import com.example.admin.videorecorddemo.bean.FaceBean;
import com.example.admin.videorecorddemo.utils.BitmapUtil;
import com.example.admin.videorecorddemo.view.TitleBar;
import com.qiniu.pili.droid.shortvideo.PLShortVideoTrimmer;
import com.qiniu.pili.droid.shortvideo.PLVideoFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/6/11.
 */

public class SelectFaceActivity extends BaseActivity {

    private static final String INPUTPATH = "INPUTPATH";
    private static final String OUTPUTPATH = "OUTPUTPATH";
    @BindView(R.id.big_face)
    ImageView mBigFace;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    private PLShortVideoTrimmer mShortVideoTrimmer;
    private long mDurationMs;
    private ArrayList<FaceBean> mList = new ArrayList<>();
    private FaceSelectAdapter mAdapter;
    private boolean isFrist = true;
    private int selectPosition = -1;


    @Override
    protected void initData() {
        String inputPath = getIntent().getStringExtra(INPUTPATH);
        String outputPath = getIntent().getStringExtra(OUTPUTPATH);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new FaceSelectAdapter(R.layout.item_activity_select_face, mList);
        mRecyclerView.setAdapter(mAdapter);

        if (mShortVideoTrimmer == null) {
            mShortVideoTrimmer = new PLShortVideoTrimmer(getApplicationContext(), inputPath, outputPath);
            // true 限定为关键帧，false 为所有视频帧
            final boolean keyFrame = true;
            // 获取视频帧总数
            final int SLICE_COUNT = mShortVideoTrimmer.getVideoFrameCount(keyFrame);
            mDurationMs = mShortVideoTrimmer.getSrcDurationMs();

            new AsyncTask<Void, PLVideoFrame, Void>() {
                @Override
                protected Void doInBackground(Void... v) {
                    for (int i = 0; i < SLICE_COUNT; ++i) {
                        PLVideoFrame frame = mShortVideoTrimmer.getVideoFrameByTime((long) ((1.0f * i / SLICE_COUNT) * mDurationMs), keyFrame);
                        publishProgress(frame);
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(PLVideoFrame... values) {
                    super.onProgressUpdate(values);
                    PLVideoFrame frame = values[0];
                    if (frame != null) {
                        if (isFrist) {
                            mAdapter.addData(new FaceBean(true, frame));
                            mBigFace.setImageBitmap(frame.toBitmap());
                            selectPosition = 0;
                            isFrist = false;
                        } else {
                            mAdapter.addData(new FaceBean(false, frame));
                        }
                    }
                }
            }.execute();
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mBigFace.setImageBitmap(mList.get(position).getFrame().toBitmap());
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        selectPosition = i;
                        mList.get(i).setSelect(true);
                    } else {
                        mList.get(i).setSelect(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mTitleBar.setOnLeftButtonClickListener(new TitleBar.OnLeftButtonClickListener() {
            @Override
            public void onLeftButtonClick() {
                finish();
            }
        });
        mTitleBar.setOnRightTextClickListener(new TitleBar.OnRightTextClickListener() {
            @Override
            public void onRightTextClick() {
                String path = BitmapUtil.saveBitmap(SelectFaceActivity.this, mList.get(selectPosition).getFrame().toBitmap());
                Uri localUri = Uri.fromFile(new File(path));
                Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                sendBroadcast(localIntent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_select_face;
    }

    public static void actionStart(Activity activity, String inputPath, String outputPath) {
        Intent intent = new Intent(activity, SelectFaceActivity.class);
        intent.putExtra(INPUTPATH, inputPath);
        intent.putExtra(OUTPUTPATH, outputPath);
        activity.startActivity(intent);
    }
}
