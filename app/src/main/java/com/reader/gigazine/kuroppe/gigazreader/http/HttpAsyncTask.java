package com.reader.gigazine.kuroppe.gigazreader.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import com.reader.gigazine.kuroppe.gigazreader.AsyncTaskCallbacks;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class HttpAsyncTask extends AsyncTask<Void, Void, Document>{

    //コールバックインターフェース
    private AsyncTaskCallbacks callback = null;
    private Activity activity = null;
    private String url = "http://gigazine.net/P0/";
    public ProgressDialog progressDialog;

    public HttpAsyncTask(Activity activity, AsyncTaskCallbacks callback){
        this.activity = activity;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        // プログレスダイアログの生成
        this.progressDialog = new ProgressDialog(this.activity);
        this.progressDialog.setMessage("読み込み中...");
        this.progressDialog.show();
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
        HtmlParser html = new HtmlParser(document, this.activity);
        html.onParse();
        // プログレスダイアログを閉じる
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
            //終了をActivityに通知
            callback.onTaskFinished();
        }
    }

    @Override
    protected void onCancelled() {
        Log.v("AsyncTask", "onCancelled");
        callback.onTaskCancelled();
    }
}
