package com.ucap.lawyers.activitys.publicActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.ucap.lawyers.R;
import com.ucap.lawyers.view.SignDialogView;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 签到页面
 */
public class SignActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.rl_sign_rule)
    RelativeLayout rlRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_sign);
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
        rlRule.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == rlRule) {
            SignDialogView.showDialog(this, "积分规则", "这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则,这是积分规则");
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
