package com.reader.gigazine.kuroppe.gigazreader.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.reader.gigazine.kuroppe.gigazreader.RxAndroidCallbacks;
import com.reader.gigazine.kuroppe.gigazreader.R;

public class SearchDialogFragment extends DialogFragment {
    private int position;
    private RxAndroidCallbacks rxAndroidCallbacks = null;
    private String TAG = "SearchDialogFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RxAndroidCallbacks) {
            rxAndroidCallbacks = (RxAndroidCallbacks) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.search_dialog);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final String[] menuItems = getResources().getStringArray(R.array.menu_items);
        ListView listView = (ListView) dialog.findViewById(R.id.search_dialog_list);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_single_choice, menuItems));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i + 1;
            }
        });

        // OK ボタンのリスナ
        dialog.findViewById(R.id.positive_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != 0) rxAndroidCallbacks.updateTaskCallbacks(position);
                dismiss();
            }
        });

        return dialog;
    }
}
