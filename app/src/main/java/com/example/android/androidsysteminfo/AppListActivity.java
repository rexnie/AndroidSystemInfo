package com.example.android.androidsysteminfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private ListView mListView;
    private AppListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        findViewById(R.id.btn_pm_all_app).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.lv_app_list);
        mListView.setEmptyView(findViewById(R.id.tv_empty_view));

        mAdapter = new AppListAdapter(this);
        mAdapter.updateData(null);
        mListView.setAdapter(mAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pm_all_app:
                AppInfo appInfo = new AppInfo(this);
                List<AppInfo> list = appInfo.getAppInfo(AppInfo.ALL_APP);
                mAdapter.updateData(list);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}

class AppListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<AppInfo> mData;

    public AppListAdapter(Context c) {
        mInflater = LayoutInflater.from(c);
    }

    public void updateData(List<AppInfo> l) {
        if (mData != null) {
            mData.clear();
            mData = null;
        }
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
