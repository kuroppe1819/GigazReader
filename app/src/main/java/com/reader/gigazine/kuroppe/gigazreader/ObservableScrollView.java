package com.reader.gigazine.kuroppe.gigazreader;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

    public interface ScrollViewListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    // onScrollChanged が複数回呼び出されるのを防止するために利用
    private int lastt = 0;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // 高速にスクロールした場合、画面上部で t, oldt がマイナスとなる場合は処理をスキップ
        if (scrollViewListener == null || lastt == t || t < 0 || oldt < 0) return;

        lastt = t;
        scrollViewListener.onScrollChanged(l, t, oldl, oldt);
    }

}