package com.reader.gigazine.kuroppe.gigazreader.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.reader.gigazine.kuroppe.gigazreader.R;

public class NewArticleList extends Fragment {
    private String TAG = "NewArticleList";
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.article_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArticleListView articleListView = new ArticleListView(getActivity(), view);
        articleListView.ArticleList();
    }
}