package com.example.android.androidsysteminfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niedaocai on 04/12/2016.
 */

public class AppInfo {
    private static final String TAG = "AppInfo";
    private String mAppLabel;
    private Drawable mAppIcon;
    private String mPackageName;
    private PackageManager mPackageManager;

    public static final int SYSTEM_APP = 1;
    public static final int THIRD_APP = 2;
    public static final int SDCARD_APP = 3;
    public static final int ALL_APP = -1;

    public AppInfo(Context context) {
        mPackageManager = context.getPackageManager();
    }

    private AppInfo() {
    }

    private AppInfo getInstance() {
        if (mPackageManager == null) {
            Log.e(TAG, "getInstance: mPackageManager is null");
            return null;
        } else {
            return new AppInfo();
        }
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

    private AppInfo makeAppInfo(ApplicationInfo app) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppLabel((String) app.loadLabel(mPackageManager));
        appInfo.setAppIcon(app.loadIcon(mPackageManager));
        appInfo.setPackageName(app.packageName);
        return appInfo;
    }

    public List<AppInfo> getAppInfo(int flag) {
        List<ApplicationInfo> applicationInfos =
                mPackageManager.getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        List<AppInfo> appInfos = new ArrayList<>();
        switch (flag) {
            case ALL_APP:
                for (ApplicationInfo ai : applicationInfos) {
                    appInfos.add(makeAppInfo(ai));
                }
                break;
            case SYSTEM_APP:
                for (ApplicationInfo ai : applicationInfos) {
                    if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        appInfos.add(makeAppInfo(ai));
                    }
                }
                break;
            case THIRD_APP:
                for (ApplicationInfo ai : applicationInfos) {
                    if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        appInfos.add(makeAppInfo(ai));
                    } else if ((ai.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
                        appInfos.add(makeAppInfo(ai));
                    }
                }
                break;
            case SDCARD_APP:
                for (ApplicationInfo ai : applicationInfos) {
                    if ((ai.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                        appInfos.add(makeAppInfo(ai));
                    }
                }
                break;
            default:
                Log.e(TAG, "getAppInfo: unknown flag");
                break;
        }
        return appInfos;
    }
}
