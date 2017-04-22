package com.reader.gigazine.kuroppe.gigazreader.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParseHtmlUtil {
    public static String getContentHtml(Document document){
//        Elements content = document.select("div .cntimage");
        Elements content = document.select("html");
//        Log.d("ParseHtmlUtil", content.html());
        return content.html();
    }
}
