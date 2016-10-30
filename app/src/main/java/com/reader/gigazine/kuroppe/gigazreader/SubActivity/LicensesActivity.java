package com.reader.gigazine.kuroppe.gigazreader.SubActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.reader.gigazine.kuroppe.gigazreader.R;

public class LicensesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.licenses_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.licenses_toolbar);
        toolbar.setTitle(R.string.action_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean result = true;
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }
        return result;
    }
}
