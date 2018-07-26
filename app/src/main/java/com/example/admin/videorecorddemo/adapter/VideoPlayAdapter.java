package com.example.admin.videorecorddemo.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.videorecorddemo.R;
import com.example.admin.videorecorddemo.view.MediaController;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2018/6/5.
 */

public class VideoPlayAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public VideoPlayAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setImageResource(R.id.image_view, R.mipmap.ic_launcher);
    }
}
