package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.reader.gigazine.kuroppe.gigazreader.Dialog.FavoriteDialogFragment;
import com.reader.gigazine.kuroppe.gigazreader.PageChangeListener;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlList;
import com.reader.gigazine.kuroppe.gigazreader.SubActivity.WebActivity;

public class FavoriteListFragment extends Fragment implements PageChangeListener {
    private View view;
    private ListView listView;
    private FavoriteAdapter favoriteAdapter;
    private FileIO fileIO;
    private Activity activity;
    private String TAG = "FavoriteListFragment";

    public void FavoriteListUpdate() {
        HtmlList htmlList = new HtmlList();
        favoriteAdapter.clear();
        favoriteAdapter.addAll(htmlList.getFavorite(activity));
        favoriteAdapter.notifyDataSetChanged();
        listView.invalidateViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArticleListFragment articleListFragment = new ArticleListFragment();
        articleListFragment.setTargetFragment(FavoriteListFragment.this, 0);
        view = inflater.inflate(R.layout.favorite_layout, null);
        this.activity = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fileIO = new FileIO(activity);
        if (fileIO.Output() != null) {
            HtmlList htmlList = new HtmlList();
            favoriteAdapter = new FavoriteAdapter(activity, 0, htmlList.getFavorite(activity));
            listView = (ListView) activity.findViewById(R.id.favorite_list);
            listView.setAdapter(favoriteAdapter);
            /** スワイプしたときにToolbarを隠す **/
            ViewCompat.setNestedScrollingEnabled(listView, true);
            /** WebActivityに遷移 **/
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String url = fileIO.Output().get(position).get(3).toString();
                    String title = fileIO.Output().get(position).get(0).toString();
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", title);
                    startActivityForResult(intent, 5678);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                    /** ダイアログを表示 **/
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FavoriteDialogFragment dialogFragment = new FavoriteDialogFragment();
                    dialogFragment.setTargetFragment(FavoriteListFragment.this, position);
                    dialogFragment.show(fm, "dialog");
                    return true;
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        Log.d(TAG, String.valueOf(requestCode));
        FavoriteListUpdate();
    }

    @Override
    public void dialogCallback() {
        FavoriteListUpdate();
    }
}
