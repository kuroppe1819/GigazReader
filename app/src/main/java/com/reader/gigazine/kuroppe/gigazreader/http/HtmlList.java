package com.reader.gigazine.kuroppe.gigazreader.http;

import android.app.Activity;

import com.reader.gigazine.kuroppe.gigazreader.List.ArticleData;
import com.reader.gigazine.kuroppe.gigazreader.List.FileIO;

import java.util.ArrayList;

public class HtmlList {
    private String TAG = "HtmlList";

    public ArrayList<ArticleData> getArticle() {
        ArrayList<ArticleData> objects = new ArrayList<>();
        HtmlParameter params = new HtmlParameter();
        for (int i = 0; i < params.getTitle().size(); i++) {
            ArticleData articleData = new ArticleData();
            articleData.setTitle(params.getTitle().get(i));
            articleData.setCategory(params.getCategory().get(i));
            articleData.setImgs(params.getImgs().get(i));
            articleData.setUrl(params.getUrl().get(i));
            articleData.setTime(params.getTime().get(i));
            objects.add(articleData);
        }
        return objects;
    }

    public ArrayList<ArticleData> getFavorite(Activity activity) {
        FileIO fileIO = new FileIO(activity);
        ArrayList<ArticleData> objects = new ArrayList<>();
        if (fileIO.Output() == null) return null;
        for (int i = fileIO.Output().size() - 1; i >= 0; i--){
            ArticleData articleData = new ArticleData();
            articleData.setTitle(fileIO.Output().get(i).get(0).toString());
            articleData.setCategory(fileIO.Output().get(i).get(1).toString());
            articleData.setImgs(fileIO.Output().get(i).get(2).toString());
            articleData.setUrl(fileIO.Output().get(i).get(3).toString());
            articleData.setTime(fileIO.Output().get(i).get(4).toString());
            objects.add(articleData);
        }
        return objects;
    }
}
