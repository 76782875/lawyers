package com.ucap.lawyers.base.imple.ranking;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.ranking.answerClassify.ranking.RankingFirmListBean;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.RankingFirmLawyersListDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/12/5.
 * <p>
 * 律师解答排行
 */

public class BankingFirm extends PagerBase {
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.tv_is_null)
    TextView tvIsNull;
    ArrayList<RankingFirmListBean.RowBean> mListData;
    public RequestQueue requestQueue;
    public LoadingDialogView loadingDialog;
    public ContentAdapter adapter;

    public BankingFirm(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.ranking_firm, null);
        flContent.addView(view);
        ButterKnife.bind(this, view);
        requestQueue = Volley.newRequestQueue(mActivity);
        loadingDialog = new LoadingDialogView(mActivity);
        mListData = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RankingFirmLawyersListDialog.showDialog(mActivity, adapter.getItem(position).getGroupid());
            }
        });
        getDataForService("all");//默认显示总排行榜
    }

    /**
     * 设置排行天数 月排行榜，周排行榜，总排行榜
     *
     * @param day
     */
    public void setDay(String day) {
        Toast.makeText(mActivity, day, Toast.LENGTH_SHORT).show();
        if (day.equals("总排行榜")) {
            getDataForService("all");
        } else if (day.equals("周排行榜")) {
            getDataForService("7");
        } else if (day.equals("月排行榜")) {
            getDataForService("30");
        }
    }

    /**
     * 获取服务器数据
     */
    public void getDataForService(String day) {
        loadingDialog.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.RANKING_FIRM_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("RANKING_FIRM_LIST", "律所解答排行榜，律所列表: " + response);
                if (!response.isEmpty()) {
                    gsonData(response);
                }
                loadingDialog.dismiss();
                tvIsNull.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                tvIsNull.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析数据
     *
     * @param data
     */
    public void gsonData(String data) {
        Gson gson = new Gson();
        RankingFirmListBean rankingFirmListBean = gson.fromJson(data, RankingFirmListBean.class);
        if (rankingFirmListBean != null) {
            List<RankingFirmListBean.RowBean> row = rankingFirmListBean.getRow();
            mListData.clear();
            mListData.addAll(row);
            adapter.notifyDataSetChanged();
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public RankingFirmListBean.RowBean getItem(int position) {
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
                convertView = View.inflate(mActivity, R.layout.item_banking_firm, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                holder.tvFirmName.setTextColor(mActivity.getResources().getColor(R.color.number_1));
                holder.tvAnswerSize.setTextColor(mActivity.getResources().getColor(R.color.number_1));
                holder.tvFabulous.setTextColor(mActivity.getResources().getColor(R.color.number_1));
                holder.ivNumber.setBackgroundResource(R.drawable.number_1);
                holder.ivNumber.setVisibility(View.VISIBLE);
                holder.llItem.setBackgroundResource(R.drawable.selector_bg_item_number_1);
            } else if (position == 1) {
                holder.tvFirmName.setTextColor(mActivity.getResources().getColor(R.color.number_2));
                holder.tvAnswerSize.setTextColor(mActivity.getResources().getColor(R.color.number_2));
                holder.tvFabulous.setTextColor(mActivity.getResources().getColor(R.color.number_2));
                holder.ivNumber.setBackgroundResource(R.drawable.number_2);
                holder.ivNumber.setVisibility(View.VISIBLE);
                holder.llItem.setBackgroundResource(R.drawable.selector_bg_item_number_2);
            } else if (position == 2) {
                holder.tvFirmName.setTextColor(mActivity.getResources().getColor(R.color.number_3));
                holder.tvAnswerSize.setTextColor(mActivity.getResources().getColor(R.color.number_3));
                holder.tvFabulous.setTextColor(mActivity.getResources().getColor(R.color.number_3));
                holder.ivNumber.setBackgroundResource(R.drawable.number_3);
                holder.ivNumber.setVisibility(View.VISIBLE);
                holder.llItem.setBackgroundResource(R.drawable.selector_bg_item_number_3);
            } else {
                holder.tvFirmName.setTextColor(mActivity.getResources().getColor(R.color.number));
                holder.tvAnswerSize.setTextColor(mActivity.getResources().getColor(R.color.number));
                holder.tvFabulous.setTextColor(mActivity.getResources().getColor(R.color.number));
                holder.ivNumber.setBackgroundResource(R.drawable.shape_bg_number);
                holder.ivNumber.setVisibility(View.INVISIBLE);
                holder.llItem.setBackgroundResource(R.drawable.selector_bg_item);
            }
            RankingFirmListBean.RowBean item = getItem(position);
            holder.tvFirmName.setText(item.getGroupname());
            holder.tvAnswerSize.setText(item.getJdzs());
            holder.tvFabulous.setText(item.getDzzs());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_firm_name)
        TextView tvFirmName;//律所名称
        @Bind(R.id.tv_answer_size)
        TextView tvAnswerSize;//解答总条数
        @Bind(R.id.tv_fabulous)
        TextView tvFabulous;//点赞总数
        @Bind(R.id.iv_number)
        ImageView ivNumber;//奖牌
        @Bind(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
