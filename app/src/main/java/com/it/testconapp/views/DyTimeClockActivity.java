package com.it.testconapp.views;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.it.testconapp.R;
import com.it.testconapp.widget.DateTimeView;

public class DyTimeClockActivity  extends AppCompatActivity {
    private DateTimeView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dy_time_clock);
        initView();
    }

    private void initView() {
       time= (DateTimeView) findViewById(R.id.time_colock);
       time.setName("某");
    }
}
