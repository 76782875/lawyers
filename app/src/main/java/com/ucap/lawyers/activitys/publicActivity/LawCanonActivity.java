package com.ucap.lawyers.activitys.publicActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.lawCanon.LawCanonDetailedActivity;
import com.ucap.lawyers.activitys.publicActivity.lawCanon.LwaFindActivity;
import com.ucap.lawyers.adapter.ViewPagerAdapter;
import com.ucap.lawyers.base.BlankPager;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.law.LawBrowseListBean;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 百姓法宝列表
 */
public class LawCanonActivity extends Activity {
    @Bind(R.id.vp_content)
    ViewPager vpContent;//用于实现左滑关闭页面
    ArrayList<PagerBase> mList;
    ArrayList<String> mListDate;
    public String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_canon);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        Log.i("title", "---标题---" + title);
        mList = new ArrayList<>();
        mList.add(new BlankPager(this));
        mList.add(new ContentPager(this));
        vpContent.setAdapter(new ViewPagerAdapter(mList));
        vpContent.setCurrentItem(mList.size() - 1);
        vpContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("position", "当前页面：" + position + "    位置偏移:" + positionOffset + "     像素偏移:" + positionOffsetPixels);
                if (position == 0 && positionOffsetPixels <= 200) {//偏移量达到超出屏幕3／2在销毁此页面
                    finish();//滑动到第一个页面时关闭
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ContentPager extends PagerBase {
        @Bind(R.id.iv_back)
        ImageView ivBack;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.rv_content)
        RecyclerView rvContent;
        @Bind(R.id.tv_law_find)
        TextView tvLawFind;//搜索
        @Bind(R.id.tv_is_null)
        TextView tvIsNull;//暂无数据
        public TextView tvNumber;
        public RequestQueue requestQueue;
        public LoadingDialogView loadingDialog;
        public ArrayList<LawBrowseListBean.RowBean> mData;
        public ContentAdapter adapter;

        public ContentPager(Activity mActivity) {
            super(mActivity);
            setDate();
        }

        @Override
        public void intiDate() {
            requestQueue = Volley.newRequestQueue(mActivity);
            loadingDialog = new LoadingDialogView(mActivity);
            View contentView = View.inflate(mActivity, R.layout.content_law_canon, null);
            flContent.addView(contentView);
            ButterKnife.bind(this, contentView);

        }

        @Override
        public void intiListener() {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.finish();
                }
            });
            tvLawFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.startActivity(new Intent(mActivity, LwaFindActivity.class));
                }
            });
        }

        @Override
        public void setDate() {
            tvTitle.setText(title);
            mData = new ArrayList<>();
            rvContent.setLayoutManager(new GridLayoutManager(mActivity, 1));
            adapter = new ContentAdapter(mData);
            rvContent.setAdapter(adapter);
            getDataForService();
        }

        /**
         * 获取服务器数据
         */
        public void getDataForService() {
            loadingDialog.show();
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, DataInterface.LAW_BROWSE_LIST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    gsonData(response);
                    Log.i("TEST_ANSWER_LIST", "百姓法宝数据，分类+" + title + "   " + response);
                    loadingDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("TEST_ANSWER_LIST_ERROR", "访问错误：" + error.getMessage());
                    loadingDialog.dismiss();
                    tvIsNull.setVisibility(View.VISIBLE);
                    tvIsNull.setText("网路链接错误！");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("type", title);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        }

        public void gsonData(String data) {
            Gson gson = new Gson();
            LawBrowseListBean lawBrowseListBean = gson.fromJson(data, LawBrowseListBean.class);
            if (lawBrowseListBean != null) {
                List<LawBrowseListBean.RowBean> row = lawBrowseListBean.getRow();
                if (row != null && row.size() > 0) {
                    mData.addAll(row);
                    adapter.notifyDataSetChanged();
                    tvIsNull.setVisibility(View.GONE);

                } else {
                    tvIsNull.setVisibility(View.VISIBLE);
                    tvIsNull.setText("当前分类暂无数据！");

                }
            }
        }

        class ContentAdapter extends RecyclerView.Adapter<MyHolder> {

            ArrayList<LawBrowseListBean.RowBean> mData;

            public ContentAdapter(ArrayList<LawBrowseListBean.RowBean> mData) {
                this.mData = mData;
            }

            @Override
            public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyHolder(View.inflate(parent.getContext(), R.layout.item_law_canon_list, null));
            }

            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                final LawBrowseListBean.RowBean rowBean = mData.get(position);
                holder.llItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mActivity, LawCanonDetailedActivity.class);
                        intent.putExtra("itemId", rowBean.getId());
                        intent.putExtra("title", rowBean.getName());
                        mActivity.startActivity(intent);
                    }
                });

                holder.tvTitle.setText(rowBean.getName());
                holder.tvProvider.setText("提供者：" + rowBean.getGroupname() + "    " + rowBean.getFirstname());
                holder.tvProviderSize.setText("累计提供(" + rowBean.getSize() + ")条");
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        }

        class MyHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_title)
            TextView tvTitle;//法律名称
            @Bind(R.id.tv_provider)
            TextView tvProvider;//提供者
            @Bind(R.id.tv_provider_size)
            TextView tvProviderSize;//累计提供条
            @Bind(R.id.ll_item)
            LinearLayout llItem;

            public MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
