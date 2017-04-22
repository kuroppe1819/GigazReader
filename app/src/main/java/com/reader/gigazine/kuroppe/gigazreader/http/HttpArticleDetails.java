package com.reader.gigazine.kuroppe.gigazreader.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;


public class HttpArticleDetails {
    public static Observable<Document> request(String url) {
        return Observable.create(subscriber -> {
            try {
                Document document = Jsoup.connect(url).get();
                subscriber.onNext(document);
            } catch (IOException e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        });
    }
}

