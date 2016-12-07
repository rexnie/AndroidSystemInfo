package com.example.android.androidsysteminfo;

/**
 * Created by niedaocai on 16-12-7.
 */

public class ProcessInfo {
    private String mPid;
    private String mUid;
    private String mMemorySize;
    private String mProcessName;

    public String getPid() {
        return mPid;
    }

    public void setPid(String pid) {
        mPid = pid;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getMemorySize() {
        return mMemorySize;
    }

    public void setMemorySize(String memorySize) {
        mMemorySize = memorySize;
    }

    public String getProcessName() {
        return mProcessName;
    }

    public void setProcessName(String processName) {
        mProcessName = processName;
    }
}
