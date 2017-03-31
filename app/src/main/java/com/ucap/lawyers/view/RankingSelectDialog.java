package com.ucap.lawyers.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/12/5.
 */

public class RankingSelectDialog extends BaseDialog {
    @Bind(R.id.tv_total)
    TextView tvTotal;//总排行
    @Bind(R.id.tv_week)
    TextView tvWeek;//周排行
    @Bind(R.id.tv_month)
    TextView tvMonth;//月排行

    String isRanking;

    public RankingSelectDialog(Context context, String isRanking, OnSelectRankingListener onSelectRankingListener) {
        super(context);
        this.onSelectRankingListener = onSelectRankingListener;
        this.isRanking = isRanking;
    }

//    public static void showDialog(Context ctx, String isRanking, OnSelectRankingListener onSelectRankingListener) {
//        RankingSelectDialog dialog = new RankingSelectDialog(ctx, isRanking, onSelectRankingListener);
//        dialog.show();
//    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_select_ranking);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        tvTotal.setOnClickListener(this);
        tvWeek.setOnClickListener(this);
        tvMonth.setOnClickListener(this);

    }

    @Override
    public void setData() {
        if ("总排行榜".equals(isRanking)) {
            tvTotal.setTextColor(Color.GRAY);
            tvTotal.setEnabled(false);
            tvWeek.setTextColor(Color.BLACK);
            tvWeek.setEnabled(true);
            tvMonth.setTextColor(Color.BLACK);
            tvMonth.setEnabled(true);
        } else if ("周排行榜".equals(isRanking)) {
            tvTotal.setTextColor(Color.BLACK);
            tvTotal.setEnabled(true);
            tvWeek.setTextColor(Color.GRAY);
            tvWeek.setEnabled(false);
            tvMonth.setTextColor(Color.BLACK);
            tvMonth.setEnabled(true);
        } else if ("月排行榜".equals(isRanking)) {
            tvTotal.setTextColor(Color.BLACK);
            tvTotal.setEnabled(true);
            tvWeek.setTextColor(Color.BLACK);
            tvWeek.setEnabled(true);
            tvMonth.setTextColor(Color.GRAY);
            tvMonth.setEnabled(false);
        }
    }

    @Override
    public void setClick(View v) {
        if (v == tvTotal) {
            onSelectRankingListener.onSelectRanking("总排行榜");
            setData();
        } else if (v == tvWeek) {
            onSelectRankingListener.onSelectRanking("周排行榜");
            setData();
        } else if (v == tvMonth) {
            onSelectRankingListener.onSelectRanking("月排行榜");
            setData();
        }
        dismiss();
    }

    private OnSelectRankingListener onSelectRankingListener;

    /**
     * 选择排行榜回调
     */
    public interface OnSelectRankingListener {
        void onSelectRanking(String ranking);
    }
}
