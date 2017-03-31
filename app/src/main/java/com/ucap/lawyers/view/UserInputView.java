package com.ucap.lawyers.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/24.
 * 账户密码输入框
 */
public class UserInputView extends RelativeLayout {
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_password)
    EditText etPassword;

    public UserInputView(Context context) {
        super(context);
        intiView();
    }

    public UserInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiView();
    }

    public UserInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView();
    }

    /***
     * 初始化
     */
    public void intiView() {
        View view = View.inflate(getContext(), R.layout.view_user_input, null);
        ButterKnife.bind(this, view);
        this.addView(view);
    }

    /**
     * 设置输入框的指示文本
     */
    public void setInputHintText(String nameHint, String passwordHint) {
        etName.setHint(nameHint);
        etPassword.setHint(passwordHint);
    }

    /**
     * 获取密码
     */
    public String getInputPassword() {
        return etPassword.getText().toString();
    }

    /**
     * 获取账号
     */
    public String getInputName() {
        return etName.getText().toString();
    }

    /**
     * 设置账号,密码
     */
    public void setInputName(String name, String password) {
        etName.setText(name);
        etPassword.setText(password);
    }
}
