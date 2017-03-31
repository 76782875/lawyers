package com.ucap.lawyers.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ucap.lawyers.R;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/9/26.
 */
public class AddFilesDialogView extends BaseDialog {
    Bitmap file;
    String name;
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.iv_files)
    ImageView ivFiles;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.et_edit_name)
    EditText etName;
    private OnSaveListener onSaveListener;

    public AddFilesDialogView(Context context, Bitmap file, String name, OnSaveListener onSaveListener) {
        super(context);
        this.file = file;
        this.name = name;
        this.onSaveListener = onSaveListener;
    }

    public static void showDialog(Context context, Bitmap file, String name, OnSaveListener onSaveListener) {
        AddFilesDialogView dialogView = new AddFilesDialogView(context, file, name, onSaveListener);
        dialogView.setCancelable(false);
        dialogView.setView(new EditText(context));
        dialogView.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_add_files);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void setData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        file.compress(Bitmap.CompressFormat.PNG, 10, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(getContext()).load(bytes).into(ivFiles);
    }


    @Override
    public void setClick(View v) {
        if (v == ivDismiss) {
            dismiss();
        } else if (v == btnSave) {
            String saveName = etName.getText().toString();
            if (!saveName.isEmpty()) {
                onSaveListener.onSave(saveName);
                dismiss();
            } else {
                Toast.makeText(getContext(), "您的为您的卷宗,取个名字!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface OnSaveListener {
        void onSave(String name);
    }
}
