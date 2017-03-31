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

/***
 * 律律所行政主管登录
 */
public class UserExecutiveDirectorActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.user_input_executive_director)
    UserInputView userInputView;
    @Bind(R.id.tv_user_executive_director_back_password)
    TextView tvBackPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_executive_director);
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
        btnLogin.setOnClickListener(this);
        userInputView.setInputHintText("账户号", "密码");
    }

    @Override
    public void onClick(View v) {
        if (v == tvBackPassword) {
            Intent intent = new Intent(UserExecutiveDirectorActivity.this, BackPasswordActivity.class);
            intent.putExtra("startActivityName", "UserExecutiveDirectorActivity");
            startActivity(intent);
            finish();
        } else if (v == btnLogin) {
            Intent intent = new Intent(UserExecutiveDirectorActivity.this, LawyerPersonalActivity.class);
            intent.putExtra("title", "律师行政主管");
            startActivity(intent);

        }
    }
}
