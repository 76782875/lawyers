package com.ucap.lawyers.view;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ucap.lawyers.R;

/**
 * Created by wekingwang on 16/8/30.
 */
public class UpdateDialogView extends BaseDialog {

    int versionCode;
    int newVersionCode;
    private static UpdateDialogView updateDialogView;

    protected UpdateDialogView(Context context, int versionCode, int newVersionCode) {
        super(context);
        this.versionCode = versionCode;
        this.newVersionCode = newVersionCode;
    }


    public static void show(Context ctx, int versionCode, int newVersionCode) {
        updateDialogView = new UpdateDialogView(ctx, versionCode, newVersionCode);
        updateDialogView.show();

    }

    @Override
    public void setView() {
        View view = View.inflate(getContext(), R.layout.dialog_update, null);
        Display dp = getWindow().getWindowManager().getDefaultDisplay();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dp.getWidth() / 3, dp.getHeight() / 5);
        setContentView(view, lp);
        if (versionCode < newVersionCode) {
            Toast.makeText(getContext(), "发现新版本", Toast.LENGTH_LONG).show();
            View newView = View.inflate(getContext(), R.layout.update_new_version, null);
            LinearLayout.LayoutParams newLp = new LinearLayout.LayoutParams(dp.getWidth() / 2 + 160, ViewGroup.LayoutParams.WRAP_CONTENT);
            setContentView(newView, newLp);
        }

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setClick(View v) {

    }
}
