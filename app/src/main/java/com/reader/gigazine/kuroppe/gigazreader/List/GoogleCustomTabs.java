package com.reader.gigazine.kuroppe.gigazreader.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.util.TypedValue;
import com.reader.gigazine.kuroppe.gigazreader.R;
import org.chromium.customtabsclient.shared.CustomTabsHelper;

public class GoogleCustomTabs{

    public GoogleCustomTabs(Uri uri, Activity activity) {
        // GoogleChrome v45以上が利用可能か調べる。
        String packageName = CustomTabsHelper.getPackageNameToUse(activity);

        // URLを共有するメニューアイテムの生成
        final Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, uri.toString());
        final PendingIntent sharePendingIntent = PendingIntent.getActivity(activity, 0, shareIntent, 0);
//        final Bitmap shareIcon = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_share_white);

        // 戻るボタンの生成
//        Bitmap backbutton = BitmapFactory.decodeResource(activity.getResources(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder()
                .setShowTitle(true) // Webサイトのタイトルを表示
                .enableUrlBarHiding()
                .setToolbarColor(getThemeColorPrimary(activity))
//                .setCloseButtonIcon(backbutton) // 閉じるボタンのアイコン
//                .setActionButton(shareIcon, activity.getString(R.string.action_share), sharePendingIntent) // 共有ボタンの追加
                .addMenuItem(activity.getString(R.string.action_share), sharePendingIntent) // 共有メニューを追加
                .build();

        // CustomTabsでの実行を明示する。
        tabsIntent.intent.setPackage(packageName);

        // Chromeの起動
        tabsIntent.launchUrl(activity, uri);
    }

    // Toolbarの色を取得
    private int getThemeColorPrimary(Activity activity) {
        final TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}