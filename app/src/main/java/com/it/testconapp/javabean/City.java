package com.it.testconapp.javabean;

import java.io.Serializable;

/**
 * Created by zt on 2019/5/16.
 */
public class City implements Serializable {

    private static final long serialVersionUID = 2457463820339316299L;
    /**
     * cityName : 鞍山
     * firstLetter : A
     */

    private String cityName;
    private String firstLetter;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getCityName() {
        return cityName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }
}
