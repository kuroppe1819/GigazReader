package com.reader.gigazine.kuroppe.gigazreader.http;

import com.reader.gigazine.kuroppe.gigazreader.List.HtmlData;
import java.util.ArrayList;

public class HtmlList {
    private String TAG = "HtmlList";
    private HtmlData htmlData;

    public ArrayList<HtmlData> getList(){
        ArrayList<HtmlData> objects = new ArrayList<HtmlData>();
        HtmlParameter params = new HtmlParameter();
        for (int i=0; i<params.getTitle().size(); i++){
            htmlData = new HtmlData();
            htmlData.setTitle(params.getTitle().get(i));
            htmlData.setCategory(params.getCategory().get(i));
            htmlData.setImgs(params.getImgs().get(i));
            htmlData.setUrl(params.getUrl().get(i));
            htmlData.setTime(params.getTime().get(i));
            objects.add(htmlData);
        }
        return objects;
    }
}
