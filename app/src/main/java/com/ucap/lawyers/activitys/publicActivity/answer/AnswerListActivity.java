package com.ucap.lawyers.activitys.publicActivity.answer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.adapter.ViewPagerAdapter;
import com.ucap.lawyers.base.BlankPager;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.ranking.answerClassify.AnswerClassifyListBean;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已解答列表
 */


public class AnswerListActivity extends Activity {
    @Bind(R.id.vp_content)
    ViewPager vpContent;//用于实现左滑关闭页面
    ArrayList<PagerBase> mList;
    public String titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_answer_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        titleName = intent.getStringExtra("classify");
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

        @Bind(R.id.lv_content)
        ListView lvContent;
        @Bind(R.id.iv_back)
        ImageView ivBack;
        @Bind(R.id.tv_title)
        TextView tvTitle;//标题
        @Bind(R.id.tv_is_null)
        TextView tvIsNull;//暂无数据
        ArrayList<AnswerClassifyListBean.RowBean> mListData;
        public RequestQueue requestQueue;
        public ContentAdapter adapter;
        public LoadingDialogView loadingDialogView;

        /**
         * 分类对应id
         * 213	刑事法务
         * 214	行政法务
         * 215	经济纠纷
         * 216	婚姻家庭
         * 217	金融保险
         * 218	社保劳资
         * 219	交通医疗
         * 220	其他法务
         */
        public ContentPager(Activity mActivity) {
            super(mActivity);
        }

        @Override
        public void intiDate() {
            requestQueue = Volley.newRequestQueue(mActivity);
            loadingDialogView = new LoadingDialogView(mActivity);
            View view = View.inflate(mActivity, R.layout.ranking_answer_list_content, null);
            flContent.addView(view);
            ButterKnife.bind(this, view);
            if (titleName.equals("213")) {
                tvTitle.setText("刑事法务");
            } else if (titleName.equals("214")) {
                tvTitle.setText("行政法务");
            } else if (titleName.equals("215")) {
                tvTitle.setText("经济纠纷");
            } else if (titleName.equals("216")) {
                tvTitle.setText("婚姻家庭");
            } else if (titleName.equals("217")) {
                tvTitle.setText("金融保险");
            } else if (titleName.equals("218")) {
                tvTitle.setText("社保劳资");
            } else if (titleName.equals("219")) {
                tvTitle.setText("交通医疗");
            } else if (titleName.equals("220")) {
                tvTitle.setText("其他法务");
            }
            mListData = new ArrayList<>();
            adapter = new ContentAdapter();
            lvContent.setAdapter(adapter);
            getDataForService(titleName);

        }

        /**
         * 获取服务器数据
         * typeId 分类id
         * * 213	刑事法务
         * 214	行政法务
         * 215	经济纠纷
         * 216	婚姻家庭
         * 217	金融保险
         * 218	社保劳资
         * 219	交通医疗
         * 220	其他法务
         */
        public void getDataForService(final String typeId) {
            loadingDialogView.show();
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.ANSWER_LIST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("ANSWER_LIST", "分类id " + typeId + " ---已答问题：" + response);
                    if (!response.isEmpty()) {
                        Gson gson = new Gson();
                        AnswerClassifyListBean answerClassifyListBean = gson.fromJson(response, AnswerClassifyListBean.class);
                        if (answerClassifyListBean != null) {
                            List<AnswerClassifyListBean.RowBean> row = answerClassifyListBean.getRow();
                            if (row != null && row.size() > 0) {
                                mListData.addAll(row);
                                adapter.notifyDataSetChanged();
                                tvIsNull.setVisibility(View.GONE);
                            } else {
                                tvIsNull.setText("暂无数据..");
                                tvIsNull.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    loadingDialogView.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialogView.dismiss();
                    tvIsNull.setVisibility(View.VISIBLE);
                    tvIsNull.setText("网络联接错误！");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("typeid", typeId);
                    return map;
                }
            };
            requestQueue.add(request);
        }

        @Override
        public void intiListener() {
            lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mActivity, AnswerLawyersDetailedActivity.class);
                    AnswerClassifyListBean.RowBean item = adapter.getItem(position);
                    intent.putExtra("orderid", item.getId());
                    intent.putExtra("title", item.getFirstname());
                    mActivity.startActivity(intent);
                }
            });
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.finish();
                }
            });
        }

        class ContentAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return mListData.size();
            }

            @Override
            public AnswerClassifyListBean.RowBean getItem(int position) {
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
                    convertView = View.inflate(mActivity, R.layout.item_ranking_answer_list, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                AnswerClassifyListBean.RowBean item = getItem(position);
                holder.tvTitle.setText(item.getTitle());
                holder.tvName.setText(item.getFirstname());
                Glide.with(mActivity)
                        .load(item.getPhoto())
                        .into(holder.ivPhoto);
                return convertView;
            }
        }

        class ViewHolder {
            @Bind(R.id.tv_title)
            TextView tvTitle;//标题
            @Bind(R.id.iv_photo)
            ImageView ivPhoto;//咨询者头像
            @Bind(R.id.tv_name)
            TextView tvName;//咨询者名称

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }


}
