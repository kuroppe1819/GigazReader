package com.reader.gigazine.kuroppe.gigazreader.List;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.reader.gigazine.kuroppe.gigazreader.R;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlList;
import org.chromium.customtabsclient.shared.CustomTabsHelper;

public class NewArticleList extends Fragment {
    private String TAG = "position";
    private View view;
    private HtmlList htmlList = new HtmlList();
    private GetNum num = new GetNum();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.article_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), htmlList.getList()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Uri urlStr = (Uri) htmlList.getList().get(num.getUrlNum).get(position);
                        Log.d(TAG, String.valueOf(position));
                        Log.d(TAG, String.valueOf(urlStr));

                        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
                        String packageName = CustomTabsHelper.getPackageNameToUse(getActivity());
                        tabsIntent.intent.setPackage(packageName);
                        tabsIntent.launchUrl(getActivity(), urlStr);
                    }
                })
        );
    }
}