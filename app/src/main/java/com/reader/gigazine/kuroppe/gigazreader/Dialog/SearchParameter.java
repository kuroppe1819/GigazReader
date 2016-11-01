package com.reader.gigazine.kuroppe.gigazreader.Dialog;
import java.util.Objects;

public class SearchParameter {
    private static String categoryUrl = "";
    private static String categoryName = "";

    public void setCategoryUrl(String categoryUrl) {
        if (Objects.equals(categoryUrl, "")) {
            SearchParameter.categoryUrl = categoryUrl;
        } else {
            SearchParameter.categoryUrl = "news/" + categoryUrl;
        }
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryName(String categoryName){
        SearchParameter.categoryName = categoryName;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public void onResetParameter(){
        SearchParameter.categoryUrl = "";
        SearchParameter.categoryName = "";
    }
}
