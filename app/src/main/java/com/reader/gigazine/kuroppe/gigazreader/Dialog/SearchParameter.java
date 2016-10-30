package com.reader.gigazine.kuroppe.gigazreader.Dialog;

import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

public class SearchParameter {
    private static String categoryUrl = "";

    public void setCategoryUrl(String categoryUrl) {
        if (!Objects.equals(categoryUrl, "")) {
            SearchParameter.categoryUrl = "news/" + categoryUrl;
        } else {
            SearchParameter.categoryUrl = categoryUrl;
        }
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }
}
