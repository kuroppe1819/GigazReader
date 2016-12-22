package com.reader.gigazine.kuroppe.gigazreader.List;

import java.io.Serializable;

public class ArticleData implements Serializable{
    private String title, category, imgs, url, time;
    private String TAG = "ArticleData";

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String getCategory() {
        return category;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    String getImgs() {
        return imgs;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
