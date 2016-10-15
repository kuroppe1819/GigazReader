package com.reader.gigazine.kuroppe.gigazreader.Http;

import android.app.Activity;
import android.util.Log;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class HtmlParser {
    private String TAG = "HTML";
    private Document document;
    private int PROMOTIONNUMBER;
    static private ArrayList<String> titleList = new ArrayList<>();
    static private ArrayList<String> categoryList = new ArrayList<>();
    static private ArrayList<String> imgList = new ArrayList<>();
    static private ArrayList<String> urlList = new ArrayList<>();
    static private ArrayList<String> timeList = new ArrayList<>();

    public HtmlParser(Document document, int pageNumber){
        this.document = document;
        PROMOTIONNUMBER = pageNumber + 20;
    }

    private void Image(){
        Elements img = document.select(".card .thumb a img");
        for (int i = 0; i<img.size(); i++){
            if (img.get(i).attr("src") == "") {
                imgList.add(img.get(i).attr("data-src"));
            } else{
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

    private void addPromotion(){
        Log.d(TAG, String.valueOf(PROMOTIONNUMBER));
        titleList.add(PROMOTIONNUMBER, "広告");
        categoryList.add(PROMOTIONNUMBER, "PR");
        imgList.add(PROMOTIONNUMBER, "http://image.itmedia.co.jp/nl/articles/1610/12/mofigtwwcrg001.jpg");
        urlList.add(PROMOTIONNUMBER, "http://ansaikuropedia.org/wiki/%E9%87%91%E6%B2%A2%E5%B7%A5%E6%A5%AD%E5%A4%A7%E5%AD%A6");
        timeList.add(PROMOTIONNUMBER, "");
    }

    private void getLog(){
        for (int i=0; i<titleList.size(); i++) {
            Log.d(TAG, "Title" + " " + titleList.get(i) + " " + titleList.size());
//            Log.d(TAG, "Category" + " " + categoryList.get(i) + " " + categoryList.size());
//            Log.d(TAG, "Image" + " " + imgList.get(i) + " " + imgList.size());
//            Log.d(TAG, "Url" + " " + urlList.get(i) + " " + urlList.size());
//            Log.d(TAG, "Time" + " " + timeList.get(i) + " " + timeList.size());
        }
    }

    public void onParse(){
        Title();
        Image();
        Url();
        Time();
        addPromotion();
        getLog();
        HtmlParameter htmlList = new HtmlParameter();
        htmlList.setTitle(titleList);
        htmlList.setCategory(categoryList);
        htmlList.setImgs(imgList);
        htmlList.setUrl(urlList);
        htmlList.setTime(timeList);
    }
}
