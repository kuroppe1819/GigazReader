package com.reader.gigazine.kuroppe.gigazreader.http;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.reader.gigazine.kuroppe.gigazreader.AsyncTaskCallbacks;
import com.reader.gigazine.kuroppe.gigazreader.Dialog.SearchParameter;
import com.reader.gigazine.kuroppe.gigazreader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HttpAsyncTask extends AsyncTask<Void, Void, Document> {

    //コールバックインターフェース
    private AsyncTaskCallbacks callback = null;
    private Activity activity = null;
    private String url;
    private int pageNumber;
    private String TAG = "AsyncTask";

    public HttpAsyncTask(Activity activity, AsyncTaskCallbacks callback, int pageNumber) {
        SearchParameter searchParameter = new SearchParameter();
        this.activity = activity;
        this.callback = callback;
        this.url = "http://gigazine.net/" + searchParameter.getCategoryUrl() + "/P" + String.valueOf(pageNumber);
        Log.d(TAG, url);
        this.pageNumber = pageNumber;
    }

    @Override
    protected void onPreExecute() {
    }


    @Override
    protected Document doInBackground(Void... voids) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Override
    protected void onPostExecute(Document document) {
        if (document != null) {
            HtmlParser html = new HtmlParser(document, pageNumber);
            html.onParse();
        } else {
            Toast.makeText(activity, R.string.timeout, Toast.LENGTH_LONG).show();
        }
        //終了をActivityに通知
        callback.onTaskFinished();
    }

    @Override
    protected void onCancelled() {
        callback.onTaskCancelled();
    }
}
