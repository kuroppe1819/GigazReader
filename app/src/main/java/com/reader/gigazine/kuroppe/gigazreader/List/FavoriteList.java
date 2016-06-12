package com.reader.gigazine.kuroppe.gigazreader.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.reader.gigazine.kuroppe.gigazreader.R;

import java.util.ArrayList;

public class FavoriteList extends Fragment{
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.favorite_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < 100; i++){
            arrayList.add(String.valueOf(i));
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), arrayList));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
    }
}
