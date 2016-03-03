package sortingApp.apurv.com.sortingApp.custom;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;

/**
 * Created by Admin on 23-03-2015.
 */
public class MenuItemView extends TextView {

    Paint paint;
    float angle = 0.0f;
    RectF mRectF;
    boolean isFocussed = false;
    int index;
    int eyeID = -1;
    private int strokeWidth;
    boolean isProgressStarted = false;
    Timer timer;
    int iconSize;

    public MenuItemView(Context context) {
        super(context);
        init(context);
    }

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int mWidth = size.x / 2;
        int mHeight = size.y;

        iconSize = (int)(0.084*mWidth);
        strokeWidth = (int)(0.0625f*iconSize);
        paint = new Paint();
        paint.setColor(Color.argb(255, 246, 145, 31));//ARGB format
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        setPadding(iconSize / 8, iconSize / 8, iconSize / 8, iconSize / 8);

        mRectF = new RectF((float) strokeWidth, (float) strokeWidth, (float) iconSize - strokeWidth, (float) iconSize - strokeWidth);
        setGravity(Gravity.CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 4);
        setTypeface (Typeface.DEFAULT_BOLD);
        setTextColor(getResources().getColor(android.R.color.white));
        setMaxLines(2);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.index != 0) {
            if (isFocussed) {
                if (!isProgressStarted) {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new ProgressTimer(), 500, 16);
                    isProgressStarted = true;
                }
                canvas.drawArc(mRectF, -90, angle, false, paint);
                invalidate();
            } else if (!isFocussed && timer != null) {
                isProgressStarted = false;
                angle = 0.0f;
                timer.cancel();
                timer = null;
            }
        }
    }

    class ProgressTimer extends java.util.TimerTask {
        @Override
        public void run() {
            if (angle < 360.0f) {
                angle = angle + 2.0f;
            } else if (angle >= 360.0f) {
                angle = 0.0f;
                timer.cancel();
            }
        }
    }

    public void notifyTrigger()
    {
        //Log.i("test","Timer cancelled");
        timer.cancel();
    }


}
