package com.ucap.lawyers.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys.OrdinaryPersonalCenterActivity;
import com.ucap.lawyers.adapter.ViewPagerAdapter;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.base.imple.FirmBase;
import com.ucap.lawyers.base.imple.HomeBase;
import com.ucap.lawyers.base.imple.LawyerBase;
import com.ucap.lawyers.base.imple.PersonalCenterBase;
import com.ucap.lawyers.bean.VersionUpdate;
import com.ucap.lawyers.service.NewMassageService;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.UpdateLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Color.BLACK;

public class HomeActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.vp_content)
    ViewPager vpContent;
    @Bind(R.id.rg_buttons)
    RadioGroup rgButtons;
    @Bind(R.id.btn_home)
    RadioButton btnHome;
    @Bind(R.id.btn_firm)
    RadioButton btnFirm;
    @Bind(R.id.btn_lawyer)
    RadioButton btnLawyer;
    @Bind(R.id.btn_my)
    RadioButton btnPersonal;
    @Bind(R.id.iv_firm_invisible)

    ImageView ivFirmUp;
    @Bind(R.id.iv_lawyer_invisible)
    ImageView ivLawyerUp;
    @Bind(R.id.iv_home)
    ImageView ivHome;
    @Bind(R.id.iv_firm)
    ImageView ivFirm;
    @Bind(R.id.iv_lawyer)
    ImageView ivLawyer;
    @Bind(R.id.iv_personal)
    ImageView ivPersonal;
    @Bind(R.id.btn_location)
    TextView btnLocation;
    @Bind(R.id.tv_new_massage_icon)
    TextView tvNewMassageSize;//新消息提醒数量
    private ArrayList<PagerBase> mListDate;
    public RequestQueue requestQueue;
    public static boolean isSplash = true;
    public LoadingDialogView dialogView;
    public PersonalCenterBase personalCenterBase;
    public AMapLocationClient mLocationClient;
    public static String WEIXIN_APP_ID = "wxc7fc3fa765fe1862";
    public static IWXAPI wxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_home);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//启动时不弹出输入框
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (isSplash) {//启动画面只执行一次
            startActivity(new Intent(this, SplashActivity.class));
            isSplash = false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(BLACK);
        }
        requestQueue = Volley.newRequestQueue(this);
        ButterKnife.bind(this);
        intiDate();
        intiListener();
        update();
        getMyLocation();
        //注册微信
        wxapi = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, false);
        wxapi.registerApp(WEIXIN_APP_ID);
        startNewMassgaeService();
    }

    /**
     * 启动新消息推送服务
     */
    private void startNewMassgaeService() {
        Intent intent = new Intent(this, NewMassageService.class);
        startService(intent);
    }

    /**
     * 版本更新
     */

    public void update() {
        StringRequest request = new StringRequest(StringRequest.Method.GET, DataInterface.VERSION_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("update", response);
                Gson gson = new Gson();
                VersionUpdate versionUpdate = gson.fromJson(response, VersionUpdate.class);
                VersionUpdate.RowsBean rowsBean = versionUpdate.getRows().get(0);
                try {
                    int versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                    if (versionCode < rowsBean.getVersionnumber()) {//比较新版本和本地版本
                        UpdateLoadingDialog.showDialog(HomeActivity.this, HomeActivity.this, rowsBean.getVersionnumber(), rowsBean.getVersioname(), rowsBean.getVersiondescription(), rowsBean.getVersionurl());
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private void intiListener() {
        btnLocation.setOnClickListener(this);

    }

    /**
     * 启用指定的图标
     *
     * @param position
     */
    public void setImageVIewEn(int position) {
        switch (position) {
            case 0:
                ivHome.setSelected(true);
                ivFirm.setSelected(false);
                ivLawyer.setSelected(false);
                ivPersonal.setSelected(false);
                break;
            case 1:
                ivHome.setSelected(false);
                ivFirm.setSelected(true);
                ivLawyer.setSelected(false);
                ivPersonal.setSelected(false);
                break;
            case 2:
                ivHome.setSelected(false);
                ivFirm.setSelected(false);
                ivLawyer.setSelected(true);
                ivPersonal.setSelected(false);
                break;
            case 3:
                ivHome.setSelected(false);
                ivFirm.setSelected(false);
                ivLawyer.setSelected(false);
                ivPersonal.setSelected(true);
                break;
        }
    }

    private void intiDate() {
        dialogView = new LoadingDialogView(this);
        ivHome.setOnClickListener(this);
        ivFirm.setOnClickListener(this);
        ivLawyer.setOnClickListener(this);
        ivPersonal.setOnClickListener(this);
        mListDate = new ArrayList<>();
        mListDate.add(new HomeBase(this));
        mListDate.add(new FirmBase(this));
        mListDate.add(new LawyerBase(this));
        personalCenterBase = new PersonalCenterBase(this);
        mListDate.add(personalCenterBase);
        vpContent.setAdapter(new ViewPagerAdapter(mListDate));
        rgButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_home://首页
                        vpContent.setCurrentItem(0);
                        ivFirmUp.setVisibility(View.GONE);//隐藏律所收缩按钮
                        ivLawyerUp.setVisibility(View.GONE);//隐藏律师收缩按钮
                        btnLocation.setVisibility(View.VISIBLE);
                        setImageVIewEn(0);//启用对应的图标
                        // mListDate.get(0).intiDate();//切换到对应页面时才初始化对应数据
                        break;
                    case R.id.btn_firm://律所
                        vpContent.setCurrentItem(1);
                        ivFirmUp.setVisibility(View.VISIBLE);//只有在律所页面才显示收缩按钮
                        ivLawyerUp.setVisibility(View.GONE);//只有在律所页面才显示收缩按钮
                        btnLocation.setVisibility(View.GONE);
                        setImageVIewEn(1);//启用对应的图标
                        // mListDate.get(1).intiDate();
                        break;
                    case R.id.btn_lawyer://律师
                        vpContent.setCurrentItem(2);
                        ivFirmUp.setVisibility(View.GONE);
                        ivLawyerUp.setVisibility(View.VISIBLE);
                        btnLocation.setVisibility(View.GONE);
                        setImageVIewEn(2);//启用对应的图标
                        // mListDate.get(2).intiDate();
                        break;
                    case R.id.btn_my://个人中心(我的)
                        vpContent.setCurrentItem(3);
                        ivFirmUp.setVisibility(View.GONE);
                        ivLawyerUp.setVisibility(View.GONE);
                        btnLocation.setVisibility(View.GONE);
                        setImageVIewEn(3);//启用对应的图标

                        //  mListDate.get(3).intiDate();
//                        Toast.makeText(HomeActivity.this, "此版本暂时提供查询功能，敬请期待...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btnHome.setChecked(true);
                        break;
                    case 1:
                        btnFirm.setChecked(true);
                        break;
                    case 2:
                        btnLawyer.setChecked(true);
                        break;
                    case 3:
                        btnPersonal.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setImageVIewEn(0);//默认启用第一个图标
    }


    @Override
    public void onClick(View v) {
        //点击对应图标,切换到对应页面
        if (v == ivHome) {
            setImageVIewEn(0);//对应图标选择状态
            btnHome.setChecked(true);//对应页标选中状态
            vpContent.setCurrentItem(0);//切换到对应页面
        } else if (v == ivFirm) {
            setImageVIewEn(1);
            btnFirm.setChecked(true);
            vpContent.setCurrentItem(1);
        } else if (v == ivLawyer) {
            setImageVIewEn(2);
            btnLawyer.setChecked(true);
            vpContent.setCurrentItem(2);
        } else if (v == ivPersonal) {
            setImageVIewEn(3);
            btnPersonal.setChecked(true);
            vpContent.setCurrentItem(3);

        } else if (v == btnLocation) {
            startActivity(new Intent(HomeActivity.this, LocationActivity.class));
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
//        友盟统计
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        personalCenterBase.intiDate();//回主页面时刷新角色选择的状态，禁用掉非本角色的登陆入口
    }

    /**
     * 打开客户端获取当前位置信息
     * <p>
     * ps：改客户端只能咋成都市使用
     */
    public void getMyLocation() {
        //定位回调
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    //错误码为0时标示定位成功
                    if (aMapLocation.getErrorCode() == 0) {
                        Log.e("AmapError", "城市：" + aMapLocation.getCity() + "  " + aMapLocation.getDistrict()
                                + "----国籍：" + aMapLocation.getCountry());
                        PrefUtils.putString("my_location", aMapLocation.getCity(), getApplicationContext());//保存位置信息在本地
                        PrefUtils.remove("my_location_err", getApplicationContext());
//                        Toast.makeText(getApplicationContext(), PrefUtils.getString("my_location", "", getApplicationContext()), Toast.LENGTH_SHORT).show();
                    } else {
                        PrefUtils.putString("my_location_err", aMapLocation.getErrorInfo() + "", getApplicationContext());//保存位置信息在本地
//                        Toast.makeText(getApplicationContext(), aMapLocation.getErrorCode() + "", Toast.LENGTH_SHORT).show();
                        Log.e("AmapError", "ErrCode:" + aMapLocation.getErrorCode() + ",errInfo:" + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //定位添加监听
        mLocationClient.setLocationListener(mLocationListener);
        //用于定位参数设置
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置高精度定位
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置单次定位
//        mLocationOption.setOnceLocation(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(10000);
        //将参数设置给定位器
        mLocationClient.setLocationOption(mLocationOption);
        //开始定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位
        PrefUtils.remove("my_location", getApplicationContext());//删除位置信息
        PrefUtils.remove("my_location_err", getApplicationContext());//删除定位错误信息
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNewMassageSize();
    }

    public void getNewMassageSize() {
        final String user_id = PrefUtils.getString("user_id", "", this);
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
            requestQueue.add(stringRequest);
        } else {
            tvNewMassageSize.setVisibility(View.GONE);
        }
    }
}
