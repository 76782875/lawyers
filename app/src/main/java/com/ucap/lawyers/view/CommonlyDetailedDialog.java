package com.ucap.lawyers.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userLawyer.CommonlyDetailed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/12/19.
 * 案件查看详细
 */

public class CommonlyDetailedDialog extends BaseDialog {
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    @Bind(R.id.ll_data)
    LinearLayout llData;
    @Bind(R.id.tv_err)
    TextView tvErr;
    @Bind(R.id.ll_lawyers)
    LinearLayout llLawyers;//承办律师
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.tv_order_on)
    TextView tvOrderOn;//编号
    @Bind(R.id.tv_create_time)
    TextView tvCreateTime;//派单时间
    @Bind(R.id.tv_casename)
    TextView tvCasename;//委托方名称
    @Bind(R.id.tv_casecode)
    TextView tvCasecode;//委托方证号
    @Bind(R.id.tv_casenumber)
    TextView tvCasenumber;//协议标的
    @Bind(R.id.tv_casecose)
    TextView tvCasecose;//预计收费
    @Bind(R.id.tv_optdate)
    TextView tvOptdate;//受理日期
    @Bind(R.id.tv_casetype)
    TextView tvCasetype;//案件类型

    String sid;//案件id

    public CommonlyDetailedDialog(Context context, String sid) {
        super(context);
        this.sid = sid;
    }

    @Override
    public void setView() {
        View view = View.inflate(getContext(), R.layout.dialog_commonly_detailed, null);
        setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void setData() {
        llData.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        getDataForService();
    }

    @Override
    public void setClick(View v) {

    }

    /**
     * 获取服务器数据
     */
    public void getDataForService() {
        Log.i("commonlyDetailedData", "案件sid：--" + sid);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, DataInterface.TEST_MY_COMMONLY_DETAILED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("commonlyDetailedData", "案件详细：--" + response);
                if (!response.isEmpty()) {
                    gsonData(response);
                    llData.setVisibility(View.VISIBLE);
                    pbLoading.setVisibility(View.INVISIBLE);
                    tvErr.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llData.setVisibility(View.INVISIBLE);
                pbLoading.setVisibility(View.INVISIBLE);
                tvErr.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("sid", sid);
                return map;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    /**
     * 解析数据
     *
     * @param data
     */
    public void gsonData(String data) {
        Gson gson = new Gson();
        CommonlyDetailed commonlyDetailed = gson.fromJson(data, CommonlyDetailed.class);
        if (commonlyDetailed != null) {
            List<CommonlyDetailed.RowsBean> rows = commonlyDetailed.getRows();
            if (rows != null && rows.size() > 0) {
                CommonlyDetailed.RowsBean rowsBean = rows.get(0);
                tvOrderOn.setText(rowsBean.getOrder_no());
                tvCreateTime.setText(rowsBean.getCreate_time());
                tvCasename.setText(rowsBean.getCasename());
                tvCasecode.setText(rowsBean.getCasecode());
                tvCasenumber.setText(rowsBean.getCasenumber());
                tvCasecose.setText(rowsBean.getCasecose());
                tvOptdate.setText(rowsBean.getOptdate());
                tvCasetype.setText(rowsBean.getCasetype());
                List<CommonlyDetailed.RowsBean.LawlistBean> lawlist = rowsBean.getLawlist();
                if (lawlist != null && lawlist.size() > 0) {
                    ArrayList<CommonlyDetailed.RowsBean.LawlistBean> mLawyers = new ArrayList<>();
                    mLawyers.addAll(lawlist);
                    for (CommonlyDetailed.RowsBean.LawlistBean lawyers : mLawyers) {
                        View view = View.inflate(getContext(), R.layout.item_commonly_lawyers, null);
                        TextView tvLawname = (TextView) view.findViewById(R.id.tv_lawname);//律师姓名
                        TextView tvLawphone = (TextView) view.findViewById(R.id.tv_lawphone);//律师电话
                        TextView tvLawcontactid = (TextView) view.findViewById(R.id.tv_lawcontactid);//律师证号
                        tvLawname.setText(lawyers.getLawname());
                        tvLawphone.setText(lawyers.getLawphone());
                        tvLawcontactid.setText(lawyers.getLawcontactid());
                        llLawyers.addView(view);
                    }
                }
            }
        }
    }
}
