package com.reader.gigazine.kuroppe.gigazreader.SubActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
//        toolbar.setSubtitle(url);

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
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }
}
