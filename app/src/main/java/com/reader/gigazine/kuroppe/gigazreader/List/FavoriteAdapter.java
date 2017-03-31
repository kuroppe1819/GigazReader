package com.reader.gigazine.kuroppe.gigazreader.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.reader.gigazine.kuroppe.gigazreader.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

class FavoriteAdapter extends ArrayAdapter<ArticleData> {
    private LayoutInflater layoutInflater_;
    private Context context;

    FavoriteAdapter(Context context, int textViewResourceId, ArrayList<ArticleData> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // 特定の行(position)のデータを得る
        ArticleData articleData = (ArticleData) getItem(position);

        // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
        if (null == convertView) {
            convertView = layoutInflater_.inflate(R.layout.articlelist_item, null);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.title_text);
        assert articleData != null;
        titleText.setText(articleData.getTitle());
        TextView categoryText = (TextView) convertView.findViewById(R.id.category_text);
        categoryText.setText(articleData.getCategory());
        TextView timeText = (TextView) convertView.findViewById(R.id.time_text);
        timeText.setText(articleData.getTime());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
        // BitmapDataに変換
        Glide.with(this.context)
                .load(articleData.getImgs())
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(450, 450)
                .error(R.drawable.no_image)
                .into(imageView);
        return convertView;
    }
}