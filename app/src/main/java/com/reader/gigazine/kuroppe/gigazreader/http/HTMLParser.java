package com.reader.gigazine.kuroppe.gigazreader.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HtmlParser {
    private String TAG = "HTML";
    private Document document;
    private ArrayList<String> titleList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();
    private ArrayList<String> imgList = new ArrayList<String>();
    private ArrayList<String> urlList = new ArrayList<String>();
    private ArrayList<Bitmap> bitmapList = new ArrayList<>();
    private Context context;

    public HtmlParser(Document document, Context context){
        this.document = document;
        this.context = context;
    }

    public void onParse(){
        Title();
        Image();
        Url();
        Log.d(TAG, titleList.get(39) + " " + titleList.size());
        Log.d(TAG, categoryList.get(39));
        Log.d(TAG, imgList.get(39) + " " + imgList.size());
        Log.d(TAG, urlList.get(39) + " " + urlList.size());
//        Log.d("HTML", String.valueOf(bitmapList.size()));
        HtmlParameter htmlList = new HtmlParameter();
        htmlList.setTitle(titleList);
        htmlList.setCategory(categoryList);
        htmlList.setImgs(imgList);
        htmlList.setUrl(urlList);
//        htmlList.setBitmap(bitmapList);
    }

    private void Image(){
        Elements img = document.getElementsByTag("img");
//        int count = 0;
        for (int i=3; i<img.size()-1; i++){
            if (img.get(i).attr("src") == ""){
                imgList.add(img.get(i).attr("data-src"));
            }else{
                imgList.add(img.get(i).attr("src"));
            }
//            Log.d(TAG, imgs.get(i) + " " + count);
//            count++;
        }
    }

    private void Title(){
        Elements span = document.getElementsByTag("span");
        String[] span_split = span.html().toString().split("\n", 0);
        for (int i=0 ; i<span_split.length; i+=2) {
            titleList.add(span_split[i]);
            categoryList.add(span_split[i+1]);
//            html.setTitle(span_split[i]);
        }
    }

    private void Url(){
        final String prefix = "<a href=\"";
        final String suffix = "\"><span>";
        Elements h2 = document.select("h2");
        for (int i=0; i<h2.size(); i++){
            String element = h2.get(i).toString();
            int preIdx = element.indexOf(prefix) + prefix.length();
            int sufIdx = element.indexOf(suffix);
            urlList.add(element.substring(preIdx, sufIdx));
        }
    }
}
