package com.ucap.lawyers.base.imple.ranking;

import android.app.Activity;
import android.content.Intent;
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
import com.ucap.lawyers.activitys.publicActivity.ranking.RankingAnswerListActivity;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.ranking.answerClassify.ranking.RankingLawyersListBean;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/12/5.
 * <p>
 * 律师解答排行，列表
 */

public class BankingLawyers extends PagerBase {
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.tv_is_null)
    TextView tvIsNull;
    ArrayList<RankingLawyersListBean.RowBean> mDataList;
    public RequestQueue requestQueue;
    public LoadingDialogView loadingView;
    public ContentAdapter adapter;

    public BankingLawyers(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.ranking_lawyer, null);
        flContent.addView(view);
        ButterKnife.bind(this, view);
        requestQueue = Volley.newRequestQueue(mActivity);
        loadingView = new LoadingDialogView(mActivity);
        mDataList = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, RankingAnswerListActivity.class);
                RankingLawyersListBean.RowBean item = adapter.getItem(position);
                intent.putExtra("itemId", item.getAmanofpraise());
                intent.putExtra("title", item.getFirstname());
                mActivity.startActivity(intent);
            }
        });
        getDataForService("all");//默认总排行榜
    }

    /**
     * 选择排行天数
     *
     * @param day 周排行7，月排行30，总排行all
     */
    public void selectRankingType(String day) {
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
     * 获取服务数据
     */
    public void getDataForService(final String day) {
        loadingView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.RANKING_LAWYERS_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("RANKING_LAWYERS_LIST", "排行好律师列表： " + response);
                if (!response.isEmpty()) {
                    gsonData(response);
                }
                loadingView.dismiss();
                tvIsNull.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingView.dismiss();
                tvIsNull.setText("网络链接超时！");
                tvIsNull.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("day", day);
                return map;
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
        RankingLawyersListBean rankingLawyersListBean = gson.fromJson(data, RankingLawyersListBean.class);
        if (rankingLawyersListBean != null) {
            List<RankingLawyersListBean.RowBean> row = rankingLawyersListBean.getRow();
            mDataList.clear();
            mDataList.addAll(row);
            adapter.notifyDataSetChanged();
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public RankingLawyersListBean.RowBean getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_banking_lawyers, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                holder.tvName.setTextColor(mActivity.getResources().getColor(R.color.number_1));
                holder.tvFirmName.setTextColor(mActivity.getResources().getColor(R.color.number_1));
                holder.tvAnswerSize.setTextColor(mActivity.getResources().getColor(R.color.number_1));
                holder.tvFabulous.setTextColor(mActivity.getResources().getColor(R.color.number_1));
                holder.ivNumber.setBackgroundResource(R.drawable.number_1);
                holder.ivNumber.setVisibility(View.VISIBLE);
                holder.llItem.setBackgroundResource(R.drawable.selector_bg_item_number_1);
            } else if (position == 1) {
                holder.tvName.setTextColor(mActivity.getResources().getColor(R.color.number_2));
                holder.tvFirmName.setTextColor(mActivity.getResources().getColor(R.color.number_2));
                holder.tvAnswerSize.setTextColor(mActivity.getResources().getColor(R.color.number_2));
                holder.tvFabulous.setTextColor(mActivity.getResources().getColor(R.color.number_2));
                holder.ivNumber.setBackgroundResource(R.drawable.number_2);
                holder.ivNumber.setVisibility(View.VISIBLE);
                holder.llItem.setBackgroundResource(R.drawable.selector_bg_item_number_2);
            } else if (position == 2) {
                holder.tvName.setTextColor(mActivity.getResources().getColor(R.color.number_3));
                holder.tvFirmName.setTextColor(mActivity.getResources().getColor(R.color.number_3));
                holder.tvAnswerSize.setTextColor(mActivity.getResources().getColor(R.color.number_3));
                holder.tvFabulous.setTextColor(mActivity.getResources().getColor(R.color.number_3));
                holder.ivNumber.setBackgroundResource(R.drawable.number_3);
                holder.ivNumber.setVisibility(View.VISIBLE);
                holder.llItem.setBackgroundResource(R.drawable.selector_bg_item_number_3);
            } else {
                holder.tvName.setTextColor(mActivity.getResources().getColor(R.color.number));
                holder.tvFirmName.setTextColor(mActivity.getResources().getColor(R.color.number));
                holder.tvAnswerSize.setTextColor(mActivity.getResources().getColor(R.color.number));
                holder.tvFabulous.setTextColor(mActivity.getResources().getColor(R.color.number));
                holder.ivNumber.setBackgroundResource(R.drawable.shape_bg_number);
                holder.ivNumber.setVisibility(View.INVISIBLE);
                holder.llItem.setBackgroundResource(R.drawable.selector_bg_item);
            }
            RankingLawyersListBean.RowBean item = getItem(position);
            holder.tvName.setText(item.getFirstname());
            holder.tvFirmName.setText(item.getGroupname());
            holder.tvAnswerSize.setText(item.getJiedasize());
            holder.tvFabulous.setText(item.getDianzansize());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;//律师名称
        @Bind(R.id.tv_firm_name)
        TextView tvFirmName;//所在律所
        @Bind(R.id.tv_answer_size)
        TextView tvAnswerSize;//解答总数
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
