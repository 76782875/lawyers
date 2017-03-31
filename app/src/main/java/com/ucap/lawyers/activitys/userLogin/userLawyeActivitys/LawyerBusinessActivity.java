package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

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
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的业务
 */
public class LawyerBusinessActivity extends AppCompatActivity {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.tv_loading)
    TextView tvLoading;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    private ArrayList<LawyerBusiness.RowsBean> mListData;
    public RequestQueue requestQueue;
    public ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_business);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiDate();

    }

    private void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String userid = intent.getStringExtra("userid");
        String uri = intent.getStringExtra("MyBusiness");
        pbLoading.setVisibility(View.VISIBLE);
        getDataForSevice(userid, uri);
        mListData = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LawyerBusinessActivity.this, "请在电脑上登录\"成都市律师综合信息系统\"办理!", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 获取服务器数据
     */
    private void getDataForSevice(final String userid, String uri) {
        Log.i("uri", uri);
        Log.i("uri", userid);
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("serviceData", response);
                gsonData(response);
                pbLoading.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbLoading.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("userid", userid);
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析数据并显示
     *
     * @param response
     */
    private void gsonData(String response) {
        Gson gson = new Gson();
        List<LawyerBusiness.RowsBean> rows = gson.fromJson(response, LawyerBusiness.class).getRows();
        if (rows != null && rows.size() > 0) {
            mListData.clear();
            mListData.addAll(rows);
            adapter.notifyDataSetChanged();
        } else {
            tvLoading.setVisibility(View.VISIBLE);
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public LawyerBusiness.RowsBean getItem(int position) {
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
                convertView = View.inflate(LawyerBusinessActivity.this, R.layout.item_lawyer_business, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawyerBusiness.RowsBean item = getItem(position);
            String createtime = item.getCreatetime();//开始时间
            String task = item.getTask();//流程名称
            String displayname = item.getDisplayname();//任务名称
            holder.tvProcessName.setText(task);
            holder.tvTimer.setText(createtime);
            holder.tvTaskName.setText(displayname);
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_process_name)
        TextView tvProcessName;//任务名称
        @Bind(R.id.tv_timer)
        TextView tvTimer;//开始时间
        @Bind(R.id.tv_task_name)
        TextView tvTaskName;//流程名称

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
