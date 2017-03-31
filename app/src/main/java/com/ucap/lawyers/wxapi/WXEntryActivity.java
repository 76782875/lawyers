package com.ucap.lawyers.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.HomeActivity;
import com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys.OrdinaryPersonalCenterActivity;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserQQLogin;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserWeXinTaccessToken;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserWeiXinInfo;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserWeiXinUpdateToken;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    static Activity activity;
    static LoadingDialogView loadingDialog;
    public RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        HomeActivity.wxapi.handleIntent(getIntent(), this);

    }

    public static String WEIXIN_APP_ID = "wxc7fc3fa765fe1862";
    public static String WEIXIN_APP_SECRET = "854f96a795d9cb0461e38bc2f81a0c1c";
    /**
     * 使用微信code获取access_token
     */
    private String access_token;

    /**
     * 微信登陆
     */
    public static void weixinLogin(Activity mActivity, LoadingDialogView loadingDialogView) {
        activity = mActivity;
        loadingDialog = loadingDialogView;
        SendAuth.Req req = new SendAuth.Req();
        req.transaction = WEIXIN_APP_ID;
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        HomeActivity.wxapi.sendReq(req);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        HomeActivity.wxapi.handleIntent(intent, WXEntryActivity.this);//必须调用此句话
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        requestQueue = Volley.newRequestQueue(WXEntryActivity.this);
        Log.i("weixinOpenId", "onResp");
        Log.i("weixinOpenId", "code=" + baseResp.errCode);
        if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {//用户成功授权
            finish();
            Bundle bundle = new Bundle();
            baseResp.toBundle(bundle);
            SendAuth.Resp sp = new SendAuth.Resp(bundle);
            Log.i("weixinOpenId", "code=" + sp.code);
            access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WEIXIN_APP_ID + "&secret=" + WEIXIN_APP_SECRET + "&code=" + sp.code + "&grant_type=authorization_code";
            StringRequest request = new StringRequest(access_token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("weixinOpenId", response);
                    Gson gson = new Gson();
                    UserWeXinTaccessToken userWeXinTaccessToken = gson.fromJson(response, UserWeXinTaccessToken.class);
                    updateToken(userWeXinTaccessToken.getRefresh_token());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request);
        } else {
            finish();//用户取消授权
            loadingDialog.dismiss();
        }
    }

    /**
     * 刷新token
     *
     * @param token
     */
    public void updateToken(String token) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String token_uri = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + WEIXIN_APP_ID + "&grant_type=refresh_token&refresh_token=" + token;
        StringRequest request = new StringRequest(token_uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("weixinOpenId_token", response);
                Gson gson = new Gson();
                UserWeiXinUpdateToken userWeiXinUpdateToken = gson.fromJson(response, UserWeiXinUpdateToken.class);
                getUserInfo(userWeiXinUpdateToken.getAccess_token(), userWeiXinUpdateToken.getOpenid());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(String token, String openid) {
        String uri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid;
        StringRequest request = new StringRequest(uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("weixinOpenId_userInfo", response);
                Gson gson = new Gson();
                UserWeiXinInfo userWeiXinInfo = gson.fromJson(response, UserWeiXinInfo.class);
                Log.i("weixinPhoto", "微信头像从微信获取：" + userWeiXinInfo.getHeadimgurl());
                HashMap<String, String> map = new HashMap<>();
                map.put("photo", userWeiXinInfo.getHeadimgurl());
                map.put("name", userWeiXinInfo.getNickname());
                if (userWeiXinInfo.getSex() == 1) {
                    map.put("sex", "男");
                } else {
                    map.put("sex", "女");
                }
                map.put("openid", userWeiXinInfo.getOpenid());
                map.put("phonetype", "android");
                map.put("logintype", "weixin");
                getUserLogin(map);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    /**
     * 获取登陆数据
     *
     * @param map
     */
    public void getUserLogin(final HashMap<String, String> map) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.ORDINARY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("loginData", response);
                gsonDataLogin(response);//使用qq openid登陆并解析数据
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("loginData", "链接出错！");
                Toast.makeText(WXEntryActivity.this, "链接超时！", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
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
            Intent intent = new Intent(WXEntryActivity.this, OrdinaryPersonalCenterActivity.class);
            intent.putExtra("id", rowsBean.getId() + "");//头像
            intent.putExtra("photo", rowsBean.getPhoto());//头像
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
            PrefUtils.putString("user_info_name", rowsBean.getUserid(), WXEntryActivity.this);//保存账号
            PrefUtils.putString("user_info_password", rowsBean.getPassword(), WXEntryActivity.this);//保存密码
            PrefUtils.putString("user_info_type", DataInterface.UserLogin.USER_TYPE_ORDINARY, WXEntryActivity.this);//账户角色
            PrefUtils.putString("user_id", rowsBean.getId() + "", WXEntryActivity.this);//保存用户id，用于咨询
            PrefUtils.putString("user_photo", rowsBean.getPhoto(), WXEntryActivity.this);//头像
            Log.i("weixinPhoto", "微信头像——从服务器获取：" + rowsBean.getPhoto());
//            finish();//销毁登陆页面
            activity.finish();
            loadingDialog.dismiss();
        }
    }
}
