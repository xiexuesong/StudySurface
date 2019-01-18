package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by admin on 2019/1/16.
 */

public class StudyMediaImageView extends View {

    private Bitmap bitmap;
    private Context context;
    private Paint paint_bottom;
    private int left;
    private int right;
    private Paint paint_test;

    public StudyMediaImageView(Context context) {
        super(context);
        init(context);
    }

    public StudyMediaImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StudyMediaImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint_bottom = new Paint();
        paint_bottom.setAntiAlias(true);
        paint_bottom.setAlpha(254);

        paint_test = new Paint();
        paint_test.setColor(Color.RED);
        paint_test.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(bitmap != null) {
            canvas.drawRect(0,0,200,200,paint_test);
            canvas.translate(0,0);
            canvas.drawBitmap(bitmap, left, right, paint_bottom);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        //先回收之前的bitmap,防止内存溢出
        if (this.bitmap != null && this.bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = bitmap;
        invalidate();
    }


}
