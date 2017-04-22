package com.reader.gigazine.kuroppe.gigazreader.SubActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.reader.gigazine.kuroppe.gigazreader.List.ArticleData;
import com.reader.gigazine.kuroppe.gigazreader.List.FileIO;
import com.reader.gigazine.kuroppe.gigazreader.ObservableScrollView;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.http.HttpArticleDetails;
import com.reader.gigazine.kuroppe.gigazreader.util.ParseHtmlUtil;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WebActivity extends AppCompatActivity implements Observer<Document> {
    private String TAG = "WebActivity";
    private BroadcastReceiver broadcastReceiver;
    private WebView webView;
    private boolean favorite_frag = false;
    private AdView mAdView;
    private String content;

    private ArrayList<String> addInputData(ArticleData articleData) {
        ArrayList<String> arrayArticle = new ArrayList<>();
        arrayArticle.add(articleData.getTitle());
        arrayArticle.add(articleData.getCategory());
        arrayArticle.add(articleData.getImgs());
        arrayArticle.add(articleData.getUrl());
        arrayArticle.add(articleData.getTime());
        return arrayArticle;
    }

    private void onExistCheck(String url, FileIO fileIO) {
        if (fileIO.Output() != null) {
            for (int i = 0; i < fileIO.Output().size(); i++) {
                if (url.equals(fileIO.Output().get(i).get(3).toString())) {
                    favorite_frag = true;
                }
            }
        }
    }

    private void delFavoriteList(String url, FileIO fileIO) {
        for (int i = 0; i < fileIO.Output().size(); i++) {
            if (url.equals(fileIO.Output().get(i).get(3).toString()))
                fileIO.PreferencesDelete(i);
        }
    }

    private Toolbar ToolbarSetting(final String title) {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scrollview);
        scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                int diff = t - oldt; // 縦軸の移動量
                int translationY = 0 < diff
                        ? Math.max((int) toolbar.getY() - diff, -toolbar.getHeight())
                        : Math.min((int) toolbar.getY() - diff, 0);
                toolbar.setTranslationY(translationY);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return toolbar;
    }

    private void showAdView() {
        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_unit_id));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(String.valueOf(R.string.device_id))
                .build();
        mAdView.loadAd(adRequest);
    }

    private void webViewSetUp() {
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 右端の余白を削除する
        webView.loadData(content, "text/html; charset=utf-8", "UTF-8");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_main);
//        if (!BuildConfig.DEBUG) {
//            showAdView();
//        }

        final Intent intent = new Intent();
        final FileIO fileIO = new FileIO(this);
        final ArticleData articleData = (ArticleData) getIntent().getSerializableExtra("article");
        Log.d(TAG, articleData.getTitle());
        final ArrayList<String> arrayArticle = addInputData(articleData);
        onExistCheck(articleData.getUrl(), fileIO);
        Toolbar toolbar = ToolbarSetting(articleData.getTitle());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(WebActivity.this);
                        builder.setChooserTitle(R.string.article_share);
                        builder.setText(articleData.getTitle() + " " + articleData.getUrl());
                        builder.setType("text/plain");
                        builder.startChooser();
                        break;
                    case R.id.favorite_on:
                        setResult(RESULT_OK, intent);
                        fileIO.Input(arrayArticle);
                        invalidateOptionsMenu();
                        break;
                    case R.id.favorite_off:
                        setResult(RESULT_OK, intent);
                        delFavoriteList(articleData.getUrl(), fileIO);
                        invalidateOptionsMenu();
                        break;
                }
                return true;
            }
        });

        // ブロードキャストリスナー
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.d(TAG, "SCREEN_OFF");
                    webView.reload();
                }
            }
        };
        // リスナーの登録
        this.registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));

        // Http通信
        HttpArticleDetails.request(articleData.getUrl())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, String.valueOf(favorite_frag));
        int id = item.getItemId();
        boolean result = true;
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem offFavoriteMenu = (MenuItem) menu.findItem(R.id.favorite_on);
        MenuItem onFavoriteMenu = (MenuItem) menu.findItem(R.id.favorite_off);
        if (!favorite_frag) {
            onFavoriteMenu.setVisible(false);
            offFavoriteMenu.setVisible(true);
            favorite_frag = true;
        } else {
            onFavoriteMenu.setVisible(true);
            offFavoriteMenu.setVisible(false);
            favorite_frag = false;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
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
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
        if (webView != null) {
            webView.destroy();
        }
        this.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onNext(Document document) {
        content = ParseHtmlUtil.getContentHtml(document);
    }

    @Override
    public void onCompleted() {
        webViewSetUp();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(this, R.string.timeout, Toast.LENGTH_LONG).show();
    }
}
