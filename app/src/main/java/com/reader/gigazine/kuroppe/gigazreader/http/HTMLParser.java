package com.reader.gigazine.kuroppe.gigazreader.http;

import android.util.Log;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class HTMLParser {
    private Document document;
    private int count = 1;
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> category = new ArrayList<String>();
    private ArrayList<String> imgs = new ArrayList<String>();

    public HTMLParser(Document document){
        this.document = document;
    }

    public void onParse(){
        getTitle();
        getImage();
        Log.d("HTML", imgs.get(0));
        Log.d("HTML", title.get(0));
        Log.d("HTML", category.get(0));
    }

    private void getImage(){
        Elements img = document.getElementsByTag("img");
        for (int i=1; i<img.size()-1; i++){
            if (img.get(i).attr("src") == ""){
                imgs.add(img.get(i).attr("data-src"));
            }else{
                imgs.add(img.get(i).attr("src"));
            }
//            Log.d("HTML", imgs.get(i-1));
        }
    }

    private void getTitle(){
        Elements span = document.getElementsByTag("span");
        String[] span_split = span.html().toString().split("\n", 0);
        for (int i=0 ; i<span_split.length; i+=2) {
            title.add(span_split[i]);
            category.add(span_split[i+1]);
        }
    }

    private void getUrl(){

    }

}
