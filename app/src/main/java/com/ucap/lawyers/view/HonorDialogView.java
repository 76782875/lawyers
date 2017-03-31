package com.ucap.lawyers.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.homeFind.FirmCommend;
import com.ucap.lawyers.bean.homeFind.FirmData;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/9/18.
 */
public class HonorDialogView extends BaseDialog {
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    String firmId;
    String parameter;
    String uri;
    private ArrayList<FirmCommend.RowsBean> mListData;
    private ContentAdapter adapter;

    protected HonorDialogView(Context context, String firmId, String parameter, String uri) {
        super(context);
        this.firmId = firmId;
        this.parameter = parameter;
        this.uri = uri;
    }

    public static void showDialog(Context ctx, String firmId, String parameter, String uri) {
        HonorDialogView dialogView = new HonorDialogView(ctx, firmId, parameter, uri);
        dialogView.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_honnor);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
    }

    @Override
    public void setData() {
        if (firmId.isEmpty()) {
            return;
        }
        mListData = new ArrayList<>();
        adapter = new ContentAdapter(mListData);
        lvContent.setAdapter(adapter);
        getFirmCommend();
    }

    /**
     * 获取荣誉表彰详细
     */
    public void getFirmCommend() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.isEmpty())
                    return;
                Log.i("dataCommend", response);
                analysisCommend(response);
                llLoading.setVisibility(View.INVISIBLE);
                lvContent.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(parameter, firmId);
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析荣誉表彰
     *
     * @param data
     */
    public void analysisCommend(String data) {
        Gson gson = new Gson();
        FirmCommend firmCommend = gson.fromJson(data, FirmCommend.class);
        List<FirmCommend.RowsBean> rows = firmCommend.getRows();
        mListData.addAll(rows);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setClick(View v) {
        if (v == ivDismiss) {
            dismiss();
        }
    }

    class ContentAdapter extends BaseAdapter {
        ArrayList<FirmCommend.RowsBean> dataList;

        ContentAdapter(ArrayList<FirmCommend.RowsBean> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public FirmCommend.RowsBean getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_commend, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            FirmCommend.RowsBean item = getItem(position);
            holder.tvSubject.setText(item.getSubject());
            holder.tvGetDate.setText(item.getGetdate());
            holder.tvDisname.setText(item.getDisname());
            holder.tvOpername.setText(item.getOpername());
            holder.tvRemark.setText(item.getRemark());
            if (parameter.equals(DataInterface.FIRM_COMMEND_KEY)) {//律所的荣誉表彰
                holder.tvTitleName.setText("律所名称:");
            } else {
                holder.tvTitleName.setText("律师姓名:");
            }
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_subject)
        TextView tvSubject;//名称
        @Bind(R.id.tv_getdata)
        TextView tvGetDate;//获证时间
        @Bind(R.id.tv_disname)
        TextView tvDisname;//授权单位
        @Bind(R.id.tv_opername)
        TextView tvOpername;//姓名
        @Bind(R.id.tv_remark)
        TextView tvRemark;//内容介绍
        @Bind(R.id.tv_title_name)
        TextView tvTitleName;//授权单位名称（律师／律所）

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
