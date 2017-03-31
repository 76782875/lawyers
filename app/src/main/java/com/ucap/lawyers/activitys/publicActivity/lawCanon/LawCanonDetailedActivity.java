package com.ucap.lawyers.activitys.publicActivity.lawCanon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.FirmDetailedActivity;
import com.ucap.lawyers.adapter.ViewPagerAdapter;
import com.ucap.lawyers.base.BlankPager;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.law.LawBrowseDetailedBean;
import com.ucap.lawyers.bean.vagueFind.FirmFindData;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 百姓法宝详细
 */
public class LawCanonDetailedActivity extends Activity {
    @Bind(R.id.vp_content)
    ViewPager vpContent;//用于实现左滑关闭页面
    ArrayList<PagerBase> mList;
    public String itemId;
    public String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_canon_detailed);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        title = intent.getStringExtra("title");
        mList = new ArrayList<>();
        mList.add(new BlankPager(this));
        mList.add(new ContentPager(this));
        vpContent.setAdapter(new ViewPagerAdapter(mList));
        vpContent.setCurrentItem(mList.size() - 1);
        vpContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && positionOffsetPixels <= 200) {//偏移量达到超出屏幕3／2在销毁此页面
                    finish();//滑动到第一个页面时关闭
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class ContentPager extends PagerBase {
        @Bind(R.id.iv_back)
        ImageView ivBack;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_firm)
        TextView tvFirm;
        @Bind(R.id.tv_lawyer)
        TextView tvLawyer;
        @Bind(R.id.tv_is_null)
        TextView tvIsNull;
        @Bind(R.id.tv_tigongzhr)
        TextView tvTGZ;
        public RequestQueue requestQueue;
        public LoadingDialogView loadingDialogView;
        public String firmName;
        public String contactid;
        public String firstname;

        public ContentPager(Activity mActivity) {
            super(mActivity);
            setDate();
        }

        @Override
        public void intiDate() {
            View view = View.inflate(mActivity, R.layout.content_law_canon_detailed, null);
            flContent.addView(view);
            ButterKnife.bind(this, view);
            requestQueue = Volley.newRequestQueue(mActivity);
            loadingDialogView = new LoadingDialogView(mActivity);
            tvTitle.setText(title);

        }

        public void getDataForService() {
            loadingDialogView.show();
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.LAW_BROWSE_DETAILED, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("TEST_LAW_BROWSE", "详细页面数据：" + response);
                    gsonData(response);
                    loadingDialogView.dismiss();
                    tvIsNull.setVisibility(View.INVISIBLE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialogView.dismiss();
                    tvIsNull.setVisibility(View.VISIBLE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("lid", itemId);
                    return map;
                }
            };
            requestQueue.add(request);
        }

        public void gsonData(String data) {
            Gson gson = new Gson();
            LawBrowseDetailedBean lawBrowseDetailedBean = gson.fromJson(data, LawBrowseDetailedBean.class);
            List<LawBrowseDetailedBean.RowBean> row = lawBrowseDetailedBean.getRow();
            if (row != null && row.size() > 0) {
                LawBrowseDetailedBean.RowBean rowBean = row.get(0);
                tvContent.setText(rowBean.getContent());
                firmName = rowBean.getGroupname();
                tvFirm.setText(firmName);
                firstname = rowBean.getFirstname();
                tvLawyer.setText(firstname);
                tvTGZ.setText("提供者：");
                contactid = rowBean.getContactid();
            }
        }

        @Override
        public void intiListener() {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.finish();
                }
            });
            tvFirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", firmName);
                    findFirm(map);
                }
            });
            tvLawyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TEST_LAW_BROWSE", "名称：" + firstname + "---证号：" + contactid);
                    findLawyer(firstname, contactid, loadingDialogView, requestQueue);
                }
            });
        }

        /**
         * 点击律所名称时查询改律所详细信息并跳转到详细页面
         *
         * @param map
         */
        public void findFirm(final HashMap<String, String> map) {
            loadingDialogView.show();
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
                            Intent intent = new Intent(mActivity, FirmDetailedActivity.class);
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
                            Toast.makeText(mActivity, "未查询到所属律所信息！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    loadingDialogView.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialogView.dismiss();
                    Toast.makeText(mActivity, "网络异常！", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return map;
                }
            };
            requestQueue.add(request);
        }

        /**
         * 点击律师头像跳转到律师详细页面
         */
        public void findLawyer(final String name, final String contactId, final LoadingDialogView lodingDialogView, RequestQueue requestQueue) {
            lodingDialogView.show();
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Lawyer.LAWYERS_VAGUE_FIND, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("lawyersData", response);
                    Gson gson = new Gson();
                    LawyersFindData lawyersFindData = gson.fromJson(response, LawyersFindData.class);
                    List<LawyersFindData.RybzrowsBean> rybzrows = lawyersFindData.getRybzrows();
                    if (rybzrows != null && rybzrows.size() > 0) {
                        LawyersFindData.RybzrowsBean rybzrowsBean = rybzrows.get(0);
                        JumpDetailedTools.jumpLawyerGybzDetailed(mActivity, rybzrowsBean);//跳转到律师详细页面
                    }
                    lodingDialogView.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    lodingDialogView.dismiss();
                    Toast.makeText(getApplicationContext(), "网络连接失败!", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("firstname", name);
                    map.put("contactid", contactId);
                    return map;
                }
            };
            Log.i("url", request.getUrl());
            requestQueue.add(request);
        }


        @Override
        public void setDate() {
            getDataForService();
        }
    }
}
