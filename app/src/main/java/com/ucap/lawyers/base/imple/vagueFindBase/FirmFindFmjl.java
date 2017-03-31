package com.ucap.lawyers.base.imple.vagueFindBase;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.vagueFind.FirmFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/10/27.
 * 负面指数
 */

public class FirmFindFmjl extends PagerBase {
    @Bind(R.id.lv_firm_content)
    ListView lvContent;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    ArrayList<FirmFindData.FmjlrowBean> mDataList;
    public ContentAdapter adapter;

    public FirmFindFmjl(Activity mActivity, ArrayList<FirmFindData.FmjlrowBean> listFmjlrow) {
        super(mActivity);
        this.mDataList = listFmjlrow;
        setDate();
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.base_firm_honor, null);
        ButterKnife.bind(this, view);
        flContent.addView(view);
        llLoading.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setDate() {
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirmFindData.FmjlrowBean rowsBean = adapter.getItem(position);
                JumpDetailedTools.jumpFirmFmjlDetailed(mActivity, rowsBean);//跳转到律所详细页面
            }
        });
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public FirmFindData.FmjlrowBean getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_firm_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            FirmFindData.FmjlrowBean item = getItem(position);
            holder.tvFirmName.setText(item.getName());
            holder.tvFirmCertificatesNumber.setText(item.getGroupnum());
            holder.tvFirmLawyerName.setText(item.getTypesettings());
            holder.tvFirmTelephone.setText(item.getPhone());
            holder.tvFirmAddress.setText(item.getAddress());
            holder.tvFirmNegative.setText(item.getFmzs());
            holder.tvFirmSpecs.setText(item.getGfzs());
            holder.tvFirmHonor.setText(item.getRyzs());
            holder.tvFirmPublicWelfare.setText(item.getGyzs());
            holder.tvFirmAssess.setText(item.getYearcheck());
            holder.tvShowName.setText(item.getShowname());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_firm_name)
        TextView tvFirmName;//律所名称
        @Bind(R.id.tv_firm_certificates_number)
        TextView tvFirmCertificatesNumber;//律所许可证号
        @Bind(R.id.tv_firm_lawyer_name)
        TextView tvFirmLawyerName;//律所负责人
        @Bind(R.id.tv_firm_telephone)
        TextView tvFirmTelephone;//律所电话
        @Bind(R.id.tv_firm_address)
        TextView tvFirmAddress;//律所地
        @Bind(R.id.tv_firm_negative)
        TextView tvFirmNegative;//负面指数
        @Bind(R.id.tv_firm_specs)
        TextView tvFirmSpecs;//规范指数
        @Bind(R.id.tv_firm_honor)
        TextView tvFirmHonor;//荣誉表彰
        @Bind(R.id.tv_firm_public_welfare)
        TextView tvFirmPublicWelfare;//公益服务
        @Bind(R.id.tv_firm_assess)
        TextView tvFirmAssess;//年度考核
        @Bind(R.id.tv_showname)
        TextView tvShowName;//组织形式

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
