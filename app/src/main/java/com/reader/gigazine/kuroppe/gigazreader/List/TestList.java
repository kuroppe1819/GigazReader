package com.reader.gigazine.kuroppe.gigazreader.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reader.gigazine.kuroppe.gigazreader.R;

public class TestList extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test_item, null);
        return view;
    }
}