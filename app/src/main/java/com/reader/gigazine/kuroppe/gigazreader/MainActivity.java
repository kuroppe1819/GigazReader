package com.reader.gigazine.kuroppe.gigazreader;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.reader.gigazine.kuroppe.gigazreader.Dialog.SearchDialogFragment;
import com.reader.gigazine.kuroppe.gigazreader.Dialog.SearchParameter;
import com.reader.gigazine.kuroppe.gigazreader.http.HttpAsyncTask;
import com.reader.gigazine.kuroppe.gigazreader.List.FavoriteListFragment;
import com.reader.gigazine.kuroppe.gigazreader.SubActivity.LicensesActivity;

public class MainActivity extends AppCompatActivity implements AsyncTaskCallbacks {
    private String TAG = "MainActivity";
    private static final int ArticleListFragmentCode = 1234;
    private MyPagerAdapter pagerAdapter = null;
    private ViewPager viewPager = null;
    private int pageNumber = 0;
    private TabLayout tabLayout = null;
    private Snackbar snackbar = null;
    private View view;
    private ProgressDialog progressDialog;
    private AdView mAdView;


    private void onHttpGet(int pageNumber) {
        HttpAsyncTask http = new HttpAsyncTask(this, this, pageNumber);
        http.execute();
    }

    private void onPagerSettings() {
        if (pagerAdapter == null) {
            pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        }
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** 広告の表示 **/
        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_unit_id));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(String.valueOf(R.string.device_id))
                .build();
        mAdView.loadAd(adRequest);
        view = this.findViewById(android.R.id.content);
        /** ツールバーの設定 **/
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.White));
        setSupportActionBar(toolbar);
        /** プログレスダイアログ **/
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("読み込み中…");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        onHttpGet(pageNumber);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, LicensesActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                FragmentManager fm = getSupportFragmentManager();
                SearchDialogFragment dialogFragment = new SearchDialogFragment();
                dialogFragment.show(fm, "dialog");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskFinished() {
        onPagerSettings();
        if (snackbar != null) snackbar.dismiss();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onTaskCancelled() {
        Log.d(TAG, "キャンセル");
        if (progressDialog != null) {
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
    public void updateTaskCallbacks(int position) {
        SearchParameter searchParameter = new SearchParameter();
        String[] menuItemsUrl = getResources().getStringArray(R.array.menu_items_url);
        searchParameter.setCategoryUrl(menuItemsUrl[position]);
        if (position != 0) {
            String[] menuItemsName = getResources().getStringArray(R.array.menu_items);
            searchParameter.setCategoryName(menuItemsName[position - 1]);
        }else {
            searchParameter.onResetParameter();
        }
        snackbar = Snackbar.make(view, R.string.loading, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.SeaGreen));
        snackbar.show();
        pageNumber = 0;
        onHttpGet(pageNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO カテゴリ検索
//        getMenuInflater().inflate(R.menu.search_menu, menu);
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, String.valueOf(requestCode + " " + resultCode));
        if (requestCode == ArticleListFragmentCode && resultCode == RESULT_OK) {
            /** Fragmentの再生成 **/
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction trans = fm.beginTransaction();
            FavoriteListFragment favoriteListFragment = new FavoriteListFragment();
            trans.replace(R.id.fragment, favoriteListFragment);
            trans.commit();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
