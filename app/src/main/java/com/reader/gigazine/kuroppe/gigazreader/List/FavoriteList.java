package com.reader.gigazine.kuroppe.gigazreader.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reader.gigazine.kuroppe.gigazreader.R;

/**
 * Created by atsusuke on 2016/06/05.
 */
public class FavoriteList extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.favorite_layout,null);
    }
}
