package com.reader.gigazine.kuroppe.gigazreader.http;

import android.util.Log;

import org.jsoup.nodes.Document;

/**
 * Created by atsusuke on 2016/06/05.
 */
public class HTMLParse {

    private Document document;

    public HTMLParse(Document document){
        this.document = document;
    }

    public void onParse(){
        Log.d("HTML", document.toString());
    }
}
