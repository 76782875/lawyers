package com.ucap.lawyers.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.LawsRegulationsActivity;
import com.ucap.lawyers.bean.LawListData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/11/7.
 */

public class LawMoreDialog extends BaseDialog {
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.lv_content)
    ListView lvContent;
    ArrayList<LawListData.RowsBean> mData;

    protected LawMoreDialog(Context context, ArrayList<LawListData.RowsBean> mListDate) {
        super(context);
        this.mData = mListDate;

    }

    public static void showDialog(Context ctx, ArrayList<LawListData.RowsBean> mListDate) {
        LawMoreDialog dialog = new LawMoreDialog(ctx, mListDate);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_law_more);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
    }

    @Override
    public void setData() {
        final ContentAdapter adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LawListData.RowsBean item = adapter.getItem(position);
                Intent intent = new Intent(getContext(), LawsRegulationsActivity.class);
                intent.putExtra("title", item.getName());
                intent.putExtra("content", item.getContent());
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void setClick(View v) {
        if (v == ivDismiss) {
            dismiss();
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public LawListData.RowsBean getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_law_more, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText("《" + getItem(position).getName() + "》");
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
