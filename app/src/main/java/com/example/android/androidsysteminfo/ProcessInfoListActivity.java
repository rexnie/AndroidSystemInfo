package com.example.android.androidsysteminfo;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProcessInfoListActivity extends AppCompatActivity {
    private static final String TAG = "ProcessInfoListActivity";
    private List<ProcessInfo> mProcessInfoList;
    private ActivityManager mActivityManager;
    private ListView mProcessInfoListView;
    private ProcessInfoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_info_list);
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        mProcessInfoListView = (ListView) findViewById(R.id.lv_process_info_list);

        mAdapter = new ProcessInfoListAdapter(this);
        mAdapter.updateData(getRunningProcessInfo());
        mProcessInfoListView.setAdapter(mAdapter);
    }

    private List<ProcessInfo> getRunningProcessInfo() {
        mProcessInfoList = new ArrayList<>();
        List<ActivityManager.RunningAppProcessInfo> apl =
                mActivityManager.getRunningAppProcesses();

        for (int i = 0; i < apl.size(); i++) {
            ActivityManager.RunningAppProcessInfo ap = apl.get(i);
            int pid = ap.pid;
            int uid = ap.uid;
            String processName = ap.processName;
            Debug.MemoryInfo[] memoryInfo =
                    mActivityManager.getProcessMemoryInfo(new int[]{pid});
            Log.d(TAG, "getRunningProcessInfo: " + memoryInfo.length);
            int memorySize = memoryInfo[0].getTotalPss() / 1024;

            ProcessInfo pi = new ProcessInfo();
            pi.setPid("pid=" + pid);
            pi.setUid("uid=" + uid);
            pi.setProcessName(processName);
            pi.setMemorySize(memorySize + "KB");

            mProcessInfoList.add(pi);
        }
        return mProcessInfoList;
    }
}

class ProcessInfoListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ProcessInfo> mData;

    public ProcessInfoListAdapter(Context c) {
        mInflater = LayoutInflater.from(c);
    }

    public void updateData(List<ProcessInfo> l) {
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
            convertView = mInflater.inflate(R.layout.process_info_item, null);
            vh = new ViewHolder();
            vh.pid = (TextView) convertView.findViewById(R.id.tv_pi_pid);
            vh.uid = (TextView) convertView.findViewById(R.id.tv_pi_uid);
            vh.memorySize = (TextView) convertView.findViewById(R.id.tv_pi_memory_size);
            vh.processName = (TextView) convertView.findViewById(R.id.tv_pi_process_name);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ProcessInfo item = (ProcessInfo) getItem(position);
        vh.pid.setText(item.getPid());
        vh.uid.setText(item.getUid());
        vh.memorySize.setText(item.getMemorySize());
        vh.processName.setText(item.getProcessName());

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
        TextView pid;
        TextView uid;
        TextView memorySize;
        TextView processName;
    }
}