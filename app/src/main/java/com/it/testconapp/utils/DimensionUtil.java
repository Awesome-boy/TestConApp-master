package com.it.testconapp.utils;

import android.content.Context;

public class DimensionUtil {
    public static int dpToPx(Context context,int dp) {
        float density =context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }
}
