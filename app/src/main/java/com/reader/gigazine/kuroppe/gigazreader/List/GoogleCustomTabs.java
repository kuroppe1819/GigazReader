package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.util.TypedValue;

import com.reader.gigazine.kuroppe.gigazreader.R;
import org.chromium.customtabsclient.shared.CustomTabsHelper;

public class GoogleCustomTabs{
    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession mCustomTabsSession;
    private Activity activity;

    public GoogleCustomTabs(Activity activity){
        this.activity = activity;
    }

    // Toolbarの色を取得
    private int getThemeColorPrimary() {
        final TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public void ChromeStartUp(Uri uri) {
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient = customTabsClient;
                // 先読み
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        // GoogleChrome v45以上が利用可能か調べる。
        String packageName = CustomTabsHelper.getPackageNameToUse(activity);
        // Chrome と接続
        CustomTabsClient.bindCustomTabsService(activity, packageName, mCustomTabsServiceConnection);

        // URLを共有するメニューアイテムの生成
        final Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, uri.toString());
        final PendingIntent sharePendingIntent = PendingIntent.getActivity(activity, 0, shareIntent, 0);


        final Intent actionIntent = new Intent();
        Bundle bandle = new Bundle();
        bandle.putInt("test",5);
        actionIntent.putExtras(bandle);
        final PendingIntent actionPendingIntent = PendingIntent.getActivity(activity, 0, actionIntent, 0);
        final Bitmap icon = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);


        // 戻るボタンの生成
//        Bitmap backbutton = BitmapFactory.decodeResource(activity.getResources(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true) // Webサイトのタイトルを表示
                .enableUrlBarHiding()
                .setToolbarColor(getThemeColorPrimary())
//                .setCloseButtonIcon(backbutton) // 閉じるボタンのアイコン
                .setActionButton(icon, activity.getString(R.string.action_share), actionPendingIntent)
                .addMenuItem(activity.getString(R.string.action_share), sharePendingIntent) // 共有メニューを追加
                .build();
        // CustomTabsでの実行を明示する。
        tabsIntent.intent.setPackage(packageName);
        // Chromeの起動
        tabsIntent.launchUrl(activity, uri);
    }

    public void unbindCustomTabsService() {
        if (mCustomTabsServiceConnection == null) return;
        activity.unbindService(mCustomTabsServiceConnection);
        mCustomTabsClient = null;
        mCustomTabsSession = null;
    }
}