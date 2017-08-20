package com.zfj.android.newcalender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zfj_ on 2017/8/16.
 */

@SuppressLint("AppCompatCustomView")
public class Calendar_day_textView extends TextView {
    public boolean isToday = false;
    private Paint paint = new Paint();
    public Calendar_day_textView(Context context) {
        super(context);
        initControl();
    }

    public Calendar_day_textView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public Calendar_day_textView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public Calendar_day_textView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }


    private  void initControl(){
        //红色描边
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#ff0000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isToday){
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,paint);
        }

    }
}

