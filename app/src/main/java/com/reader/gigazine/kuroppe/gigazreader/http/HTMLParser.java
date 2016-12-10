package com.reader.gigazine.kuroppe.gigazreader.http;

import android.util.Log;

import com.reader.gigazine.kuroppe.gigazreader.Dialog.SearchParameter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

class HtmlParser {
    private String TAG = "HTML";
    private Document document;
    private int PROMOTION_NUMBER;
    private HtmlParameter htmlParameter;
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
        htmlParameter.onDestroyList();
    }

    public HtmlParser(Document document, int pageNumber) {
        this.document = document;
//        PROMOTION_NUMBER = pageNumber + 15;
        htmlParameter = new HtmlParameter();
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

    private void Category(Elements category, int i) {
        categoryList.add(category.get(i).text());
    }

    private void Title(Elements title, int i) {
        titleList.add(title.get(i).text());
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
//            Log.d(TAG, "Title" + " " + titleList.get(i) + " " + titleList.size());
//            Log.d(TAG, "Category" + " " + categoryList.get(i) + " " + categoryList.size());
//            Log.d(TAG, "Image" + " " + imgList.get(i) + " " + imgList.size());
//            Log.d(TAG, "Url" + " " + urlList.get(i) + " " + urlList.size());
//            Log.d(TAG, "Time" + " " + timeList.get(i) + " " + timeList.size());
        }
    }

    public void onParse() {
        int listSize = titleList.size();
        Elements title = document.select("h2 a span");
        Elements category = document.select(".catab");
        Elements url = document.select("h2 a");
        Elements img = document.select(".card .thumb a img");
        Elements time = document.select("time");
        SearchParameter searchParameter = new SearchParameter();
        String choiceCategory = searchParameter.getCategoryName();
        for (int i = 0; i < title.size(); i++) {
            if (Objects.equals(choiceCategory, category.get(i).text()) || choiceCategory == "") {
                Title(title, i);
                Category(category, i);
                Image(img, i);
                Url(url, i);
                Time(time, i);
            }
        }
//        addPromotion();
//        getLog();
        htmlParameter.setTitle(titleList);
        htmlParameter.setCategory(categoryList);
        htmlParameter.setImgs(imgList);
        htmlParameter.setUrl(urlList);
        htmlParameter.setTime(timeList);
        htmlParameter.setArticleCount(titleList.size() - listSize);
        Log.d(TAG, String.valueOf(titleList.size() - listSize));
    }
}
