package com.it.testconapp.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.it.testconapp.R;
import com.it.testconapp.widget.PageIndicatorView;
import com.it.testconapp.widget.PageRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndicatorActivity extends AppCompatActivity {

    private PageRecyclerView mRecyclerView;
    private List<Map<String, Object>> dataList;
    PageRecyclerView.PageAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        initData();
        initView();
    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i=0;i<7;i++){
            Map<String, Object> testMap = new HashMap<String, Object>();
            testMap.put("text","haha");
            dataList.add(testMap);
        }
        Log.d("zt","zt--"+dataList.size());
    }

    private void initView() {
        mRecyclerView = (PageRecyclerView) findViewById(R.id.cusom_swipe_view);
        // 设置指示器
        mRecyclerView.setIndicator((PageIndicatorView) findViewById(R.id.indicator));
        // 设置行数和列数
        mRecyclerView.setPageSize(1, 4);

        // 设置页间距
        mRecyclerView.setPageMargin(30);
        // 设置数据


        mRecyclerView.setAdapter(myAdapter=mRecyclerView.new PageAdapter(dataList, new PageRecyclerView.CallBack() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(IndicatorActivity.this).inflate(R.layout.indicatoritem, parent, false);
                return new MyHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                Map<String,Object> map= (Map<String, Object>) dataList.get(position);
//                Glide.with(IndicatorActivity.this).load(map.get("img").toString()).into( ((MyHolder) holder).iv_huanyuan_zuo);
//                ((MyHolder) holder).tv_title.setText((String) map.get("txt"));
//                ((MyHolder) holder).iv_backgroud.setImageDrawable((Drawable) map.get("img"));
            }

            @Override
            public void onItemClickListener(View view, int position) {
                                        Toast.makeText(IndicatorActivity.this, "点击："
                                                + dataList.get(position), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                                        Toast.makeText(IndicatorActivity.this, "删除："
                                                + dataList.get(position), Toast.LENGTH_SHORT).show();
                                        myAdapter.remove(position);
            }
        }));

    }

    private class MyHolder extends RecyclerView.ViewHolder {
//        public ImageView iv_backgroud;
//        public TextView tv_title;
        public MyHolder(View itemView) {
            super(itemView);
//            iv_backgroud = (ImageView) itemView.findViewById(R.id.picture);
//            tv_title = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
