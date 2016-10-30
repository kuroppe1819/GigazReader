package com.reader.gigazine.kuroppe.gigazreader.http;

import android.app.Activity;
import android.util.Log;
import com.reader.gigazine.kuroppe.gigazreader.List.FavoriteData;
import com.reader.gigazine.kuroppe.gigazreader.List.FileIO;
import com.reader.gigazine.kuroppe.gigazreader.List.ArticleData;
import java.util.ArrayList;

public class HtmlList {
    private String TAG = "HtmlList";

    public ArrayList<ArticleData> getArticle(){
        ArrayList<ArticleData> objects = new ArrayList<>();
        HtmlParameter params = new HtmlParameter();
        for (int i=0; i<params.getTitle().size(); i++){
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

    public ArrayList<FavoriteData> getFavorite(Activity activity){
        FileIO fileIO = new FileIO(activity);
        ArrayList<FavoriteData> objects = new ArrayList<>();
        if (fileIO.Output() == null) return null;
        for (int i=0; i<fileIO.Output().size(); i++){
//            Log.d(TAG, String.valueOf(fileIO.Output().size()));
            FavoriteData favoriteData = new FavoriteData();
            favoriteData.setTitle(fileIO.Output().get(i).get(0).toString());
            favoriteData.setCategory(fileIO.Output().get(i).get(1).toString());
            favoriteData.setImgs(fileIO.Output().get(i).get(2).toString());
            favoriteData.setUrl(fileIO.Output().get(i).get(3).toString());
            favoriteData.setTime(fileIO.Output().get(i).get(4).toString());
            objects.add(favoriteData);
        }
        return objects;
    }
}
