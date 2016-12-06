package com.example.android.androidsysteminfo;

import android.graphics.drawable.Drawable;

/**
 * Created by niedaocai on 04/12/2016.
 */

public class AppInfo {
    private static final String TAG = "AppInfo";
    private String mAppLabel;
    private Drawable mAppIcon;
    private String mPackageName;


    public AppInfo() {
    }

    public String getAppLabel() {
        return mAppLabel;
    }

    public void setAppLabel(String appName) {
        mAppLabel = appName;
    }


    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        mAppIcon = appIcon;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String toString() {
        return getPackageName() + "-->" + getAppLabel();
    }


}
