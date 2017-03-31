package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerMyAnswer;
import com.ucap.lawyers.view.DeleteDialogView;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.SlideCutListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LawyerMyAnswerActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    SlideCutListView lvContent;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    @Bind(R.id.tv_data_null)
    TextView tvDataNull;//我的回答是空的时候提示语
    private ArrayList<LawyerMyAnswer.RowsBean> mListDate;
    private ContentAdapter adapter;
    public RequestQueue requestQueue;
    /**
     * 删除我的回答
     * sid：条目id
     */
    public static final String MY_ANSWER_DELETE_URI = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!DeleteAswerConsultation.action?fifter=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_my_answer);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiDate();
        intiListener();
    }

    private void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String uri = intent.getStringExtra("MyAnswer");
        getServiceData(id, uri);

        mListDate = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
    }

    /**
     * 获取服务器数据
     *
     * @param id
     * @param uri
     */
    private void getServiceData(final String id, String uri) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LawyerMyAnserData", response);
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
                map.put("userid", id);
                //不添加请求角标则返回所有条目
//                map.put("curPageCountop", "1");//条数开始角标
//                map.put("curPageCountend", "30");//目标条数
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析服务器数据并显示
     *
     * @param response
     */
    private void gsonData(String response) {
        List<LawyerMyAnswer.RowsBean> rows = new Gson().fromJson(response, LawyerMyAnswer.class).getRows();
        if (rows != null && rows.size() > 0) {
            mListDate.clear();
            mListDate.addAll(rows);
            adapter.notifyDataSetChanged();
            pbLoading.setVisibility(View.INVISIBLE);
            tvDataNull.setVisibility(View.INVISIBLE);
            lvContent.setVisibility(View.VISIBLE);
        } else {
            tvDataNull.setVisibility(View.VISIBLE);
            lvContent.setVisibility(View.INVISIBLE);
            pbLoading.setVisibility(View.INVISIBLE);
        }
    }

    private void intiListener() {
        lvContent.setRemoveListener(new SlideCutListView.RemoveListener() {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, final int position) {
                DeleteDialogView.showDialog(LawyerMyAnswerActivity.this, new DeleteDialogView.OnDetermineListener() {
                    @Override
                    public void onDetermine() {
                        final LawyerMyAnswer.RowsBean item = adapter.getItem(position);
                        StringRequest request = new StringRequest(StringRequest.Method.POST, MY_ANSWER_DELETE_URI, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("delete", response);
                                if ("删除成功".equals(response)) {
                                    mListDate.remove(position);
                                    adapter.notifyDataSetChanged();
                                    if (mListDate.size() == 0) {
                                        tvDataNull.setVisibility(View.VISIBLE);
                                        lvContent.setVisibility(View.INVISIBLE);
                                        pbLoading.setVisibility(View.INVISIBLE);
                                    }
                                    Toast.makeText(LawyerMyAnswerActivity.this, response, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LawyerMyAnswerActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("sid", item.getId());
                                return map;
                            }
                        };
                        requestQueue.add(request);
                    }

                });

            }
        });
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public LawyerMyAnswer.RowsBean getItem(int position) {
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
                convertView = View.inflate(LawyerMyAnswerActivity.this, R.layout.item_lawyer_my_answer, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawyerMyAnswer.RowsBean item = getItem(position);
            holder.tvTimer.setText(item.getCreatetime());
            holder.tvTitle.setText(item.getContent());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;//回答内容
        @Bind(R.id.tv_timer)
        TextView tvTimer;//回答时间

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
