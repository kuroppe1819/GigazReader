package com.reader.gigazine.kuroppe.gigazreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.reader.gigazine.kuroppe.gigazreader.http.HttpAsyncTask;

public class MainActivity extends AppCompatActivity implements AsyncTaskCallbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpAsyncTask http = new HttpAsyncTask(this,this);
        http.execute();
    }

    @Override
    public void onTaskFinished() {
        Log.d("callback","読みこみ終わり");
    }

    @Override
    public void onTaskCancelled() {
        Log.d("callback","キャンセル");
    }
}
