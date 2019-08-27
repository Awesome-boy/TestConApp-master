package com.it.testconapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.it.testconapp.R;
import com.it.testconapp.adpter.CoordAdapter;

import java.util.ArrayList;
import java.util.List;

public class CoordViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private List<String> mlist;
    private CoordAdapter mAdapter;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coord_view);
        initView();
        initData();
    }

    private void initData() {

        for (int i=0;i<23;i++){
            mlist.add("第"+i+"个item");
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mlist = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("TestConApp");
        listView = (ListView) findViewById(R.id.listView);
        mAdapter = new CoordAdapter(this,mlist);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if(i==0){
                   startActivity(new Intent(CoordViewActivity.this,BlurActivity.class));
               }else if(i>5){
                   startActivity(new Intent(CoordViewActivity.this,TreeViewActivity.class));
               }else if (i<5){
//                   startActivity(new Intent(CoordViewActivity.this, NFCActivity.class));
               }else if (i==5){
                   startActivity(new Intent(CoordViewActivity.this, IndicatorActivity.class));
               }
            }
        });
    }
}
