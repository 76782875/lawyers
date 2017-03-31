package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/11/28.
 */

public class CodeImageDialog extends BaseDialog {
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;

    protected CodeImageDialog(Context context) {
        super(context);
    }

    public static void showDialog(Context ctx) {
        CodeImageDialog dialog = new CodeImageDialog(ctx);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_code_image);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void setClick(View v) {
        if (v == ivDismiss) {
            dismiss();
        }
    }
}
