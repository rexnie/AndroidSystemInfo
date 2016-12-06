package com.example.android.androidsysteminfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AppListActivity";

    public static final int SYSTEM_APP = 1;
    public static final int THIRD_APP = 2;
    public static final int SDCARD_APP = 3;
    public static final int ALL_APP = -1;

    private ListView mListView;
    private AppListAdapter mAdapter;
    private PackageManager mPackageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        findViewById(R.id.btn_pm_all_app).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.lv_app_list);
        mListView.setEmptyView(findViewById(R.id.tv_empty_view));

        mPackageManager = getPackageManager();

        mAdapter = new AppListAdapter(this);
        mAdapter.updateData(null);
        mListView.setAdapter(mAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pm_all_app:
                mAdapter.updateData(getAppInfo(ALL_APP));
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private AppInfo makeAppInfo(ApplicationInfo app) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppLabel((String) app.loadLabel(mPackageManager));
        appInfo.setAppIcon(app.loadIcon(mPackageManager));
        appInfo.setPackageName(app.packageName);
        return appInfo;
    }

    private List<AppInfo> getAppInfo(int flag) {
        List<ApplicationInfo> applicationInfoList = mPackageManager.getInstalledApplications(
                PackageManager.MATCH_UNINSTALLED_PACKAGES);
        List<AppInfo> appInfoList = new ArrayList<>();
        switch (flag) {

            // 列出所有安装的App
            case ALL_APP:
                for (ApplicationInfo ai : applicationInfoList) {
                    appInfoList.add(makeAppInfo(ai));
                }
                break;

            // 列出所有系统 App
            case SYSTEM_APP:
                for (ApplicationInfo ai : applicationInfoList) {
                    if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        appInfoList.add(makeAppInfo(ai));
                    }
                }
                break;

            // 列出所有第三方App
            case THIRD_APP:
                for (ApplicationInfo ai : applicationInfoList) {
                    if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        // 非系统 App
                        appInfoList.add(makeAppInfo(ai));
                    } else if ((ai.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
                        // 原系统的App经过升级后的 app 也是第三方App
                        appInfoList.add(makeAppInfo(ai));
                    }
                }
                break;

            // 列出所有安装在Sdcard App
            case SDCARD_APP:
                for (ApplicationInfo ai : applicationInfoList) {
                    if ((ai.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                        appInfoList.add(makeAppInfo(ai));
                    }
                }
                break;
            default:
                Log.e(TAG, "getAppInfo: unknown flag");
                break;
        }
        return appInfoList;
    }
}

class AppListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<AppInfo> mData;

    public AppListAdapter(Context c) {
        mInflater = LayoutInflater.from(c);
    }

    public void updateData(List<AppInfo> l) {
        if (l == null) {
            mData = new ArrayList<>();
        } else {
            mData = l;
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.app_item, null);
            vh = new ViewHolder();
            vh.appIcon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
            vh.appLabel = (TextView) convertView.findViewById(R.id.tv_app_label);
            vh.appPackageName = (TextView) convertView.findViewById(R.id.tv_app_package_name);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        AppInfo item = (AppInfo) getItem(position);
        vh.appIcon.setImageDrawable(item.getAppIcon());
        vh.appLabel.setText(item.getAppLabel());
        vh.appPackageName.setText(item.getPackageName());

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public final class ViewHolder {
        ImageView appIcon;
        TextView appLabel;
        TextView appPackageName;
    }
}
