package com.it.testconapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.it.testconapp.R;
import com.it.testconapp.widget.CodeInput;
import com.it.testconapp.widget.VerificationCodeView;

import java.util.Arrays;
import java.util.List;

import test.jinesh.captchaimageviewlib.CaptchaImageView;

public class CaptchaImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CaptchaImageActivity.class.getSimpleName();
    private Button bt_refresh;
    private CaptchaImageView captchaImageView;
    private VerificationCodeView codeView;
    private CodeInput input_code;
    private SimpleMarqueeView<String> marqueeView;
    private final String[] views = {"View 1", "View 2", "View 3", "View 4", "View 5", "View 6",
            "View 7", "View 8", "View 9", "View 10", "View 11", "View 12"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha_image);
        initView();
    }

    private void initView() {
        final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");

        bt_refresh = (Button) findViewById(R.id.bt_refresh);
        captchaImageView = (CaptchaImageView) findViewById(R.id.captchaImageView);
        codeView = (VerificationCodeView) findViewById(R.id.codeView);
        marqueeView = (SimpleMarqueeView<String>) findViewById(R.id.marqueeView1);
        SimpleMF<String> marqueeFactory = new SimpleMF(this);
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
        marqueeView.setOnItemClickListener(new OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(TextView mView, String mData, int mPosition) {
                /**
                 * 注意：
                 * 当MarqueeView有子View时，mView：当前显示的子View，mData：mView所填充的数据，mPosition：mView的索引
                 * 当MarqueeView无子View时，mView：null，mData：null，mPosition：－1
                 */
                Toast.makeText(CaptchaImageActivity.this, String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
            }
        });
        input_code = (CodeInput) findViewById(R.id.input_code);
        input_code.setInputType(InputType.TYPE_CLASS_NUMBER);
        input_code.setCodeReadyListener(new CodeInput.codeReadyListener() {
            @Override
            public void onCodeReady(Character[] code) {
                // Code has been entered ....
                input_code.setCode(Arrays.toString(code));
                Log.i(TAG,"CURRENT WORDS---"+Arrays.toString(code));
//                Toast.makeText(CaptchaImageActivity.this,"code entered is : "+ Arrays.toString(code),Toast.LENGTH_SHORT).show();
            }
        });

        ListView mListView = (ListView) findViewById(R.id.listview);

        MAdapter adapter = new MAdapter(this, 0);
        adapter.addAll(views);
        mListView.setAdapter(adapter);
        codeView.setOnClickListener(this);
        bt_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_refresh:
                startActivity(new Intent(this,RelogActivity.class));
                captchaImageView.regenerate();
                Log.i(TAG,"CURRENT WORDS---"+captchaImageView.getCaptchaCode());
                break;
            case R.id.codeView:
                codeView.refreshCode();
                Log.i(TAG,"CURRENT WORDS---"+codeView.getvCode());
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    protected void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    private class MAdapter extends ArrayAdapter<String> {
        private final String[] labels = {"step1", "step2", "step3", "step4", "step5","step6"};
        public MAdapter(Context context, int i) {
            super(context,i);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mLabel.setText(getItem(position));

            holder.mStepsView.setCompletedPosition(position % labels.length)
                    .setLabels(labels)
                    .setBarColorIndicator(
                            getContext().getResources().getColor(R.color.material_blue_grey_800))
                    .setProgressColorIndicator(getContext().getResources().getColor(R.color.colorAccent))
                    .setLabelColorIndicator(getContext().getResources().getColor(R.color.orange))
                    .drawView();

            return convertView;
        }
         class ViewHolder {

            TextView mLabel;
            StepsView mStepsView;

            public ViewHolder(View view) {
                mLabel = (TextView) view.findViewById(R.id.label);
                mStepsView = (StepsView) view.findViewById(R.id.stepsView);
            }
        }
    }
}
