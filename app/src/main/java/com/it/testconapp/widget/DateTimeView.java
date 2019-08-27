package com.it.testconapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.BuildConfig;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.it.testconapp.utils.DimensionUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import static android.content.Context.MODE_PRIVATE;
import static com.it.testconapp.utils.DimensionUtil.dpToPx;


public class DateTimeView extends View {
    private int      dayIndex;
    private String[] days;

    private  int      hourIndex;
    private String[] hours;

    private float mScale;

    private int      minusIndex;
    private String[] minutes;

    private float    secondDelta;
    private int      secondIndex;
    private String[] seconds;

    private Context mContext;

    private int mViewWidth;

    private LinearGradient mLinearGradient;
    private Matrix         mGradientMatrix;
    private int            mTranslate;

    private String mName = "宋";

    private Paint paint;//画笔 ，画时间
    private Paint mTextPaint; //画姓氏
    private Paint mTextPaint2;//画顶部提示语

//    private SharedPreferences mShare;

    public DateTimeView(Context context) {
        this(context, null);
    }

    public DateTimeView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        paint = new Paint();
        mContext = context;
        initData();
        Log.d("TH","------");
        init();
    }

    private void initData() {
       mScale = 0.5f;
       secondDelta = 0.0f;
       dayIndex = 0;
       days = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一"};
       hourIndex = 0;
       hours = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
       minusIndex = 0;
       minutes = new String[]{"一分", "二分", "三分", "四分", "五分", "六分", "七分", "八分", "九分", "十分", "十一分", "十二分", "十三分", "十四分", "十五分", "十六分", "十七分", "十八分", "十九分", "二十分", "二十一分", "二十二分", "二十三分", "二十四分", "二十五分", "二十六分", "二十七分", "二十八分", "二十九分", "三十分", "三十一分", "三十二分", "三十三分", "三十四分", "三十五分", "三十六分", "三十七分", "三十八分", "三十九分", "四十分", "四十一分", "四十二分", "四十三分", "四十四分", "四十五分", "四十六分", "四十七分", "四十八分", "四十九分", "五十分", "五十一分", "五十二分", "五十三分", "五十四分", "五十五分", "五十六分", "五十七分", "五十八分", "五十九分", BuildConfig.FLAVOR};
       secondIndex = 0;
       seconds = new String[]{"一秒", "二秒", "三秒", "四秒", "五秒", "六秒", "七秒", "八秒", "九秒", "十秒", "十一秒", "十二秒", "十三秒", "十四秒", "十五秒", "十六秒", "十七秒", "十八秒", "十九秒", "二十秒", "二十一秒", "二十二秒", "二十三秒", "二十四秒", "二十五秒", "二十六秒", "二十七秒", "二十八秒", "二十九秒", "三十秒", "三十一秒", "三十二秒", "三十三秒", "三十四秒", "三十五秒", "三十六秒", "三十七秒", "三十八秒", "三十九秒", "四十秒", "四十一秒", "四十二秒", "四十三秒", "四十四秒", "四十五秒", "四十六秒", "四十七秒", "四十八秒", "四十九秒", "五十秒", "五十一秒", "五十二秒", "五十三秒", "五十四秒", "五十五秒", "五十六秒", "五十七秒", "五十八秒", "五十九秒", BuildConfig.FLAVOR};
    }

    private void init() {
//        mShare = mContext.getSharedPreferences(Constant.PDACLIENT, MODE_PRIVATE);

        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50.0f);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Calendar instance = Calendar.getInstance();
                dayIndex = instance.get(Calendar.DATE) - 1;
                hourIndex = instance.get(Calendar.HOUR) - 1;
                minusIndex = instance.get(Calendar.MINUTE) - 1;
                secondIndex = instance.get(Calendar.SECOND) - 1;
                secondDelta = ((((float) (System.currentTimeMillis() % 1000)) * 1.0f) / 1000.0f) * 3.0f;
                if (secondDelta > 0.6f) {
                    secondDelta = 1.0f;
                }
                postInvalidate();
            }
        }, 0, 16, TimeUnit.MILLISECONDS);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);//设置画笔的颜色
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);//描边加填充
        mTextPaint.setStrokeWidth(3);//设置画笔的宽度
        mTextPaint.setAntiAlias(true);//防锯齿，设置为true时,会损失一定的性能,使用时视情况而定
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);//圆形  笔帽,就是画笔画出来的线的两端的样式
        mTextPaint.setStrokeJoin(Paint.Join.ROUND);//圆角   两线相交样式
        mTextPaint.setLetterSpacing(12);//设置字符间间距
        mTextPaint.setTextSize(dpToPx(mContext,70));//设置字符大小
        mTextPaint.setTextAlign(Paint.Align.CENTER);//居中  文本对齐方式
        Typeface customFont = Typeface.createFromAsset(mContext.getAssets(), "yzqs.ttf");
        Log.d("TH","---2---");
        mViewWidth = dpToPx(mContext,70);

        mLinearGradient = new LinearGradient(mViewWidth, 0, mViewWidth * 2, 0,
                new int[]{Color.WHITE, Color.rgb(241, 60, 124), Color.rgb(42, 197, 139),
                        Color.rgb(193, 66, 255), Color.rgb(255, 126, 40), Color.WHITE}
                , null, Shader.TileMode.CLAMP);
        mTextPaint.setShader(mLinearGradient);
        mGradientMatrix = new Matrix();

        mTextPaint2 = new Paint();
        mTextPaint2.setAntiAlias(true);//防锯齿，
        mTextPaint2.setColor(Color.WHITE);//设置画笔的颜色
        mTextPaint2.setStyle(Paint.Style.FILL_AND_STROKE);//描边加填充
