package com.ucap.lawyers.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/9/5.
 */
public class InputDialogView extends BaseDialog {
    @Bind(R.id.btn_determine)
    Button btnDetermine;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.et_content)
    EditText etContent;
    private DetermineListener determineListener;

    public InputDialogView(Context context, DetermineListener determineListener) {
        super(context);
        this.determineListener = determineListener;
    }

    public static void showDialog(Context ctx, DetermineListener determineListener) {
        InputDialogView inputDialogView = new InputDialogView(ctx, determineListener);
        inputDialogView.setCancelable(false);
        inputDialogView.setView(new EditText(ctx));
        inputDialogView.show();
    }


    @Override
    public void setView() {
        View view = View.inflate(getContext(), R.layout.dialog_input_view, null);
        ButterKnife.bind(this, view);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels - 100;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width2, LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(view, lp);

    }

    @Override
    public void setListener() {
        btnDetermine.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }


    @Override
    public void setClick(View v) {
        if (v == btnDetermine) {
            String content = etContent.getText().toString();
            if (!content.isEmpty()) {
                determineListener.determine(content);
                dismiss();
            }
        } else if (v == btnCancel) {
            dismiss();
        }
    }

    public interface DetermineListener {
        /***
         * 确认回答
         *
         * @param content
         */
        void determine(String content);
    }
}
