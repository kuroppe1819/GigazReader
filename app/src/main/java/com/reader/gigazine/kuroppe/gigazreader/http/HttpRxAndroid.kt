package com.reader.gigazine.kuroppe.gigazreader.http

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast

import com.reader.gigazine.kuroppe.gigazreader.RxAndroidCallbacks
import com.reader.gigazine.kuroppe.gigazreader.Dialog.SearchParameter
import com.reader.gigazine.kuroppe.gigazreader.R

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import java.io.IOException

class HttpRxAndroid(activity: Activity, onRxCallback: RxAndroidCallbacks, private val pageNumber: Int) {

    //コールバックインターフェース
    private var onRxCallback: RxAndroidCallbacks? = null
    private var activity: Activity? = null
    private val url: String

    init {
        val searchParameter = SearchParameter()
        this.activity = activity
        this.onRxCallback = onRxCallback
        this.url = "http://gigazine.net/" + searchParameter.categoryUrl + "/P" + pageNumber.toString()
    }

    fun HttpConnect() {
        Observable
                .create(Observable.OnSubscribe<Document> { subscriber ->
                    val document: Document
                    try {
                        document = Jsoup.connect(url).get()
                        subscriber.onNext(document)
                        subscriber.onCompleted()
                    } catch (e: IOException) {
                        subscriber.onError(e)
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(object : Subscriber<Document>() {
                    override fun onCompleted() {
                        Log.d(TAG, "onCompleted")
                        onRxCallback?.onTaskFinished()
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: " + e)
                        onRxCallback?.onTaskCancelled()
                    }

                    override fun onNext(document: Document?) {
                        if (document != null) {
                            val html = HtmlParser(document, pageNumber)
                            html.onParse()
                        }
                    }
                })
    }

    companion object {
        private val TAG = "HttpRxAndroid"
    }
}
