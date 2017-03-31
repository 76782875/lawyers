package com.ucap.lawyers.activitys.publicActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys.OrdinaryPersonalCenterActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 修改密码
 */
public class BackPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.et_telephone_number)
    EditText etTelephoneNumber;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    int timer = 30;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            timer -= 1;
            if (timer > 0) {
                btnGetCode.setText(timer + "秒后重新获取");
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                btnGetCode.setEnabled(true);
                btnGetCode.setText("获取验证码");
                ivDelete.setEnabled(true);
                timer = 30;
            }
        }
    };
    private boolean type = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinar_edit_password);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        type = intent.getBooleanExtra("type", false);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();

    }

    private void intiDate() {
        ivDelete.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivDelete) {
            etTelephoneNumber.setText("");
        } else if (v == btnGetCode) {
            if (!etTelephoneNumber.getText().toString().isEmpty()) {
                btnGetCode.setEnabled(false);
                handler.sendEmptyMessage(0);
                ivDelete.setEnabled(false);
            } else {
                Toast.makeText(this, "请输入手机号!", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnSubmit) {
            if (type) {//判读是否是,"手机号快速登录"
                Intent intent = new Intent(BackPasswordActivity.this, OrdinaryPersonalCenterActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(BackPasswordActivity.this, SetNewPasswordActivity.class);
                startActivity(intent);
                finish();
            }
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
