package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/10/8.
 * 首页查询出同名同姓律师选择弹窗
 */

public class LawyerSelectDialogView extends BaseDialog {

    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.lv_content)
    ListView lvContent;
    ArrayList<LawyersFindData.RybzrowsBean> mData;

    protected LawyerSelectDialogView(Context context, List<LawyersFindData.RybzrowsBean> mData) {
        super(context);
        this.mData = (ArrayList<LawyersFindData.RybzrowsBean>) mData;
    }

    public static void showDialogSelect(Context context, List<LawyersFindData.RybzrowsBean> mData) {
        LawyerSelectDialogView dialogView = new LawyerSelectDialogView(context, mData);
        dialogView.setCancelable(false);
        dialogView.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_lawyer_select);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LawyersFindData.RybzrowsBean rowsBean = mData.get(position);
                JumpDetailedTools.jumpLawyerGybzDetailed(getContext(), rowsBean);//跳转到律师详细页面
            }
        });
    }

    @Override
    public void setData() {
        lvContent.setAdapter(new ContentAdapter());
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
        public LawyersFindData.RybzrowsBean getItem(int position) {
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
                convertView = View.inflate(getContext(), R.layout.item_select_lawyer_dialog, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawyersFindData.RybzrowsBean item = getItem(position);
            holder.tvName.setText(item.getFirstname());
            holder.tvFirmName.setText(item.getName());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_firm_name)
        TextView tvFirmName;

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
