package com.reader.gigazine.kuroppe.gigazreader.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.reader.gigazine.kuroppe.gigazreader.R;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ArrayList> htmlList = new ArrayList<ArrayList>();
    private LayoutInflater inflater;
    private Context context;
    private GetNum num = new GetNum();
    private int BitmapSize = 150;

    public RecyclerAdapter(Context context, ArrayList<ArrayList> htmlList) {
        this.context = context;
        this.htmlList = htmlList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.article_textView.setText((String)htmlList.get(num.getTitleNum).get(i));
//        viewHolder.category_textView.setText((String)htmlList.get(category).get(i));
        Glide.with(this.context).load(htmlList.get(num.getImgsNum).get(i)).asBitmap().into(new SimpleTarget<Bitmap>(/*BitmapSize,BitmapSize*/) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                viewHolder.imageView.setImageBitmap(resource);
            }
        });
    }

    @Override
    public int getItemCount() {
        return htmlList.get(num.getTitleNum).size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView article_textView;
//        final TextView category_textView;
        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.img);
            article_textView = (TextView) view.findViewById(R.id.article_text);
//            category_textView = (TextView) view.findViewById(R.id.category_text);
        }
    }
}
