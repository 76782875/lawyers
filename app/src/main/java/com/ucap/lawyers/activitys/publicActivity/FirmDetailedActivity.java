package com.ucap.lawyers.activitys.publicActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.homeFind.LawyerDate;
import com.ucap.lawyers.tools.JumpDetailedTools;
import com.ucap.lawyers.view.GyzsDetailedDialog;
import com.ucap.lawyers.view.HonorDialogView;
import com.ucap.lawyers.view.LawyerListDialogView;
import com.ucap.lawyers.view.LoadingDialogView;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FirmDetailedActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.tv_detailed)
    TextView tvShowDetailed;//荣誉表彰查看详细
    @Bind(R.id.tv_show_lawyerlist)
    TextView tvSowLawyerlist;//律师名单查看更多
    @Bind(R.id.tv_gyzs_detailed)
    TextView tvGyzsDetailed;//公益服务查询详细
    @Bind(R.id.tv_navigation)
    TextView tvNavigation;//点击导航
    @Bind(R.id.tv_firm_name)
    TextView tvFirmName;//律所名称
    @Bind(R.id.tv_description)
    TextView tvDescription;//简介
    @Bind(R.id.tv_address)
    TextView tvAddress;//地址
    @Bind(R.id.tv_groupnum)
    TextView tvGroupnum;//许可证号
    @Bind(R.id.tv_groupnaeme)
    TextView tvGroupnName;//主管机关
    @Bind(R.id.tv_typesettings)
    TextView tvTypesettings;//负责人
    @Bind(R.id.tv_phone)
    TextView tvPhone;//电话
    @Bind(R.id.tv_email)
    TextView tvEmail;//邮箱
    @Bind(R.id.tv_web_url)
    TextView tvWebUrl;//网站
    @Bind(R.id.tv_ryzs)
    TextView tvRyzs;//荣誉表彰
    @Bind(R.id.tv_gyzs)
    TextView tvGyzs;//公益服务
    @Bind(R.id.tv_fmjf)
    TextView tvFmjf;//负面指数
    @Bind(R.id.tv_gfzs)
    TextView tvGfzs;//规范指数
    @Bind(R.id.tv_yaer_assess)
    TextView tvYaerArress;//年度考核
    @Bind(R.id.tv_show_name)
    TextView tvShowName;//组织形式
    @Bind(R.id.ll_lawyers_list)
    LinearLayout llLawyerList;//律师名单
    @Bind(R.id.ll_hhr)
    LinearLayout llHHR;//合伙人布局
    @Bind(R.id.rv_hhrlist)
    RecyclerView rvList;//合伙人列表

    private String address;
    private String id;
    private ArrayList<String> lawyerlist;
    public String name;
    public String gyzs;
    public LoadingDialogView dialogView;
    public RequestQueue requestQueue;
    public String typesettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_detailed);
        ButterKnife.bind(this);
        intiData();
        dialogView = new LoadingDialogView(this);
        requestQueue = Volley.newRequestQueue(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvShowDetailed.setOnClickListener(new View.OnClickListener() {//点击荣誉指数查看详细
            @Override
            public void onClick(View view) {
                HonorDialogView.showDialog(FirmDetailedActivity.this, id, DataInterface.FIRM_COMMEND_KEY, DataInterface.FIRM_COMMEND);
            }
        });
        tvNavigation.setOnClickListener(new View.OnClickListener() {//点击导航
            @Override
            public void onClick(View view) {
                startNavigation();
            }
        });
        tvSowLawyerlist.setOnClickListener(new View.OnClickListener() {//点击律师名单查看更多
            @Override
            public void onClick(View view) {
                LawyerListDialogView.showDialog(FirmDetailedActivity.this, lawyerlist, id);
            }
        });
        if ("无".equals(gyzs)) {
            tvGyzsDetailed.setVisibility(View.INVISIBLE);
        }
        tvGyzsDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//点击公益指数查看详细
                GyzsDetailedDialog.showDialog(FirmDetailedActivity.this, name, DataInterface.FIRM_PUBLIC_SERVICE, "name");
            }
        });
        tvTypesettings.setOnClickListener(new View.OnClickListener() {//点击负责人名称
            @Override
            public void onClick(View view) {
                dialogView.show();
                getDataLawyer(typesettings, dialogView, requestQueue);
            }
        });

    }


    private void intiData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //律所名称
        name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");//律所简介
        //律所地址
        address = intent.getStringExtra("address");
        String groupnum = intent.getStringExtra("groupnum");//许可证号
        //负责人
        typesettings = intent.getStringExtra("typesettings");
        String phone = intent.getStringExtra("phone");//律所电话
        String email = intent.getStringExtra("email");//律所邮箱
        String weburl = intent.getStringExtra("weburl");//律所网站
        String fmjf = intent.getStringExtra("fmjf");//负面指数
        String ryzs = intent.getStringExtra("ryzs");//荣誉指数
        //公益指数
        gyzs = intent.getStringExtra("gyzs");
        String gfzs = intent.getStringExtra("gfzs");//规范指数
        String yaerassess = intent.getStringExtra("yaerassess");
        ArrayList<String> hhrList = intent.getStringArrayListExtra("hhrList");//合伙人列表
        if (hhrList != null && hhrList.size() > 0) {
            showHHRList(hhrList);
            llHHR.setVisibility(View.VISIBLE);
        } else {
            llHHR.setVisibility(View.GONE);
        }

        //律师名单
        lawyerlist = intent.getStringArrayListExtra("lawyerlist");
        String showname = intent.getStringExtra("showname");//组织形式
        tvShowName.setText(showname);
        String groupname = intent.getStringExtra("groupname");//主管机关
        tvFirmName.setText(name);
        tvDescription.setText(description);
        tvAddress.setText(address);
        tvGroupnum.setText(groupnum);
        tvGroupnName.setText(groupname);
        tvTypesettings.setText(typesettings);
        tvPhone.setText(phone);
        tvEmail.setText(email);
        tvWebUrl.setText(weburl);
        tvRyzs.setText(ryzs);
        if ("无".equals(ryzs) || "0".equals(ryzs)) {//如果没有荣誉表彰就隐藏"查看详细"按钮
            tvShowDetailed.setVisibility(View.INVISIBLE);
        }
        tvGyzs.setText(gyzs);
        tvFmjf.setText(fmjf);
        tvGfzs.setText(gfzs);
        tvYaerArress.setText(yaerassess);

        int count = 5;
        if (lawyerlist.size() < 5) {
            count = lawyerlist.size();
        }
        for (int i = 0; i < count; i++) {//律师名单
            final String nameList = lawyerlist.get(i);
            TextView tvName = (TextView) View.inflate(this, R.layout.item_dialog_lawyer_list, null);
            tvName.setText(nameList);
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.show();
                    getDataLawyer(nameList, dialogView, requestQueue);
                }
            });
            llLawyerList.addView(tvName);
        }


    }

    //显示合伙人
    public void showHHRList(ArrayList<String> hhrList) {
        rvList.setLayoutManager(new GridLayoutManager(this, 4));
        rvList.setAdapter(new HHRListAdapter(hhrList));
    }

    /**
     * 点击律师名字跳转到律师详细页面
     */
    public void getDataLawyer(final String name, final LoadingDialogView lodingDialogView, RequestQueue requestQueue) {

        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.FIRM_DETAILED_LAWYERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("lawyersData", response);
                if (!response.isEmpty()) {
                    Gson gson = new Gson();
                    LawyerDate lawyerDate = gson.fromJson(response, LawyerDate.class);
                    List<LawyerDate.RowsBean> rows = lawyerDate.getRows();
                    LawyerDate.RowsBean rowsBean = rows.get(0);
                    JumpDetailedTools.jumpFirmLawyerListDetailed(FirmDetailedActivity.this, rowsBean);//跳转到律师详细
                }
                lodingDialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lodingDialogView.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接失败!", Toast.LENGTH_LONG).show();
                Log.i("HttpException", error.getLocalizedMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("firstname", name);
                map.put("id", id);
                return map;
            }
        };
        Log.i("url", request.getUrl());
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
    int temp = 0;

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
        if (temp <= 2) {
            try {
                temp++;
                Intent intent = new Intent();//destination=五道口&mode=driving&region=北京
                intent.setData(Uri.parse("baidumap://map/direction?origin=我的位置&" + "destination=" + address + "&mode=driving&region=成都"));
                startActivity(intent);
            } catch (Exception e) {
                minimap();
            }
        } else {
            Toast.makeText(this, "导航功能,支持\"百度地图\"和\"高德地图\",系统未监测到...", Toast.LENGTH_LONG).show();
        }

    }

    private void minimap() {//高德地图
        if (temp <= 2) {
            try {
                temp++;
                Intent intent = new Intent();
                intent.setData(Uri
                        .parse("androidamap://route?sourceApplication=softname"
                                + "&slat=36.2&slon=116.1&sname=我的位置&dname=" + address + "  " + name + "&dev=0&m=0&t=1&showType=1"));
                startActivity(intent);
            } catch (Exception e) {
                baiduMap();
            }
        } else {
            Toast.makeText(this, "导航功能,支持\"百度地图\"和\"高德地图\",系统未监测到...", Toast.LENGTH_LONG).show();
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

    /**
     * 合伙人适配器
     */
    class HHRListAdapter extends RecyclerView.Adapter<HHRListViewHolder> {
        ArrayList<String> mData;

        public HHRListAdapter(ArrayList<String> mData) {
            this.mData = mData;
        }

        @Override
        public HHRListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HHRListViewHolder(View.inflate(parent.getContext(), R.layout.item_hhrlist, null));
        }

        @Override
        public void onBindViewHolder(HHRListViewHolder holder, final int position) {
            holder.tvName.setText(mData.get(position));
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.show();
                    getDataLawyer(mData.get(position), dialogView, requestQueue);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    class HHRListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;//名称

        public HHRListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
