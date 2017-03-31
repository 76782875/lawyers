package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerPublicConsultItem;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 公众咨询列表页面
 */
public class LawyerPublicConsultActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    private ArrayList<LawyerPublicConsultItem.RowsBean> mListData;
    public RequestQueue requestQueue;
    public ContentAdapter adapter;
    public String lawyerId;
    public String uri;
    public LoadingDialogView loadingDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_public_consult);
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

    private void intiListener() {
    }

    private void intiData() {
        loadingDialogView = new LoadingDialogView(this);
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        lawyerId = intent.getStringExtra("id");
        String firmId = intent.getStringExtra("firmId");
        uri = intent.getStringExtra("PublicConsultationList");
        getDataForService(uri);
        mListData = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
    }

    /**
     * 获取服务器数据
     *
     * @param uri
     */
    private void getDataForService(String uri) {
        loadingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LawyerPublicConsultData", response);
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
                map.put("logintype", "67");//登陆角色编号
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析服务器数据，并显示
     *
     * @param response
     */
    private void gsonData(String response) {
        List<LawyerPublicConsultItem.RowsBean> rows = new Gson().fromJson(response, LawyerPublicConsultItem.class).getRows();
        if (rows != null && rows.size() > 0) {
            mListData.clear();
            mListData.addAll(rows);
            adapter.notifyDataSetChanged();

        }
        loadingDialogView.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("onRestart", "onRestart");
        getDataForService(uri);//回答完问题后，在重新获取服务器数据
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public LawyerPublicConsultItem.RowsBean getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(LawyerPublicConsultActivity.this, R.layout.item_lawyer_public_consult, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawyerPublicConsultItem.RowsBean item = getItem(position);
            final String id = item.getId();//单条id，请求详细回答回传
            final String answerConsultationUrl = item.getAnswerConsultationUrl();//详细回答数据接口
            final String firstname = item.getFirstname();//提问人姓名
            int size = item.getSize();//已回答总条数
            String title = item.getTitle();//提问内容
            holder.tvTitle.setText(title);
            holder.tvName.setText(firstname);
            holder.tvSmsNumber.setText(size + "");
            Glide.with(LawyerPublicConsultActivity.this).load(item.getPhoto()).into(holder.ivPhoto);
            holder.llReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LawyerPublicConsultActivity.this, LawyerAnswerProblemActivity.class);
                    intent.putExtra("name", firstname + "的提问");
                    intent.putExtra("id", id);
                    intent.putExtra("lawyerId", lawyerId);
                    intent.putExtra("answerConsultationUrl", answerConsultationUrl);
                    startActivity(intent);

                }
            });
            return convertView;
        }
    }


    class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;//问题内容
        @Bind(R.id.iv_photo)
        ImageView ivPhoto;//提问人头像
        @Bind(R.id.tv_name)
        TextView tvName;//提问人姓名
        @Bind(R.id.tv_sms_number)
        TextView tvSmsNumber;//总回答条数
        @Bind(R.id.ll_reply)
        LinearLayout llReply;//点击回答

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
