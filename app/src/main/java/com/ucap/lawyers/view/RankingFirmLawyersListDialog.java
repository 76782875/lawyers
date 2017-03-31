package com.ucap.lawyers.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ucap.lawyers.activitys.publicActivity.ranking.RankingAnswerListActivity;
import com.ucap.lawyers.bean.ranking.answerClassify.ranking.RankingFirmLawyerListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/12/7.
 * 律所排行榜律师选择弹窗
 */

public class RankingFirmLawyersListDialog extends BaseDialog {
    @Bind(R.id.rv_content)
    RecyclerView rvContent;
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    @Bind(R.id.tv_is_null)
    TextView tvIsNull;
    ArrayList<RankingFirmLawyerListBean.RowBean> mListData;
    String greoupid;
    public RequestQueue requestQueue;
    public ContentAdapter adapter;

    protected RankingFirmLawyersListDialog(Context context, String greoupid) {
        super(context);
        this.greoupid = greoupid;
    }

    /**
     * @param ctx
     * @param greoupid 获取律所名单所需参数 id:律所ID(律所排行榜返回的groupid)
     */
    public static void showDialog(Context ctx, String greoupid) {
        RankingFirmLawyersListDialog dialog = new RankingFirmLawyersListDialog(ctx, greoupid);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_ranking_firm_lawyers_list);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void setData() {
        requestQueue = Volley.newRequestQueue(getContext());
        mListData = new ArrayList<>();
        rvContent.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        adapter = new ContentAdapter();
        rvContent.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                RankingFirmLawyerListBean.RowBean item = mListData.get(position);
                Intent intent = new Intent(getContext(), RankingAnswerListActivity.class);
                intent.putExtra("itemId", item.getUserid() + "");
                intent.putExtra("title", item.getFirstname());
                getContext().startActivity(intent);
            }
        });
        getDataForService();
    }

    @Override
    public void setClick(View v) {

    }

    public void getDataForService() {
        pbLoading.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.RANKING_FIRM_LAWYER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("RANKING_LAWYER_LIST", "律所解答排行榜律师名单： " + response);
                if (!response.isEmpty()) {
                    gsonData(response);
                }
                pbLoading.setVisibility(View.GONE);
                tvIsNull.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbLoading.setVisibility(View.GONE);
                tvIsNull.setVisibility(View.VISIBLE
                );
                Toast.makeText(getContext(), "网络链接超时！", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", greoupid);
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
        RankingFirmLawyerListBean rankingFirmLawyersListBean = gson.fromJson(data, RankingFirmLawyerListBean.class);
        if (rankingFirmLawyersListBean != null) {
            List<RankingFirmLawyerListBean.RowBean> row = rankingFirmLawyersListBean.getRow();
            if (row != null && row.size() > 0) {
                mListData.addAll(row);
                adapter.notifyDataSetChanged();
            }
        }
    }

    class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private OnItemClickListener onItemClickListener;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(getContext(), R.layout.item_ranking_firm_lawyer_list, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            RankingFirmLawyerListBean.RowBean rowBean = mListData.get(position);
            Glide.with(getContext())
                    .load(rowBean.getPhoto())
                    .into(holder.ivPhoto);
            holder.tvName.setText(rowBean.getFirstname());
            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null)
                        onItemClickListener.itemClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mListData.size();
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

    }

    public interface OnItemClickListener {
        void itemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_photo)
        ImageView ivPhoto;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
