package com.ucap.lawyers.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.LocationData;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/9/20.
 */
public class LocationListDialogVIew extends BaseDialog {
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    ArrayList<LocationData.RowsBean> mListData;
    int location;//地区编号
    public RequestQueue requestQueue;
    public ContentAdapter adapter;
    public String locationName;

    public static void showDialog(Context ctx, int location, String locationName) {
        LocationListDialogVIew dialogVIew = new LocationListDialogVIew(ctx, location, locationName);
        dialogVIew.show();
    }

    protected LocationListDialogVIew(Context context, int location, String locationName) {
        super(context);
        this.location = location;
        this.locationName = locationName;
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_location_list);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        requestQueue = Volley.newRequestQueue(getContext());
        ivDismiss.setOnClickListener(this);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationData.RowsBean rowsBean = adapter.getItem(position);
                JumpDetailedTools.jumpFirmLocationDetailed(getContext(), rowsBean);
            }
        });
        getDataForService();
    }

    /**
     * 查询对于位置的律所信息
     */
    public void getDataForService() {
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.LOCATION_CHART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("locationData", "----位置分布图，辖区律所数据----" + response);
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
                map.put("classid", location + "");
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 　解析数据
     *
     * @param response
     */
    private void gsonData(String response) {
        Gson gson = new Gson();
        LocationData locationData = gson.fromJson(response, LocationData.class);
        List<LocationData.RowsBean> rows = locationData.getRows();
        mListData.addAll(rows);
        adapter.notifyDataSetChanged();
        llLoading.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setData() {
        tvTitle.setText(locationName);
        mListData = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
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
            return mListData.size();
        }

        @Override
        public LocationData.RowsBean getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_dialog_location_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LocationData.RowsBean item = getItem(position);
            holder.tvName.setText(item.getName());
            holder.tvGroupnum.setText(item.getGroupnum());
            holder.tvTypesettings.setText(item.getTypesettings());
            holder.tvAddress.setText(item.getAddress());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;//律所名称
        @Bind(R.id.tv_groupnum)
        TextView tvGroupnum;//律所证号
        @Bind(R.id.tv_typesettings)
        TextView tvTypesettings;//律所负责人姓名
        @Bind(R.id.tv_address)
        TextView tvAddress;//律所地址

        ViewHolder(View view) {
//            @Bind(R.id.)
            ButterKnife.bind(this, view);
        }
    }
}
