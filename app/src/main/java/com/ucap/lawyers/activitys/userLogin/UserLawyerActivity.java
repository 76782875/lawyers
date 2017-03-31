package com.ucap.lawyers.activitys.userLogin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.userLogin.userLawyeActivitys.LawyerBindTelephoneActivity;
import com.ucap.lawyers.activitys.userLogin.userLawyeActivitys.LawyerPersonalActivity;
import com.ucap.lawyers.activitys.publicActivity.BackPasswordActivity;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerErrMssg;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerInfo;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.UserInputView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 律师登陆页面
 */
public class UserLawyerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1001;//扫描二维码,请求码
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.user_input_ordinary)
    UserInputView userInputView;//登陆密码&&账号输入
    @Bind(R.id.tv_user_lawyer_back_password)
    TextView tvBackPassword;
    @Bind(R.id.btn_user_lawyer_code_login)
    Button btnCodeLogin;//扫描二维码登陆
    @Bind(R.id.btn_login)
    Button btnLogin; //登陆按钮
    public RequestQueue requestQueue;
    public LoadingDialogView dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lawyer);
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
        userInputView.setInputHintText("账户号", "密码");
        btnLogin.setOnClickListener(this);
        btnCodeLogin.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        dialogView = new LoadingDialogView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String lawyers_user = PrefUtils.getString("lawyers_user", "", this);
        String lawyers_password = PrefUtils.getString("lawyers_password", "", this);
        if (!lawyers_user.isEmpty() && !lawyers_password.isEmpty()) {
            userInputView.setInputName(lawyers_user, lawyers_password);
        }
    }

    @Override
    public void onClick(View v) {
        if (tvBackPassword == v) {
            Intent intent = new Intent(UserLawyerActivity.this, BackPasswordActivity.class);
            startActivity(intent);
        } else if (btnLogin == v) {
            String inputName = userInputView.getInputName();
            String inputPassword = userInputView.getInputPassword();
            if (!inputName.isEmpty() && !inputPassword.isEmpty()) {
                getLoginInfo(inputName, inputPassword);
                PrefUtils.putString("lawyers_user", inputName, this);
                PrefUtils.putString("lawyers_password", inputPassword, this);
                dialogView.show();
            } else if (inputName.isEmpty() && inputPassword.isEmpty()) {
                Toast.makeText(UserLawyerActivity.this, "请输入登陆信息！", Toast.LENGTH_SHORT).show();
            } else if (inputName.isEmpty()) {
                Toast.makeText(UserLawyerActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.isEmpty()) {
                Toast.makeText(UserLawyerActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnCodeLogin) {
            Intent intent = new Intent(UserLawyerActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    /**
     * 获取登陆后信息
     */
    public void getLoginInfo(final String user, final String password) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.UserLogin.LAWYER_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("loginData", response);
                Gson gson = new Gson();
                LawyerInfo lawyerInfo = gson.fromJson(response, LawyerInfo.class);
                ArrayList<LawyerInfo.RowsBean> rows = (ArrayList<LawyerInfo.RowsBean>) lawyerInfo.getRows();
                LawyerInfo.RowsBean rowsBean = rows.get(0);
                if (rowsBean.getLawyerinfo() != null) {
                    PrefUtils.putString("user_info_name", user, UserLawyerActivity.this);//保存账号
                    PrefUtils.putString("user_info_password", password, UserLawyerActivity.this);//保存密码
                    PrefUtils.putString("user_info_type", DataInterface.UserLogin.USER_TYPE_LAWYER, UserLawyerActivity.this);//账户角色
                    Intent intent = new Intent(UserLawyerActivity.this, LawyerPersonalActivity.class);
                    intent.putExtra("title", "执业律师");
                    Bundle bundle = new Bundle();
                    bundle.putString("photo", rowsBean.getPhoto());//律师头像
                    bundle.putString("id", rowsBean.getId() + "");//律师id
                    bundle.putString("userid", rowsBean.getUserid() + "");//律师id
                    bundle.putString("firmId", rowsBean.getGroupid() + "");//律所id
                    bundle.putString("firstname", rowsBean.getFirstName());//律师名称
                    bundle.putString("InfoAnnouncement", rowsBean.getInfoAnnouncement());//信息公告数据地址
                    bundle.putString("Lawyerinfo", rowsBean.getLawyerinfo());//律师基本信息(电子名片)
                    bundle.putString("MyBusiness", rowsBean.getMyBusiness());//我的业务
                    bundle.putString("MyAnswer", rowsBean.getMyAnswer());//我的回答
                    bundle.putString("LawyerPhotoInfo", rowsBean.getLawyerPhotoInfo());//个人信息编辑
                    bundle.putString("PublicConsultationList", rowsBean.getPublicConsultationList());//公众咨询
                    intent.putExtra("info", bundle);
                    startActivity(intent);
                    finish();
                    dialogView.dismiss();
                } else {
                    Gson gson1 = new Gson();
                    LawyerErrMssg lawyerErrMssg = gson1.fromJson(response, LawyerErrMssg.class);
                    LawyerErrMssg.RowsBean errMsg = lawyerErrMssg.getRows().get(0);
                    Log.i("loginData", errMsg.getPrompt());
                    dialogView.dismiss();
                    Toast.makeText(UserLawyerActivity.this, errMsg.getPrompt(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogView.dismiss();
                Toast.makeText(UserLawyerActivity.this, "登陆超时！请检查网络链接！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", user);
                map.put("password", password);
                map.put("logintype", "67");//登陆角色
                return map;
            }
        };
        requestQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserLawyerActivity.this, LawyerPersonalActivity.class);
//                    intent.putExtra("userName", resultCode);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(UserLawyerActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
