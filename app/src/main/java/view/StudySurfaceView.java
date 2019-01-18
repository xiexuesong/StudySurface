package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by admin on 2019/1/15.
 */

public class StudySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap bitmap;
    private Paint paint_bottom;
    private Paint paint_test;
    private SurfaceHolder mHolder;
    private Context context;
    private int left;
    private int right;
    private boolean mStart = false;
    private String EXCTORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String videoPath = EXCTORAGE_PATH + "/xiaoYa_mvColor.mp4";//带透明通道的MP4
    private long time = 0;
    private Timer timer = new Timer();
    private long duration;
    private FFmpegMediaMetadataRetriever fFmpegMediaMetadataRetriever;
    private MediaMetadataRetriever mediaMetadataRetriever;

    public StudySurfaceView(Context context) {
        super(context);
        init(context);
    }

    public StudySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StudySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mHolder = this.getHolder();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mHolder.addCallback(this);

        this.context = context;
        paint_bottom = new Paint();
        paint_bottom.setAntiAlias(true);
        paint_bottom.setAlpha(254);

        paint_test = new Paint();
        paint_test.setColor(Color.RED);
        paint_test.setStyle(Paint.Style.FILL);

        fFmpegMediaMetadataRetriever = new FFmpegMediaMetadataRetriever();
        fFmpegMediaMetadataRetriever.setDataSource(videoPath);
        String durationStr = fFmpegMediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION);
        duration = Long.parseLong(durationStr);
      //  fFmpegMediaMetadataRetriever.release();

        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(videoPath);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        start();
    }

    /**
     * 不靠谱
     */
    private void start() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Bitmap bitmap = fFmpegMediaMetadataRetriever.getFrameAtTime(time, MediaMetadataRetriever.OPTION_CLOSEST);
                        if (bitmap != null) {
                            time += 20;
                            drawView(bitmap);
                            if (time >= duration) {
                                cancel();
                            }
                        }
                    }
                }, 0, 20);
            }
        }.start();

    }

    private void drawView(Bitmap bitmap) {
        if (mHolder == null) {
            return;
        }
        if (bitmap == null) {
            return;
        }
        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }
        Canvas canvas = mHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawRect(0, 0, 200, 200, paint_test);
            canvas.drawBitmap(bitmap, 0, 0, paint_bottom);
            mHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void setBitmap(Bitmap bitmap) {
        //先回收之前的bitmap,防止内存溢出
        if (this.bitmap != null && this.bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = bitmap;
    }

    public void setDrawEnd() {
        mStart = true;
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
