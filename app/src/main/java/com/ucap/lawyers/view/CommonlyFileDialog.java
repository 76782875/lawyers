package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerMyCommonlyJournal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/12/21.
 */

public class CommonlyFileDialog extends BaseDialog {
    @Bind(R.id.lv_content)
    ListView lvContent;
    public ArrayList<LawyerMyCommonlyJournal.RowsBean.FilesBean> mData;

    public CommonlyFileDialog(Context context, List<LawyerMyCommonlyJournal.RowsBean.FilesBean> files) {
        super(context);
        mData = (ArrayList<LawyerMyCommonlyJournal.RowsBean.FilesBean>) files;
    }

    @Override
    public void setView() {
        setContentView(View.inflate(getContext(), R.layout.dialog_commonly_file, null));
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setData() {
        lvContent.setAdapter(new Adapter());

    }

    @Override
    public void setClick(View v) {

    }

    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public LawyerMyCommonlyJournal.RowsBean.FilesBean getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item_commonly_file, null);
                holder = new MyViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }
            LawyerMyCommonlyJournal.RowsBean.FilesBean item = getItem(position);
            holder.tvFileName.setText(item.getRamark());
            return convertView;
        }
    }

    class MyViewHolder {
        @Bind(R.id.tv_file_name)
        TextView tvFileName;//卷宗名称

        public MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
