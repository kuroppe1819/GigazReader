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

public class FileIO {
    private ArrayList<String> articleData = new ArrayList<>();
    private ArrayList<ArrayList> favoriteList = new ArrayList<>();
    private String TAG = "FileIO";
    private Activity activity;
    private JSONArray jsonArray = new JSONArray();
    private Gson gson = new Gson();
    private SharedPreferences preferences;

    public FileIO(Activity activity){
        this.activity = activity;
        this.preferences = activity.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void Input(int position, HtmlParameter htmlParameter){
        // 保存したデータを削除
        preferences.edit().clear().commit();

        if (Output() != null) favoriteList = Output();
        articleData.add(htmlParameter.getTitle().get(position));
        articleData.add(htmlParameter.getCategory().get(position));
        articleData.add(htmlParameter.getImgs().get(position));
        articleData.add(htmlParameter.getUrl().get(position));
        articleData.add(htmlParameter.getTime().get(position));
        favoriteList.add(articleData);
        Log.d(TAG, String.valueOf(favoriteList));
        Log.d(TAG, String.valueOf(favoriteList.size()));

        // データの保存
        preferences.edit().putString("list", gson.toJson(favoriteList)).commit();
    }

    public ArrayList<ArrayList> Output(){
        return gson.fromJson(preferences.getString("list", ""), new TypeToken<ArrayList<ArrayList>>(){}.getType());
    }
}
