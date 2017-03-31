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
import com.ucap.lawyers.bean.vagueFind.FirmFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/11/9.
 */

public class FirmSeleteDialog extends BaseDialog {
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lv_content)
    ListView lvContent;
    List<FirmFindData.RybzrowBean> data;
    public ContentAdapter adapter;

    protected FirmSeleteDialog(Context context, List<FirmFindData.RybzrowBean> data) {
        super(context);
        this.data = data;
    }

    public static void showDialog(Context ctx, List<FirmFindData.RybzrowBean> data) {
        FirmSeleteDialog dialog = new FirmSeleteDialog(ctx, data);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_selecte_firm_lawyer);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirmFindData.RybzrowBean item = adapter.getItem(position);
                JumpDetailedTools.jumpFirmRybzDetailed(getContext(), item);
            }
        });
    }

    @Override
    public void setData() {
        tvTitle.setText("选择律所");
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
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
            return data.size();
        }

        @Override
        public FirmFindData.RybzrowBean getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_dialog_location_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            FirmFindData.RybzrowBean item = getItem(position);
            holder.tvName.setText(item.getName());
            holder.tvGroupnum.setText(item.getGroupnum());
            holder.tvTypesettings.setText(item.getTypesettings());
            holder.tvAddress.setText(item.getAddress());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;//律所名称
        @Bind(R.id.tv_groupnum)
        TextView tvGroupnum;//律所证号
        @Bind(R.id.tv_typesettings)
        TextView tvTypesettings;//律所负责人姓名
        @Bind(R.id.tv_address)
        TextView tvAddress;//律所地址

        ViewHolder(View view) {
//            @Bind(R.id.)
            ButterKnife.bind(this, view);
        }
    }
}
