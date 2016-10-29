package com.reader.gigazine.kuroppe.gigazreader.SubActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlParameter;
import com.reader.gigazine.kuroppe.gigazreader.List.FavoriteListFragment;
import com.reader.gigazine.kuroppe.gigazreader.List.FileIO;
import com.reader.gigazine.kuroppe.gigazreader.ObservableScrollView;
import com.reader.gigazine.kuroppe.gigazreader.R;

public class WebActivity extends AppCompatActivity {
    private String TAG = "WebActivity";
    private FileIO fileIO;
    private String url;
    private Intent intent;
    private int position;
    private boolean favorite_frag = false;

    private void ToolbarSetting(final String title, final String url) {
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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                HtmlParameter htmlParameter = new HtmlParameter();
                switch (item.getItemId()) {
                    case R.id.share:
                        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(WebActivity.this);
                        builder.setChooserTitle(R.string.article_share);
                        builder.setText(title + " " + url);
                        builder.setType("text/plain");
                        builder.startChooser();
                        break;
                    case R.id.favorite_on:
                        setResult(RESULT_OK, intent);
                        fileIO.Input(position, htmlParameter);
                        invalidateOptionsMenu();
                        break;
                    case R.id.favorite_off:
                        setResult(RESULT_OK, intent);
                        for (int i = 0; i < fileIO.Output().size(); i++) {
                            if (url.equals(fileIO.Output().get(i).get(3).toString()))
                                fileIO.PreferencesDelete(i);
                        }
                        invalidateOptionsMenu();
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_main);
        intent = new Intent();
        fileIO = new FileIO(this);
        url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        this.position = getIntent().getIntExtra("position", 0);
        addFavoriteCheck(url);
        ToolbarSetting(title, url);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    private void addFavoriteCheck(String url) {
        if (fileIO.Output() != null) {
            for (int i = 0; i < fileIO.Output().size(); i++) {
                if (url.equals(fileIO.Output().get(i).get(3).toString())) favorite_frag = true;
            }
        }
    }
}
