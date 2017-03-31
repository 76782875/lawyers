package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.PublicData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用户注册协议
 */
public class AgreementDialogView extends BaseDialog {
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    protected AgreementDialogView(Context context, OnAgreeListener onAgreeListener) {
        super(context);
        this.onAgreeListener = onAgreeListener;
    }

    public static void showDialog(Context ctx, OnAgreeListener onAgreeListener) {
        AgreementDialogView dialogView = new AgreementDialogView(ctx, onAgreeListener);
        dialogView.setCancelable(false);
        dialogView.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_agreement);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void setData() {
        tvContent.setText(PublicData.agreementContent);
    }

    @Override
    public void setClick(View v) {
        if (v == btnCancel) {
            dismiss();
        } else if (v == btnSubmit) {
            onAgreeListener.onAgree();
            dismiss();
        }
    }

    private OnAgreeListener onAgreeListener;

    /**
     * 点击同意回调接口
     */
    public interface OnAgreeListener {
        void onAgree();
    }
}
