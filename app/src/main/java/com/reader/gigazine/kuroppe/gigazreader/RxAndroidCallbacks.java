package com.reader.gigazine.kuroppe.gigazreader;

public interface RxAndroidCallbacks {
    //終了
    void onTaskFinished();

    //キャンセル
    void onTaskCancelled();

    //データの追加
    void addTaskCallbacks();

    //データの更新
    void updateTaskCallbacks(int position);
}
