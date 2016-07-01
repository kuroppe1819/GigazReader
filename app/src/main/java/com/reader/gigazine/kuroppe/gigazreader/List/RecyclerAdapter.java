package com.reader.gigazine.kuroppe.gigazreader.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.reader.gigazine.kuroppe.gigazreader.R;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ArrayList> htmlList = new ArrayList<ArrayList>();
    private LayoutInflater inflater;
    private int title = 0;
    private int category = 1;
    private int imgs = 2;
    private int url = 3;

    public RecyclerAdapter(Context context, ArrayList<ArrayList> htmlList) {
        this.htmlList = htmlList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.textView.setText((String)htmlList.get(title).get(i));
    }

    @Override
    public int getItemCount() {
        return htmlList.get(title).size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
        }
    }
}
