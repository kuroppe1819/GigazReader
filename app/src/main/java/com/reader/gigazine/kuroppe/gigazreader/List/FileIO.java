package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reader.gigazine.kuroppe.gigazreader.http.HtmlParameter;
import org.json.JSONArray;
import java.util.ArrayList;

public class FileIO implements FavoriteList.Callback{
    private ArrayList<ArrayList> favoriteList = new ArrayList<>();
    private String TAG = "FileIO";
    private Activity activity;
    private JSONArray jsonArray = new JSONArray();
    private Gson gson = new Gson();
    private SharedPreferences preferences;

    public FileIO(Activity activity){
        this.activity = activity;
        this.preferences = activity.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // 保存したデータを削除
//        preferences.edit().clear().commit();
    }

    public void Input(int position, HtmlParameter htmlParameter){
//        Log.d(TAG, String.valueOf(position) + " " + htmlParameter.getTitle().get(position));
        if (Output() != null) favoriteList = Output();
        ArrayList<String> articleData = new ArrayList<>();
        articleData.add(htmlParameter.getTitle().get(position));
        articleData.add(htmlParameter.getCategory().get(position));
        articleData.add(htmlParameter.getImgs().get(position));
        articleData.add(htmlParameter.getUrl().get(position));
        articleData.add(htmlParameter.getTime().get(position));
        favoriteList.add(articleData);
//        for (int i=0; i<favoriteList.size(); i++) {
//            Log.d(TAG, String.valueOf(favoriteList.get(i).get(0)));
//        }
        // データの保存
        preferences.edit().putString("list", gson.toJson(favoriteList)).commit();
        Update();
    }

    public ArrayList<ArrayList> Output(){
        return gson.fromJson(preferences.getString("list", ""), new TypeToken<ArrayList<ArrayList>>(){}.getType());
    }

    // FavoriteListにCallback
    public void Update(){
        FavoriteList favoriteList = new FavoriteList();
        favoriteList.setCallback(this);
        favoriteList.onUpdate();
    }

    @Override
    public void callbackMethod() {
        Log.d(TAG, "Updating and finishing");
    }
}
