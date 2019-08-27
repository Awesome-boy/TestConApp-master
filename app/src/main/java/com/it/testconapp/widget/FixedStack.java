package com.it.testconapp.widget;

import java.util.Stack;

/**
 * Created by zt on 2019/4/10.
 */
public class FixedStack<T> extends Stack<T> {

    int maxSize = 0;

    @Override
    public T push(T object) {
        if (maxSize > size()) {
            return super.push(object);
        }

        return object;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
