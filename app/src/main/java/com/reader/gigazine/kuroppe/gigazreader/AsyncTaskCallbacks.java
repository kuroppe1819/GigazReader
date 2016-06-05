package com.reader.gigazine.kuroppe.gigazreader;

public interface AsyncTaskCallbacks {
    //終了
    public void onTaskFinished();

    //キャンセル
    public void onTaskCancelled();
}
