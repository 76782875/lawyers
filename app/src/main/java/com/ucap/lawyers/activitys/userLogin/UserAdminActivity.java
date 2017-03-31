package com.ucap.lawyers.activitys.userLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.BackPasswordActivity;
import com.ucap.lawyers.activitys.userLogin.userLawyeActivitys.LawyerPersonalActivity;
import com.ucap.lawyers.view.UserInputView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserAdminActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.user_input_admin)
    UserInputView userInputView;
    @Bind(R.id.tv_user_admin_back_password)
    TextView tvBackPassword;
    @Bind(R.id.btn_login)
    Button btnLoagin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();
    }

    private void intiDate() {
        tvBackPassword.setOnClickListener(this);
        btnLoagin.setOnClickListener(this);
        userInputView.setInputHintText("账户号", "密码");

    }

    @Override
    public void onClick(View v) {
        if (v == tvBackPassword) {
            Intent intent = new Intent(UserAdminActivity.this, BackPasswordActivity.class);
            intent.putExtra("startActivityName", "UserAdminActivity");
            startActivity(intent);
            finish();
        } else if (btnLoagin == v) {
            Intent intent = new Intent(UserAdminActivity.this, LawyerPersonalActivity.class);
            intent.putExtra("title", "管理员");
            startActivity(intent);
        }
    }
}
