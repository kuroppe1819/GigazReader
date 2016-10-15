package com.reader.gigazine.kuroppe.gigazreader.Http;

import android.app.Activity;
import android.util.Log;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class HtmlParser {
    private String TAG = "HTML";
    private Document document;
    static private ArrayList<String> titleList = new ArrayList<>();
    static private ArrayList<String> categoryList = new ArrayList<>();
    static private ArrayList<String> imgList = new ArrayList<>();
    static private ArrayList<String> urlList = new ArrayList<>();
    static private ArrayList<String> timeList = new ArrayList<>();

    public HtmlParser(Document document, Activity activity){
        this.document = document;
    }

    private void Image(){
        Elements img = document.select(".card .thumb a img");
        for (int i = 0; i<img.size(); i++){
            if (img.get(i).attr("src") == "") {
                imgList.add(img.get(i).attr("data-src"));
            } else {
                imgList.add(img.get(i).attr("src"));
            }
        }

    }

    private void Title(){
        Elements span = document.getElementsByTag("span");
        String[] span_split = span.html().toString().split("\n", 0);
        for (int i=0 ; i<span_split.length; i+=2) {
            titleList.add(span_split[i]);
            categoryList.add(span_split[i+1]);
        }
    }

    private void Url(){
        Elements url = document.select("h2 a");
        for (int i=0; i<url.size(); i++){
            urlList.add(url.get(i).attr("href"));
        }
    }

    private void Time(){
        final String prefix = "月";
        final String suffix = "</a>";
        Elements time = document.select("time");
        for (int i=0; i<time.size(); i++) {
            String element = time.get(i).toString();
            int preIdx = element.indexOf(prefix) + prefix.length() - 3;
            int sufIdx = element.indexOf(suffix);
            timeList.add(element.substring(preIdx, sufIdx));
        }
    }

    private void getLog(){
        Log.d(TAG, "Title" + " " + titleList.get(39) + " " + titleList.size());
        Log.d(TAG, "Category" + " " + categoryList.get(39) + " " + categoryList.size());
        Log.d(TAG, "Image" + " " + imgList.get(39) + " " + imgList.size());
        Log.d(TAG, "Url" + " " + urlList.get(39) + " " + urlList.size());
        Log.d(TAG, "Time" + " " + timeList.get(39) + " " + timeList.size());
    }

    public void onParse(){
        Title();
        Image();
        Url();
        Time();
//        getLog();
        HtmlParameter htmlList = new HtmlParameter();
        htmlList.setTitle(titleList);
        htmlList.setCategory(categoryList);
        htmlList.setImgs(imgList);
        htmlList.setUrl(urlList);
        htmlList.setTime(timeList);
    }
}
