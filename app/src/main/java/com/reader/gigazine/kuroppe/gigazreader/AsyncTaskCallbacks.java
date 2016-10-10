package com.reader.gigazine.kuroppe.gigazreader;

public interface AsyncTaskCallbacks {
    //終了
    void onTaskFinished();

    //キャンセル
    void onTaskCancelled();
}
