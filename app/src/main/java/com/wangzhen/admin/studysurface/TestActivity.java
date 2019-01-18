package com.wangzhen.admin.studysurface;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.VideoView;

/**
 * Created by admin on 2019/1/16.
 */

public class TestActivity extends Activity {

    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        videoView = findViewById(R.id.videoView);

    }
}
