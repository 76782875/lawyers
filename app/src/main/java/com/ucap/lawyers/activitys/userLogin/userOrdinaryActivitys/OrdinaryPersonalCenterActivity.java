package com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.CollectionActivity;
import com.ucap.lawyers.activitys.userLogin.UserOrdinaryActivity;
import com.ucap.lawyers.service.NewMassageService;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.CodeImageDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 普通用户中心
 */
public class OrdinaryPersonalCenterActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;//名称
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;//头像
    //    @Bind(R.id.rl_personal_sign)//此功能暂时禁用
//    RelativeLayout rlSign;
    @Bind(R.id.rl_personal_next)
    LinearLayout rlPersonal;//个人中心
    @Bind(R.id.rl_collection)
    RelativeLayout rlCollection;

    @Bind(R.id.rl_problem)
    RelativeLayout rlProblem;//我的提问
    //
    @Bind(R.id.rl_massage)
    RelativeLayout rlMassage;//消息提醒

    @Bind(R.id.rl_help)
    RelativeLayout rlHelp;

    @Bind(R.id.rl_telephone)
    RelativeLayout rlTelephone;

    @Bind(R.id.tv_phone_text)
    TextView tvPhoneText;
    @Bind(R.id.tv_cancellation)
    TextView tvCancellation;//注销
    @Bind(R.id.tv_new_massage_size)
    TextView tvNewMassageSize;//新消息数量
    @Bind(R.id.rl_code)
    RelativeLayout rlCode;//app分享二维码
    public String id;
    public String ordinaryUsersInfo;
    public String ordinaryUsersMyQuestions;
    public String photo;
    public String messageAlert;
    public Intent intent;
    /**
     * 新消息数量接口
     * 参数：sid（登陆id）
     */
    public static final String NEW_MASSAGE_SIZE = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!MessageAlertSize.action?fifter=0";
    public RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_personal_center);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();

    }


    @Override
    protected void onStart() {
        super.onStart();//放在start页面可以时时更新数据
        Log.i("ordinaryPhoto", "onStart");
        for (int i = 0; i <= 2; i++) {
            Glide.with(OrdinaryPersonalCenterActivity.this).load(PrefUtils.getString("user_photo", "", this)).error(R.drawable.icon_user1).into(ivPhoto);
        }
        setTvNewMassageSize();
    }

    /**
     * 设置新消息
     */
    public void setTvNewMassageSize() {
        StringRequest request = new StringRequest(StringRequest.Method.POST, NEW_MASSAGE_SIZE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("newMassageSize", response);
                if (response.equals("0")) {
                    tvNewMassageSize.setVisibility(View.GONE);
                } else {
                    tvNewMassageSize.setVisibility(View.VISIBLE);
                    tvNewMassageSize.setText(response + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("sid", id);
                return map;
            }
        };
        requestQueue.add(request);
//        int newMassageSize = intent.getIntExtra("newMassageSize", 0);//消息数量
//        Log.i("newMassageSize", newMassageSize + "");

    }

    private void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        intent = getIntent();
        //头像
        photo = intent.getStringExtra("photo");
        Log.i("ordinaryInfo", photo + "");
        String name = intent.getStringExtra("name");//名称
        //登陆者id
        id = intent.getStringExtra("id");
        Log.i("ordinaryInfo", id + "----");
        String userid = intent.getStringExtra("userid");//账号（随机）
        String password = intent.getStringExtra("password");//密码（随机）
        ordinaryUsersInfo = intent.getStringExtra("OrdinaryUsersInfo");//个人中心
        //消息提醒
        messageAlert = intent.getStringExtra("MessageAlert");


//我的提问
        ordinaryUsersMyQuestions = intent.getStringExtra("OrdinaryUsersMyQuestions");
        tvTitle.setText(name + "个人中心");
        rlPersonal.setOnClickListener(this);
//        personal.setOnItemClickListener(new PersonalItemView.OnItemClickListener() {
//            @Override
//            public void onItemClick() {
//
//            }
//        });
//        rlSign.setOnClickListener(this);
        rlCollection.setOnClickListener(this);
        rlCollection.setEnabled(false);//我的收藏暂时禁用
        rlProblem.setOnClickListener(this);
        rlMassage.setOnClickListener(this);
        rlHelp.setOnClickListener(this);
        rlTelephone.setOnClickListener(this);
        tvCancellation.setOnClickListener(this);
        rlCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.rl_personal_sign:
//                startActivity(new Intent(OrdinaryPersonalCenterActivity.this, SignActivity.class));
//                break;
            case R.id.rl_personal_next://个人中心
                Intent intent = new Intent(OrdinaryPersonalCenterActivity.this, OrdinaryInfoActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("ordinaryUsersInfo", ordinaryUsersInfo);
                startActivity(intent);
                break;
            case R.id.rl_collection:
                startActivity(new Intent(OrdinaryPersonalCenterActivity.this, CollectionActivity.class));
                break;
            case R.id.rl_problem://我的提问
                Intent problemIntent = new Intent(OrdinaryPersonalCenterActivity.this, OrdinaryProblemActivity.class);
                problemIntent.putExtra("id", id);
                problemIntent.putExtra("ordinaryUsersMyQuestions", ordinaryUsersMyQuestions);
                startActivity(problemIntent);
                break;
            case R.id.rl_massage://消息提醒
                Intent massageIntent = new Intent(OrdinaryPersonalCenterActivity.this, OrdinaryMessageActivity.class);
                massageIntent.putExtra("id", id);
                massageIntent.putExtra("messageAlert", messageAlert);
                startActivity(massageIntent);
                break;
            case R.id.rl_help:
                startActivity(new Intent(OrdinaryPersonalCenterActivity.this, OrdinaryAboutActivity.class));
                break;
            case R.id.rl_telephone:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:" + tvPhoneText.getText()));
                startActivity(intent1);
                break;
            case R.id.tv_cancellation://点击注销
                PrefUtils.remove("user_info_name", OrdinaryPersonalCenterActivity.this);//删除名称
                PrefUtils.remove("user_info_password", OrdinaryPersonalCenterActivity.this);//删除密码
                PrefUtils.remove("user_info_type", OrdinaryPersonalCenterActivity.this);//删除类型
                PrefUtils.remove("user_info_openid", OrdinaryPersonalCenterActivity.this);//删除openid
                PrefUtils.remove("user_photo", OrdinaryPersonalCenterActivity.this);//删除头像
                PrefUtils.remove("user_id", OrdinaryPersonalCenterActivity.this);//删除用户id
                startActivity(new Intent(OrdinaryPersonalCenterActivity.this, UserOrdinaryActivity.class));//重新会到登陆页面
                finish();
                stopService(new Intent(this, NewMassageService.class));//停止新消息提醒服务
                break;
            case R.id.rl_code:
                CodeImageDialog.showDialog(this);
                break;
        }
    }
}
