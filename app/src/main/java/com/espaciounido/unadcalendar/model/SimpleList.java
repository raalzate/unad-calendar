package com.espaciounido.unadcalendar.model;

import android.graphics.drawable.Drawable;

/**
 * Created by rauloko on 22/03/15.
 */
public  class SimpleList {
    public String name;
    public boolean selected;
    public int bgColor;
    public int textColor;
    public Drawable img;

    public SimpleList(String name, boolean selected) {
        this.name = name;
        this.selected = selected;

    }
}
