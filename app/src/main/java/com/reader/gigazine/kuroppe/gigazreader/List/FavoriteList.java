package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.reader.gigazine.kuroppe.gigazreader.CustomDialogFragment;
import com.reader.gigazine.kuroppe.gigazreader.PageChangeListener;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlList;

import java.security.PublicKey;

public class FavoriteList extends Fragment implements PageChangeListener{
    private View view;
    private ListView listView;
    private FavoriteAdapter favoriteAdapter;
    private FileIO fileIO;
    private Activity activity;
    private String TAG = "FavoriteList";

    public FavoriteList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        ArticleList articleList = new ArticleList();
        articleList.setTargetFragment(FavoriteList.this,0);
        view = inflater.inflate(R.layout.favorite_layout, null);
        this.activity = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fileIO = new FileIO(activity);
//        final HtmlParameter htmlParameter = new HtmlParameter();
        if (fileIO.Output() != null) {
            HtmlList htmlList = new HtmlList();
            favoriteAdapter = new FavoriteAdapter(activity, 0, htmlList.getFavorite(activity));
            listView = (ListView) activity.findViewById(R.id.favorite_list);
            listView.setAdapter(favoriteAdapter);

            // スワイプしたときにToolbarを隠す
            ViewCompat.setNestedScrollingEnabled(listView, true);

            //リスト項目が選択された時のイベントを追加
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    Log.d(TAG, String.valueOf(position));
                    Uri uri = Uri.parse(fileIO.Output().get(position).get(3).toString());
//                    Log.d(TAG, String.valueOf(uri));
                    //  外部ブラウザに飛ばす
                    GoogleCustomTabs customTabs = new GoogleCustomTabs(getActivity());
                    customTabs.ChromeStartUp(uri);
                    customTabs.unbindCustomTabsService();
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                    // ダイアログを表示
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    CustomDialogFragment dialogFragment = new CustomDialogFragment();
                    dialogFragment.setTargetFragment(FavoriteList.this,position);
                    dialogFragment.show(fm, "dialog");
                    return true;
                }
            });
        }
    }

    private void FavoriteListUpdate() {
        HtmlList htmlList = new HtmlList();
        favoriteAdapter.clear();
        favoriteAdapter.addAll(htmlList.getFavorite(activity));
        favoriteAdapter.notifyDataSetChanged();
        listView.invalidateViews();
    }

    @Override
    public void dialogCallback() {
        FavoriteListUpdate();
    }

    @Override
    public void ArticleListCallback() {

    }
}
