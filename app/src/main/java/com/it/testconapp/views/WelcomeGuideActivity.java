package com.it.testconapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.it.testconapp.R;
import com.it.testconapp.adpter.GuideViewPagerAdapter;
import com.it.testconapp.interf.CommonAdapterOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewpager;
    private GuideViewPagerAdapter adapter;
    //View数据
    private List<View> views;

    //引导页图片、布局资源
    private int[] pics = {R.layout.guide_view1, R.layout.guide_view2, R.layout.guide_view3, R.layout.guide_view4};
    //引导点资源
    private ImageView[] imageViews;

    //引导点
    private ImageView imageOne, imageTwo, imageThree, imageFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_guide);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        imageOne = (ImageView) findViewById(R.id.imageOne);
        imageTwo = (ImageView) findViewById(R.id.imageTwo);
        imageThree = (ImageView) findViewById(R.id.imageThree);
        imageFour = (ImageView) findViewById(R.id.imageFour);
        initView();
    }

    private void initView() {
        if (views == null) {
            views = new ArrayList<>();
        }
        if (imageViews == null) {
            imageViews = new ImageView[4];
        }
        //初始化引导点,赋值
        imageViews[0] = imageOne;
        imageViews[1] = imageTwo;
        imageViews[2] = imageThree;
        imageViews[3] = imageFour;
        //默认全部为未选中
        setPointSelect(true, 0);

        //初始化引导页视图列表,将4个布局View添加至list中
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            views.add(view);
        }
        //初始化adapter
        adapter = new GuideViewPagerAdapter(views);
        //将adapter设置到viewpager中
        viewpager.setAdapter(adapter);
        //ViewPager切换事件
        viewpager.addOnPageChangeListener(this);
        adapter.setCommonAdapterOnClickListener(new CommonAdapterOnClickListener() {
            @Override
            public void onClick(int id, int position, Object... args) {
                startActivity(new Intent(WelcomeGuideActivity.this,MainActivity.class));
            }
        });
    }

    /**
     * ViewPager滑动事件
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //当前页面被滑动时调用
        //position :当前页面，及你点击滑动的页面
        //positionOffset:当前页面偏移的百分比
        //positionOffsetPixels:当前页面偏移的像素位置
    }

    @Override
    public void onPageSelected(int position) {
        //当新的页面被选中时调用(position：当前位置)
        //先设置为未选中，在将当前设置为选中状态
        setPointSelect(false, position % views.size() );
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //当滑动状态改变时调用
        //state ==1 的时候默示正在滑动
        //state ==2 的时候默示滑动完毕了
        //state ==0 的时候默示什么都没做
    }

    /**
     * 设置引导点为为选中状态
     */
    private void setPointSelect(boolean state, int position) {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setBackgroundResource(R.drawable.bg_white);
        }
        if (state) {
            //第一张为选中状态
            imageViews[0].setBackgroundResource(R.drawable.bg_white);
        } else {
            imageViews[position].setBackgroundResource(R.drawable.bg_gray);
        }
    }


}
