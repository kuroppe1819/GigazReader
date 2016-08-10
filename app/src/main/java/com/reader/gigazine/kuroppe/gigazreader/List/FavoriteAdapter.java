package com.reader.gigazine.kuroppe.gigazreader.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.reader.gigazine.kuroppe.gigazreader.R;
import java.util.ArrayList;

public class FavoriteAdapter extends ArrayAdapter<FavoriteData> {
    private LayoutInflater layoutInflater_;
    private Context context;

    public FavoriteAdapter(Context context, int textViewResourceId, ArrayList<FavoriteData> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 特定の行(position)のデータを得る
        FavoriteData favoriteData = (FavoriteData) getItem(position);

        // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
        if (null == convertView) {
            convertView = layoutInflater_.inflate(R.layout.list_item, null);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.title_text);
        titleText.setText(favoriteData.getTitle());
        TextView categoryText = (TextView) convertView.findViewById(R.id.category_text);
        categoryText.setText(favoriteData.getCategory());
        TextView timeText = (TextView) convertView.findViewById(R.id.time_text);
        timeText.setText(favoriteData.getTime());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
        // BitmapDataに変換
        Glide.with(this.context)
                .load(favoriteData.getImgs())
                .asBitmap()
                .override(450, 450)
                .error(android.R.drawable.ic_delete)
                .into(imageView);

        return convertView;
    }
}