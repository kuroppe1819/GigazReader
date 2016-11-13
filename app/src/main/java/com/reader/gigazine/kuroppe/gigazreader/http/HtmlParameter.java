package com.reader.gigazine.kuroppe.gigazreader.http;

import java.util.ArrayList;

public class HtmlParameter {
    private static ArrayList<String> title = new ArrayList<>();
    private static ArrayList<String> category = new ArrayList<>();
    private static ArrayList<String> imgs = new ArrayList<>();
    private static ArrayList<String> url = new ArrayList<>();
    private static ArrayList<String> time = new ArrayList<>();
    private static int count;

    public void setTitle(ArrayList<String> title) {
        HtmlParameter.title = title;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    void setCategory(ArrayList<String> category) {
        HtmlParameter.category = category;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    void setImgs(ArrayList<String> imgs) {
        HtmlParameter.imgs = imgs;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    void setUrl(ArrayList<String> url) {
        HtmlParameter.url = url;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setTime(ArrayList<String> time) {
        HtmlParameter.time = time;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    void setArticleCount(int count){
        HtmlParameter.count = count;
    }

    public int getArticleCount(){
        return count;
    }

    void onDestroyList() {
        title.clear();
        category.clear();
        imgs.clear();
        url.clear();
        time.clear();
    }
}
