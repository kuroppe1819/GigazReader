package com.reader.gigazine.kuroppe.gigazreader.http;

import android.app.Activity;
import android.util.Log;

import com.reader.gigazine.kuroppe.gigazreader.http.HtmlParameter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HtmlParser {
    private String TAG = "HTML";
    private Document document;
    private int PROMOTION_NUMBER;
    private HtmlParameter htmlList;
    static private ArrayList<String> titleList = new ArrayList<>();
    static private ArrayList<String> categoryList = new ArrayList<>();
    static private ArrayList<String> imgList = new ArrayList<>();
    static private ArrayList<String> urlList = new ArrayList<>();
    static private ArrayList<String> timeList = new ArrayList<>();

    private void onDestroyList() {
        timeList.clear();
        categoryList.clear();
        imgList.clear();
        urlList.clear();
        timeList.clear();
        htmlList.onDestroyList();
    }

    public HtmlParser(Document document, int pageNumber) {
        this.document = document;
        PROMOTION_NUMBER = pageNumber + 15;
        htmlList = new HtmlParameter();
        if (pageNumber == 0) {
            onDestroyList();
        }
    }

    private void Image(Elements img, int i) {
        if (img.get(i).attr("src") == "") {
            imgList.add(img.get(i).attr("data-src"));
        } else {
            imgList.add(img.get(i).attr("src"));
        }
    }

    private void Title(String[] span_split) {
        for (int i = 0; i < span_split.length; i += 2) {
            titleList.add(span_split[i]);
            categoryList.add(span_split[i + 1]);
        }
    }

    private void Url(Elements url, int i) {
        urlList.add(url.get(i).attr("href"));
    }

    private void Time(Elements time, int i) {
        final String prefix = "月";
        final String suffix = "</a>";
        String element = time.get(i).toString();
        int preIdx = element.indexOf(prefix) + prefix.length() - 3;
        int sufIdx = element.indexOf(suffix);
        timeList.add(element.substring(preIdx, sufIdx));
    }

    private void addPromotion() {
        for (int i = 0; i <= 15; i = i + 15) {
            titleList.add(PROMOTION_NUMBER + i, "広告");
            categoryList.add(PROMOTION_NUMBER + i, "PR");
            imgList.add(PROMOTION_NUMBER + i, "http://image.itmedia.co.jp/nl/articles/1610/12/mofigtwwcrg001.jpg");
            urlList.add(PROMOTION_NUMBER + i, "http://ansaikuropedia.org/wiki/%E9%87%91%E6%B2%A2%E5%B7%A5%E6%A5%AD%E5%A4%A7%E5%AD%A6");
            timeList.add(PROMOTION_NUMBER + i, "");
        }
    }

    private void getLog() {
        for (int i = 0; i < titleList.size(); i++) {
            Log.d(TAG, "Title" + " " + titleList.get(i) + " " + titleList.size());
//            Log.d(TAG, "Category" + " " + categoryList.get(i) + " " + categoryList.size());
//            Log.d(TAG, "Image" + " " + imgList.get(i) + " " + imgList.size());
//            Log.d(TAG, "Url" + " " + urlList.get(i) + " " + urlList.size());
//            Log.d(TAG, "Time" + " " + timeList.get(i) + " " + timeList.size());
        }
    }

    public void onParse() {
        Elements url = document.select("h2 a");
        Elements img = document.select(".card .thumb a img");
        Elements span = document.getElementsByTag("span");
        String[] span_split = span.html().split("\n", 0);
        Elements time = document.select("time");
        Title(span_split);
        for (int i = 0; i < url.size(); i++) {
            Image(img, i);
            Url(url, i);
            Time(time, i);
        }
        addPromotion();
//        getLog();
        htmlList.setTitle(titleList);
        htmlList.setCategory(categoryList);
        htmlList.setImgs(imgList);
        htmlList.setUrl(urlList);
        htmlList.setTime(timeList);
    }
}
