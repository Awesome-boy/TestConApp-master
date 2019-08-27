package com.it.testconapp.javabean;

import android.graphics.Bitmap;

/**
 * Created by zt on 2019/1/10.
 */
public class ImageInfor {

    private Bitmap imageId;
    private String name;

    public ImageInfor(Bitmap imageId, String name){
        this.imageId=imageId;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImageId() {
        return imageId;
    }

    public void setImageId(Bitmap imageId) {
        this.imageId = imageId;
    }


}
