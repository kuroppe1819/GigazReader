package com.reader.gigazine.kuroppe.gigazreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.reader.gigazine.kuroppe.gigazreader.List.FavoriteList;
import com.reader.gigazine.kuroppe.gigazreader.List.NewArticleList;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlParameter;
import java.io.Serializable;
import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter implements Serializable {
    final int PAGE_COUNT = 2;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewArticleList();
            case 1:
                return new FavoriteList();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position){
            case 0:
                title = "最新記事";
                break;
            case 1:
                title = "お気に入り";
                break;
        }
        return title;
    }
}
