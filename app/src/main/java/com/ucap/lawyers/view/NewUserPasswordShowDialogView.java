package com.ucap.lawyers.view;

import android.content.Context;
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
public class NewUserPasswordShowDialogView extends BaseDialog {

    @Bind(R.id.btn_determine)
    Button btnDetermine;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_password)
    TextView tvPassword;

    String userid;
    String password;

    public NewUserPasswordShowDialogView(Context context, String userid, String password, OnbtnDetermine onbtnDetermine) {
        super(context);
        this.password = password;
        this.userid = userid;
        this.onbtnDetermine = onbtnDetermine;
    }

    public static void showDialog(Context tex, String userid, String password, OnbtnDetermine onbtnDetermine) {
        NewUserPasswordShowDialogView dialogView = new NewUserPasswordShowDialogView(tex, userid, password, onbtnDetermine);
        dialogView.setCancelable(false);
        dialogView.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_new_user_password_show);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        btnDetermine.setOnClickListener(this);
    }

    @Override
    public void setData() {
        tvContent.setText(userid);
        tvPassword.setText(password);
    }

    private OnbtnDetermine onbtnDetermine;

    public interface OnbtnDetermine {
        void btnDetermine();
    }

    @Override
    public void setClick(View v) {
        if (v == btnDetermine) {
            onbtnDetermine.btnDetermine();
            dismiss();
        }
    }
}
