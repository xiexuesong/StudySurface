package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by admin on 2019/1/16.
 */

public class StudyImageView extends View {

    private Bitmap bitmap_top;
    private Bitmap bitmap_bottom;
    private Bitmap bitmap_top_jpg;
    private String EXCTORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/";
    private Paint paint_top;
    private Paint paint_bottom;

    public StudyImageView(Context context) {
        super(context);
        Log.i("MDL","单参");
        init();
    }

    public StudyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i("MDL","双参");
        init();
    }

    public StudyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("MDL","三参");
        init();
    }

    private void init() {
        bitmap_top = BitmapFactory.decodeFile(EXCTORAGE_PATH + "test.png");
        bitmap_bottom = BitmapFactory.decodeFile(EXCTORAGE_PATH + "bottom.jpg");
        bitmap_top_jpg = BitmapFactory.decodeFile(EXCTORAGE_PATH + "test.jpg");



        paint_bottom = new Paint();
        paint_top = new Paint();
        paint_top.setAntiAlias(true);
        paint_bottom.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(bitmap_bottom!= null && bitmap_top != null) {
            canvas.drawBitmap(bitmap_bottom, 0, 0, paint_bottom);
            canvas.drawBitmap(bitmap_top, 60, 60, paint_top);

            canvas.drawBitmap(bitmap_top_jpg,200,200,paint_top);
        }
    }
}