//        if (mShare.getBoolean("isSuccessttf", false)) {
//            File file = new File(Constant.DICTIONARY + "yzqs.ttf");
//            if (file.exists()) {
//                Typeface customFont = Typeface.createFromFile(Constant.DICTIONARY + "yzqs.ttf");
                mTextPaint.setTypeface(customFont); //字体 Typeface.create(familyName, style)//加载自定义字体
                mTextPaint2.setTypeface(customFont);
//            } else {
//                Toast.makeText(mContext, "字体库缺失，请重启应用", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(mContext, "字体库缺失，请重启应用", Toast.LENGTH_SHORT).show();
//        }
        mTextPaint2.setTextAlign(Paint.Align.CENTER);

    }

    public void setName(String name) {
        mName = name;
        postInvalidate();
    }

    protected void onDraw(Canvas canvas) {
        float f;
        float f2;
        StringBuilder stringBuilder;
        Canvas canvas2 = canvas;
        super.onDraw(canvas);

        canvas2.drawColor(Color.BLACK);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bgnew1, null);
//        Rect rect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
//        canvas2.drawBitmap(bitmap, null, rect, paint);

        Matrix matrix = new Matrix();

        if (mGradientMatrix != null) {
            mTranslate += 3;//变换的速度
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        mTextPaint2.setTextSize(dpToPx(mContext,25));//设置字符大小
        canvas.drawText("明明什么都没做", canvas2.getWidth() / 2, dpToPx(mContext,40), mTextPaint2);//画提示语
        mTextPaint2.setTextSize(dpToPx(mContext,45));//设置字符大小
        canvas.drawText("就已经 " + formatter.format(curDate) + " 了", canvas2.getWidth() / 2, dpToPx(mContext,40) * 2 + 40, mTextPaint2);//画提示语

        canvas.drawText(mName, canvas2.getWidth() / 2, getHeight() / 2 + dpToPx(mContext,26), mTextPaint);//画姓氏


        String[] strArr = hours;
        mScale = (((float) canvas2.getWidth()) / 1080.0f) * 0.5f;
        float length = 360.0f / ((float) strArr.length);
        float f3 = 0.0f - (((float) hourIndex) * length);
        int i4 = 0;
        f = f3;
        for (String str222 : strArr) {
            matrix.reset();
            matrix.postTranslate(300.0f, 0.0f);
            matrix.postRotate(f, 0.0f, 0.0f);
            f += length;
            matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
            f2 = mScale;
            matrix.postScale(f2, f2, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas2.setMatrix(matrix);
            paint.setColor(i4 == hourIndex ? Color.WHITE : -7829368);
            stringBuilder = new StringBuilder();
            stringBuilder.append(str222);
            stringBuilder.append("点");
            canvas2.drawText(stringBuilder.toString(), 0.0f, 0.0f, paint);
            i4++;
        }
        strArr = minutes;
        length = 360.0f / ((float) strArr.length);
        f3 = 0.0f - (((float) minusIndex) * length);
        f = f3;
        i4 = 0;
        for (String str2222 : strArr) {
            matrix.reset();
            matrix.postTranslate(500.0f, 0.0f);
            matrix.postRotate(f, 0.0f, 0.0f);
            f += length;
            matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
            f2 = mScale;
            matrix.postScale(f2, f2, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas2.setMatrix(matrix);
            paint.setColor(i4 == minusIndex ? Color.WHITE : -7829368);
            canvas2.drawText(str2222, 0.0f, 0.0f, paint);
            i4++;
        }
        strArr = seconds;
        float length3 = 360.0f / ((float) strArr.length);
        length = (0.0f - (((float) secondIndex) * length3)) - (secondDelta * length3);
        float f5 = length;
        int i5 = 0;
        for (String str3 : strArr) {
            matrix.reset();
            matrix.postTranslate(760.0f, 0.0f);
            matrix.postRotate(f5, 0.0f, 0.0f);
            f5 += length3;
            matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
            float f6 = mScale;
            matrix.postScale(f6, f6, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas2.setMatrix(matrix);
            if (secondDelta == 1.0f) {
                paint.setColor(i5 == secondIndex + 1 ? Color.WHITE : -7829368);
            } else {
                paint.setColor(i5 == secondIndex ? Color.WHITE : -7829368);
            }
            canvas2.drawText(str3, 0.0f, 0.0f, paint);
            i5++;
        }
    }
}
