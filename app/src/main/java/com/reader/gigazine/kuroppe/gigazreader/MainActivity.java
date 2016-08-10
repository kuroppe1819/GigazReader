package com.reader.gigazine.kuroppe.gigazreader;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.reader.gigazine.kuroppe.gigazreader.Http.HttpAsyncTask;

public class MainActivity extends AppCompatActivity implements AsyncTaskCallbacks, View.OnClickListener{
    private String appTitle = "Gigazine";
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpAsyncTask http = new HttpAsyncTask(this,this);
        http.execute();
    }

    @Override
    public void onTaskFinished() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitle(appTitle);
        setSupportActionBar(toolbar);
        onPagerSettings();
    }

    @Override
    public void onTaskCancelled() {
        Log.d(TAG,"キャンセル");
    }

    private void onPagerSettings(){
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "押されたよ！");
    }
}
