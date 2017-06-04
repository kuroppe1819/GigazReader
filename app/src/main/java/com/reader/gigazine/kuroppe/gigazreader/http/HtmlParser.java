package com.reader.gigazine.kuroppe.gigazreader.http;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

class HtmlParser {
    private String TAG = "HTML";
    private Document document;
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

    public void onParse() {
        int listSize = titleList.size();
        Elements title = document.select("h2 a span");
        Elements category = document.select(".catab");
        Elements url = document.select("h2 a");
        Elements img = document.select(".card .thumb a img");
        Elements time = document.select("time");
        for (int i = 0; i < title.size(); i++) {
                Title(title, i);
                Category(category, i);
                Image(img, i);
                Url(url, i);
                Time(time, i);
        }
        htmlParameter.setTitle(titleList);
        htmlParameter.setCategory(categoryList);
        htmlParameter.setImgs(imgList);
        htmlParameter.setUrl(urlList);
        htmlParameter.setTime(timeList);
        htmlParameter.setArticleCount(titleList.size() - listSize);
    }
}
