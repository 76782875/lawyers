package com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserProblemItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的提问列表
 */
public class OrdinaryProblemActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_problem_list)
    ListView lvContent;
    @Bind(R.id.tv_loading)
    TextView tvLoading;//没有数据提醒
    @Bind(R.id.pb_loading)
    ProgressBar pbLading;//加载中
    ArrayList<UserProblemItem.RowsBean> mDateList;
    public RequestQueue requestQueue;
    public ContentAdapter adapter;
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_problem);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();
        intiListener();
    }

    private void intiListener() {
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long itemid) {
                UserProblemItem.RowsBean item = adapter.getItem(position);
                if (item.getState().equals("1")) {
                    String itemId = item.getId();
                    Intent intent = new Intent(OrdinaryProblemActivity.this, OrdinaryProblemDetailedActivity.class);
                    intent.putExtra("itemId", itemId);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(OrdinaryProblemActivity.this, "该问题还没有回答..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String uri = intent.getStringExtra("ordinaryUsersMyQuestions");
        Log.i("problemData", "---" + uri + "&&id=" + id);
        getDataForService(uri, id);
        mDateList = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);

    }

    /**
     * 获取服务器信息
     *
     * @param id
     */
    private void getDataForService(String uri, final String id) {
        lvContent.setVisibility(View.INVISIBLE);
        pbLading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ordinaryProblemData", response);
                gsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lvContent.setVisibility(View.GONE);
                pbLading.setVisibility(View.GONE);
                tvLoading.setText("网络异常！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", id);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 解析并显示
     *
     * @param response
     */
    private void gsonData(String response) {
        Gson gson = new Gson();
        UserProblemItem userProblemItem = gson.fromJson(response, UserProblemItem.class);
        List<UserProblemItem.RowsBean> rows = userProblemItem.getRows();
        if (rows != null && rows.size() > 0) {
            mDateList.clear();
            mDateList.addAll(rows);
            adapter.notifyDataSetChanged();
            tvLoading.setVisibility(View.GONE);
            lvContent.setVisibility(View.VISIBLE);
            pbLading.setVisibility(View.GONE);
        } else {
            tvLoading.setVisibility(View.VISIBLE);
            lvContent.setVisibility(View.GONE);
            pbLading.setVisibility(View.GONE);
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mDateList.size();
        }

        @Override
        public UserProblemItem.RowsBean getItem(int position) {
            return mDateList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(OrdinaryProblemActivity.this, R.layout.item_ordinary_problem, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            UserProblemItem.RowsBean item = getItem(position);
            holder.tvTitle.setText(item.getTitle());
            holder.tvTimer.setText(item.getCreatetime());
            if (item.getState().equals("1")) {
                holder.tvState.setText("已回答");
                holder.tvState.setTextColor(getResources().getColor(R.color.ordinaryProblem_0));
            } else {
                holder.tvState.setText("待回答");
                holder.tvState.setTextColor(getResources().getColor(R.color.ordinaryProblem_1));
            }
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.tv_timer)
        TextView tvTimer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
