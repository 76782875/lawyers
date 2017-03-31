package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.CollectionActivity;
import com.ucap.lawyers.activitys.publicActivity.LawyerDetailedActivity;
import com.ucap.lawyers.activitys.userLogin.UserLawyerActivity;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.CodeImageDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 执业律师个人中心
 */
public class LawyerPersonalActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;

    /**
     * 律师个人中心
     */
    @Bind(R.id.rl_personal_next)
    RelativeLayout rlPersonalNext;
    /**
     * 签到(暂时禁用)
     */
//    @Bind(R.id.rl_sign)
//    RelativeLayout rlSign;
    /***
     * 信息公告
     */
    @Bind(R.id.rl_message)
    RelativeLayout rlMessage;
    /**
     * 公众咨询
     */
    @Bind(R.id.rl_consult)
    RelativeLayout rlConsult;
    /**
     * 我的业务
     */
    @Bind(R.id.rl_business)
    RelativeLayout rlBusiness;
    /**
     * 我的案件
     */
    @Bind(R.id.rl_case)
    RelativeLayout rlCase;
    /**
     * 我的回答
     */
    @Bind(R.id.rl_answer)
    RelativeLayout rlAnswer;
    /**
     * 法律众筹
     */
    @Bind(R.id.rl_law_add)
    RelativeLayout rlLawAdd;
    /**
     * 我的收藏
     */
    @Bind(R.id.rl_collection)
    RelativeLayout rlCollection;//暂无字段
    /**
     * 电子名片
     */
    @Bind(R.id.rl_basic)
    RelativeLayout rlBasic;
    /**
     * 绑定手机号
     */
    @Bind(R.id.btn_bind_telephone)
    Button btnBindTelephone;
    /**
     * 分享
     */
    @Bind(R.id.rl_code)
    RelativeLayout rlCode;//二维码分享
    @Bind(R.id.tv_title)
    TextView tvTitle;//律师名称
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;//律师 头像
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.tv_cancellation)
    TextView tvCancellation;//注销账户
    private String title;
    public String lawyerinfo;
    public String id;
    public String lawyerPhotoInfo;
    public String myBusiness;
    public String firmId;
    public String firstname;
    public String infoAnnouncement;
    public String publicConsultationList;
    public String myAnswer;
    public String photo;
    public String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_personal);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        Bundle info = intent.getBundleExtra("info");
        //信息公告数据地址
        infoAnnouncement = info.getString("InfoAnnouncement");
        id = info.getString("id");//id
        userid = info.getString("userid");
        //律师头像
        photo = info.getString("photo");
        PrefUtils.putString("user_photo", photo, this);//保存当前头像
        firmId = info.getString("firmId");//律所id
        firstname = info.getString("firstname"); //律师名称
        lawyerinfo = info.getString("Lawyerinfo");//律师基本信息(电子名片)
        lawyerPhotoInfo = info.getString("LawyerPhotoInfo");//个人中心
        myBusiness = info.getString("MyBusiness"); //我的业务
        myAnswer = info.getString("MyAnswer");//我的回答
        publicConsultationList = info.getString("PublicConsultationList");//公众咨询
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();
        setListener();
    }

    private void setListener() {
        rlPersonalNext.setOnClickListener(this);
        // rlSign.setOnClickListener(this);
        rlMessage.setOnClickListener(this);
        rlConsult.setOnClickListener(this);
        rlBusiness.setOnClickListener(this);
        rlCase.setOnClickListener(this);//我的案件
        rlAnswer.setOnClickListener(this);
//        rlCollection.setOnClickListener(this);
        rlBasic.setOnClickListener(this);
        btnBindTelephone.setOnClickListener(this);
        tvCancellation.setOnClickListener(this);
        rlCode.setOnClickListener(this);
        rlLawAdd.setOnClickListener(this);
    }

    private void intiDate() {
        tvTitle.setText(firstname + "个人中心");
        tvTopTitle.setText(title);
        for (int i = 0; i <= 2; i++) {//连续请求三次，避免获取不到头像
            Glide.with(this).load(photo).into(ivPhoto);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == rlPersonalNext) {//个人中心
            Intent intent = new Intent(LawyerPersonalActivity.this, LawyerInfoActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("LawyerPhotoInfo", lawyerPhotoInfo);
            startActivity(intent);
        }
//        else if (v == rlSign) {
//            startActivity(new Intent(LawyerPersonalActivity.this, SignActivity.class));
//        }
        else if (v == rlMessage) {//信息公告
            Intent intent = new Intent(LawyerPersonalActivity.this, LawyerMessageNoticeActivity.class);
            intent.putExtra("InfoAnnouncement", infoAnnouncement);
            intent.putExtra("id", id);
            intent.putExtra("firmId", firmId);
            startActivity(intent);
        } else if (v == rlConsult) {//公众咨询
            Intent intent = new Intent(LawyerPersonalActivity.this, LawyerPublicConsultActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("firmId", firmId);
            intent.putExtra("PublicConsultationList", publicConsultationList);
            startActivity(intent);
        } else if (v == rlBusiness) {//我的业务
            Intent intent = new Intent(LawyerPersonalActivity.this, LawyerBusinessActivity.class);
            intent.putExtra("userid", userid);//律师姓名
            intent.putExtra("MyBusiness", myBusiness);
            startActivity(intent);
        } else if (v == rlCase) {//我的案件，暂缺数据
            Intent commonlyIntent = new Intent(LawyerPersonalActivity.this, LawyerCaseActivity.class);
            commonlyIntent.putExtra("id", id);
            commonlyIntent.putExtra("firstname", firstname);
            startActivity(commonlyIntent);
        } else if (v == rlAnswer) {//我的回答
            Intent intent = new Intent(LawyerPersonalActivity.this, LawyerMyAnswerActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("MyAnswer", myAnswer);
            startActivity(intent);
        } else if (v == rlCollection) {//我的收藏，暂缺数据
            startActivity(new Intent(LawyerPersonalActivity.this, CollectionActivity.class));
        } else if (v == rlBasic) {//电子名片
            Intent intent = new Intent(LawyerPersonalActivity.this, LawyerDetailedActivity.class);
            intent.putExtra("lawyerinfo", lawyerinfo);
            intent.putExtra("id", id);
            startActivity(intent);
        } else if (v == btnBindTelephone) {
            startActivity(new Intent(LawyerPersonalActivity.this, LawyerBindTelephoneActivity.class));
        } else if (v == tvCancellation) {//注销账户,删除本地账户
            PrefUtils.remove("user_info_name", this);
            PrefUtils.remove("user_info_password", this);
            PrefUtils.remove("user_info_type", this);
            PrefUtils.remove("user_photo", this);
            startActivity(new Intent(LawyerPersonalActivity.this, UserLawyerActivity.class));
            finish();
        } else if (v == rlCode) {
            CodeImageDialog.showDialog(this);
        } else if (v == rlLawAdd) {//法律众筹
            Intent intent = new Intent(this, LawUploadActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
