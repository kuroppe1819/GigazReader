package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlList;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlParameter;

public class ArticleListView {
    private Activity activity;
    private android.widget.ListView listView;
    private View view;
    private String TAG = "ArticleListView";

    public ArticleListView(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public void ArticleList() {
        HtmlList htmlList = new HtmlList();
        final HtmlParameter htmlParameter = new HtmlParameter();
        final HtmlAdapter htmlAdapter = new HtmlAdapter(activity, 0, htmlList.getList());
        listView = (ListView) activity.findViewById(R.id.article_list);
        listView.setAdapter(htmlAdapter);

        // スワイプしたときにToolbarを隠す
        ViewCompat.setNestedScrollingEnabled(listView, true);

        //リスト項目が選択された時のイベントを追加
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, String.valueOf(position));
                Uri uri = Uri.parse(htmlParameter.getUrl().get(position));
                Log.d(TAG, String.valueOf(uri));
                //  外部ブラウザに飛ばす
                new GoogleCustomTabs(uri, activity);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(activity, "お気に入りに追加", Toast.LENGTH_LONG).show();
                return true;
            }
        }); // End setOnItemLongClickListener
    }

    public void FavoriteList(){

    }
}