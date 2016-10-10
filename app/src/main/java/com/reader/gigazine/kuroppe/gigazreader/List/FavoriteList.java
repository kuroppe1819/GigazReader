package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.content.Context;
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

public class FavoriteList extends Fragment{
    private View view;
    private ListView listView;
    private FavoriteAdapter favoriteAdapter;
    private FileIO fileIO;
    private Activity activity;
    private DialogClickListener dialogClickListener = null;
    private String TAG = "FavoriteList";

    public interface OnPageChangeListener{
        void onDeleteChange();
    }

    public interface DialogClickListener {
        void onDialogShow(int position);
    }

    //Activityへ通知
    //Fragment内でページを更新したい場合に呼ぶ
    public void refresh(){
        Activity activity = getActivity();
        if(activity instanceof OnPageChangeListener == false){
            System.out.println("activity unimplement OnPageChangeListener");
            return;
        }
        ((OnPageChangeListener)activity).onDeleteChange();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DialogClickListener){
            dialogClickListener = (DialogClickListener) context;
        }else{
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
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
                    new GoogleCustomTabs(uri, activity);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                    // ダイアログを表示
                    dialogClickListener.onDialogShow(position);
                    return true;
                }
            });
        }
    }

    private void onDialog(final int position){
        new AlertDialog.Builder(activity)
                .setMessage(R.string.dialog_message)
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OKのとき
                        fileIO.PreferencesDelete(position);
                        refresh();
                        final Snackbar snackbar = Snackbar.make(view, R.string.delete_favorite, Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.SeaGreen));
                        snackbar.show();
                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }
}
