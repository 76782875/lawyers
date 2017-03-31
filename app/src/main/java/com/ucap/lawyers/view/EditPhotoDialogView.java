package com.ucap.lawyers.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/26.
 */
public class EditPhotoDialogView extends BaseDialog implements View.OnClickListener {
    @Bind(R.id.tv_camera)
    TextView tvCamera;
    @Bind(R.id.tv_image)
    TextView tvImage;
    @Bind(R.id.tv_dismiss)
    TextView tvDismiss;
    @Bind(R.id.ll_root_main)
    LinearLayout llRoot;
    OnEditPhotoDialogListener onEditPhotoDialogListener;

    protected EditPhotoDialogView(Context context, OnEditPhotoDialogListener onEditPhotoDialogListener) {
        super(context);
        this.onEditPhotoDialogListener = onEditPhotoDialogListener;
    }

    public static void show(Context tex, OnEditPhotoDialogListener onEditPhotoDialogListener) {
        EditPhotoDialogView dialogView = new EditPhotoDialogView(tex, onEditPhotoDialogListener);
        dialogView.show();
    }

    @Override
    public void setView() {
        View view = View.inflate(getContext(), R.layout.view_dialog_edit_photo, null);
        ButterKnife.bind(this, view);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels;
        int height2 = outMetrics.heightPixels - 80;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width2, height2);
        setContentView(view, lp);


    }


    @Override
    public void setListener() {
        tvImage.setOnClickListener(this);
        tvCamera.setOnClickListener(this);
        tvDismiss.setOnClickListener(this);
        llRoot.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void setClick(View v) {
        if (v == tvImage) {
            onEditPhotoDialogListener.OnImage();
        } else if (v == tvCamera) {
            onEditPhotoDialogListener.OnCamera();
        } else if (v == tvDismiss) {
            dismiss();
        } else if (v == llRoot) {
            dismiss();
        }
        dismiss();
    }

    public interface OnEditPhotoDialogListener {
        void OnCamera();

        void OnImage();
    }
}
