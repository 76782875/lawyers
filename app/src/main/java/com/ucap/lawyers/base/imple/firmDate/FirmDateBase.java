package com.ucap.lawyers.base.imple.firmDate;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.R;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.FirmDate;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/17.
 * 用于显示"律所"数据的公共类,其数据封装到的FirmDate里
 */
public class FirmDateBase extends PagerBase {
    @Bind(R.id.lv_firm_content)
    ListView lvContent;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    @Bind(R.id.tv_isloding)
    TextView tvIsLoading;//点击加载
    ArrayList<FirmDate.RowsBean> mListDate;
    String uri;
    String name;
    private RequestQueue requestQueue;
    private ContentAdapter adapter;
    List<FirmDate.RowsBean> rows;

    public FirmDateBase(Activity mActivity, String uri, String name) {
        super(mActivity);
        this.uri = uri;
        for (int i = 0; i <= 2; i++) {//连续请求三次避免出现获取不到数据的情况
            getDataForService();
        }
        this.name = name;
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.base_firm_honor, null);
        ButterKnife.bind(this, view);
        flContent.addView(view);
        requestQueue = Volley.newRequestQueue(mActivity);
        mListDate = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirmDate.RowsBean rowsBean = rows.get(position);
                JumpDetailedTools.jumpFirmDefaultListDetailed(mActivity, rowsBean);//跳转到律所详细页面
            }
        });

    }

    /**
     * 从数据库获取数据
     */
    public void getDataForService() {
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Log.i("firmData", "---律所默认加载分类数据---" + response);
                    gsonData(response);
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvIsLoading.setVisibility(View.VISIBLE);
                lvContent.setVisibility(View.GONE);
                llLoading.setVisibility(View.GONE);
                tvIsLoading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        llLoading.setVisibility(View.VISIBLE);
                        tvIsLoading.setVisibility(View.GONE);
                        getDataForService();
                    }
                });
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap();
                map.put("curPageCountop", "1");
                map.put("curPageCountend", "30");
                return map;
            }
        };
        Log.i("url", request.getUrl());
        requestQueue.add(request);
    }

    /**
     * 解析数据
     *
     * @param response
     */
    private void gsonData(String response) {
        mListDate.clear();
        Gson gson = new Gson();
        FirmDate firmDate = gson.fromJson(response, FirmDate.class);
        rows = firmDate.getRows();
        mListDate.addAll(rows);
        adapter.notifyDataSetChanged();
        llLoading.setVisibility(View.GONE);
        tvIsLoading.setVisibility(View.GONE);
        lvContent.setVisibility(View.VISIBLE);

    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public FirmDate.RowsBean getItem(int position) {
            return mListDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_firm_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            FirmDate.RowsBean item = getItem(position);
            holder.tvFirmName.setText(item.getName());
            holder.tvFirmCertificatesNumber.setText(item.getGroupnum());
            holder.tvFirmLawyerName.setText(item.getTypesettings());
            holder.tvFirmTelephone.setText(item.getPhone());
            holder.tvFirmAddress.setText(item.getAddress());
            holder.tvFirmNegative.setText(item.getFmzs());
            holder.tvFirmSpecs.setText(item.getGfzs());
            holder.tvFirmHonor.setText(item.getRyzs());
            holder.tvFirmPublicWelfare.setText(item.getGyzs());
            holder.tvFirmAssess.setText(item.getYearcheck());
            holder.tvShowName.setText(item.getShowname());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_firm_name)
        TextView tvFirmName;//律所名称
        @Bind(R.id.tv_firm_certificates_number)
        TextView tvFirmCertificatesNumber;//律所许可证号
        @Bind(R.id.tv_firm_lawyer_name)
        TextView tvFirmLawyerName;//律所负责人
        @Bind(R.id.tv_firm_telephone)
        TextView tvFirmTelephone;//律所电话
        @Bind(R.id.tv_firm_address)
        TextView tvFirmAddress;//律所地
        @Bind(R.id.tv_firm_negative)
        TextView tvFirmNegative;//负面指数
        @Bind(R.id.tv_firm_specs)
        TextView tvFirmSpecs;//规范指数
        @Bind(R.id.tv_firm_honor)
        TextView tvFirmHonor;//荣誉表彰
        @Bind(R.id.tv_firm_public_welfare)
        TextView tvFirmPublicWelfare;//公益服务
        @Bind(R.id.tv_firm_assess)
        TextView tvFirmAssess;//年度考核
        @Bind(R.id.tv_showname)
        TextView tvShowName;//组织形式

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
