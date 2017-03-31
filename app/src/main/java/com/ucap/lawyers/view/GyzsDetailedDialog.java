package com.ucap.lawyers.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.LawyesPublicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/11/10.
 * 公益服务详细
 */

public class GyzsDetailedDialog extends BaseDialog {
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.lv_content)
    ListView lvContent;
    ArrayList<LawyesPublicService.RowsBean> mData;
    String id;//接口参数
    String uri;// 接口地址
    String key;// 接口参数名称

    public final RequestQueue requestQueue;
    public ContentAdapter adapter;

    protected GyzsDetailedDialog(Context context, String id, String uri, String key) {
        super(context);
        this.id = id;
        this.uri = uri;
        this.key = key;
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * @param ctx
     * @param id  参数
     * @param uri 地址
     * @param key 参数名称
     */
    public static void showDialog(Context ctx, String id, String uri, String key) {
        GyzsDetailedDialog detailedDialog = new GyzsDetailedDialog(ctx, id, uri, key);
        detailedDialog.show();

    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_gyzs_detailed);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
    }

    @Override
    public void setData() {
        mData = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        getDataForService();
    }

    /**
     * 获取服务器数据
     */
    public void getDataForService() {
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("lawyerPublicService", response);
                if (response.isEmpty())
                    return;
                Gson gson = new Gson();
                LawyesPublicService lawyesPublicService = gson.fromJson(response, LawyesPublicService.class);
                List<LawyesPublicService.RowsBean> rows = lawyesPublicService.getRows();
                mData.addAll(rows);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(key, id);
                return map;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void setClick(View v) {
        if (v == ivDismiss) {
            dismiss();
        }

    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public LawyesPublicService.RowsBean getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_gyzs_dialog, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawyesPublicService.RowsBean item = getItem(position);
            holder.tvSubject.setText(item.getSubject());
            holder.tvGetdata.setText(item.getGetdate());
            holder.tvOpername.setText(item.getOpername());
            holder.tvRemark.setText(item.getRemark());
            if ("name".equals(key)) {
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
        TextView tvGetdata;//服务时间
        @Bind(R.id.tv_opername)
        TextView tvOpername;//律师名称
        @Bind(R.id.tv_remark)
        TextView tvRemark;//内容简介
        @Bind(R.id.tv_title_name)
        TextView tvTitleName;//公益服务单位名称（律师／律所）

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
