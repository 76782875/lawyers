package com.ucap.lawyers.activitys.publicActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.ucap.lawyers.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetNewPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.iv_visible_password)
    ImageView ivVisiblePassword;
    @Bind(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        intiListener();
    }

    private void intiListener() {
        btnSubmit.setOnClickListener(this);
        ivVisiblePassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                //backActivity();
                finish();
                break;
            case R.id.iv_visible_password:
                boolean selected = ivVisiblePassword.isSelected();
                ivVisiblePassword.setSelected(!selected);
                setIvVisiblePassword(selected);
                break;
        }
    }

    public void setIvVisiblePassword(boolean state) {

        if (!state) {//显示密码
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        //将光标移动到文本最后
        CharSequence text = etPassword.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //        友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
