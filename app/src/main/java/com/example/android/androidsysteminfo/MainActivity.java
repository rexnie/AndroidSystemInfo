package com.example.android.androidsysteminfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_pm_test).setOnClickListener(this);
        findViewById(R.id.btn_am_test).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pm_test:
                startActivity(new Intent(this, AppListActivity.class));
                break;
            case R.id.btn_am_test:
                startActivity(new Intent(this, ProcessInfoListActivity.class));
                break;
        }
    }
}
