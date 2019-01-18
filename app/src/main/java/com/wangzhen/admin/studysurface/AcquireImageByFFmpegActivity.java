package com.wangzhen.admin.studysurface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;

import view.StudySurfaceView;

/**
 * Created by admin on 2019/1/18.
 */

public class AcquireImageByFFmpegActivity extends Activity {

    private String EXCTORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String videoPath = EXCTORAGE_PATH + "/xiaoYa_mvColor.mp4";//带透明通道的MP4
    private String videoPath2 = EXCTORAGE_PATH + "/taohua.mp4";
    private Timer timer = new Timer();
    private long time = 0;
    private StudySurfaceView studyMediaImageView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acquireimagefromvideo);
        studyMediaImageView = findViewById(R.id.studyMediaImageView);
       /* FFmpegMediaMetadataRetriever fFmpegMediaMetadataRetriever = new FFmpegMediaMetadataRetriever();
        fFmpegMediaMetadataRetriever.setDataSource(videoPath);
        String durationStr = fFmpegMediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION);
        final long duration = Long.parseLong(durationStr);
        fFmpegMediaMetadataRetriever.release();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                FFmpegMediaMetadataRetriever fFmpegMediaMetadataRetriever = new FFmpegMediaMetadataRetriever();
                fFmpegMediaMetadataRetriever.setDataSource(videoPath);
                Bitmap bitmap = fFmpegMediaMetadataRetriever.getFrameAtTime(time, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
                if(bitmap != null) {
                    studyMediaImageView.setBitmap(bitmap);
                  //  saveBitmapToSD(bitmap);
                    time += 20;
                    if (time >= duration) {
                        cancel();
                        studyMediaImageView.setDrawEnd();
                        fFmpegMediaMetadataRetriever.release();

                    }
                    fFmpegMediaMetadataRetriever.release();
                }
            }
        },0,20);*/
    }

    private void saveBitmapToSD(Bitmap bitmap) {
        String path = EXCTORAGE_PATH + "/a/" + System.currentTimeMillis() + ".png";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
