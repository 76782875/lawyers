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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserProblemDetailed;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息提醒详细
 */
public class OrdinaryMassageDetailedActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    public RequestQueue requestQueue;
    public String uri;
    public ContentAdapter adapter;
    public ArrayList<UserProblemDetailed.RowsBean.AnswerConsultationjsonBean> mListDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_massage_detailed);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiData();
    }

    private void intiData() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String sid = intent.getStringExtra("sid");
        uri = intent.getStringExtra("uri");
        Log.i("massageDetailed", uri + "&&sid=" + sid);
        mListDate = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        getDataForService(sid);
    }

    /**
     * 获取服务器数据
     *
     * @param sid
     */
    private void getDataForService(final String sid) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("massageDetailed", response);
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
                map.put("sid", sid);
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
        View head = View.inflate(this, R.layout.item_answer_peroblem_head, null);//list头布局
        //问题内容
        TextView tvHeadTitle = (TextView) head.findViewById(R.id.tv_title);
        //回答时间
        TextView tvHeadTimer = (TextView) head.findViewById(R.id.tv_timer);
        TextView tvNumber = (TextView) head.findViewById(R.id.tv_number);//回答总个数
        tvNumber.setVisibility(View.GONE);
        Gson gson = new Gson();
        UserProblemDetailed userProblemDetailed = gson.fromJson(response, UserProblemDetailed.class);
        List<UserProblemDetailed.RowsBean> rows = userProblemDetailed.getRows();
        if (rows != null && rows.size() > 0) {
            UserProblemDetailed.RowsBean rowsBean = rows.get(0);
            tvHeadTimer.setText(rowsBean.getCreatetime());
            tvHeadTitle.setText(rowsBean.getTitle());
            lvContent.addHeaderView(head);
            List<UserProblemDetailed.RowsBean.AnswerConsultationjsonBean> answerConsultationjson = rowsBean.getAnswerConsultationjson();
            mListDate.addAll(answerConsultationjson);
            adapter.notifyDataSetChanged();
            pbLoading.setVisibility(View.GONE);
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public UserProblemDetailed.RowsBean.AnswerConsultationjsonBean getItem(int position) {
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
                convertView = View.inflate(OrdinaryMassageDetailedActivity.this, R.layout.item_lawyer_answer_problem, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final UserProblemDetailed.RowsBean.AnswerConsultationjsonBean item = getItem(position);
            viewHolder.tvName.setText(item.getFirstname());
            viewHolder.tvMessage.setText(item.getContent());
            viewHolder.tvTimer.setText(item.getCreatetime());
            Glide.with(OrdinaryMassageDetailedActivity.this)
                    .load(item.getPhoto())
                    .into(viewHolder.ivPhoto);
            viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {//点击律师头像跳转到律师i详细页面
                @Override
                public void onClick(View view) {
                    getDataLawyer(item.getFirstname(), new LoadingDialogView(OrdinaryMassageDetailedActivity.this), requestQueue);
                }
            });
            return convertView;
        }

    }

    /**
     * 点击律师头像跳转到律师详细页面
     */
    public void getDataLawyer(final String name, final LoadingDialogView lodingDialogView, RequestQueue requestQueue) {
        lodingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Lawyer.LAWYERS_VAGUE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("lawyersData", response);
                Gson gson = new Gson();
                LawyersFindData lawyersFindData = gson.fromJson(response, LawyersFindData.class);
                List<LawyersFindData.RybzrowsBean> rybzrows = lawyersFindData.getRybzrows();
                if (rybzrows != null && rybzrows.size() > 0) {
                    LawyersFindData.RybzrowsBean rybzrowsBean = rybzrows.get(0);
                    JumpDetailedTools.jumpLawyerGybzDetailed(OrdinaryMassageDetailedActivity.this, rybzrowsBean);//跳转到详细
                }
                lodingDialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lodingDialogView.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接失败!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("firstname", name);
                return map;
            }
        };
        Log.i("url", request.getUrl());
        requestQueue.add(request);
    }

    class ViewHolder {
        @Bind(R.id.iv_photo)
        ImageView ivPhoto;//头像
        @Bind(R.id.tv_name)
        TextView tvName;//律师名称
        @Bind(R.id.tv_message)
        TextView tvMessage;//回答内容
        @Bind(R.id.tv_timer)
        TextView tvTimer;//回答时间

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
