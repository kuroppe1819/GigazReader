package com.reader.gigazine.kuroppe.gigazreader.Dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.reader.gigazine.kuroppe.gigazreader.List.FileIO;
import com.reader.gigazine.kuroppe.gigazreader.PageChangeListener;
import com.reader.gigazine.kuroppe.gigazreader.R;

public class FavoriteDialogFragment extends DialogFragment {
    private PageChangeListener pageChangeListener = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pageChangeListener = (PageChangeListener) getTargetFragment();
        if (pageChangeListener == null) {
            throw new ClassCastException("must implement OnFragmentInteractionListener");
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
        dialog.setContentView(R.layout.favorite_dialog);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // OK ボタンのリスナ
        dialog.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileIO fileIO = new FileIO(getActivity());
                fileIO.PreferencesDelete(getTargetRequestCode());
                pageChangeListener.dialogCallback();
                dismiss();
            }
        });
        // Close ボタンのリスナ
        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }
}
