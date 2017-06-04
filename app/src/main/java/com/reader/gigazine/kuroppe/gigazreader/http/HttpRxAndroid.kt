package com.reader.gigazine.kuroppe.gigazreader.http

import android.app.Activity
import android.util.Log
import com.reader.gigazine.kuroppe.gigazreader.RxAndroidCallbacks
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class HttpRxAndroid(activity: Activity, onRxCallback: RxAndroidCallbacks, private val pageNumber: Int) {

    private var onRxCallback: RxAndroidCallbacks? = null
    private var activity: Activity? = null
    private val url: String

    init {
        this.activity = activity
        this.onRxCallback = onRxCallback
        this.url = "http://gigazine.net/P" + pageNumber.toString()
    }

    fun HttpConnect() {
        Observable
                .create<Document> { subscriber ->
                    val document: Document
                    try {
                        document = Jsoup.connect(url).get()
                        subscriber.onNext(document)
                    } catch (e: IOException) {
                        subscriber.onError(e)
                    }
                    subscriber.onComplete()
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(object : Observer<Document> {
                    override fun onSubscribe(d: Disposable?) {

                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG, "onError: " + e)
                        onRxCallback?.onTaskCancelled()
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onCompleted")
                        onRxCallback?.onTaskFinished()
                    }

                    override fun onNext(document: Document?) {
                        val html = HtmlParser(document, pageNumber)
                        html.onParse()
                    }
                })
    }

    companion object {
        private val TAG = "HttpRxAndroid"
    }
}
