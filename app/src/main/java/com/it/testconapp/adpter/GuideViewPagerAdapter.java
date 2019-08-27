package com.it.testconapp.adpter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.it.testconapp.R;
import com.it.testconapp.interf.CommonAdapterOnClickListener;

import java.util.List;

/**
 * Created by zt on 2019/3/26.
 */
public class GuideViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    int count;
    private CommonAdapterOnClickListener commonAdapterOnClickListener;
    public GuideViewPagerAdapter(List<View> views) {
        super();
        this.views = views;
        this.count=views.size();
    }

    public CommonAdapterOnClickListener getCommonAdapterOnClickListener() {
        return commonAdapterOnClickListener;
    }

    public void setCommonAdapterOnClickListener(CommonAdapterOnClickListener commonAdapterOnClickListener) {
        this.commonAdapterOnClickListener = commonAdapterOnClickListener;
    }

    @Override
    public int getCount() {
        if (views != null) {
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(views.get(position%count));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int temp = position % count;
        View view = views.get(temp);
        Log.i("zz","zt"+position+"--"+temp);
        if (view.getParent() == container) {

            container.removeView(view);
        }
        container.addView(view);
        Button btn_next=view.findViewById(R.id.start_home);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonAdapterOnClickListener.onClick(1,position,null);
            }
        });

        return view;
    }


}
