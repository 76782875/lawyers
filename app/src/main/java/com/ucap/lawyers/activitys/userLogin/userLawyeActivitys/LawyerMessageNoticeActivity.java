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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerMessageNotice;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.FileListDialog;
import com.ucap.lawyers.view.MessageNoticeDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 信息公告
 */
public class LawyerMessageNoticeActivity extends AppCompatActivity {

    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.tv_file_list)
    TextView tvFileList;//文件列表
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    private ArrayList<LawyerMessageNotice.RowsBean> mListDate;
    public String uri;
    public RequestQueue requestQueue;
    public ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_message_notice);
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

    private void intiListener() {
        tvFileList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileListDialog.showDialog(LawyerMessageNoticeActivity.this);
            }
        });
    }

    private void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String uri = intent.getStringExtra("InfoAnnouncement");
        String lawyerId = intent.getStringExtra("id");//律师id
        String firmId = intent.getStringExtra("firmId");//律所id
        getDataForService(lawyerId, firmId, uri);
        mListDate = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LawyerMessageNotice.RowsBean item = adapter.getItem(position);
                HashMap<String, Object> data = new HashMap<>();
                data.put("id", item.getId() + "");
                data.put("optname", item.getOptname());//发布者姓名
                data.put("pubdata", item.getPubdate());//发布时间
                data.put("subject", item.getSubject());//发布内容
                data.put("pathobjs", item.getPathobjs());//附件内容
                MessageNoticeDialogView noticeDialogView = new MessageNoticeDialogView(LawyerMessageNoticeActivity.this, data);
                noticeDialogView.setCancelable(false);
                noticeDialogView.show();
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 获取服务器数据，并显示数据
     *
     * @param lawyerId
     * @param firmId
     * @param uri
     */
    private void getDataForService(final String lawyerId, final String firmId, String uri) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("lawyerMessageData", response);
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
                map.put("appuserid", lawyerId);
                map.put("appgroupid", firmId);
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析服务器数据
     *
     * @param response
     */
    private void gsonData(String response) {
        Gson gson = new Gson();
        List<LawyerMessageNotice.RowsBean> rows = gson.fromJson(response, LawyerMessageNotice.class).getRows();
        if (rows != null && rows.size() > 0) {
            mListDate.clear();
            mListDate.addAll(rows);
            adapter.notifyDataSetChanged();
            pbLoading.setVisibility(View.GONE);
        }
    }

    public class ContentAdapter extends BaseAdapter {


        public String messageNoticeState;

        ContentAdapter() {
            //获取已阅读的id
            PrefUtils.getString("messageNoticeState", "", LawyerMessageNoticeActivity.this);
        }

        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public LawyerMessageNotice.RowsBean getItem(int position) {
            return mListDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            messageNoticeState = PrefUtils.getString("messageNoticeState", "", LawyerMessageNoticeActivity.this);
            Log.i("messageNoticeState", "刷新数据！");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(LawyerMessageNoticeActivity.this, R.layout.item_lawyer_message_notice, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawyerMessageNotice.RowsBean item = getItem(position);
            holder.tvTitle.setText(item.getSubject());//发布内容
            holder.tvTimer.setText(item.getPubdate());//时间
            holder.tvAuthor.setText(item.getOptname());//发布者姓名

            if (messageNoticeState.indexOf(item.getId() + "") == -1) {//判断改条数据是否已经阅读
                holder.ivNewMessage.setVisibility(View.VISIBLE);
            } else {
                holder.ivNewMessage.setVisibility(View.GONE);
            }
            Log.i("messageNoticeState", messageNoticeState);
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_timer)
        TextView tvTimer;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.iv_new_massage)
        ImageView ivNewMessage;//新消息标志

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
