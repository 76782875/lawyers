package com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserMassageList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息提醒列表
 */
public class OrdinaryMessageActivity extends AppCompatActivity {


    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.tv_is_null)
    TextView tvIsNull;//消息为空是提醒
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    ArrayList<UserMassageList.RowsBean> mListDate;
    public String uri;
    public RequestQueue requestQueue;
    public ContentAdapter adapter;
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_message);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();
    }

    private void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        uri = intent.getStringExtra("messageAlert");
//        getDataForService(id);
        Log.i("massage", uri + "&&sid=" + id);
        mListDate = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
    }

    /**
     * 返回页面时刷新数据
     */
    @Override
    protected void onStart() {
        super.onStart();
        getDataForService(id);
    }

    /**
     * 获取服务器数据
     *
     * @param id
     */
    private void getDataForService(final String id) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("massage", response);
                gsonData(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvIsNull.setText("网络链接异常！");
                tvIsNull.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
                lvContent.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("sid", id);
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 显示数据
     *
     * @param response
     */
    private void gsonData(String response) {
        Gson gson = new Gson();
        UserMassageList userMassageList = gson.fromJson(response, UserMassageList.class);
        List<UserMassageList.RowsBean> rows = userMassageList.getRows();
        if (rows != null) {
            mListDate.clear();
            mListDate.addAll(rows);
            adapter.notifyDataSetChanged();
            lvContent.setVisibility(View.VISIBLE);
            pbLoading.setVisibility(View.GONE);
            tvIsNull.setVisibility(View.GONE);
        } else {
            lvContent.setVisibility(View.GONE);
            pbLoading.setVisibility(View.GONE);
            tvIsNull.setText("暂时没有任何消息！");
            tvIsNull.setVisibility(View.VISIBLE);
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public UserMassageList.RowsBean getItem(int position) {
            return mListDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_ordinary_message, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final UserMassageList.RowsBean item = getItem(position);
            viewHolder.tvTitle.setText(item.getTitle());
            viewHolder.tvTimer.setText(item.getCreatetime());
            viewHolder.tvSee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OrdinaryMessageActivity.this, OrdinaryMassageDetailedActivity.class);
                    intent.putExtra("sid", item.getId());
                    intent.putExtra("uri", item.getMessageAlertInfoUrl());
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.tv_title)
            TextView tvTitle;
            @Bind(R.id.tv_timer)
            TextView tvTimer;
            @Bind(R.id.tv_see)
            TextView tvSee;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}