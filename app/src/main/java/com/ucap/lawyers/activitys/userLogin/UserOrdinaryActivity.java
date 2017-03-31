package com.ucap.lawyers.activitys.userLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.BackPasswordActivity;
import com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys.OrdinaryNewUserActivity;
import com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys.OrdinaryPersonalCenterActivity;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserQQInfo;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserQQLogin;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.AgreementDialogView;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.UserInputView;
import com.ucap.lawyers.wxapi.WXEntryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 普通用户登录页面
 */
public class UserOrdinaryActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.user_input_ordinary)
    UserInputView userInput;
    @Bind(R.id.tv_user_ordinary_back_password)
    TextView tvBackPassword;
    @Bind(R.id.tv_user_ordinary_phone_login)
    TextView tvPhoneLogin;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_new_user)
    Button btnNewUser;
    @Bind(R.id.iv_qq)
    ImageView ivQQLogin;
    @Bind(R.id.iv_weixin)
    ImageView ivWeiXin;
    public QQIUiListener iUiListener;
    public Tencent mTencent;
    public String openID;//qq号生产的唯一key(MD5)
    public RequestQueue requestQueue;
    public LoadingDialogView loadingDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ordinary);
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
        requestQueue = Volley.newRequestQueue(this);
        loadingDialogView = new LoadingDialogView(this);
        tvBackPassword.setOnClickListener(this);
        tvPhoneLogin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnNewUser.setOnClickListener(this);
        ivQQLogin.setOnClickListener(this);
        userInput.setInputHintText("用户名", "密码");
        ivWeiXin.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        String user = PrefUtils.getString("ordinary_user", "", this);
        String password = PrefUtils.getString("ordinary_password", "", this);
        if (!user.isEmpty() && !password.isEmpty()) {
            userInput.setInputName(user, password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();//页面销毁时，删除数据

    }

    @Override
    public void onClick(View v) {
        if (v == tvBackPassword) {
            Intent intent = new Intent(UserOrdinaryActivity.this, BackPasswordActivity.class);
            startActivity(intent);
        } else if (v == tvPhoneLogin) {
            Intent intent = new Intent(UserOrdinaryActivity.this, BackPasswordActivity.class);
            intent.putExtra("type", true);
            startActivity(intent);
        } else if (v == btnLogin) {
            String name = userInput.getInputName();
            String password = userInput.getInputPassword();
            if (!name.isEmpty() && !password.isEmpty()) {
                provingLogin(name, password);
                PrefUtils.putString("ordinary_user", name, this);
                PrefUtils.putString("ordinary_password", password, this);
            }
        } else if (v == btnNewUser) {
            AgreementDialogView.showDialog(UserOrdinaryActivity.this, new AgreementDialogView.OnAgreeListener() {
                @Override
                public void onAgree() {
//                    startActivity(new Intent(UserOrdinaryActivity.this, BackPasswordActivity.class));
                    startActivity(new Intent(UserOrdinaryActivity.this, OrdinaryNewUserActivity.class));
                }
            });
        } else if (v == ivQQLogin) {
            loadingDialogView.show();
            mTencent = Tencent.createInstance("1105754205", getApplicationContext());
            iUiListener = new QQIUiListener();
            mTencent.login(UserOrdinaryActivity.this, "all", iUiListener);
        } else if (ivWeiXin == v) {
            weixinLogin();
        }
    }

    /**
     * 微信登陆
     */
    private void weixinLogin() {
        loadingDialogView.show();
        WXEntryActivity.weixinLogin(this, loadingDialogView);
    }

    /**
     * 验证登陆
     *
     * @param name     名称
     * @param password 密码
     */
    public void provingLogin(final String name, final String password) {
        loadingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.ORDINARY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("loginData", response);
                if (response.isEmpty() || "登录名或密码不正确!".equals(response)) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    loadingDialogView.dismiss();
                    return;
                }
                gsonDataLogin(response);//解析数据并登陆
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("loginData", "链接出错！");
                loadingDialogView.dismiss();
                Toast.makeText(UserOrdinaryActivity.this, "链接超时！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("userid", name);
                map.put("password", password);
                map.put("phonetype", "android");
                return map;
            }
        };
        requestQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {

            Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
        }
    }

    /**
     * 登陆，验证
     */
    class QQIUiListener implements IUiListener {
        /**
         * 获取qq信息
         *
         * @param jsonObject
         */
        private void doComplete(JSONObject jsonObject) {
            try {
                if (jsonObject.getInt("ret") == 0) {
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    openID = jsonObject.getString(Constants.PARAM_OPEN_ID);
                    //**下面这两步设置很重要,如果没有设置,返回为空**
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(token, expires);
                    /**
                     * 获取用户信息
                     */
                    UserInfo qqInfo = new UserInfo(UserOrdinaryActivity.this, mTencent.getQQToken());
                    qqInfo.getUserInfo(new UserIUiListener());

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

        @Override
        public void onComplete(Object o) {
            Log.i("QQLogin", "数据--" + o.toString());
            JSONObject jsonResponse = (JSONObject) o;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Log.i("QQLogin", "onComplete--登陆失败");
                return;
            }
            doComplete((JSONObject) o);
        }

        @Override
        public void onError(UiError uiError) {
            Log.i("QQ", "onError");
            loadingDialogView.dismiss();
        }

        @Override
        public void onCancel() {
            Log.i("QQ", "onCancel");
            loadingDialogView.dismiss();
        }
    }

    /**
     * 解析登陆数据并，登陆
     *
     * @param data
     */
    public void gsonDataLogin(String data) {
        Gson gson = new Gson();
        UserQQLogin userQQLogin = gson.fromJson(data, UserQQLogin.class);
        List<UserQQLogin.RowsBean> rows = userQQLogin.getRows();
        if (rows != null && rows.size() > 0) {
            UserQQLogin.RowsBean rowsBean = rows.get(0);
            Intent intent = new Intent(UserOrdinaryActivity.this, OrdinaryPersonalCenterActivity.class);
            intent.putExtra("id", rowsBean.getId() + "");
            intent.putExtra("photo", rowsBean.getPhoto());//头像
            Log.i("userPhoto", "个人中心头像：" + rowsBean.getPhoto());
            intent.putExtra("name", rowsBean.getFirstname());//名称
            intent.putExtra("userid", rowsBean.getUserid());//账号
            intent.putExtra("password", rowsBean.getPassword());//密码
            intent.putExtra("OrdinaryUsersInfo", rowsBean.getOrdinaryUsersInfo());//个人中心
            intent.putExtra("OrdinaryUsersMyQuestions", rowsBean.getOrdinaryUsersMyQuestions());//我的提问
            intent.putExtra("MessageAlert", rowsBean.getMessageAlert());//消息提醒
            intent.putExtra("newMassageSize", rowsBean.getSize());//新消息数量
            Log.i("massage", "qqOpenid登陆：" + rowsBean.getMessageAlert());
            startActivity(intent);

            /**
             * 登陆信息
             */
            PrefUtils.putString("ordinary_user", rowsBean.getUserid(), this);
            PrefUtils.putString("ordinary_password", rowsBean.getPassword(), this);
            PrefUtils.putString("user_info_name", rowsBean.getUserid(), UserOrdinaryActivity.this);//保存账号
            PrefUtils.putString("user_info_password", rowsBean.getPassword(), UserOrdinaryActivity.this);//保存密码
            PrefUtils.putString("user_info_type", DataInterface.UserLogin.USER_TYPE_ORDINARY, UserOrdinaryActivity.this);//账户角色
            PrefUtils.putString("user_id", rowsBean.getId() + "", UserOrdinaryActivity.this);//保存用户id，用于咨询
            PrefUtils.putString("user_photo", rowsBean.getPhoto(), UserOrdinaryActivity.this);//头像
            finish();//销毁登陆页面

            loadingDialogView.dismiss();
        }
    }

    /**
     * 获取用户信息
     */
    public class UserIUiListener implements IUiListener {

        public UserQQInfo userQQInfo;

        @Override
        public void onComplete(Object o) {
            Log.i("QQLogin", "数据--" + o.toString());
            Gson gson = new Gson();
            userQQInfo = gson.fromJson(o.toString(), UserQQInfo.class);
            HashMap<String, String> map = new HashMap<>();
            map.put("photo", userQQInfo.getFigureurl_qq_2());
            Log.i("qqPhoto", userQQInfo.getFigureurl_qq_2());
            map.put("name", userQQInfo.getNickname());
            map.put("sex", userQQInfo.getGender());
            map.put("openid", openID);
            map.put("phonetype", "android");
            map.put("logintype", "qq");
            getUserLogin(map);

        }

        public void getUserLogin(final HashMap<String, String> map) {
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.ORDINARY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("loginData", response);
                    if (response.isEmpty()) {
                        loadingDialogView.dismiss();
                        return;
                    }
                    gsonDataLogin(response);//使用qq openid登陆并解析数据
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("loginData", "链接出错！");
                    loadingDialogView.dismiss();
                    Toast.makeText(UserOrdinaryActivity.this, "链接超时！", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return map;
                }
            };
            requestQueue.add(request);
            Log.i("loginData", request.getUrl() + request.getBodyContentType());
        }


        @Override
        public void onError(UiError uiError) {
            loadingDialogView.dismiss();
        }

        @Override
        public void onCancel() {
            loadingDialogView.dismiss();
        }
    }

}
