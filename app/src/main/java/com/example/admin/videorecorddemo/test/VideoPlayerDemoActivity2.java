package com.example.admin.videorecorddemo.test;

import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.admin.videorecorddemo.R;
import com.example.admin.videorecorddemo.activity.BaseVideoActivity;
import com.pili.pldroid.player.PLOnPreparedListener;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class VideoPlayerDemoActivity2 extends BaseVideoActivity  {
    private ArrayList<DouyinBean.DataBean> mList = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    private VideoPlayAdapter mAdapter;
    private LinearLayoutManager mLayoutmanager;
    private PagerSnapHelper mSnapHelper;
    private boolean isFrist = true;
    private int page = 0;
    private int mDrift;

    private int position;
    HttpPostRequest mHttpPostRequest = new HttpPostRequest();

    @Override
    protected void initData() {
        initRecycler();
        initList();
    }

    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
//            PLVideoTextureView plVideoTextureView = view.findViewById(R.id.pl_video_view);
//            plVideoTextureView.start();
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            if (mDrift >= 0){
                releaseVideo(0);
            }else {
                releaseVideo(1);
            }
        }
    };

    private void releaseVideo(int index) {
        try {
            VideoView plVideoTextureView = mRecyclerView.getChildAt(index).findViewById(R.id.pl_video_view);
            ImageView imageView = mRecyclerView.getChildAt(index).findViewById(R.id.image_view);
            imageView.animate().alpha(1f).start();;
            plVideoTextureView.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);

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
                    if (mRecyclerView.getChildCount() == 1) {
                        startPlay();
                    }
                    if (mLayoutmanager.findFirstVisibleItemPosition() == mList.size() - 1) {
                        loadMore();
                    }
                } else {
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDrift = dy;
            }
        });
    }

    private void startPlay() {
        try {
            VideoView plVideoTextureView = mRecyclerView.getChildAt(0).findViewById(R.id.pl_video_view);
            plVideoTextureView.start();
            plVideoTextureView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                            mediaPlayer.setLooping(true);
                            if (i== MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                                ImageView imageView = mRecyclerView.getChildAt(0).findViewById(R.id.image_view);
                                imageView.setVisibility(View.GONE);
                            }
                            return true;
                        }
                    });
                }
            });
        } catch (Exception e) {
        }
    }

    private void loadMore() {
        String url = "http://39.105.77.233:8080/mitanapi/index/hotList";        //公司url不方便透露,请见谅
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("page", page + "");
        hashMap.put("user_uid", "mtc9cd4b516eaf297f8af3f2791f1428ce");
        hashMap.put("app_flag", "mt");
        mHttpPostRequest.requestPost(url, hashMap, new OnRequestListener() {
            @Override
            public void onSuccess(String url, String result, int id) {
                DouyinBean douyinBean = JsonObjectUtils.parseJsonToBean(result, DouyinBean.class);
                if (page < douyinBean.getPages()) {
                    mList.addAll(douyinBean.getData());
                    mAdapter.notifyDataSetChanged();
                    mSmartRefreshLayout.finishRefresh();
                    mSmartRefreshLayout.finishLoadmore();
                    mAdapter.notifyDataSetChanged();
                    if (page == 1) {
                        startPlay();
                    }
                    page++;
                } else {
                    Toast.makeText(VideoPlayerDemoActivity2.this, "没有更多的数据了", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFaild(String url, String response, int id) {

            }
        }, 0);
    }

    private void initList() {
        mList.clear();
        page = 1;
        loadMore();
    }

    private void initRecycler() {
        mLayoutmanager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutmanager);
        mAdapter = new VideoPlayAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
//        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mSmartRefreshLayout.setDisableContentWhenLoading(true);
        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(mRecyclerView);

    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_video_play2;
    }


    @Override
    protected void onPause() {
        super.onPause();
//        mPlVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFrist) {
           startPlay();
        }
        isFrist = false;
    }
}
