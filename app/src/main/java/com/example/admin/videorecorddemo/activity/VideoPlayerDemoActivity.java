package com.example.admin.videorecorddemo.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.admin.videorecorddemo.R;
import com.example.admin.videorecorddemo.adapter.VideoPlayAdapter;
import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

public class VideoPlayerDemoActivity extends BaseVideoActivity implements PLOnPreparedListener {
    private ArrayList<String> mList = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.pl_video_view)
    PLVideoTextureView mPlVideoView;
    private VideoPlayAdapter mAdapter;
    private LinearLayoutManager mLayoutmanager;
    private PagerSnapHelper mSnapHelper;
    private boolean isFrist = true;
    private int position;

    @Override
    protected void initData() {
        initRecycler();
        initList();
        mPlVideoView.setLooping(true);
        mPlVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        mPlVideoView.setOnPreparedListener(this);
        mPlVideoView.setVideoPath(mList.get(0));
        mPlVideoView.start();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initList();
                mSmartRefreshLayout.finishRefresh();
            }
        });
        mSmartRefreshLayout.setEnableLoadmore(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    position = mLayoutmanager.findFirstVisibleItemPosition();
                    mPlVideoView.setVideoPath(mList.get(position));
                    mPlVideoView.start();
                    if (mLayoutmanager.findFirstVisibleItemPosition() == mList.size() - 1) {
                        loadMore();
                    }
                } else {
                    for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                        mRecyclerView.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                    mPlVideoView.pause();
                }
            }
        });
    }

    private void loadMore() {
        mList.add("https://douyindemo.oss-cn-hangzhou.aliyuncs.com/demo/d7be6bc96a158af778ba3b97752e8aea.mp4");
        mList.add("https://douyindemo.oss-cn-hangzhou.aliyuncs.com/demo/20180530025127048_740_nologin.mp4");
        mList.add("https://douyindemo.oss-cn-hangzhou.aliyuncs.com/demo/6dffc6dafd9b69320ef3de5133fac7e5.mp4");
        mList.add("https://douyindemo.oss-cn-hangzhou.aliyuncs.com/demo/WeChatSight712.mp4");
        mAdapter.notifyDataSetChanged();
    }

    private void initList() {
        mList.clear();
        mList.add("https://douyindemo.oss-cn-hangzhou.aliyuncs.com/demo/WeChatSight711.mp4");
        mList.add("https://douyindemo.oss-cn-hangzhou.aliyuncs.com/demo/d7be6bc96a158af778ba3b97752e8aea.mp4");
        mList.add("https://douyindemo.oss-cn-hangzhou.aliyuncs.com/demo/WeChatSight710.mp4");
        mAdapter.notifyDataSetChanged();
    }

    private void initRecycler() {
        mLayoutmanager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutmanager);
        mAdapter = new VideoPlayAdapter(R.layout.item_activity_video_play, mList);
        mRecyclerView.setAdapter(mAdapter);
//        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mSmartRefreshLayout.setDisableContentWhenLoading(true);
        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(mRecyclerView);

    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_video_play;
    }

    @Override
    public void onPrepared(int time) {
        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            mRecyclerView.getChildAt(i).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFrist) {
            mPlVideoView.start();
        }
        isFrist = false;
    }
}
