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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.ucap.lawyers.adapter.ViewPagerAdapter;
import com.ucap.lawyers.base.BlankPager;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.ranking.answerClassify.AnswerClassifyDetaildeBean;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;
import com.ucap.lawyers.view.LoadingDialogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已答详细
 */
public class AnswerLawyersDetailedActivity extends Activity {
    @Bind(R.id.vp_content)
    ViewPager vpContent;//用于实现左滑关闭页面
    ArrayList<PagerBase> mList;

    public String orderid;
    public String titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_lawyers_detailed);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderid = intent.getStringExtra("orderid");
        titleText = intent.getStringExtra("title");
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
        @Bind(R.id.lv_content)
        ListView lvContent;
        @Bind(R.id.tv_is_null)
        TextView tvIsNull;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        ArrayList<AnswerClassifyDetaildeBean.UserrowBean> mListDate;
        public TextView tvHeadTitle;
        public TextView tvHeadTimer;
        public TextView tvNumber;
        public RequestQueue requestQueue;
        public ContentAdapter adapter;
        public LoadingDialogView loadingDialogView;

        public ContentPager(Activity mActivity) {
            super(mActivity);
        }

        @Override
        public void intiDate() {
            loadingDialogView = new LoadingDialogView(mActivity);
            requestQueue = Volley.newRequestQueue(mActivity);
            mListDate = new ArrayList<>();
            View view = View.inflate(mActivity, R.layout.pager_ranking_lawyers_detailed, null);
            flContent.addView(view);
            ButterKnife.bind(this, view);
            Log.i("orderid", "orderid:--" + orderid + "title:  " + titleText);
            tvTitle.setText(titleText);
            adapter = new ContentAdapter();
            lvContent.setAdapter(adapter);
            getDataForService();
        }

        /**
         * 获取服务器数据
         */
        public void getDataForService() {
            loadingDialogView.show();
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.ANSWER_DETAILED, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("TEST_ANSWER_DETAILED", "已答问题详细(orderid " + orderid + ")：" + response);
                    if (!response.isEmpty())
                        gsonData(response);
                    loadingDialogView.dismiss();
                    tvIsNull.setVisibility(View.INVISIBLE);
                    tvIsNull.setText("");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialogView.dismiss();
                    tvIsNull.setVisibility(View.VISIBLE);
                    tvIsNull.setText("链接错误，无法获取数据!");

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("orderid", orderid);
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
            AnswerClassifyDetaildeBean answerClassifyDetaildeBean = gson.fromJson(data, AnswerClassifyDetaildeBean.class);
            if (answerClassifyDetaildeBean != null) {
                //头部问题数据
                List<AnswerClassifyDetaildeBean.RowBean> row = answerClassifyDetaildeBean.getRow();
                if (row != null && row.size() > 0) {
                    AnswerClassifyDetaildeBean.RowBean rowBean = row.get(0);
                    if (rowBean != null) {
                        View head = View.inflate(mActivity, R.layout.item_answer_peroblem_head, null);//list头布局
                        //问题内容
                        tvHeadTitle = (TextView) head.findViewById(R.id.tv_title);
                        tvHeadTitle.setText(rowBean.getPublicConsultationcontent());
                        //回答时间
                        tvHeadTimer = (TextView) head.findViewById(R.id.tv_timer);
                        tvHeadTimer.setText(rowBean.getPublicConsultationcreatetime());
                        //回答总个数
                        tvNumber = (TextView) head.findViewById(R.id.tv_number);
                        tvNumber.setText(rowBean.getSize() + "条回答");
                        lvContent.addHeaderView(head);
                    }
                }
                //列表回答数据
                List<AnswerClassifyDetaildeBean.UserrowBean> userrow = answerClassifyDetaildeBean.getUserrow();
                if (userrow != null && userrow.size() > 0) {
                    mListDate.addAll(userrow);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void intiListener() {
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
                return mListDate.size();
            }

            @Override
            public AnswerClassifyDetaildeBean.UserrowBean getItem(int position) {
                return mListDate.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null) {
                    convertView = View.inflate(mActivity, R.layout.item_lawyer_answer_problem, null);
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.btnNice.setVisibility(View.GONE);//隐藏点赞
                final AnswerClassifyDetaildeBean.UserrowBean item = getItem(position);
                viewHolder.tvMessage.setText(item.getContent());
                viewHolder.tvName.setText(item.getFirstname());
                viewHolder.tvTimer.setText(item.getCreattime());
                Glide.with(mActivity)
                        .load(item.getPhoto())
                        .into(viewHolder.ivPhoto);
                viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("firstname", item.getFirstname());
                        map.put("contactid", item.getContactid());
                        findLawyerDateForService(map, loadingDialogView);
                    }
                });
                return convertView;
            }

        }

        /**
         * 点击律师头像跳转到律师详细页面
         */
        public void findLawyerDateForService(final HashMap<String, String> map, final LoadingDialogView loadingDialogView) {
            loadingDialogView.show();
            StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Lawyer.LAWYERS_VAGUE_FIND, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("dataLawyers", "------" + response);
                    Gson gson = new Gson();
                    LawyersFindData lawyersFindData = gson.fromJson(response, LawyersFindData.class);
                    List<LawyersFindData.RybzrowsBean> rybzrows = lawyersFindData.getRybzrows();
                    if (rybzrows != null && rybzrows.size() > 0) {
                        JumpDetailedTools.jumpLawyerGybzDetailed(mActivity, rybzrows.get(0));//跳转到律师详细页面
                    } else {
                        Toast.makeText(mActivity, "为查询到该律师信息", Toast.LENGTH_SHORT).show();
                    }
                    loadingDialogView.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialogView.dismiss();
                    Toast.makeText(mActivity, "网络连接失败!", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return map;
                }
            };
            Log.i("lawyerUri", request.getUrl());
            requestQueue.add(request);

        }


        class ViewHolder {
            @Bind(R.id.iv_photo)
            ImageView ivPhoto;//头像
            @Bind(R.id.tv_name)
            TextView tvName;//律师名称
            @Bind(R.id.tv_message)
            TextView tvMessage;//回答内容
            @Bind(R.id.tv_timer)
            TextView tvTimer;//回答时间
            @Bind(R.id.btn_nice)
            Button btnNice;//点赞

            ViewHolder(View v) {
                ButterKnife.bind(this, v);
            }
        }
    }
}
