package com.reader.gigazine.kuroppe.gigazreader;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.reader.gigazine.kuroppe.gigazreader.AsyncTaskCallbacks;
import com.reader.gigazine.kuroppe.gigazreader.Http.HttpAsyncTask;
import com.reader.gigazine.kuroppe.gigazreader.List.FavoriteList;
import com.reader.gigazine.kuroppe.gigazreader.MyPagerAdapter;
import com.reader.gigazine.kuroppe.gigazreader.R;

public class MainActivity extends AppCompatActivity implements AsyncTaskCallbacks {

    private String TAG = "MainActivity";
    private MyPagerAdapter pagerAdapter = null;
    private ViewPager viewPager = null;
    private int pageNumber = 0;
    private TabLayout tabLayout = null;
    private Snackbar snackbar = null;
    private View view;
    private ProgressDialog progressDialog;

    private void onHttpGet(int pageNumber){
        HttpAsyncTask http = new HttpAsyncTask(this, this, pageNumber);
        http.execute();
    }

    private void onPagerSettings(){
        if (pagerAdapter == null) {
            pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        }
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = this.findViewById(android.R.id.content);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        onHttpGet(pageNumber);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("読み込み中...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    public void onTaskFinished() {
        onPagerSettings();
        if (snackbar != null) snackbar.dismiss();
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onTaskCancelled() {
        Log.d(TAG,"キャンセル");
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void addTaskCallbacks() {
        pageNumber += 40;
        onHttpGet(pageNumber);
    }

    @Override
    public void updateTaskCallbacks() {
        snackbar = Snackbar.make(view, "読み込み中...", Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this,R.color.SeaGreen));
        snackbar.show();
        pageNumber = 0;
        onHttpGet(pageNumber);
    }

    private void onUpdate(){
        // Fragmentの再生成
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        FavoriteList favoriteList = new FavoriteList();
        trans.replace(R.id.fragment, favoriteList);
        trans.commit();
    }

}
