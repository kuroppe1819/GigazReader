package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.reader.gigazine.kuroppe.gigazreader.AsyncTaskCallbacks;
import com.reader.gigazine.kuroppe.gigazreader.PageChangeListener;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlList;
import com.reader.gigazine.kuroppe.gigazreader.Http.HtmlParameter;
import com.reader.gigazine.kuroppe.gigazreader.SubActivity.WebActivity;

public class ArticleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private String TAG = "ArticleListFragment";
    private View view;
    private ListView listView;
    private boolean scrollFinished = false;
    private AsyncTaskCallbacks asyncTaskCallbacks = null;
    private View mFooter;
    private SwipeRefreshLayout mSwipeRefresh;
    private ArticleAdapter articleAdapter;

    private View getFooter(Bundle bundle) {
        if (mFooter == null) {
            mFooter = getLayoutInflater(bundle).inflate(R.layout.listview_footer, null);
        }
        return mFooter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AsyncTaskCallbacks){
            asyncTaskCallbacks = (AsyncTaskCallbacks) context;
        }else{
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.article_layout, null);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.DarkSeaGreen);
        mSwipeRefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HtmlList htmlList = new HtmlList();
        final FileIO fileIO = new FileIO(getActivity());
        final HtmlParameter htmlParameter = new HtmlParameter();
        articleAdapter = new ArticleAdapter(getActivity(), 0, htmlList.getArticle());
        listView = (ListView) getActivity().findViewById(R.id.article_list);
        listView.setAdapter(articleAdapter);
        listView.addFooterView(getFooter(savedInstanceState));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /** スクロール完了したとき **/
                if ((totalItemCount - visibleItemCount) == firstVisibleItem && firstVisibleItem != 0) {
                    scrollFinished = true;
                }
            }

            /** ListViewがスクロール中かどうか **/
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if(arg1 == 0 && scrollFinished == true){
                    scrollFinished = false;
                    mFooter = null;
                    asyncTaskCallbacks.addTaskCallbacks();
                }
            }
        });

        /** スワイプしたときにToolbarを隠す **/
        ViewCompat.setNestedScrollingEnabled(listView, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String url = htmlParameter.getUrl().get(position);
                String title = htmlParameter.getTitle().get(position);
                Intent intent = new Intent(getContext(),WebActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                /** データの保存 **/
                fileIO.Input(position, htmlParameter);
                Log.d(TAG, String.valueOf(getTargetFragment()));
                final Snackbar snackbar = Snackbar.make(view, R.string.add_favorite, Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.SeaGreen));
                snackbar.setAction("閉じる", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                articleAdapter.clear();
                articleAdapter.notifyDataSetChanged();
                listView.invalidateViews();
                asyncTaskCallbacks.updateTaskCallbacks();
                /** 更新が終了したらインジケータ非表示 **/
                mSwipeRefresh.setRefreshing(false);
                mFooter = null;
            }
        }, 800);
    }

//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//
//    }
}