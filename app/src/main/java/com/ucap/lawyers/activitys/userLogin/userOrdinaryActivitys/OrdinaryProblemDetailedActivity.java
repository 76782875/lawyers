package com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
 * 我的提问详细
 */
public class OrdinaryProblemDetailedActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;//
    ArrayList<UserProblemDetailed.RowsBean.AnswerConsultationjsonBean> mListData;
    /**
     * 内容接口地址
     * 参数：条目id
     */
    private String uri = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!OrdinaryUsersMyQuestionsInfo.action?fifter=0";
    public RequestQueue requestQueue;
    public String itemId;
    public ContentAdapter adapter;
    public String id;
    public String headId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_problem_detailed);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiData();
        intiListener();

    }

    private void intiData() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        id = intent.getStringExtra("id");
        Log.i("dateUri", uri + "&&sid=" + itemId);
        mListData = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        getDataForService();
    }

    private void intiListener() {

    }

    @Override
    public void onClick(View view) {

    }

    public void getDataForService() {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("OrdinaryProblemiledData", response);
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
                map.put("sid", itemId);
                return map;
            }
        };
        requestQueue.add(stringRequest);
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
            headId = rowsBean.getId();
            tvHeadTimer.setText(rowsBean.getCreatetime());
            tvHeadTitle.setText(rowsBean.getTitle());
            lvContent.addHeaderView(head);
            List<UserProblemDetailed.RowsBean.AnswerConsultationjsonBean> answerConsultationjson = rowsBean.getAnswerConsultationjson();
            mListData.addAll(answerConsultationjson);
            for (int i = 0; i < answerConsultationjson.size(); i++) {
                isNiceData.add(i, answerConsultationjson.get(i).isState());
            }
            adapter.notifyDataSetChanged();
            pbLoading.setVisibility(View.GONE);
        }
    }

    ArrayList<Boolean> isNiceData = new ArrayList<>();

    class ContentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public UserProblemDetailed.RowsBean.AnswerConsultationjsonBean getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(OrdinaryProblemDetailedActivity.this, R.layout.item_lawyer_answer_problem, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final UserProblemDetailed.RowsBean.AnswerConsultationjsonBean item = getItem(position);
            viewHolder.tvName.setText(item.getFirstname());
            viewHolder.tvMessage.setText(item.getContent());
            viewHolder.tvTimer.setText(item.getCreatetime());
            viewHolder.btnNice.setVisibility(View.VISIBLE);
            if (isNiceData.get(position)) {
                viewHolder.btnNice.setSelected(isNiceData.get(position));
                viewHolder.btnNice.setText("已赞");
                viewHolder.btnNice.setTextColor(Color.WHITE);
            } else {
                viewHolder.btnNice.setSelected(false);
                viewHolder.btnNice.setText("点赞");
                viewHolder.btnNice.setTextColor(getResources().getColor(R.color.nice));
            }
            viewHolder.btnNice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isNiceData.get(position)) {
                        viewHolder.btnNice.setSelected(isNiceData.get(position));
                        viewHolder.btnNice.setText("已赞");
                        viewHolder.btnNice.setTextColor(Color.WHITE);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("pointpraisepeople", id);
                        map.put("Amanofpraise", item.getUid() + "");
                        map.put("publicconsulttionid", headId);
                        Log.i("isNice", DataInterface.ORDINARY_PROBLEM_DETAILED_NICE + "pointpraisepeople=" + id + "&&Amanofpraise=" + item.getUid() + "&&publicconsulttionid=" + headId);
                        isNice(parent.getContext(), map, viewHolder.btnNice, position);
                    }
                }
            });
            Glide.with(OrdinaryProblemDetailedActivity.this)
                    .load(item.getPhoto())
                    .into(viewHolder.ivPhoto);
            viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {//点击律师头像跳转到律师i详细页面
                @Override
                public void onClick(View view) {
                    Log.i("userProblem", DataInterface.Lawyer.LAWYERS_VAGUE_FIND + "&firstname=" + item.getFirstname() + "&contactid=" + item.getContactid());
                    Log.i("userProblem", item.getFirstname() + "-----" + item.getContactid());
                    getDataLawyer(item.getFirstname(), item.getContactid(), new LoadingDialogView(OrdinaryProblemDetailedActivity.this), requestQueue);
                }
            });
            return convertView;
        }

        public void isNice(final Context ctx, final HashMap<String, String> map, final Button btn, final int position) {
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.ORDINARY_PROBLEM_DETAILED_NICE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("isNice", "onResponse: " + response);
                    if (response.equals("已点赞")) {
                        Toast.makeText(ctx, "已赞", Toast.LENGTH_SHORT).show();
                        isNiceData.set(position, true);
                        adapter.notifyDataSetChanged();
                    } else {
                        btn.setSelected(false);
                        btn.setText("点赞");
                        btn.setTextColor(getResources().getColor(R.color.nice));
                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    btn.setSelected(false);
                    btn.setText("点赞");
                    btn.setTextColor(getResources().getColor(R.color.nice));
                    Toast.makeText(ctx, "网络链接失败，不能点赞！", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return map;
                }
            };
            requestQueue.add(request);
        }

    }

    /**
     * 点击律师头像跳转到律师详细页面
     */
    public void getDataLawyer(final String name, final String contactId, final LoadingDialogView lodingDialogView, RequestQueue requestQueue) {
        lodingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Lawyer.LAWYERS_VAGUE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("problemDetailedData", response);
                Log.i("dataLawyers", "------" + response);
                Gson gson = new Gson();
                LawyersFindData lawyersFindData = gson.fromJson(response, LawyersFindData.class);
                List<LawyersFindData.RybzrowsBean> rybzrows = lawyersFindData.getRybzrows();
                if (rybzrows != null && rybzrows.size() > 0) {
                    LawyersFindData.RybzrowsBean rybzrowsBean = rybzrows.get(0);
                    JumpDetailedTools.jumpLawyerGybzDetailed(OrdinaryProblemDetailedActivity.this, rybzrowsBean);
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
                map.put("contactid", contactId);
//                map.put("id", id);
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
        @Bind(R.id.btn_nice)
        Button btnNice;//点赞

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
