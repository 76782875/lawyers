package com.ucap.lawyers.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.law.LawBrowseSizeBean;
import com.ucap.lawyers.tools.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2017/1/11.
 */

public class LawCanonSelectDialog extends BaseDialog {
    @Bind(R.id.rv_content)
    RecyclerView rvContent;
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    public RequestQueue requestQueue;
    public ArrayList<String> mData;
    public ContentAdapter adapter;

    public LawCanonSelectDialog(Context context, OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
    }

    public static void showDialog(Context ctx, OnSelectListener onSelectListener) {
        LawCanonSelectDialog dialog = new LawCanonSelectDialog(ctx, onSelectListener);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_law_canon_select);
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
        mData = new ArrayList<>();
        rvContent.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new ContentAdapter(mData);
        rvContent.setAdapter(adapter);
        String law_browse_size = PrefUtils.getString("law_browse_size", "", getContext());
        if (!law_browse_size.isEmpty()) {//缓存
            gsonData(law_browse_size);
        }
        getDataForService();

    }
    public void getDataForService() {
        StringRequest request = new StringRequest(DataInterface.LAW_BROWSE_SIZE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TEST_LAW_BROWSE_SIZE", "分类总条数：" + response);
                gsonData(response);
                PrefUtils.putString("law_browse_size", response, getContext());
                pbLoading.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbLoading.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(request);
    }

    public void gsonData(String data) {
        Gson gson = new Gson();
        LawBrowseSizeBean lawBrowseSizeBean = gson.fromJson(data, LawBrowseSizeBean.class);
        List<String> row = lawBrowseSizeBean.getRow();
        if (row != null && row.size() > 0) {
            mData.clear();
            mData.addAll(row);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setClick(View v) {

    }

    private OnSelectListener onSelectListener;

    public interface OnSelectListener {
        /**
         */
        void onSelect(String id);
    }

    class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        ArrayList<String> mData;

        public ContentAdapter(ArrayList<String> mData) {
            this.mData = mData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(getContext(), R.layout.item_law_canon_select, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final String title = mData.get(position);
            holder.tvClassify.setText(title);
            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSelectListener.onSelect(title);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_classify)
        TextView tvClassify;//分类
        @Bind(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
