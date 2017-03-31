package com.ucap.lawyers.base.imple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.userLogin.UserLawyerActivity;
import com.ucap.lawyers.activitys.userLogin.UserOrdinaryActivity;
import com.ucap.lawyers.activitys.userLogin.userLawyeActivitys.LawyerPersonalActivity;
import com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys.OrdinaryPersonalCenterActivity;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerInfo;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserQQLogin;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.PersonalItemView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/12.
 * 登陆角色选择
 */
public class PersonalCenterBase extends PagerBase {
    @Bind(R.id.pl_user1)
    PersonalItemView user1;
    @Bind(R.id.pl_user2)
    PersonalItemView user2;
    @Bind(R.id.pl_user3)
    PersonalItemView user3;
    @Bind(R.id.pl_user4)
    PersonalItemView user4;
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;//头像
    @Bind(R.id.tv_name)
    TextView tvName;//名称
    @Bind(R.id.tv_new_massage_icon)
    TextView tvNewMassageSize;//新消息数量
    public LoadingDialogView dialogView;
    public RequestQueue requestQueue;

    public PersonalCenterBase(Activity mActivity) {
        super(mActivity);
        requestQueue = Volley.newRequestQueue(mActivity);
        dialogView = new LoadingDialogView(mActivity);
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.base_personal_center, null);
        ButterKnife.bind(this, view);
        flContent.addView(view);
        user1.setTvTitle("普通用户");
        user1.setIvPhoto(R.drawable.icon_user1);
        user1.setTitleColor(Color.BLACK);
        user1.setOnItemClickListener(new PersonalItemView.OnItemClickListener() {
            @Override
            public void onItemClick() {
                String user_info_name = PrefUtils.getString("user_info_name", "", mActivity);//保存账号
                String user_info_password = PrefUtils.getString("user_info_password", "", mActivity);//保存密码
                String user_info_type = PrefUtils.getString("user_info_type", "", mActivity);//账户角色
                if (!user_info_name.isEmpty() && !user_info_password.isEmpty() && !user_info_type.isEmpty()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("userid", user_info_name);
                    map.put("password", user_info_password);
                    map.put("phonetype", "android");//登陆用于区分ios，和安卓
                    isOrdinary(map, user_info_type);//如果切换到登陆页面系统保存了登陆信息就直接跳转到登陆页面
                    isUserType(user_info_type);//判断用户角色并禁用其他非本角色的登陆入口
                } else {
                    mActivity.startActivity(new Intent(mActivity, UserOrdinaryActivity.class));
                }
            }

        });
        user2.setTvTitle("执业律师");
        user2.setTitleColor(Color.BLACK);
        user2.setIvPhoto(R.drawable.icon_user2);
        user2.setOnItemClickListener(new PersonalItemView.OnItemClickListener() {
            @Override
            public void onItemClick() {
                /**
                 * 判断用户是否已经登陆如果是就直接跳转到个人中心，如果不是就跳转到登陆界面
                 */
                String user_info_name = PrefUtils.getString("user_info_name", "", mActivity);//保存账号
                String user_info_password = PrefUtils.getString("user_info_password", "", mActivity);//保存密码
                String user_info_type = PrefUtils.getString("user_info_type", "", mActivity);//账户角色
                if (!user_info_name.isEmpty() && !user_info_password.isEmpty() && !user_info_type.isEmpty()) {
                    getUserInfo(user_info_name, user_info_password, user_info_type);//如果切换到登陆页面系统保存了登陆信息就直接跳转到登陆页面
                    isUserType(PrefUtils.getString("user_info_type", "", mActivity));//判断用户角色并禁用其他非本角色的登陆入口
                } else {
                    mActivity.startActivity(new Intent(mActivity, UserLawyerActivity.class));
                }
            }
        });
        user3.setTvTitle("律所行政主管");
        user3.setIvPhoto(R.drawable.test_ico);
//        user3.setOnItemClickListener(new PersonalItemView.OnItemClickListener() {
//            @Override
//            public void onItemClick() {
//                mActivity.startActivity(new Intent(mActivity, UserExecutiveDirectorActivity.class));
//            }
//        });
        user4.setTvTitle("管理员");
        user4.setIvPhoto(R.drawable.test_ico);
//        user4.setOnItemClickListener(new PersonalItemView.OnItemClickListener() {
//            @Override
//            public void onItemClick() {
//                mActivity.startActivity(new Intent(mActivity, UserAdminActivity.class));
//            }
//        });
        isUserType(PrefUtils.getString("user_info_type", "", mActivity));//判断用户角色并禁用其他非本角色的登陆入口
        /**
         * 如果当前账户已登陆就显示出登陆的头像名称
         *
         */
        String user_photo = PrefUtils.getString("user_photo", "", mActivity);
        Log.i("userPhoto", "我的---" + user_photo);
        if (!user_photo.isEmpty()) {
            for (int i = 0; i <= 2; i++) {//连续请求三次避免获取不到头像
                Glide.with(mActivity).load(user_photo).error(R.drawable.icon_personal_top).into(ivPhoto);
            }
        }
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断登陆的角色，点击头像后直接跳转到个人中心
                /**
                 * 判断用户是否已经登陆如果是就直接跳转到个人中心，如果不是就跳转到登陆界面
                 */
                String user_info_name = PrefUtils.getString("user_info_name", "", mActivity);//保存账号
                String user_info_password = PrefUtils.getString("user_info_password", "", mActivity);//保存密码
                String user_info_type = PrefUtils.getString("user_info_type", "", mActivity);//账户角色
                if (!user_info_name.isEmpty() && !user_info_password.isEmpty() && !user_info_type.isEmpty()) {//点击头像跳转到对于个人中心
                    if (user_info_type.equals(DataInterface.UserLogin.USER_TYPE_LAWYER)) {
                        getUserInfo(user_info_name, user_info_password, user_info_type);//如果切换到登陆页面系统保存了登陆信息就直接跳转到登陆页面
                    } else if (user_info_type.equals(DataInterface.UserLogin.USER_TYPE_ORDINARY)) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("userid", user_info_name);
                        map.put("password", user_info_password);
                        map.put("phonetype", "android");
                        isOrdinary(map, user_info_type);//如果切换到登陆页面系统保存了登陆信息就直接跳转到登陆页面
                    }
                    isUserType(PrefUtils.getString("user_info_type", "", mActivity));//判断用户角色并禁用其他非本角色的登陆入口
                } else {
                    Toast.makeText(mActivity, "请选择角色登陆...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getNewMassageSize();
    }

    public void getNewMassageSize() {
        final String user_id = PrefUtils.getString("user_id", "", mActivity);
        if (!user_id.isEmpty()) {
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, OrdinaryPersonalCenterActivity.NEW_MASSAGE_SIZE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
                    tvNewMassageSize.setVisibility(View.GONE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("sid", user_id);
                    return map;
                }
            };
            Volley.newRequestQueue(mActivity).add(stringRequest);
        } else {
            tvNewMassageSize.setVisibility(View.GONE);
        }
    }

    /**
     * 判断登陆角色禁用其他角色登陆接口
     *
     * @param user_info_type
     */
    public void isUserType(String user_info_type) {
        if (!user_info_type.isEmpty()) {
            if (user_info_type.equals(DataInterface.UserLogin.USER_TYPE_LAWYER)) {//当前是执业律师
                user1.setEnabled(false);
                user3.setEnabled(false);
                user4.setEnabled(false);
                tvName.setText("执业律师");
            } else if (user_info_type.equals(DataInterface.UserLogin.USER_TYPE_XING_ZHENG_ZHU_GUAN)) {//行政主管
                user1.setEnabled(false);
                user2.setEnabled(false);
                user4.setEnabled(false);
                tvName.setText("行政主管");
            } else if (user_info_type.equals(DataInterface.UserLogin.USER_TYPE_ORDINARY)) {//普同用户
                user3.setEnabled(false);
                user2.setEnabled(false);
                user4.setEnabled(false);
                tvName.setText("普通用户");
            } else if (user_info_type.equals(DataInterface.UserLogin.USER_TYPE_ADMIN)) {//管理员
                user3.setEnabled(false);
                user2.setEnabled(false);
                user1.setEnabled(false);
                tvName.setText("管理员");
            }
        }
    }

    /**
     * 执业律师登陆
     *
     * @param user_info_name
     * @param user_info_password
     * @param user_info_type
     */
    private void getUserInfo(final String user_info_name, final String user_info_password, String user_info_type) {
        if (user_info_type.equals(DataInterface.UserLogin.USER_TYPE_LAWYER)) {//律师角色
            dialogView.show();
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.UserLogin.LAWYER_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("loginData", response);
                    if (!response.isEmpty()) {
                        Gson gson = new Gson();
                        LawyerInfo lawyerInfo = gson.fromJson(response, LawyerInfo.class);
                        ArrayList<LawyerInfo.RowsBean> rows = (ArrayList<LawyerInfo.RowsBean>) lawyerInfo.getRows();
                        LawyerInfo.RowsBean rowsBean = rows.get(0);
                        if (rowsBean.getLawyerinfo() != null) {
                            Intent intent = new Intent(mActivity, LawyerPersonalActivity.class);
                            intent.putExtra("title", "执业律师");
                            Bundle bundle = new Bundle();
                            bundle.putString("photo", rowsBean.getPhoto());//律师头像
                            bundle.putString("id", rowsBean.getId() + "");//律师id
                            bundle.putString("userid", rowsBean.getUserid() + "");
                            bundle.putString("firmId", rowsBean.getGroupid() + "");//律所id
                            bundle.putString("firstname", rowsBean.getFirstName());//律师名称
                            bundle.putString("InfoAnnouncement", rowsBean.getInfoAnnouncement());//信息公告数据地址
                            bundle.putString("Lawyerinfo", rowsBean.getLawyerinfo());//律师基本信息(电子名片)
                            bundle.putString("MyBusiness", rowsBean.getMyBusiness());//我的业务
                            bundle.putString("MyAnswer", rowsBean.getMyAnswer());//我的回答
                            bundle.putString("LawyerPhotoInfo", rowsBean.getLawyerPhotoInfo());//个人信息编辑
                            bundle.putString("PublicConsultationList", rowsBean.getPublicConsultationList());//公众咨询
                            intent.putExtra("info", bundle);
                            mActivity.startActivity(intent);
                        }
                    }
                    dialogView.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialogView.dismiss();
                    Toast.makeText(mActivity, "链接超时，请检查网络链接！", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("username", user_info_name);
                    map.put("password", user_info_password);
                    map.put("logintype", DataInterface.UserLogin.USER_TYPE_LAWYER);//登陆角色
                    return map;
                }
            };
            requestQueue.add(request);
        }
    }

    /**
     * 普通用户登陆
     */
    private void isOrdinary(final HashMap<String, String> map, String type) {
        if (type.equals(DataInterface.UserLogin.USER_TYPE_ORDINARY)) {
            dialogView.show();
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.ORDINARY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("loginData", response);
                    if (response.isEmpty() || "登录名或密码不正确!".equals(response)) {
                        dialogView.dismiss();
                        return;
                    }
                    Gson gson = new Gson();
                    UserQQLogin userQQLogin = gson.fromJson(response, UserQQLogin.class);
                    List<UserQQLogin.RowsBean> rows = userQQLogin.getRows();
                    if (rows != null && rows.size() > 0) {
                        UserQQLogin.RowsBean rowsBean = rows.get(0);
                        Intent intent = new Intent(mActivity, OrdinaryPersonalCenterActivity.class);
                        intent.putExtra("id", rowsBean.getId() + "");//头像
                        intent.putExtra("photo", rowsBean.getPhoto());//头像
                        intent.putExtra("name", rowsBean.getFirstname());//名称
                        intent.putExtra("userid", rowsBean.getUserid());//账号
                        intent.putExtra("password", rowsBean.getPassword());//密码
                        intent.putExtra("OrdinaryUsersInfo", rowsBean.getOrdinaryUsersInfo());//个人中心
                        intent.putExtra("OrdinaryUsersMyQuestions", rowsBean.getOrdinaryUsersMyQuestions());//我的提问
                        intent.putExtra("MessageAlert", rowsBean.getMessageAlert());//消息提醒
                        intent.putExtra("newMassageSize", rowsBean.getSize());//新消息数量
                        Log.i("massage", "首页快捷登陆（验证）：" + rowsBean.getMessageAlert());
                        mActivity.startActivity(intent);
                    }
                    dialogView.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("loginData", "链接出错！");
                    dialogView.dismiss();
                    Toast.makeText(mActivity, "链接超时，请检查网络链接！", Toast.LENGTH_SHORT).show();
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
    }
}
