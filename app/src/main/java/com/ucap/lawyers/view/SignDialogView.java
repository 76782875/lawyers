package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/29.
 */
public class SignDialogView extends BaseDialog {

    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    String content;
    String title;

    protected SignDialogView(Context context, String title, String content) {
        super(context);
        this.title = title;
        this.content = content;
    }

    /**
     * 弹出
     *
     * @param tex
     */
    public static void showDialog(Context tex, String title, String cn) {
        SignDialogView dialogView = new SignDialogView(tex, title, cn);
        dialogView.show();
    }

    @Override
    public void setView() {
//        WindowManager wm = getWindow().getWindowManager();
//        Display ddl = wm.getDefaultDisplay();
//        int width = ddl.getWidth();
//        int height = ddl.getHeight();
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height - 100);
//        View view = View.inflate(getContext(), R.layout.dalog_sign_view, null);
//        setContentView(view, lp);
        setContentView(R.layout.dalog_sign_view);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivClose.setOnClickListener(this);
    }

    @Override
    public void setData() {
        tvTitle.setText(title);
        tvContent.setText(content);

    }

    @Override
    public void setClick(View v) {
        if (v == ivClose) {
            dismiss();
        }
    }
}
