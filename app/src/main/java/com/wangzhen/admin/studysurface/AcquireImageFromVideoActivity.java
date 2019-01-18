package com.wangzhen.admin.studysurface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import view.StudyMediaImageView;

import static android.media.MediaMetadataRetriever.OPTION_CLOSEST;

/**
 * 看截取的每一帧图片 是否是带有透明通道的
 * Created by admin on 2019/1/16.
 * <p>
 * <p>
 * 两个问题 1、MediaMetadataRetriever没有获得对应时间的帧图片
 * 2、获取的bitmap透明部分变黑色
 */

public class AcquireImageFromVideoActivity extends Activity {

    private String EXCTORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String videoPath = EXCTORAGE_PATH + "/xiaoYa_mvColor.mp4";//带透明通道的MP4
    private String videoPath2 = EXCTORAGE_PATH + "/taohua.mp4";
    private Timer timer = new Timer();
    private long time = 0;
    private StudyMediaImageView studyMediaImageView;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acquireimagefromvideo);

        studyMediaImageView = findViewById(R.id.studyMediaImageView);
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(videoPath);
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        final long duration = Long.parseLong(durationStr);
        Log.i("MDL", "duration:" + duration);
        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(time, OPTION_CLOSEST);
        studyMediaImageView.setBitmap(bitmap);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(videoPath);
                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(time);
                time = time + 20L;
                //bitmap = changeColor(bitmap);
             //   acquireTransparence(bitmap);
                studyMediaImageView.setBitmap(bitmap);
                studyMediaImageView.postInvalidate();
                saveBitmapToSD(bitmap);
                Log.i("MDL", "time:" + time);
                if (time >= 40) {
                    cancel();
                }
                mediaMetadataRetriever.release();
            }
        }, 0, 20L);
    }

    /**
     * 获取透明度(尝试)
     */
    private void acquireTransparence(Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();


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


    private Bitmap changeColor(Bitmap bitmap) {
        int oldColor = Color.argb(255,0, 0, 0);
        int newColor = Color.argb(0,0,0,0);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = bitmap.getPixel(i, j);
                if (color == oldColor){
                    bitmap.setPixel(i,j,newColor);
                }
            }
        }
        return bitmap;
    }


}
