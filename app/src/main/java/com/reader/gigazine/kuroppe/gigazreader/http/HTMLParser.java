package com.reader.gigazine.kuroppe.gigazreader.http;

import android.util.Log;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HtmlParser {
    private Document document;
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> category = new ArrayList<String>();
    private ArrayList<String> imgs = new ArrayList<String>();
    private ArrayList<String> url = new ArrayList<String>();

    public HtmlParser(Document document){
        this.document = document;
    }

    public void onParse(){
        getTitle();
        getImage();
        getUrl();
//        Log.d("HTML", title.get(39));
//        Log.d("HTML", category.get(39));
//        Log.d("HTML", imgs.get(39));
//        Log.d("HTML", url.get(39));
        HtmlList htmlList = new HtmlList();
        htmlList.setTitle(title);
        htmlList.setCategory(category);
        htmlList.setImgs(imgs);
        htmlList.setUrl(url);
    }

    private void getImage(){
        Elements img = document.getElementsByTag("img");
//        int count = 0;
        for (int i=0; i<img.size()-1; i++){
            if (img.get(i).attr("src") == ""){
                imgs.add(img.get(i).attr("data-src"));
            }else{
                imgs.add(img.get(i).attr("src"));
            }
//            Log.d("HTML", imgs.get(i) + " " + count);
//            count++;
        }
    }

    private void getTitle(){
        Elements span = document.getElementsByTag("span");
        String[] span_split = span.html().toString().split("\n", 0);
        for (int i=0 ; i<span_split.length; i+=2) {
            title.add(span_split[i]);
            category.add(span_split[i+1]);
//            html.setTitle(span_split[i]);
        }
    }

    private void getUrl(){
        final String prefix = "<a href=\"";
        final String suffix = "\"><span>";
        Elements h2 = document.select("h2");
        for (int i=0; i<h2.size(); i++){
            String element = h2.get(i).toString();
            int preIdx = element.indexOf(prefix) + prefix.length();
            int sufIdx = element.indexOf(suffix);
            url.add(element.substring(preIdx, sufIdx));
        }
    }

}
