package com.it.testconapp.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zt on 2019/5/20.
 */
public class CounterView extends View implements View.OnClickListener {

    private Paint mPaint;
    private Rect rect;
    private int count=99;
    public CounterView(Context context) {
        super(context);
        init();
    }

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new Rect();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        String text = String.valueOf(count);
        mPaint.getTextBounds(text,0,text.length(),rect);
        float textWidth = rect.width();
        float textHeight = rect.height();
        canvas.drawText(text,getWidth()/2-textWidth/2,getHeight()/2+textHeight/2,mPaint);
    }

    @Override
    public void onClick(View view) {
        count++;
        invalidate();
    }
}
