package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

import android.content.Context;
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

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userOrdinary.MyCommonly;
import com.ucap.lawyers.view.CaseUndertakeLawyersDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的案件
 */
public class LawyerCaseActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    @Bind(R.id.tv_loading)
    TextView tvLoading;
    private ArrayList<MyCommonly.RowsBean> mDate;
    public String userId;//律师登陆id
    public RequestQueue requestQueue;
    public LawyerCaseListViewAdapter adapter;
    public String firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_case);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiDate();
        intiListener();
        getDataForService();

    }

    private void intiListener() {

    }

    public void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        firstname = intent.getStringExtra("firstname");
        Log.i("userid", "userid:------" + userId);
        mDate = new ArrayList<>();
        adapter = new LawyerCaseListViewAdapter(this, mDate);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LawyerCaseActivity.this, LawyerCommonlyDetailedActivity.class);
                intent.putExtra("orderid", adapter.getItem(position).getOrderid());
                intent.putExtra("sid", adapter.getItem(position).getId() + "");
                intent.putExtra("firstname", firstname);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取服务器数据
     */
    public void getDataForService() {
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.TEST_MY_COMMONLY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("serviceData", "我的案件数据:----" + response);
                if (!response.isEmpty()) {
                    gsonData(response);
                } else {
                    tvLoading.setVisibility(View.VISIBLE);
                    pbLoading.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvLoading.setText("网络链接失败！");
                tvLoading.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("userid", userId);
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析数据
     *
     * @param response
     */
    private void gsonData(String response) {
        Gson gson = new Gson();
        MyCommonly myCommonly = gson.fromJson(response, MyCommonly.class);
        if (myCommonly != null) {
            List<MyCommonly.RowsBean> rows = myCommonly.getRows();
            if (rows != null && rows.size() > 0) {
                mDate.addAll(rows);
                adapter.notifyDataSetChanged();
                pbLoading.setVisibility(View.GONE);
                tvLoading.setVisibility(View.GONE);
            } else {
                tvLoading.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
            }
        } else {
            tvLoading.setVisibility(View.VISIBLE);
            pbLoading.setVisibility(View.GONE);
        }
    }

    class LawyerCaseListViewAdapter extends BaseAdapter {
        private ArrayList<MyCommonly.RowsBean> mDate;
        private Context ctx;

        public LawyerCaseListViewAdapter(Context ctx, ArrayList<MyCommonly.RowsBean> mDate) {
            this.ctx = ctx;
            this.mDate = mDate;
        }

        @Override
        public int getCount() {
            return mDate.size();
        }

        @Override
        public MyCommonly.RowsBean getItem(int position) {
            return mDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(ctx, R.layout.item_lawyer_case, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final MyCommonly.RowsBean item = getItem(position);
            holder.tvNumber.setText(item.getOrder_no());
            holder.tvClient.setText(item.getZbls());
            holder.tvName.setText(item.getCasename());
            holder.tvState.setText(item.getArchchoose());
            if ("调查".equals(holder.tvState.getText().toString())) {
                holder.tvState.setTextColor(parent.getContext().getResources().getColor(R.color.item1));
            } else if ("立案".equals(holder.tvState.getText().toString())) {
                holder.tvState.setTextColor(parent.getContext().getResources().getColor(R.color.number_1));
            } else if ("待审".equals(holder.tvState.getText().toString())) {
                holder.tvState.setTextColor(parent.getContext().getResources().getColor(R.color.item3));
            } else if ("待判".equals(holder.tvState.getText().toString())) {
                holder.tvState.setTextColor(parent.getContext().getResources().getColor(R.color.item4));
            } else if ("办结".equals(holder.tvState.getText().toString())) {
                holder.tvState.setTextColor(parent.getContext().getResources().getColor(R.color.item2));
            } else {
                holder.tvState.setTextColor(parent.getContext().getResources().getColor(R.color.item1));
            }
            holder.tvClient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CaseUndertakeLawyersDialog dialog = new CaseUndertakeLawyersDialog(parent.getContext(), item.getLawlist());
                    dialog.show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.tv_number)
            TextView tvNumber;//协议编号
            @Bind(R.id.tv_client)
            TextView tvClient;//承办律师
            @Bind(R.id.tv_lawyer_name)
            TextView tvName;//委托人
            @Bind(R.id.tv_state)
            TextView tvState;//状态

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}