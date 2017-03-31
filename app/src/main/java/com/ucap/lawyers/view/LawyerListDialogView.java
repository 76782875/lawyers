package com.ucap.lawyers.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.ucap.lawyers.bean.homeFind.LawyerDate;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/9/18.
 * 律所详细页面,律师名单弹窗
 */
public class LawyerListDialogView extends BaseDialog {
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.rv_content)
    RecyclerView rvContent;

    ArrayList<String> mData;
    String id;

    protected LawyerListDialogView(Context context, ArrayList<String> mData, String id) {
        super(context);
        this.mData = mData;
        this.id = id;
    }

    public static void showDialog(Context ctx, ArrayList mData, String id) {
        LawyerListDialogView dialogView = new LawyerListDialogView(ctx, mData, id);
        dialogView.setCancelable(false);
        dialogView.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_lawyer_list);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
    }

    @Override
    public void setData() {
        rvContent.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvContent.setAdapter(new LawyersListAdapter());
    }

    @Override
    public void setClick(View v) {
        if (v == ivDismiss) {
            dismiss();
        }
    }

    public class LawyersListAdapter extends RecyclerView.Adapter<ViewHolder> {
        private LoadingDialogView lodingDialogView;
        private RequestQueue requestQueue;

        LawyersListAdapter() {
            lodingDialogView = new LoadingDialogView(getContext());
            this.requestQueue = Volley.newRequestQueue(getContext());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(getContext(), R.layout.item_dialog_lawyer_list, null);
            return new ViewHolder(view);
        }

        private void setData(final String name) {
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.FIRM_DETAILED_LAWYERS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("lawyersData", response);
                    if (!response.isEmpty()) {
                        Gson gson = new Gson();
                        LawyerDate lawyerDate = gson.fromJson(response, LawyerDate.class);
                        List<LawyerDate.RowsBean> rows = lawyerDate.getRows();
                        LawyerDate.RowsBean rowsBean = rows.get(0);
                        JumpDetailedTools.jumpFirmLawyerListDetailed(getContext(), rowsBean);//跳转到详细
                    }
                    lodingDialogView.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    lodingDialogView.dismiss();
                    Toast.makeText(getContext(), "网络连接失败!", Toast.LENGTH_LONG).show();
                    Log.i("HttpException", error.getLocalizedMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("firstname", name);
                    map.put("id", id);
                    return map;
                }
            };
            Log.i("url", request.getUrl());
            requestQueue.add(request);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final String text = mData.get(position);
            holder.tvName.setText(text);
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData(text);
                    lodingDialogView.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
