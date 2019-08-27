package com.it.testconapp.views;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.it.testconapp.R;
import com.it.testconapp.utils.NfcUtils;

/**
 * Created by zt on 2019/4/8.
 */
public class NFCActivity extends AppCompatActivity {
    private NfcAdapter mNfcAdapter = null;//1.声明一个nfc的Adapter

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_nfc);
//        Intent intent = getIntent();
//        try {
//            String str = NfcUtils.readNFCFromTag(intent);
//            Log.i("nfc", "nfc记录" + str);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        initData();
    }

    private void initData() {
        NfcCheck();
    }

    private void NfcCheck() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);//3.获取nfc适配器
        if (mNfcAdapter == null) {
            return; //3.如果获取的mNfcAdapter=null，则说明该手机不支持nfc功能
        } else {
            //5.假如手机的nfc功能没有被打开。则跳到打开nfc功能的界面
            if (!mNfcAdapter.isEnabled()) {
                Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(setNfc);
            }

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启前台调度系统
        mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭前台调度系统
        mNfcAdapter.disableForegroundDispatch(this);
    }
}
