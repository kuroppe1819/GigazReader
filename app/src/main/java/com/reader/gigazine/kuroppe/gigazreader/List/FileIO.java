package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class FileIO {
    private ArrayList<ArrayList> favoriteList = new ArrayList<>();
    private String TAG = "FileIO";
    private Activity activity;
    private Gson gson = new Gson();
    private SharedPreferences preferences;

    public FileIO(Activity activity) {
        this.activity = activity;
        this.preferences = activity.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // 保存したデータを削除
//        preferences.edit().clear().commit();
    }

    private void Save(ArrayList<ArrayList> favoriteList) {
        // データの保存
        preferences.edit().putString("list", gson.toJson(favoriteList)).apply();
    }

    public ArrayList<ArrayList> Output() {
        return gson.fromJson(preferences.getString("list", ""), new TypeToken<ArrayList<ArrayList>>() {
        }.getType());
    }

    public void Input(ArrayList articleData) {
        if (Output() != null) favoriteList = Output();
        favoriteList.add(articleData);
        Save(favoriteList);
    }

    public void PreferencesDelete(int position) {
        favoriteList = Output();
        favoriteList.remove(position);
        Save(favoriteList);
    }


}
