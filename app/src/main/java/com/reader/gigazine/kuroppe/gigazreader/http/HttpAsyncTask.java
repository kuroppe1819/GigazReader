package com.reader.gigazine.kuroppe.gigazreader.Http;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.reader.gigazine.kuroppe.gigazreader.AsyncTaskCallbacks;
import com.reader.gigazine.kuroppe.gigazreader.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class HttpAsyncTask extends AsyncTask<Void, Void, Document>{

    //コールバックインターフェース
    private AsyncTaskCallbacks callback = null;
    private Activity activity = null;
    private String url;
    private String TAG = "AsyncTask";

    public HttpAsyncTask(Activity activity, AsyncTaskCallbacks callback, int pageNumber){
        this.activity = activity;
        this.callback = callback;
        this.url = "http://gigazine.net/P" + String.valueOf(pageNumber);
//        this.url = "http://gigazine.net/P40";
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
//        Log.d(TAG, String.valueOf(document));
        if (document != null) {
            HtmlParser html = new HtmlParser(document, activity);
            html.onParse();
        }else {
            Toast.makeText(activity, R.string.timeout, Toast.LENGTH_LONG).show();
        }
            //終了をActivityに通知
            callback.onTaskFinished();
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCancelled");
        callback.onTaskCancelled();
    }
}
