package com.example.admin.videorecorddemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

import com.example.admin.videorecorddemo.R;
import com.example.admin.videorecorddemo.test.VideoPlayerDemoActivity2;
import com.example.admin.videorecorddemo.utils.GetPathFromUri;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @OnClick({R.id.record_video, R.id.play_video, R.id.edit_video,R.id.play_video2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.record_video:
                startActivity(new Intent(this, VideoRecordActivity.class));
                break;
            case R.id.play_video:
                startActivity(new Intent(this, VideoPlayerDemoActivity2.class));
                break;
            case R.id.play_video2:
                startActivity(new Intent(this, VideoPlayerDemoActivity.class));
                break;
            case R.id.edit_video:
                editVideo();
                break;
        }
    }

    /**
     * 编辑视频
     */
    private void editVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_main;
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
