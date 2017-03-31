package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerAnswerProblem;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;
import com.ucap.lawyers.view.InputDialogView;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 回答问题
 */
public class LawyerAnswerProblemActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    ArrayList<LawyerAnswerProblem.RowsBean.AnswerConsultationjsonBean> mListDate;
    @Bind(R.id.btn_answer)
    Button btnAnswer;
    private ContentAdapter contentAdapter;
    private TextView tvNumber;
    public RequestQueue requestQueue;
    public String answerurl;
    public String id;
    public String lawyerId;
    public String uri;
    public TextView tvHeadTitle;
    public TextView tvHeadTimer;
    public View head;
    public LoadingDialogView loadingDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_answer_problem);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiDate();
        intiListeren();
    }

    private void intiListeren() {
        btnAnswer.setOnClickListener(this);
    }

    private void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        loadingDialogView = new LoadingDialogView(this);
        head = View.inflate(this, R.layout.item_answer_peroblem_head, null);//list头布局
        //问题内容
        tvHeadTitle = (TextView) head.findViewById(R.id.tv_title);
        //回答时间
        tvHeadTimer = (TextView) head.findViewById(R.id.tv_timer);
        tvNumber = (TextView) head.findViewById(R.id.tv_number);//回答总个数
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        tvTitle.setText(name);
        id = intent.getStringExtra("id");
        lawyerId = intent.getStringExtra("lawyerId");
        uri = intent.getStringExtra("answerConsultationUrl");
        getDataForService(id, uri);
        mListDate = new ArrayList<>();
        contentAdapter = new ContentAdapter();
        lvContent.setAdapter(contentAdapter);

    }

    /**
     * 获取服务器数据，并显示
     *
     * @param id
     * @param uri
     */
    private void getDataForService(final String id, String uri) {
        loadingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LawyerAnswerProblem", response);
                gsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialogView.dismiss();
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
     * 解析数据并显示
     *
     * @param response
     */
    private void gsonData(String response) {
        LawyerAnswerProblem.RowsBean rowsBean = new Gson().fromJson(response, LawyerAnswerProblem.class).getRows().get(0);
        //回答问题接口地址
        answerurl = rowsBean.getAnswerurl();
        addLvContentHead(rowsBean.getTitle(), rowsBean.getCreatetime(), rowsBean.getSize() + "");
        List<LawyerAnswerProblem.RowsBean.AnswerConsultationjsonBean> answerConsultationjson = rowsBean.getAnswerConsultationjson();
        if (answerConsultationjson != null && answerConsultationjson.size() > 0) {
            mListDate.clear();
            mListDate.addAll(answerConsultationjson);
            contentAdapter.notifyDataSetChanged();
        }
        loadingDialogView.dismiss();
    }

    private void addLvContentHead(String content, String timer, String size) {
        tvHeadTimer.setText(timer);
        tvNumber.setText(size + "个人回答");
        tvHeadTitle.setText(content);
        lvContent.removeHeaderView(head);
        lvContent.addHeaderView(head);

    }

    @Override
    public void onClick(View view) {
        if (view == btnAnswer) {//点击开始回答
            InputDialogView.showDialog(this, new InputDialogView.DetermineListener() {
                @Override
                public void determine(final String content) {
                    if (!content.isEmpty()) {//提交回答内容
                        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, answerurl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("messageData", response);
                                getDataForService(id, uri);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("content", content);
                                map.put("publicid", id);
                                map.put("userid", lawyerId);
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
            });
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public LawyerAnswerProblem.RowsBean.AnswerConsultationjsonBean getItem(int position) {
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
                convertView = View.inflate(LawyerAnswerProblemActivity.this, R.layout.item_lawyer_answer_problem, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                viewHolder.tvName.setTextColor(getResources().getColor(R.color.itemAnswerProblemText));
            } else {
                viewHolder.tvName.setTextColor(getResources().getColor(R.color.itemAnswerProblemText2));
            }
            final LawyerAnswerProblem.RowsBean.AnswerConsultationjsonBean item = getItem(position);
            viewHolder.tvName.setText(item.getFirstname());
            viewHolder.tvMessage.setText(item.getContent());
            viewHolder.tvTimer.setText(item.getCreatetime());
            Glide.with(LawyerAnswerProblemActivity.this)
                    .load(item.getPhoto())
                    .into(viewHolder.ivPhoto);
            viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {//点击律师头像跳转到律师i详细页面
                @Override
                public void onClick(View view) {
                    getDataLawyer(item.getFirstname(), item.getContactid(), new LoadingDialogView(LawyerAnswerProblemActivity.this), requestQueue);
                }
            });
            return convertView;
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
                Log.i("lawyersData", response);
                Gson gson = new Gson();
                LawyersFindData lawyersFindData = gson.fromJson(response, LawyersFindData.class);
                List<LawyersFindData.RybzrowsBean> rybzrows = lawyersFindData.getRybzrows();
                if (rybzrows != null && rybzrows.size() > 0) {
                    LawyersFindData.RybzrowsBean rybzrowsBean = rybzrows.get(0);
                    JumpDetailedTools.jumpLawyerGybzDetailed(LawyerAnswerProblemActivity.this, rybzrowsBean);//跳转到律师详细页面
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

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

}
