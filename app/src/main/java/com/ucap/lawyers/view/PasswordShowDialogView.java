package com.ucap.lawyers.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/9/6.
 * 提示用户密码和账号一致
 */
public class PasswordShowDialogView extends BaseDialog {

    @Bind(R.id.btn_determine)
    Button btnDetermine;
    @Bind(R.id.tv_content)
    TextView tvContent;
    String password;

    public PasswordShowDialogView(Context context, String password) {
        super(context);
        this.password = password;
    }

    public static void showDialog(Context tex, String password) {
        PasswordShowDialogView dialogView = new PasswordShowDialogView(tex, password);
        dialogView.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_password_show);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        btnDetermine.setOnClickListener(this);
    }

    @Override
    public void setData() {
        tvContent.setText(password);
    }

    @Override
    public void setClick(View v) {
        if (v == btnDetermine) {
            dismiss();
        }
    }
}
