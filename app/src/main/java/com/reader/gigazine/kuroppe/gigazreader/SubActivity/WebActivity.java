package com.reader.gigazine.kuroppe.gigazreader.SubActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.reader.gigazine.kuroppe.gigazreader.ObservableScrollView;
import com.reader.gigazine.kuroppe.gigazreader.R;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_main);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

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
        toolbar.inflateMenu(R.menu.share_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO: 共有処理
                return true;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
}
