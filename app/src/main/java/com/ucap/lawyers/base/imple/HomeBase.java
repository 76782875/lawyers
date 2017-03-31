package com.ucap.lawyers.base.imple;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.ucap.lawyers.activitys.ConsultationActivity;
import com.ucap.lawyers.activitys.publicActivity.LawCanonActivity;
import com.ucap.lawyers.activitys.publicActivity.LawsRegulationsActivity;
import com.ucap.lawyers.activitys.publicActivity.RankingActivity;
import com.ucap.lawyers.activitys.publicActivity.answer.AnswerListActivity;
import com.ucap.lawyers.activitys.userLogin.UserOrdinaryActivity;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.FirmLawyerDataSize;
import com.ucap.lawyers.bean.LawListData;
import com.ucap.lawyers.bean.vagueFind.FirmFindData;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.sql.sqlUtils.HomeFindSQLUtils;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.AnswerClassifySelectDialog;
import com.ucap.lawyers.view.FirmSeleteDialog;
import com.ucap.lawyers.view.LawCanonSelectDialog;
import com.ucap.lawyers.view.LawMoreDialog;
import com.ucap.lawyers.view.LawyerSelectDialogView;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/12.
 */
public class HomeBase extends PagerBase implements View.OnClickListener {

    @Bind(R.id.ll_law_item)
    LinearLayout llLawList;
    @Bind(R.id.btn_firm_find)
    Button btnFirmFind;//律师查询
    @Bind(R.id.btn_lawyer_find)
    Button btnLawyerFind;//律师查询
    @Bind(R.id.btn_consultation)
    Button btnConsultation;//我要咨询
    @Bind(R.id.et_firm_name)
    EditText etFirmName;//律所名称
    @Bind(R.id.et_firm_license)
    EditText etFirmLicense;//律所许可证号
    @Bind(R.id.et_lawyer_name)
    EditText etLawyerName;//律师名称
    @Bind(R.id.et_lawyer_license)
    EditText etLawyerLicense;//律师执业证号
    @Bind(R.id.tv_law_more)
    TextView tvLaeMore;//政策法规查看更多
    @Bind(R.id.btn_ranking)
    Button btnRanking;//解答排行
    @Bind(R.id.btn_answer)
    Button btnAnswer;//已答问题
    @Bind(R.id.btn_legal_encyclopedia)
    Button btnLegalEncyclopedia;//百姓法宝
    @Bind(R.id.tv_firm_size)
    TextView tvFirmSize;//律所数据总数
    @Bind(R.id.tv_lawyer_size)
    TextView tvLawyersSize;//律所数据总数
    ArrayList<LawListData.RowsBean> mListDate;//政策法规数据
    private LoadingDialogView lodingDialogView;
    private HomeFindSQLUtils sqlUtils;//用于保存用户输入的查询信息,模糊提醒用户
    private PopupWindow pw;
    private RequestQueue requestQueue;

