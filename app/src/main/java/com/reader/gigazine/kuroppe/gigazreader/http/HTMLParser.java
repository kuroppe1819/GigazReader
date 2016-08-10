package com.reader.gigazine.kuroppe.gigazreader.Http;

import android.content.Context;
import android.util.Log;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class HtmlParser {
    private String TAG = "HTML";
    private Document document;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<String> categoryList = new ArrayList<>();
    private ArrayList<String> imgList = new ArrayList<>();
    private ArrayList<String> urlList = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();
    private Context context;

    public HtmlParser(Document document, Context context){
        this.document = document;
        this.context = context;
    }

    public void onParse(){
        Title();
        Image();
        Url();
        Time();

//        getLog();
        HtmlParameter htmlList = new HtmlParameter();
        htmlList.setTitle(titleList);
        htmlList.setCategory(categoryList);
        htmlList.setImgs(imgList);
        htmlList.setUrl(urlList);
        htmlList.setTime(timeList);
    }

    private void Image(){
        Elements img = document.getElementsByTag("img");
        int count = 0;
        while(img.get(count).attr("id") == ""){
            count++;
        }
        for (int i = count; i<img.size()-1; i++){
            if (img.get(i).attr("src") == ""){
                imgList.add(img.get(i).attr("data-src"));
            }else{
                imgList.add(img.get(i).attr("src"));
            }
//            Log.d("Imgs", img.get(i) + " " + count + " " + img.get(i).attr("id"));
            count++;
        }
    }

    private void Title(){
        Elements span = document.getElementsByTag("span");
        String[] span_split = span.html().toString().split("\n", 0);
        for (int i=0 ; i<span_split.length; i+=2) {
            titleList.add(span_split[i]);
            categoryList.add(span_split[i+1]);
//            html.setTitle(span_split[i]);
        }
    }

    private void Url(){
        final String prefix = "<a href=\"";
        final String suffix = "\"><span>";
        Elements h2 = document.select("h2");
        for (int i=0; i<h2.size(); i++){
            String element = h2.get(i).toString();
            int preIdx = element.indexOf(prefix) + prefix.length();
            int sufIdx = element.indexOf(suffix);
            urlList.add(element.substring(preIdx, sufIdx));
        }
    }

    private void Time(){
        final String prefix = "月";
        final String suffix = "</a>";
        Elements time = document.select("time");
        for (int i=0; i<time.size(); i++) {
            String element = time.get(i).toString();
            int preIdx = element.indexOf(prefix) + prefix.length() - 3;
            int sufIdx = element.indexOf(suffix);
            timeList.add(element.substring(preIdx, sufIdx));
        }
    }

    private void getLog(){
        Log.d(TAG, "Title" + " " + titleList.get(39) + " " + titleList.size());
        Log.d(TAG, "Category" + " " + categoryList.get(39) + " " + categoryList.size());
        Log.d(TAG, "Image" + " " + imgList.get(39) + " " + imgList.size());
        Log.d(TAG, "Url" + " " + urlList.get(39) + " " + urlList.size());
        Log.d(TAG, "Time" + " " + timeList.get(39) + " " + timeList.size());
//        Log.d("HTML", String.valueOf(bitmapList.size()));
    }
}
