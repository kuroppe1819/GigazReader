package com.reader.gigazine.kuroppe.gigazreader.List;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.reader.gigazine.kuroppe.gigazreader.PageChangeListener;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlList;
import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlParameter;

public class ArticleList extends Fragment{
    private String TAG = "ArticleList";
    private View view;
    private ListView listView;
    private  PageChangeListener pageChangeListener = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PageChangeListener){
            pageChangeListener = (PageChangeListener) context;
        }else{
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.article_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HtmlList htmlList = new HtmlList();
        final FileIO fileIO = new FileIO(getActivity());
        final HtmlParameter htmlParameter = new HtmlParameter();
        final ArticleAdapter articleAdapter = new ArticleAdapter(getActivity(), 0, htmlList.getArticle());
        listView = (ListView) getActivity().findViewById(R.id.article_list);
        listView.setAdapter(articleAdapter);

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
                new GoogleCustomTabs(uri, getActivity());
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                // データの保存
                fileIO.Input(position, htmlParameter);
                // ページを更新
                pageChangeListener.onPageChange();
                final Snackbar snackbar = Snackbar.make(view, R.string.add_favorite, Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.SeaGreen));
                snackbar.show();
                return true;
            }
        });
    }
}