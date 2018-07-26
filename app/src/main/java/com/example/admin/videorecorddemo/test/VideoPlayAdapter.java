package com.example.admin.videorecorddemo.test;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.videorecorddemo.R;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by admin on 2018/6/5.
 */

public class VideoPlayAdapter extends RecyclerView.Adapter<VideoPlayAdapter.MyHolder> {
    private final Context mContext;
    private final List<DouyinBean.DataBean> mList;

    public VideoPlayAdapter(Context context, List<DouyinBean.DataBean> list){
        this.mContext = context;
        this.mList = list!=null?list:new ArrayList<DouyinBean.DataBean>();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout. item_activity_video_play2, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        ImageUtils.loadImage(mContext, mList.get(position).getVideoimg(), holder.mImageView);
        holder.mPLVideoTextureView.setVideoPath(mList.get(position).getVideourl());
        holder.mPLVideoTextureView.start();
        holder.mPLVideoTextureView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
               mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                   @Override
                   public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                       mediaPlayer.setLooping(true);
                       if (i== MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                           holder.mImageView.setVisibility(View.GONE);
                       }
                       return true;
                   }
               });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public VideoView mPLVideoTextureView;
        public MyHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
            mPLVideoTextureView = (VideoView) itemView.findViewById(R.id.pl_video_view);
        }
    }

}
