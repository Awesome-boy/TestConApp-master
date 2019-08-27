package com.it.testconapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.it.testconapp.R;
import com.it.testconapp.utils.DHCoder;
import com.it.testconapp.widget.MyListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity implements MyListView.LoadListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    //甲方公钥
    private static byte[] publicKey1;
    //甲方私钥
    private static byte[] privateKey1;
    //甲方本地密钥
    private static byte[] key1;
    //乙方公钥
    private static byte[] publicKey2;
    //乙方私钥
    private static byte[] privateKey2;
    //乙方本地密钥
    private static byte[] key2;
    private MyListView myListView;
    private List<Integer> list = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initData();
//        threeDes();
        try {
            initLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
        createObserval();

    }

//    private void threeDes() {
//        String msg = "3DES加密解密案例";
//        //加密
//                 byte[] secretArr = SecretUtils.encryptMode(msg.getBytes());
//                 System.out.println("【加密后】：" + new String(secretArr));
//                    System.out.println("【加密后】：" +new String(secretArr));
//
//                 //解密
//                 byte[] myMsgArr = SecretUtils.decryptMode(secretArr);
//                 System.out.println("【解密后】：" + new String(myMsgArr));
//    }

    private void createObserval() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
                e.onNext("test1");
                e.onNext("test2");
                e.onNext("test3");
                e.onComplete();
            }
        })
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "======================onSubscribe");

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "======================onNext " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "======================onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "======================onComplete");
                    }
                });

//        Integer array[] = {1, 2, 3, 4};
        Observable.just(1,2,3,5)
                .subscribe(new Observer < Integer > () {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "=================onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "=================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "=================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "=================onComplete ");
                    }
                });



          Observable.intervalRange(2, 5, 2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer < Long > () {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "==============onSubscribe ");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "==============onNext " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }

    private void initData() {
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }


    }

    private void initLog() throws Exception {
        // TODO Auto-generated method stub
        initKey();
        Log.i("原文", "===甲方向乙方发送加密数据===");
        String input1 = "求知若饥，虚心若愚。";
        Log.i("原文", "原文:\n" + input1);
        Log.i("原文", "---使用甲方本地密钥对数据进行加密---");
        //使用甲方本地密钥对数据加密
        byte[] encode1 = DHCoder.encrypt(input1.getBytes(), key1);
        Log.i("原文", "加密:\n" + Base64.encodeToString(encode1, 0));
        Log.i("原文", "---使用乙方本地密钥对数据库进行解密---");
        //使用乙方本地密钥对数据进行解密
        byte[] decode1 = DHCoder.decrypt(encode1, key2);
        String output1 = new String(decode1);
        Log.i("原文", "解密:\n" + output1);

        Log.i("原文", "/~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~..~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~/");
//        initKey();
        Log.i("原文", "===乙方向甲方发送加密数据===");
        String input2 = "好好学习，天天向上。";
        Log.i("原文", "原文:\n" + input2);
        Log.i("原文", "---使用乙方本地密钥对数据进行加密---");
        //使用乙方本地密钥对数据进行加密
        byte[] encode2 = DHCoder.encrypt(input2.getBytes(), key2);
        Log.i("原文", "加密:\n" + Base64.encodeToString(encode2, 0));
        Log.i("原文", "---使用甲方本地密钥对数据进行解密---");
        //使用甲方本地密钥对数据进行解密
        byte[] decode2 = DHCoder.decrypt(encode2, key1);
        String output2 = new String(decode2);
        Log.i("原文", "解密:\n" + output2);
    }


    /**
     * 初始化密钥
     *
     * @throws Exception
     */
    public void initKey() throws Exception {
        //生成甲方密钥对
        Map<String, Object> keyMap1 = DHCoder.initKey();
        publicKey1 = DHCoder.getPublicKey(keyMap1);
        privateKey1 = DHCoder.getPrivateKey(keyMap1);
        Log.i("原文", "甲方公钥:\n" + Base64.encodeToString(publicKey1, 0));
        Log.i("原文", "甲方私钥:\n" + Base64.encodeToString(privateKey1, 0));
        //由甲方公钥产生本地密钥对
        Map<String, Object> keyMap2 = DHCoder.initKey(publicKey1);
        publicKey2 = DHCoder.getPublicKey(keyMap2);
        privateKey2 = DHCoder.getPrivateKey(keyMap2);
        Log.i("原文", "乙方公钥:\n" + Base64.encodeToString(publicKey2, 0));
        Log.i("原文", "乙方私钥:\n" + Base64.encodeToString(privateKey2, 0));
        key1 = DHCoder.getSecretKey(publicKey2, privateKey1);
        Log.i("原文", "甲方本地密钥:\n" + Base64.encodeToString(key1, 0));
        key2 = DHCoder.getSecretKey(publicKey1, privateKey2);
        Log.i("原文", "乙方本地密钥:\n" + Base64.encodeToString(key2, 0));
    }
    protected void showKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    private void initView() {
        myListView = (MyListView) findViewById(R.id.list_view);
        myListView.setInterface(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, list);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        startActivity(new Intent(MainActivity.this, CardViewActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, GreenDaoActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, BannerActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, TabLayoutActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, MarkWordActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, CustomerViewActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this, CreateOrderActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(MainActivity.this, OrderInfoActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public void onLoad() {
        //设置三秒延迟模仿延时获取数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //加载数据
                for (int j = 1; j < 11; j++) {

                    list.add(j);
                }
                //更新 数据
                adapter.notifyDataSetChanged();
                //加载完毕
                myListView.loadComplete();

            }
        }, 3000);
    }

    @Override
    public void pullLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 1; i < 20; i++) {
                    list.add(i + 1);
                }
                adapter.notifyDataSetChanged();
                myListView.loadComplete();

            }
        }, 2000);
    }
}
