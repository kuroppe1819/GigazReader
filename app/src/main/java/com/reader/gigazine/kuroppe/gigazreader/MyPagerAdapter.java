package com.reader.gigazine.kuroppe.gigazreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.reader.gigazine.kuroppe.gigazreader.List.FavoriteListFragment;
import com.reader.gigazine.kuroppe.gigazreader.List.ArticleListFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private ArticleListFragment articleListFragment;
    private FavoriteListFragment favoriteListFragment;
    private FragmentManager fm;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        articleListFragment = new ArticleListFragment();
        favoriteListFragment = new FavoriteListFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return articleListFragment;
            case 1:
                return favoriteListFragment;
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
        switch (position) {
            case 0:
                title = "TOP";
                break;
            case 1:
                title = "お気に入り";
                break;
        }
        return title;
    }
}
