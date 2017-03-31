package com.ucap.lawyers.activitys.publicActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.adapter.ViewPagerAdapter;
import com.ucap.lawyers.base.BlankPager;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.base.imple.ranking.BankingFirm;
import com.ucap.lawyers.base.imple.ranking.BankingLawyers;
import com.ucap.lawyers.view.RankingSelectDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 解答排行
 */
public class RankingActivity extends Activity {
    @Bind(R.id.vp_content)
    ViewPager vpContent;//用于实现左滑关闭页面
    ArrayList<PagerBase> mList;
    public ContentPager content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        mList.add(new BlankPager(this));
        content = new ContentPager(this);
        mList.add(content);
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

    /**
     * 第二个内容页页
     */
    class ContentPager extends PagerBase {
        @Bind(R.id.iv_back)
        ImageView ivBack;
        @Bind(R.id.vp_content)
        ViewPager vpContent;
        @Bind(R.id.rg_buttons)
        RadioGroup rgLayout;

        @Bind(R.id.tv_select_ranking)
        TextView tvSelectRanking;

        @Bind(R.id.tv_frim_select_ranking)
        TextView tvFrimSelectRanking;

        @Bind(R.id.btn_lawyer)
        RadioButton btnLawyers;

        @Bind(R.id.btn_firm)
        RadioButton btnFrim;

        @Bind(R.id.iv_firm)
        ImageView ivFirm;

        @Bind(R.id.iv_lawyer)
        ImageView ivLawyer;
        public BankingLawyers rankingLawyer;
        public BankingFirm rankingFiam;

        public ContentPager(Activity mActivity) {
            super(mActivity);

        }

        @Override
        public void intiDate() {
            View view = View.inflate(mActivity, R.layout.ranking_content, null);
            flContent.addView(view);
            ButterKnife.bind(this, view);
            ArrayList<PagerBase> mList = new ArrayList<>();
            rankingLawyer = new BankingLawyers(mActivity);
            mList.add(rankingLawyer);
            rankingFiam = new BankingFirm(mActivity);
            mList.add(rankingFiam);
            vpContent.setAdapter(new ViewPagerAdapter(mList));
        }

        @Override
        public void intiListener() {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.finish();
                }
            });
            vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    Log.i("position", position + "");
                    switch (position) {
                        case 0:
                            btnLawyers.setChecked(true);
                            tvSelectRanking.setVisibility(View.VISIBLE);
                            tvFrimSelectRanking.setVisibility(View.GONE);
                            break;
                        case 1:
                            btnFrim.setChecked(true);
                            tvSelectRanking.setVisibility(View.GONE);
                            tvFrimSelectRanking.setVisibility(View.VISIBLE);
                            break;
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            rgLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.btn_lawyer:
                            vpContent.setCurrentItem(0);
                            setImageVIewEn(0);
                            break;
                        case R.id.btn_firm:
                            vpContent.setCurrentItem(1);
                            setImageVIewEn(1);
                            break;
                    }
                }
            });
            setImageVIewEn(0);//默认选择律所
            ivLawyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnLawyers.setChecked(true);
                    setImageVIewEn(0);

                }
            });
            ivFirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnFrim.setChecked(true);
                    setImageVIewEn(1);
                }
            });
            tvSelectRanking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RankingSelectDialog rankingSelectDialog = new RankingSelectDialog(mActivity, tvSelectRanking.getText().toString(), new RankingSelectDialog.OnSelectRankingListener() {
                        @Override
                        public void onSelectRanking(String ranking) {
                            tvSelectRanking.setText(ranking);
                            rankingLawyer.selectRankingType(ranking);
                        }
                    });
                    rankingSelectDialog.show();
                }
            });
            tvFrimSelectRanking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RankingSelectDialog rankingSelectDialog2 = new RankingSelectDialog(mActivity, tvFrimSelectRanking.getText().toString(), new RankingSelectDialog.OnSelectRankingListener() {
                        @Override
                        public void onSelectRanking(String ranking) {
                            tvFrimSelectRanking.setText(ranking);
                            rankingFiam.setDay(ranking);
                        }
                    });
                    rankingSelectDialog2.show();
                }
            });

        }


        /**
         * 启用指定的图标
         *
         * @param position
         */
        public void setImageVIewEn(int position) {
            switch (position) {
                case 0:
                    ivFirm.setSelected(false);
                    ivLawyer.setSelected(true);
                    break;
                case 1:
                    ivFirm.setSelected(true);
                    ivLawyer.setSelected(false);
                    break;

            }
        }
    }

}
