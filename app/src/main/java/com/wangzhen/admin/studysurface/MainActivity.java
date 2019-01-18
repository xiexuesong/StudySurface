package com.wangzhen.admin.studysurface;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/***
 *  一个view在另一个view上面会出现覆盖的情况，即使设置透明度也没有办法解决遮盖的问题，这应该是必然的
 *  然后试过canvas去drawRect的时候发现即使遮盖也会显示下面的bitmap,rect重合的区域颜色出现重合，证明是可以显示底下的rect的，在canvas中
 *  然后再试一下canvas中去尝试drawBitmap，看看会不会出现图片覆盖的问题
 */
public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView_top;
    private MediaPlayer mediaPlayer_top;
    private SurfaceView surfaceView_bottom;
    private MediaPlayer mediaPlayer_bottom;

    private static final String EXTROEAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView_bottom = findViewById(R.id.surfaceView_bottom);
        surfaceView_top = findViewById(R.id.surfaceView_top);

        surfaceView_top.setZOrderOnTop(true);
        surfaceView_bottom.setZOrderOnTop(true);


        final SurfaceHolder surfaceTopHolder = surfaceView_top.getHolder();
        surfaceTopHolder.setFormat(PixelFormat.TRANSLUCENT);
        surfaceTopHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer_top.setDisplay(surfaceTopHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        mediaPlayer_top = new MediaPlayer();
        String topPath = EXTROEAGE_PATH + "/xiaoYa_mvColor.mp4";
        try {
            mediaPlayer_top.setDataSource(topPath);
            mediaPlayer_top.prepare();
            mediaPlayer_top.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.start();
                }
            });

        } catch (IOException e) {
            Log.i("MDL", "视频文件不存在" + " path:" + topPath);
            e.printStackTrace();
        }


        final SurfaceHolder surfaceBottomHolder = surfaceView_bottom.getHolder();
        surfaceBottomHolder.setFormat(PixelFormat.TRANSLUCENT);
        surfaceBottomHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer_bottom.setDisplay(surfaceBottomHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        mediaPlayer_bottom = new MediaPlayer();
//        mediaPlayer_bottom.setDisplay(surfaceBottomHolder);
        String bottomPath = EXTROEAGE_PATH + "/taohua.mp4";
        try {
            mediaPlayer_bottom.setDataSource(bottomPath);
            mediaPlayer_bottom.prepare();
            mediaPlayer_bottom.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.start();
                }
            });
        } catch (IOException e) {
            Log.i("MDL", "视频文件不存在" + " path:" + bottomPath);
            e.printStackTrace();
        }



    }
}
