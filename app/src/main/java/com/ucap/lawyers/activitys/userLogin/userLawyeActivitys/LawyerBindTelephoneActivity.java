package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

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

import butterknife.Bind;
import butterknife.ButterKnife;

public class LawyerBindTelephoneActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    /**
     * 手机号
     */
    @Bind(R.id.et_telephone_number)
    EditText etTelephoneNumber;

    /**
     * 验证码
     *
     * @param savedInstanceState
     */
    @Bind(R.id.et_code)
    EditText etCode;

    /**
     * 获取验证码
     */
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    /**
     * 确认
     */
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    /**
     * 清空文本框
     */
    @Bind(R.id.iv_delete)
    ImageView ivDeleteText;
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
                ivDeleteText.setEnabled(true);
                btnGetCode.setText("获取验证码");
                timer = 30;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_bind_telephone);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();
        intiListener();
    }

    private void intiListener() {
        btnGetCode.setOnClickListener(this);
        ivDeleteText.setOnClickListener(this);
    }

    private void intiDate() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_code:
                String telephoneNumber = etTelephoneNumber.getText().toString();
                if (!telephoneNumber.isEmpty()) {
                    btnGetCode.setEnabled(false);
                    handler.sendEmptyMessage(0);
                    ivDeleteText.setEnabled(false);
                } else {
                    Toast.makeText(this, "请输入手机号!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_delete:
                etTelephoneNumber.setText("");
                break;
        }
    }
}
