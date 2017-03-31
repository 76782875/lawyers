package com.ucap.lawyers.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ucap.lawyers.R;


/**
 * Created by Administrator on 2016/4/28.
 * 对话框的基类
 */
public abstract class BaseDialog extends AlertDialog implements View.OnClickListener {
    protected BaseDialog(Context context) {
        super(context, R.style.Dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        setListener();
        setData();
    }
    public abstract void setView();

    public abstract void setListener();

    public abstract void setData();

    public abstract void setClick(View v);

    @Override
    public void onClick(View v) {
        setClick(v);
    }
}