    public HomeBase(Activity mActivity) {
        super(mActivity);
        setDate();

    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.base_home, null);
        ButterKnife.bind(this, view);
        flContent.addView(view);
        sqlUtils = new HomeFindSQLUtils(mActivity);
        mListDate = new ArrayList<>();
        lodingDialogView = new LoadingDialogView(mActivity);
        this.requestQueue = Volley.newRequestQueue(mActivity);
        String firm_and_lawyer_data_size = PrefUtils.getString("FIRM_AND_LAWYER_DATA_SIZE", "", mActivity);
        if (!firm_and_lawyer_data_size.isEmpty()) {
            gsonFirmAndLawyerDataSize(firm_and_lawyer_data_size);
        }
        getFirmAndLawyerDataSize();
    }

    /**
     * 获取律师，律所数据总数
     */
    public void getFirmAndLawyerDataSize() {
        Volley.newRequestQueue(mActivity).add(new StringRequest(DataInterface.FIRM_AND_LAWYER_DATA_SIZE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dataSize", response);
                PrefUtils.putString("FIRM_AND_LAWYER_DATA_SIZE", response, mActivity);
                gsonFirmAndLawyerDataSize(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    /**
     * 解析解析数据大小的数据
     *
     * @param response
     */
    private void gsonFirmAndLawyerDataSize(String response) {
        if (!response.isEmpty()) {
            Gson gson = new Gson();
            FirmLawyerDataSize firmLawyerDataSize = gson.fromJson(response, FirmLawyerDataSize.class);
            tvFirmSize.setText("(" + firmLawyerDataSize.getLawfirmsize() + "条)");
            tvLawyersSize.setText("(" + firmLawyerDataSize.getLawyersize() + "条)");
        }
    }

    @Override
    public void intiListener() {
        btnFirmFind.setOnClickListener(this);
        btnLawyerFind.setOnClickListener(this);
        btnConsultation.setOnClickListener(this);
        tvLaeMore.setOnClickListener(this);
        btnRanking.setOnClickListener(this);
        btnAnswer.setOnClickListener(this);
        btnLegalEncyclopedia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_firm_find://律所查询
                String firmName = etFirmName.getText().toString();
                String firmLicense = etFirmLicense.getText().toString();
                if (!firmName.isEmpty() && !firmLicense.isEmpty()) {//查询时输入,"律所名称"和"律所许可证号"
                    lodingDialogView.show();
                    HashMap<String, String> map1 = new HashMap<>();
                    map1.put("name", firmName);
                    map1.put("groupnum", firmLicense);
                    findFirmDateForService(map1, firmName, firmLicense);
                } else if (!firmName.isEmpty() && firmLicense.isEmpty()) {//查询时输入,"律所名称"
                    lodingDialogView.show();
                    HashMap<String, String> map2 = new HashMap<>();
                    map2.put("name", firmName);
                    findFirmDateForService(map2, firmName, "");
                } else if (firmName.isEmpty() && !firmLicense.isEmpty()) {//查询时输入,"律所许可证"
                    lodingDialogView.show();
                    HashMap<String, String> map3 = new HashMap<>();
                    map3.put("groupnum", firmLicense);
                    findFirmDateForService(map3, "", firmLicense);
                } else {
                    Toast.makeText(mActivity, "不能为空", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_lawyer_find://律师查询
                String lawyerName = etLawyerName.getText().toString();
                String lawyerLicense = etLawyerLicense.getText().toString();
                if (!lawyerLicense.isEmpty() && !lawyerName.isEmpty()) {//查询时输入,"律师名称"和"律师许可证号"
                    lodingDialogView.show();
                    HashMap<String, String> map1 = new HashMap<>();
                    map1.put("firstname", lawyerName);
                    map1.put("contactid", lawyerLicense);
                    findLawyerDateForService(map1, lawyerName, lawyerLicense);
                } else if (!lawyerName.isEmpty() && lawyerLicense.isEmpty()) {//查询时输入,"律师名称"
                    lodingDialogView.show();
                    HashMap<String, String> map1 = new HashMap<>();
                    map1.put("firstname", lawyerName);
                    findLawyerDateForService(map1, lawyerName, lawyerLicense);
                } else if (!lawyerLicense.isEmpty() && lawyerName.isEmpty()) {//查询时输入,"律师执业证号"
                    lodingDialogView.show();
                    HashMap<String, String> map1 = new HashMap<>();
                    map1.put("contactid", lawyerLicense);
                    findLawyerDateForService(map1, lawyerName, lawyerLicense);
                } else {
                    Toast.makeText(mActivity, "不能为空", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_consultation://我要咨询
                //判断定位授权
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    isConsultationSize();
                } else {//请求用户授权
                    String[] location = {Manifest.permission.ACCESS_COARSE_LOCATION};
                    ActivityCompat.requestPermissions(mActivity, location, 0);
                }
                break;
            case R.id.tv_law_more://政策法规查看更多
                if (mListDate.size() > 0)
                    LawMoreDialog.showDialog(mActivity, mListDate);
                break;
            case R.id.btn_ranking://解答排行
                mActivity.startActivity(new Intent(mActivity, RankingActivity.class));
                break;
            case R.id.btn_answer://已答问题
                AnswerClassifySelectDialog.showDialog(mActivity, new AnswerClassifySelectDialog.OnSelectListener() {
                    @Override
                    public void onSelect(String id) {
                        Intent intent = new Intent(mActivity, AnswerListActivity.class);
                        intent.putExtra("classify", id);
                        mActivity.startActivity(intent);
                    }
                });
                break;
            case R.id.btn_legal_encyclopedia://百姓法宝
                LawCanonSelectDialog.showDialog(mActivity, new LawCanonSelectDialog.OnSelectListener() {
                    @Override
                    public void onSelect(String id) {
                        Intent intent = new Intent(mActivity, LawCanonActivity.class);
                        intent.putExtra("title", id);
                        mActivity.startActivity(intent);
                    }
                });
//
                break;
        }
    }

    /**
     * 点击我要咨询时请求一次服务器返回剩余还能咨询的条数
     */
    public void isConsultationSize() {
        final String user_id = PrefUtils.getString("user_id", "", mActivity);//普通用户id，律师不能使用咨询
        if (!user_id.isEmpty()) {
            lodingDialogView.show();
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, DataInterface.CONSULTATION, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("consultationData", response);
                    String my_location = PrefUtils.getString("my_location", "", mActivity);//判断地区
                    String my_location_err = PrefUtils.getString("my_location_err", "", mActivity);
                    int size = new Gson().fromJson(response, ConsultationSize.class).getRows().get(0).getSize();
                    if (!user_id.isEmpty() && size > 0) {
                        if (!my_location.isEmpty() && my_location.equals("成都市")) {//判断地址
                            Intent intent = new Intent(mActivity, ConsultationActivity.class);
                            intent.putExtra("size", size + "");
                            mActivity.startActivity(intent);
                        } else if (!my_location.isEmpty() && !my_location.equals("成都市")) {
                            Toast.makeText(mActivity, "咨询功能只在成都市区内有效...", Toast.LENGTH_SHORT).show();
                        } else if (!my_location_err.isEmpty()) {
                            Toast.makeText(mActivity, "无法获取当前的位置信息，请稍后重试 原因：" + my_location_err, Toast.LENGTH_LONG).show();
                        }
                    } else if (size <= 0) {
                        Toast.makeText(mActivity, "每天只能咨询五条！", Toast.LENGTH_SHORT).show();
                    }
                    lodingDialogView.dismiss();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mActivity, "网络链接错误！", Toast.LENGTH_SHORT).show();
                    lodingDialogView.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("userid", user_id);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            mActivity.startActivity(new Intent(mActivity, UserOrdinaryActivity.class));
            Toast.makeText(mActivity, "只有普通用户登陆后才能使用\"咨询功能\"！", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 咨询后返回当前剩余质询总条数json解析
     */
    class ConsultationSize {

        private List<RowsBean> rows;

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public class RowsBean {
            /**
             * size : 5
             */

            private int size;

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }

    @Override
    public void setDate() {
        String law_list = PrefUtils.getString("law_list", null, mActivity);//读取政策法规缓存
        if (law_list != null) {
            gsonLawList(law_list);
        }
        getLwaListData();//获取政策法规数据
    }

    /**
     * 从服务器获取政策法规数据
     */
    private void getLwaListData() {
        StringRequest request = new StringRequest(DataInterface.LAW_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gsonLawList(response);
                PrefUtils.putString("law_list", response, mActivity);//缓存到本地
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private void gsonLawList(String response) {
        Gson gson = new Gson();
        LawListData lawListData = gson.fromJson(response, LawListData.class);
        mListDate.clear();//清空数据避免重复数据
        mListDate.addAll(lawListData.getRows());
        addLawList();
    }

    /**
     * 添加"政策法规"
     */
    public void addLawList() {
        llLawList.removeAllViews();//清空所有控件以免造成重复
        for (int i = 0; i < 4; i++) {
            View view = View.inflate(mActivity, R.layout.item_law, null);
            TextView text = (TextView) view.findViewById(R.id.tv_text);
            text.setOnClickListener(new MyClickListener(i));
            LawListData.RowsBean rowsBean = mListDate.get(i);
            text.setText("《" + rowsBean.getName() + "》");
            llLawList.addView(view);
        }

    }

    /**
     * 律师查询
     *
     * @param lawyerName
     * @param lawyerLicense
     */
    public void findLawyerDateForService(final HashMap<String, String> map, final String lawyerName, final String lawyerLicense) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Lawyer.LAWYERS_VAGUE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("dataLawyers", "------" + response);
                Gson gson = new Gson();
                LawyersFindData lawyersFindData = gson.fromJson(response, LawyersFindData.class);
                List<LawyersFindData.RybzrowsBean> rybzrows = lawyersFindData.getRybzrows();
                if (rybzrows != null && rybzrows.size() > 0) {
                    LawyerSelectDialogView.showDialogSelect(mActivity, rybzrows);
                } else {
                    Toast.makeText(mActivity, "未查询到律师信息！", Toast.LENGTH_SHORT).show();
                }
                lodingDialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lodingDialogView.dismiss();
                Toast.makeText(mActivity, "网络连接失败!", Toast.LENGTH_LONG).show();
//                Log.i("HttpException", error.getLocalizedMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        Log.i("lawyerUri", request.getUrl());
        requestQueue.add(request);

    }

    /**
     * 律所查询
     */
    public void findFirmDateForService(final HashMap<String, String> map, final String name, final String license) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Firm.FIRM_VAGUE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("homeFindData", "------" + response);
                Gson gson = new Gson();
                FirmFindData firmFindData = gson.fromJson(response, FirmFindData.class);
                List<FirmFindData.RybzrowBean> rybzrow = firmFindData.getRybzrow();
                if (rybzrow != null && rybzrow.size() > 0) {
                    FirmSeleteDialog.showDialog(mActivity, rybzrow);
                    lodingDialogView.dismiss();
                } else {
                    Toast.makeText(mActivity, "未查询到律所信息！", Toast.LENGTH_SHORT).show();
                    lodingDialogView.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lodingDialogView.dismiss();
                Toast.makeText(mActivity, "网络连接失败!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        Log.i("url", request.getUrl());
        requestQueue.add(request);

    }

    class MyClickListener implements View.OnClickListener {
        int position;

        MyClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            LawListData.RowsBean item = mListDate.get(position);
            Intent intent = new Intent(mActivity, LawsRegulationsActivity.class);
            intent.putExtra("title", item.getName());
            intent.putExtra("content", item.getContent());
            mActivity.startActivity(intent);
        }
    }


}
