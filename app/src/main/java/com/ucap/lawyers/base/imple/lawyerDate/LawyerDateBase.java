package com.ucap.lawyers.base.imple.lawyerDate;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.ucap.lawyers.R;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.LawyerDate;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/17.
 * 用于显示"律师"数据的公共类,其数据封装到的LawyerDate里
 */
public class LawyerDateBase extends PagerBase {
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    @Bind(R.id.tv_isloding)
    TextView tvIsLoading;//点击加载
    ArrayList<LawyerDate.RowsBean> mListDate;
    private ContentAdapter adapter;
    private String uri;
    private String name;
    public RequestQueue requestQueue;

    public LawyerDateBase(Activity mActivity, String uri, String name) {
        super(mActivity);
        this.uri = uri;
        this.name = name;
        for (int i = 0; i <= 2; i++) {//连续请求三次避免出现请求不到数据
            getDataForService();
        }
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.base_lawyer_honor, null);
        ButterKnife.bind(this, view);
        requestQueue = Volley.newRequestQueue(mActivity);
        flContent.addView(view);
        mListDate = new ArrayList<>();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        adapter.onSetItemClick(new OnsetItemClick() {
            @Override
            public void onLvItemClick(int position) {
                LawyerDate.RowsBean rowsBean = mListDate.get(position);
                JumpDetailedTools.jumLawyerDefaultListDetailed(mActivity, rowsBean);//跳转到详细
            }

            @Override
            public void onCollection(int position, boolean state) {
                Toast.makeText(mActivity, position + "/" + state, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 获取服务起数据
     */
    public void getDataForService() {
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("lawyerData", "----律师默认显示分类列表----" + response);
                gsonData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoading.setVisibility(View.GONE);
                lvContent.setVisibility(View.GONE);
                tvIsLoading.setVisibility(View.VISIBLE);
                tvIsLoading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvIsLoading.setVisibility(View.GONE);
                        llLoading.setVisibility(View.VISIBLE);
                        getDataForService();
                    }
                });
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("curPageCountop", "1");
                map.put("curPageCountend", "30");
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析json
     *
     * @param json
     */
    public void gsonData(String json) {
        mListDate.clear();
        Gson gson = new Gson();
        LawyerDate lawyerDate = gson.fromJson(json, LawyerDate.class);
        mListDate.addAll(lawyerDate.getRows());
        adapter.notifyDataSetChanged();
        llLoading.setVisibility(View.INVISIBLE);//隐藏"请稍后。。"
        tvIsLoading.setVisibility(View.INVISIBLE);
        lvContent.setVisibility(View.VISIBLE);
    }

    class ContentAdapter extends BaseAdapter {
        private OnsetItemClick onsetItemClick;

        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public LawyerDate.RowsBean getItem(int position) {
            return mListDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_lawyer_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawyerDate.RowsBean item = getItem(position);
            holder.tvName.setText(item.getFirstname());
            holder.tvCertificates.setText(item.getContactid());
            holder.tvSex.setText(item.getSex());
            holder.tvOffice.setText(item.getName());
            holder.tvNegative.setText(item.getFmzs());
            holder.tvCommend.setText(item.getRyzs());
            holder.tvPublicWelfare.setText(item.getGyzs());
            holder.tvAssessment.setText(item.getYearcheck());
            holder.llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onsetItemClick != null)
                        onsetItemClick.onLvItemClick(position);
                }
            });
            holder.btnCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onsetItemClick != null) {
                        holder.btnCollection.setSelected(!holder.btnCollection.isSelected());
                        onsetItemClick.onCollection(position, holder.btnCollection.isSelected());
                    }
                }
            });
            return convertView;
        }


        public void onSetItemClick(OnsetItemClick onsetItemClick) {
            this.onsetItemClick = onsetItemClick;

        }
    }

    public interface OnsetItemClick {
        /**
         * 点击单条
         *
         * @param position
         */
        void onLvItemClick(int position);

        /**
         * 点击收藏
         *
         * @param position
         */
        void onCollection(int position, boolean state);
    }

    class ViewHolder {
        @Bind(R.id.ll_lawyer_root)
        LinearLayout llRoot;
        @Bind(R.id.btn_lawyer_collection)
        Button btnCollection;//收藏
        @Bind(R.id.tv_lawyer_name)
        TextView tvName;//律师名称
        @Bind(R.id.tv_lawyer_certificates)
        TextView tvCertificates;//执业证号
        @Bind(R.id.tv_lawyer_sex)
        TextView tvSex;//性别
        @Bind(R.id.tv_lawyer_office)
        TextView tvOffice;//所在律所
        @Bind(R.id.tv_lawyer_negative)
        TextView tvNegative;// 负面指数
        @Bind(R.id.tv_lawyer_commend)
        TextView tvCommend;//荣誉指数
        @Bind(R.id.tv_lawyer_public_welfare)
        TextView tvPublicWelfare;//公益服务
        @Bind(R.id.tv_lawyer_assessment)
        TextView tvAssessment;//年度考核

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
