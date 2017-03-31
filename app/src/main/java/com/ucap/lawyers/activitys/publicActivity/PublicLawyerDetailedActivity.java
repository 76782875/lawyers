package com.ucap.lawyers.activitys.publicActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.ucap.lawyers.bean.vagueFind.FirmFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;
import com.ucap.lawyers.view.GyzsDetailedDialog;
import com.ucap.lawyers.view.HonorDialogView;
import com.ucap.lawyers.view.LoadingDialogView;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 公众搜索显示律师信息
 */
public class PublicLawyerDetailedActivity extends AppCompatActivity {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;
    @Bind(R.id.tv_detailed)
    TextView tvShowDetailed;
    @Bind(R.id.tv_navigation)
    TextView tvNavigation;
    @Bind(R.id.tv_lawyer_name)
    TextView tvFirstname;//姓名
    @Bind(R.id.tv_contact)
    TextView tvContactid;//职业证号
    @Bind(R.id.tv_sex)
    TextView tvSex;//性别
    @Bind(R.id.tv_work_firm)
    TextView tvName;//所在律所
    @Bind(R.id.tv_ryzs)
    TextView tvRyzs;//荣誉表彰
    @Bind(R.id.tv_gyzs)
    TextView tvGyzs;//公益服务
    @Bind(R.id.tv_fmjf)
    TextView tvFmzs;//负面指数
    @Bind(R.id.tv_yaer_assess)
    TextView tvYaerAssess;//年度考核
    @Bind(R.id.tv_gyzs_detailed)
    TextView tvGyzsDetailed;//公益服务查询详细
    @Bind(R.id.tv_screenname)
    TextView tvScreenname;//资格证号
    @Bind(R.id.tv_groupname)
    TextView tvGroupname;//主管机关
    @Bind(R.id.tv_jobtype)
    TextView tvJobtype;//所内职务
    private String address;
    private String id;
    private Intent intent;
    private String ryzs;
    public RequestQueue requestQueue;
    public String name;
    public LoadingDialogView dialogView;
    public String gyzs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_lawyer_detailed);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent = getIntent();
        requestQueue = Volley.newRequestQueue(this);
        dialogView = new LoadingDialogView(this);
        intiDate();
        intiListener();
    }


    private void intiDate() {
        //id
        id = intent.getStringExtra("id");
        //律所地址
        address = intent.getStringExtra("address");
        String contactid = intent.getStringExtra("contactid");//执业证号
        String firstname = intent.getStringExtra("firstname");//律师姓名
        String sex = intent.getStringExtra("sex");//性别
        //所在律所名称
        name = intent.getStringExtra("name");
        String fmzs = intent.getStringExtra("fmzs");//负面指数
        //荣誉指数
        ryzs = intent.getStringExtra("ryzs");
        //公益指数
        gyzs = intent.getStringExtra("gyzs");
        String photo = intent.getStringExtra("photo");//头像
        String year = intent.getStringExtra("yearcheck");//年度考核
        String screenname = intent.getStringExtra("screenname");//资格证号
        String groupname = intent.getStringExtra("groupname");//主管机关
        String jobtype = intent.getStringExtra("jobtype");//职务
        Log.i("photoUri", photo + contactid);
        tvScreenname.setText(screenname);
        tvGroupname.setText(groupname);
        tvJobtype.setText(jobtype);
        tvContactid.setText(contactid);
        tvFirstname.setText(firstname);
        tvSex.setText(sex);
        tvName.setText(name);
        tvFmzs.setText(fmzs);
        tvRyzs.setText(ryzs);
        tvGyzs.setText(gyzs);
        tvYaerAssess.setText(year);
        Glide.with(this)
                .load(photo + contactid)
                .into(ivPhoto);
    }

    private void intiListener() {
        if ("无".equals(ryzs)) {//判读"荣誉指数"是否有
            tvShowDetailed.setVisibility(View.INVISIBLE);
        }
        //点击荣誉指数 查看详细
        tvShowDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HonorDialogView.showDialog(PublicLawyerDetailedActivity.this, id + "", DataInterface.LAWYER_COMMEND_KEY, DataInterface.LAWYER_COMMEND);
            }
        });
        tvNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNavigation();
                Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();
            }
        });
        //点击律所名称跳转到律所详细
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.show();
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                findFirm(map);
            }
        });
        if ("无".equals(gyzs)) {
            tvGyzsDetailed.setVisibility(View.INVISIBLE);
        }
        //点击公益服务 查看详细
        tvGyzsDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GyzsDetailedDialog.showDialog(PublicLawyerDetailedActivity.this, id, DataInterface.LAWYER_PUBLIC_SERVICE, "id");
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
                        JumpDetailedTools.jumpFirmRybzDetailed(PublicLawyerDetailedActivity.this, item);//跳转到律所详细页面
                    } else {
                        Toast.makeText(PublicLawyerDetailedActivity.this, "未查询到所属律所信息！", Toast.LENGTH_SHORT).show();
                    }
                }
                dialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogView.dismiss();
                Toast.makeText(PublicLawyerDetailedActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
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
            intent.setData(Uri.parse("baidumap://map/direction?origin=我的位置&" + "destination=" + address + "&mode=driving&region=成都"));
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
