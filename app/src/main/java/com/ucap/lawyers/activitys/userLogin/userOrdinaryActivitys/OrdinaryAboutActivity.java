package com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.PublicData;
import com.ucap.lawyers.view.UpdateDialogView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrdinaryAboutActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.iv_icon)
    ImageView ivIcon;

    @Bind(R.id.tv_message)
    TextView tvMessage;
    private PackageInfo pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_about);
        ButterKnife.bind(this);
        intiDate();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UpdateDialogView.show(OrdinaryAboutActivity.this);
//                UpdateDialogView.show(OrdinaryAboutActivity.this, 1, 2);
            }
        });
    }

    private void intiDate() {
        PackageManager pm = this.getPackageManager();
        pi = null;
        try {
            pi = pm.getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvMessage.setText(PublicData.helpContent);

    }

}
