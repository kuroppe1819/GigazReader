package com.reader.gigazine.kuroppe.gigazreader.List;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlList;
import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlParameter;

public class FavoriteList extends Fragment{
    private View view;
    private ListView listView;
    private FavoriteAdapter favoriteAdapter;
    private FileIO fileIO;
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
        fileIO = new FileIO(getActivity());
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
//                    Log.d(TAG, String.valueOf(position));
                    Uri uri = Uri.parse(htmlParameter.getUrl().get(position));
//                    Log.d(TAG, String.valueOf(uri));
                    //  外部ブラウザに飛ばす
                    new GoogleCustomTabs(uri, getActivity());
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                    onDialog(position);
                    return true;
                }
            });
        }
    }

    private void onDialog(final int position){
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.dialog_message)
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OKのとき
                        fileIO.PreferencesDelete(position);
                        final Snackbar snackbar = Snackbar.make(view, R.string.delete_favorite, Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                        snackbar.show();
                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
//        view = null;
//        listView.setAdapter(null);
//        favoriteAdapter.clear();
    }
}
