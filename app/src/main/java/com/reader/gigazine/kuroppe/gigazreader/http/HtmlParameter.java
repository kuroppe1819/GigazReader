package com.reader.gigazine.kuroppe.gigazreader.http;

import android.util.Log;

import java.util.ArrayList;

public class HtmlParameter {
    private static ArrayList<String> title = new ArrayList<String>();
    private static ArrayList<String> category = new ArrayList<String>();
    private static ArrayList<String> imgs = new ArrayList<String>();
    private static ArrayList<String> url = new ArrayList<String>();

    public void setTitle(ArrayList<String> title){
        this.title = title;
    }

    public ArrayList<String> getTitle(){
        return title;
    }

    public void setCategory(ArrayList<String> category){
        this.category = category;
    }

    public ArrayList<String> getCategory(){
        return category;
    }

    public void setImgs(ArrayList<String> imgs){
        this.imgs = imgs;
    }

    public ArrayList<String> getImgs(){
        return imgs;
    }

    public void setUrl(ArrayList<String> url){
        this.url = url;
    }

    public ArrayList<String> getUrl(){
        return url;
    }
}
