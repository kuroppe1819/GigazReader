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
import android.widget.Toast;

import com.reader.gigazine.kuroppe.gigazreader.Dialog.SearchDialogFragment;
import com.reader.gigazine.kuroppe.gigazreader.Dialog.SearchParameter;
import com.reader.gigazine.kuroppe.gigazreader.List.FavoriteListFragment;
import com.reader.gigazine.kuroppe.gigazreader.SubActivity.LicensesActivity;
import com.reader.gigazine.kuroppe.gigazreader.http.HttpRxAndroid;


public class MainActivity extends AppCompatActivity implements RxAndroidCallbacks {
    private String TAG = "MainActivity";
    private static final int ArticleListFragmentCode = 1234;
    private MyPagerAdapter pagerAdapter = null;
    private ViewPager viewPager = null;
    private int pageNumber = 0;
    private TabLayout tabLayout = null;
    private Snackbar snackbar = null;
    private View view;
    private ProgressDialog progressDialog;

    private void onHttpGet(int pageNumber) {
        HttpRxAndroid http = new HttpRxAndroid(this, this, pageNumber);
        http.HttpConnect();
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

    private void onSnackBarSetting(){
        snackbar = Snackbar.make(view, R.string.loading, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.SeaGreen));
        snackbar.show();
    }

    private void onInitialization(){
        pagerAdapter = null;
        viewPager = null;
        pageNumber = 0;
        tabLayout = null;
        snackbar = null;
        View view = null;
        progressDialog = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, String.valueOf(savedInstanceState));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = this.findViewById(android.R.id.content);
        /** ツールバーの設定 **/
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.White));
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            /** プログレスダイアログ **/
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("読み込み中…");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            onHttpGet(pageNumber);
        }else{
            onPagerSettings();
        }
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
        Log.d(TAG, String.valueOf("onTaskFinished"));
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
        Toast.makeText(this, R.string.timeout, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addTaskCallbacks() {
        Log.d(TAG, "addTaskCallbacks");
        pageNumber += 40;
        onHttpGet(pageNumber);
    }

    @Override
    public void updateTaskCallbacks(int position) {
        Log.d(TAG, String.valueOf(position));
        SearchParameter searchParameter = new SearchParameter();
        String[] menuItemsUrl = getResources().getStringArray(R.array.menu_items_url);
        searchParameter.setCategoryUrl(menuItemsUrl[position]);
        switch (position){
            case 0:
                searchParameter.onResetParameter();
                break;
            case 1:
                searchParameter.onResetParameter();
                onSnackBarSetting();
                break;
            default:
                String[] menuItemsName = getResources().getStringArray(R.array.menu_items);
                searchParameter.setCategoryName(menuItemsName[position - 1]);
                onSnackBarSetting();
                break;
        }
        pageNumber = 0;
        onHttpGet(pageNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO カテゴリ検索
        getMenuInflater().inflate(R.menu.search_menu, menu);
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
}
