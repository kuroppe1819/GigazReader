package com.reader.gigazine.kuroppe.gigazreader.http;

import java.util.ArrayList;

public class HtmlList {
    public ArrayList<ArrayList> getList(){
        HtmlParameter params = new HtmlParameter();
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        list.add(params.getTitle());
        list.add(params.getCategory());
        list.add(params.getImgs());
        list.add(params.getUrl());
        list.add(params.getBitmap());
        return list;
    }
}
