package com.espaciounido.unadcalendar.utils;

import android.content.res.Resources;

/**
 * Created by MyMac on 20/09/16.
 */
public abstract class ViewUtils {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
