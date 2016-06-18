package com.reader.gigazine.kuroppe.gigazreader.http;

import android.util.Log;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HTMLParser {
    private Document document;

    public HTMLParser(Document document){
        this.document = document;
    }

    public void onParse(){
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> category = new ArrayList<String>();
        Elements span = document.getElementsByTag("span");
        String[] span_split = span.html().toString().split("\n", 0);
        for (int i = 0 ; i < span_split.length; i+=2) {
            title.add(span_split[i]);
            category.add(span_split[i+1]);
        }
    }
}
