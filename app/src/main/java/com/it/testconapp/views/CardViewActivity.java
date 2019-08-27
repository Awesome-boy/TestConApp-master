package com.it.testconapp.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.testconapp.R;
import com.it.testconapp.adpter.MyAdapter;
import com.it.testconapp.javabean.ImageInfor;
import com.it.testconapp.utils.ChangeBgColorUtil;
import com.it.testconapp.utils.FastBlur;
import com.it.testconapp.widget.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CardViewActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView main_rv;
    private Button bt_next,bt_find,bt_second;
    private CustomDatePicker datePicker,timePicker;
    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private String time;
    private String date;
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        initView();
        initDatas();
    }
    private void initView() {
        main_rv = (RecyclerView) findViewById(R.id.main_rv);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_find = (Button) findViewById(R.id.bt_find);
        bt_second = (Button) findViewById(R.id.bt_second);
        selectTime = (RelativeLayout) findViewById(R.id.selectTime);
        selectTime.setOnClickListener(this);
        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(this);
        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);
        imageView = (ImageView) findViewById(R.id.iv_blur);
        initPicker();
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CardViewActivity.this, SwipListActivity.class));
            }
        });
        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CardViewActivity.this,CoordViewActivity.class));
            }
        });
        bt_second.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(CardViewActivity.this,CaptchaImageActivity.class));

            }
        });
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.girl);
        imageView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        blur(bitmap, imageView);
                        return true;
                    }
                });
    }



    public void blur( Bitmap bkg, ImageView view) {
        float scaleFactor = 8;
        float radius = 20;
        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()
                / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setBackground(new BitmapDrawable(getResources(), overlay));
    }

    private void initPicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        //设置当前显示的日期
        currentDate.setText(date);
        //设置当前显示的时间
        currentTime.setText(time);

        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                currentDate.setText(time.split(" ")[0]);
            }
        }, "2007-01-01 00:00", time);
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);

        timePicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                currentTime.setText(time);
            }
        }, "2007-01-01 00:00", "2027-12-31 23:59");//"2027-12-31 23:59"
        timePicker.showSpecificTime(true);
        timePicker.setIsLoop(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initDatas() {
        //添加数据
        List<ImageInfor> list=new ArrayList<>();
        int drawpic=R.drawable.top_bar_bg;
        Bitmap mBitmap=ChangeBgColorUtil.drawableToBitmap(getDrawable(R.drawable.top_bar_bg));
//        Bitmap bitmap=ChangeBgColorUtil.getAlphaBitmap(mBitmap,R.color.colorAccent);
        list.add(new ImageInfor(mBitmap,"change"));
        list.add(new ImageInfor(mBitmap,"school"));
        list.add(new ImageInfor(mBitmap,"hospital"));
        list.add(new ImageInfor(mBitmap,"resturant"));
        list.add(new ImageInfor(mBitmap,"hotel"));
        list.add(new ImageInfor(mBitmap,"website"));;
        //设置列表显示方式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        main_rv.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置列表默认动画效果
        main_rv.setItemAnimator(new DefaultItemAnimator());
        //绑定适配器
        MyAdapter myAdapter=new MyAdapter(list);
        main_rv.setAdapter(myAdapter);
        //列表点击事件
        myAdapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("position=",position+"");

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                datePicker.show(date);
                break;

            case R.id.selectTime:
                // 日期格式为yyyy-MM-dd HH:mm
                timePicker.show(time);
                break;
        }
    }
}
