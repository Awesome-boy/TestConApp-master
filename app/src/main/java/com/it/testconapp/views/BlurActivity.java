package com.it.testconapp.views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.testconapp.R;
import com.it.testconapp.utils.ChangeBgColorUtil;
import com.it.testconapp.utils.FglassUtil;

public class BlurActivity extends AppCompatActivity {
    private ImageView image;
    private TextView text;
    private TextView statusText;
    private CheckBox cb_fastBlur;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        initView();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {
        image = (ImageView) findViewById(R.id.picture);
        text = (TextView) findViewById(R.id.text);
        cb_fastBlur = (CheckBox) findViewById(R.id.main_cb_fastblur);
        statusText = addStatusText((ViewGroup) findViewById(R.id.controls));
        cb_fastBlur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long startMs = System.currentTimeMillis();
                if (isChecked) {
                    //设置高斯模糊
                    FglassUtil.blur(image, text, 2, 8);
                } else {
                    text.setBackgroundColor(Color.parseColor("#00ffffff"));
                }
                statusText.setText(System.currentTimeMillis() - startMs + "ms");
            }
        });
        Bitmap bitmap = ChangeBgColorUtil.drawableToBitmap(getResources().getDrawable(R.drawable.image));

        final RenderScript rs = RenderScript.create(this);
        final Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(8 /* e.g. 3.f */);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);
        Drawable drawable = new BitmapDrawable(bitmap);
        image.setBackground(drawable);

    }

    private TextView addStatusText(ViewGroup container) {
        TextView result = new TextView(this);
        result.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        result.setTextColor(0xFFFFFFFF);
        container.addView(result);
        return result;
    }
}
