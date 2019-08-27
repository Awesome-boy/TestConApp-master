package com.it.testconapp.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.it.testconapp.R;
import com.it.testconapp.javabean.HeartRateBean;
import com.it.testconapp.utils.GlideImageLoader;
import com.it.testconapp.utils.Tools;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class BannerActivity extends AppCompatActivity {

    private Banner banner;
    List<Integer> images= new ArrayList<>();
    private ArrayList<Object> list_path;
    private ArrayList<String> list_title;
    private LineChartView lineChart;
    private List<HeartRateBean> heartList = new ArrayList<>();
    private List<String> list1 = new ArrayList<>();
    private List<PointValue> mPointValues1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
        initData();
    }

    private void initData() {
        //        images.add(R.mipmap.girl);
//        images.add(R.mipmap.girl2);
//        images.add(R.mipmap.girl3);
//        images.add(R.mipmap.girl4);
        list_path = new ArrayList<>();
        list_title = new ArrayList<>();
        list_path.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        list_path.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        list_path.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        list_path.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        list_path.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        list_path.add("http://img.poco.cn/mypoco/myphoto/20071007/11/20071007114140_1251314291.jpg");
        list_path.add("http://a3.att.hudong.com/72/76/01300000012339118647690465772.jpg");
        list_title.add("爱党");
        list_title.add("爱祖国");
        list_title.add("爱人民");
        list_title.add("爱社会");
        list_title.add("爱学习");
        list_title.add("爱劳动");
        list_title.add("爱妹子");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list_path);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(list_title);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
//        BrokenLineView brokenview= (BrokenLineView) findViewById(R.id.chartView);
//        brokenview.SetInfo(
//                new String[]{"周一","周二","周三","周四","周五","周六","周日"},   //X轴刻度
//                new String[]{"","50","100","150","200","250","300","350"},   //Y轴刻度
//                new String[]{"150","230","10","136","45","40","112","313"},  //数据
//                "图标的标题");
        //循环100随机100以内数字
        for (int i = 0; i < 12; i++) {
            heartList.add(new HeartRateBean((float) (100 * Math.random()), i + ""));
        }

        Tools.setChartViewData(heartList, lineChart);

    }

    private void initView() {
        banner = (Banner) findViewById(R.id.banner);
        lineChart = (LineChartView) this.findViewById(R.id.chart);
    }
}
