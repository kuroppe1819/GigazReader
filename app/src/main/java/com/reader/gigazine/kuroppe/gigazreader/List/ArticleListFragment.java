package com.reader.gigazine.kuroppe.gigazreader.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.reader.gigazine.kuroppe.gigazreader.RxAndroidCallbacks;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.SubActivity.WebActivity;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlList;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlParameter;

public class ArticleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = "ArticleListFragment";
    private ListView listView;
    private boolean scrollFinished = false;
    private RxAndroidCallbacks rxAndroidCallbacks = null;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RxAndroidCallbacks) {
            rxAndroidCallbacks = (RxAndroidCallbacks) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_layout, null);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.DarkSeaGreen);
        mSwipeRefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HtmlList htmlList = new HtmlList();
        final HtmlParameter htmlParameter = new HtmlParameter();
        articleAdapter = new ArticleAdapter(getActivity(), 0, htmlList.getArticle());
        listView = (ListView) getActivity().findViewById(R.id.article_list);
        listView.setAdapter(articleAdapter);
        if (htmlParameter.getArticleCount() != 0) listView.addFooterView(getFooter(savedInstanceState));
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
                if (arg1 == 0 && scrollFinished) {
                    scrollFinished = false;
                    mFooter = null;
                    if (htmlParameter.getArticleCount() != 0) {
                        rxAndroidCallbacks.addTaskCallbacks();
                    }
                }
            }
        });

        /** スワイプしたときにToolbarを隠す **/
        ViewCompat.setNestedScrollingEnabled(listView, true);
        /** WebActivityに遷移 **/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position < htmlParameter.getTitle().size()) {
                    String url = htmlParameter.getUrl().get(position);
                    String title = htmlParameter.getTitle().get(position);
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", title);
                    intent.putExtra("transitionSource", TAG);
                    intent.putExtra("position", position);
                    getActivity().startActivityForResult(intent, 1234);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        /** 記事の取得数が10以下のとき追加で記事を取得 **/
        if (htmlParameter.getTitle().size() < 11 && savedInstanceState == null){
            try {
                Thread.sleep(1000); //3
            } catch (InterruptedException ignored) {
            }
            rxAndroidCallbacks.addTaskCallbacks();
        }else if (savedInstanceState != null){
            rxAndroidCallbacks.updateTaskCallbacks(0);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                articleAdapter.clear();
                articleAdapter.notifyDataSetChanged();
                listView.invalidateViews();
                rxAndroidCallbacks.updateTaskCallbacks(0);
                /** 更新が終了したらインジケータ非表示 **/
                mSwipeRefresh.setRefreshing(false);
                mFooter = null;
            }
        }, 800);
    }
}