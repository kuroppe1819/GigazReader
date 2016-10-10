package com.reader.gigazine.kuroppe.gigazreader.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.reader.gigazine.kuroppe.gigazreader.R;
import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<ArticleData>{
    private LayoutInflater layoutInflater_;
    private Context context;

    public ArticleAdapter(Context context, int textViewResourceId, ArrayList<ArticleData> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 特定の行(position)のデータを得る
        ArticleData articleData = (ArticleData) getItem(position);

        // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
        if (null == convertView) {
            convertView = layoutInflater_.inflate(R.layout.list_item, null);
        }

        TextView titleText = (TextView)convertView.findViewById(R.id.title_text);
        titleText.setText(articleData.getTitle());
        TextView categoryText = (TextView)convertView.findViewById(R.id.category_text);
        categoryText.setText(articleData.getCategory());
        TextView timeText = (TextView)convertView.findViewById(R.id.time_text);
        timeText.setText(articleData.getTime());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.img);

//        Log.d("Imgs", String.valueOf(imageView));
        // BitmapDataに変換
        Glide.with(this.context)
                .load(articleData.getImgs())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(450,450)
                .error(android.R.drawable.ic_delete)
                .into(imageView);
        return convertView;
    }
}

