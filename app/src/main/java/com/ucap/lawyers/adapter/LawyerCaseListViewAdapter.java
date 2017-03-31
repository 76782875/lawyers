package com.ucap.lawyers.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucap.lawyers.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/9/2.
 * 我的案件,适配器
 */
public class LawyerCaseListViewAdapter extends BaseAdapter {
    private ArrayList<?> mDate;
    private Context ctx;

    public LawyerCaseListViewAdapter(Context ctx, ArrayList<?> mDate) {
        this.ctx = ctx;
        this.mDate = mDate;
    }

    @Override
    public int getCount() {
        return mDate.size();
    }

    @Override
    public Object getItem(int position) {
        return mDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_lawyer_case, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_number)
        TextView tvNumber;//协议编号
        @Bind(R.id.tv_client)
        TextView tvClient;//承办律师
        @Bind(R.id.tv_lawyer_name)
        TextView tvName;//委托人
        @Bind(R.id.tv_state)
        TextView tvState;//状态

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
