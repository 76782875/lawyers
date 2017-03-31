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
 */
public class DeleteDialogView extends BaseDialog {

    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.btn_determine)
    Button btnDetermine;
    @Bind(R.id.tv_intent)
    TextView tvIntent;
    private OnDetermineListener onDetermineListener;

    public DeleteDialogView(Context context, OnDetermineListener onDetermineListener) {
        super(context);
        this.onDetermineListener = onDetermineListener;
    }

    public DeleteDialogView(Context context) {
        super(context);
    }

    public static void showDialog(Context tex, OnDetermineListener onDetermineListener) {
        DeleteDialogView dialogView = new DeleteDialogView(tex, onDetermineListener);
        dialogView.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_delete);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        btnCancel.setOnClickListener(this);
        btnDetermine.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    /**
     * 设置删除对话框显示内容
     */
    public void setContent(String title) {
        tvIntent.setText(title);
    }

    /**
     * 设置删除监听
     *
     * @param onDetermineListener
     */
    public void setDeleteListener(OnDetermineListener onDetermineListener) {
        this.onDetermineListener = onDetermineListener;
    }

    /**
     * 确定删除
     */
    public interface OnDetermineListener {
        void onDetermine();
    }

    @Override
    public void setClick(View v) {
        if (v == btnDetermine) {
            onDetermineListener.onDetermine();
            dismiss();
        } else if (v == btnCancel) {
            dismiss();
        }
    }
}
