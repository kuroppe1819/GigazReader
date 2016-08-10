package com.reader.gigazine.kuroppe.gigazreader.List;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlList;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlParameter;

public class FavoriteList extends Fragment{
    private View view;
    private ListView listView;
    private FavoriteAdapter favoriteAdapter;
    private String TAG = "FavoriteList";

    public interface Callback{
        public void callbackMethod();
    }

    private Callback callback;

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    // ListViewの更新
    public void onUpdate(){
//        favoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.favorite_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FileIO fileIO = new FileIO(getActivity());
        final HtmlParameter htmlParameter = new HtmlParameter();
        if (fileIO.Output() != null) {
            HtmlList htmlList = new HtmlList();
            favoriteAdapter = new FavoriteAdapter(getActivity(), 0, htmlList.getFavorite(getActivity()));
            listView = (ListView) getActivity().findViewById(R.id.favorite_list);
            listView.setAdapter(favoriteAdapter);

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
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        view = null;
        listView.setAdapter(null);
        favoriteAdapter.clear();
    }
}
