package com.reader.gigazine.kuroppe.gigazreader.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.reader.gigazine.kuroppe.gigazreader.R;

import java.util.ArrayList;

/**
 * Created by atsusuke on 2016/06/05.
 */
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
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                getActivity(), R.layout.support_simple_spinner_dropdown_item, arrayList);
//        ListView listView = (ListView)view.findViewById(R.id.favorite_list);
//        listView.setAdapter(adapter);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), arrayList));
    }
}
