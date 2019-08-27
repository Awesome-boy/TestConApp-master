package com.it.testconapp.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.com.sky.downloader.greendao.UserDao;
import com.it.testconapp.MyApplication;
import com.it.testconapp.R;
import com.it.testconapp.javabean.Meizi;
import com.it.testconapp.javabean.User;
import com.it.testconapp.utils.MeiziDaoUtil;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG =GreenDaoActivity.class.getSimpleName();
    private Button mAdd,mDelete,mUpdate,mFind;
    private TextView mContext;
    private User mUser;
    private UserDao mUserDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        initView();
        initEvent();
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
    }

    private void initEvent() {
        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mFind.setOnClickListener(this);
    }

    private void initView() {
        mContext = (TextView) findViewById(R.id.textView);
        mAdd = (Button) findViewById(R.id.button);
        mDelete = (Button) findViewById(R.id.button2);
        mUpdate = (Button) findViewById(R.id.button3);
        mFind = (Button) findViewById(R.id.button4);
        TextSwitcher textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(GreenDaoActivity.this);
            }
        });
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            texts.add("循环....."+i);
        }
        textSwitcher.setText(texts.get(0));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                addDate();
                break;
            case R.id.button2:
                deleteDate();
                break;
            case R.id.button3:
                updateDate();
                break;
            case R.id.button4:
                findDate();
                break;
        }
    }

    /**
     * 增加数据
     */
    private void addDate() {
        MeiziDaoUtil.insertMeizi(new Meizi(null, "Google",
                "http://7xi8d6.48096_n.jpg"));
        //批量操作
//        List<Meizi> meiziList = new ArrayList<>();
//        meiziList.add(new Meizi(null, "HuaWei",
//                "http://7xi8d648096_n.jpg"));
//        meiziList.add(new Meizi(null, "Apple",
//                "http://7xi8d648096_n.jpg"));
//        meiziList.add(new Meizi(null, "MIUI",
//                "http://7xi8d648096_n.jpg"));
//        MeiziDaoUtil.insertMultMeizi(meiziList);

    }

    /**
     * 删除数据
     */
    private void deleteDate() {
        Meizi meizi1 = new Meizi();
        meizi1.set_id(1002l);
        MeiziDaoUtil.deleteMeizi(meizi1);
    }

    /**
     * 根据主键删除User
     *
     * @param id User的主键Id
     */
    public void deleteUserById(long id) {
        User user=mUserDao.queryBuilder().where(UserDao.Properties.Id.eq(id)).unique();
        if (user.getId()!=null){
            mUserDao.deleteByKey(id);
        }
    }

    /**
     * 更改数据
     */
    private void updateDate() {
        Meizi meizi = new Meizi();
        meizi.set_id(1002l);
        meizi.setUrl("http://baidu.jpg");
        MeiziDaoUtil.updateMeizi(meizi);
    }

    /**
     * 查找数据
     */
    private void findDate() {
        List<Meizi> meiziList1 = MeiziDaoUtil.queryAllMeizi();
        for (Meizi meizi2 : meiziList1) {
            Log.i(TAG, meizi2.toString());
        }

    }
}