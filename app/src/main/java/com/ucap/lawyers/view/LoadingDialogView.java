package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;

import com.ucap.lawyers.R;

/**
 * Created by wekingwang on 16/9/22.
 */
public class LoadingDialogView extends BaseDialog {
    public LoadingDialogView(Context context) {
        super(context);
    }

    @Override
    public void setView() {
        this.setCancelable(false);
        setContentView(R.layout.dialog_loading);
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
