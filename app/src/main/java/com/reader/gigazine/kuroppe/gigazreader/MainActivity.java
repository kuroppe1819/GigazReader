package com.reader.gigazine.kuroppe.gigazreader;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.reader.gigazine.kuroppe.gigazreader.Http.HttpAsyncTask;
import com.reader.gigazine.kuroppe.gigazreader.List.FavoriteList;

public class MainActivity extends AppCompatActivity implements AsyncTaskCallbacks,PageChangeListener{

    private String TAG = "MainActivity";
    private MyPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private int pageNumber = 0;

    private void onUpdate(){
        // Fragmentの再生成
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        FavoriteList favoriteList = new FavoriteList();
        trans.replace(R.id.fragment, favoriteList);
        trans.commit();
    }

    private void onPagerSettings(){
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpAsyncTask http = new HttpAsyncTask(this, this, pageNumber);
        http.execute();
    }

    @Override
    public void onTaskFinished() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        onPagerSettings();
    }

    @Override
    public void onTaskCancelled() {
        Log.d(TAG,"キャンセル");
    }

    @Override
    public void addTaskCallbacks() {
        Log.d(TAG,"");
        pageNumber += 40;
        HttpAsyncTask http = new HttpAsyncTask(this, this, pageNumber);
        http.execute();
    }

    @Override
    public void dialogCallback() {

    }

    @Override
    public void ArticleListCallback() {
        onUpdate();
    }
}
