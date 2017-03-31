package com.ucap.lawyers.activitys.publicActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerCard;
import com.ucap.lawyers.bean.vagueFind.FirmFindData;
import com.ucap.lawyers.view.HonorDialogView;
import com.ucap.lawyers.view.LoadingDialogView;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LawyerDetailedActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.tv_navigation)
    TextView tvNavigation;//点击导航
    @Bind(R.id.tv_firstname)
    TextView tvFirstname;//律师名称
    @Bind(R.id.tv_openid)
    TextView tvOpenid;//身份证号
    @Bind(R.id.tv_name)
    TextView tvName;//所在律师名称
    @Bind(R.id.tv_jobtype)
    TextView tvJobtype;//职务
    @Bind(R.id.tv_conphone)
    TextView tvConphone;//电话
    @Bind(R.id.tv_emailaddress)
    TextView tvEmailaddress;//邮箱
    @Bind(R.id.tv_qq)
    TextView tvQQ;//qq
    @Bind(R.id.tv_weixin)
    TextView tvWeiXin;//微信
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;//头像
    @Bind(R.id.iv_code)
    ImageView ivCode;//二维码

    public RequestQueue requestQueue;
    public String id;
    public String address;
    public String name;
    public LoadingDialogView dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_detailed);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        intiDate();
        intiListener();
    }

    private void intiDate() {
        Intent intent = getIntent();
        String lawyerinfoUri = intent.getStringExtra("lawyerinfo");
        id = intent.getStringExtra("id");
        getDtaForSevice(lawyerinfoUri, id);
        dialogView = new LoadingDialogView(this);
    }

    /**
     * 获取服务器，信息
     *
     * @param lawyerinfoUri
     */
    private void getDtaForSevice(String lawyerinfoUri, final String id) {
        Log.i("lawyerinfoUri", lawyerinfoUri);
        StringRequest request = new StringRequest(StringRequest.Method.POST, lawyerinfoUri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("serviceData", response);
                gsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", id);
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析数据, 并显示数据
     *
     * @param data
     */
    public void gsonData(String data) {
        Gson gson = new Gson();
        LawyerCard lawyerCard = gson.fromJson(data, LawyerCard.class);
        LawyerCard.RowsBean rowsBean = lawyerCard.getRows().get(0);
        //律所地址
        address = rowsBean.getAddress();
        name = rowsBean.getName();
        tvFirstname.setText(rowsBean.getFirstname());//律师
        tvOpenid.setText(rowsBean.getOpenid());//身份证号
        tvName.setText(name);//律所名称
        tvJobtype.setText(rowsBean.getJobtype());//职务
        tvConphone.setText(rowsBean.getConphone());//电话
        tvEmailaddress.setText(rowsBean.getEmailaddress());//邮箱
        tvQQ.setText(rowsBean.getQq());//QQ
        tvWeiXin.setText(rowsBean.getWeixin());//weixin
        String photo = rowsBean.getPhoto();//头像地址
        String code = rowsBean.getCode();//二维码地址；
        Glide.with(this)
                .load(photo)
                .into(ivPhoto);
        Glide.with(this)
                .load(code)
                .into(ivCode);
    }

    private void intiListener() {
        tvNavigation.setOnClickListener(new View.OnClickListener() {//点击导航
            @Override
            public void onClick(View view) {
                startNavigation();
            }
        });
        tvName.setOnClickListener(new View.OnClickListener() {//点击律所名称
            @Override
            public void onClick(View view) {
                dialogView.show();
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                findFirm(map);
            }
        });
    }

    /**
     * 点击律所名称时查询改律所详细信息并跳转到详细页面
     *
     * @param map
     */
    public void findFirm(final HashMap<String, String> map) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Firm.FIRM_VAGUE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("dataFirm", response);
                if (!response.isEmpty()) {
                    Gson gson = new Gson();
                    FirmFindData firmFindData = gson.fromJson(response, FirmFindData.class);
                    List<FirmFindData.RybzrowBean> rybzrow = firmFindData.getRybzrow();
                    if (rybzrow != null && rybzrow.size() > 0) {
                        FirmFindData.RybzrowBean item = rybzrow.get(0);
                        Intent intent = new Intent(LawyerDetailedActivity.this, FirmDetailedActivity.class);
                        intent.putExtra("id", item.getId() + "");
                        intent.putExtra("name", item.getName());
                        intent.putExtra("description", item.getDescription());
                        intent.putExtra("phone", item.getPhone());
                        intent.putExtra("address", item.getAddress());
                        intent.putExtra("email", item.getEmail());
                        intent.putExtra("weburl", item.getWeburl());
                        intent.putExtra("fmjf", item.getFmzs());
                        intent.putExtra("ryzs", item.getRyzs() + "");
                        intent.putExtra("gyzs", item.getGyzs() + "");
                        intent.putExtra("gfzs", item.getGfzs());
                        intent.putExtra("groupnum", item.getGroupnum());
                        intent.putExtra("typesettings", item.getTypesettings());
                        intent.putExtra("yaerassess", item.getYearcheck());
                        intent.putExtra("groupname", item.getGroupname());
                        intent.putStringArrayListExtra("lawyerlist", (ArrayList<String>) item.getLawyerlist());
                        startActivity(intent);
                    } else {
                        Toast.makeText(LawyerDetailedActivity.this, "未查询到所属律所信息！", Toast.LENGTH_SHORT).show();
                    }
                }
                dialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogView.dismiss();
                Toast.makeText(LawyerDetailedActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        requestQueue.add(request);
    }


    //判断是否安装目标应用
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }

    /**
     * 启动第三方导航软件
     */
    private void startNavigation() {
        if (isInstallByread("com.baidu.BaiduMap")) {
            baiduMap();
        } else if (isInstallByread("com.autonavi.minimap")) {
            minimap();
        } else {
            Toast.makeText(this, "导航功能,支持\"百度地图\"和\"高德地图\",系统未监测到...", Toast.LENGTH_LONG).show();
        }
    }

    private void baiduMap() {//百度地图
        try {
            Intent intent = new Intent();//destination=五道口&mode=driving&region=北京
            intent.setData(Uri.parse("baidumap://map/direction?origin=我的位置&" + "destination=" + address + "  " + name + "&mode=driving&region=成都"));
            startActivity(intent);
        } catch (Exception e) {
            minimap();
        }
    }

    private void minimap() {//高德地图
        try {
            Intent intent = new Intent();
            intent.setData(Uri
                    .parse("androidamap://route?sourceApplication=softname"
                            + "&slat=36.2&slon=116.1&sname=我的位置&dname=" + address + "  " + name + "&dev=0&m=0&t=1&showType=1"));
            startActivity(intent);
        } catch (Exception e) {
            baiduMap();
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
