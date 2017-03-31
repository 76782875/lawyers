package com.ucap.lawyers.base.imple.vagueFindBase;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/10/27.
 * 律师模糊查询
 */

public class LawyersFindRybz extends PagerBase {

    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.ll_loading)
    LinearLayout lLoading;
    ArrayList<LawyersFindData.RybzrowsBean> mListDate;

    public ContentAdapter adapter;

    public LawyersFindRybz(Activity mActivity, ArrayList<LawyersFindData.RybzrowsBean> mListData) {
        super(mActivity);
        this.mListDate = mListData;
        setDate();
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.base_lawyer_honor, null);
        ButterKnife.bind(this, view);
        flContent.addView(view);
        lLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDate() {
        Log.i("LawyersFindFmjl", mListDate.size() + "----Fmjl");
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
        adapter.onSetItemClick(new OnsetItemClick() {
            @Override
            public void onLvItemClick(int position) {
                LawyersFindData.RybzrowsBean rowsBean = mListDate.get(position);
                JumpDetailedTools.jumpLawyerGybzDetailed(mActivity, rowsBean);
            }

            @Override
            public void onCollection(int position, boolean state) {

            }
        });
    }

    class ContentAdapter extends BaseAdapter {
        private OnsetItemClick onsetItemClick;

        @Override
        public int getCount() {
            return mListDate.size();
        }

        @Override
        public LawyersFindData.RybzrowsBean getItem(int position) {
            return mListDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_lawyer_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawyersFindData.RybzrowsBean item = getItem(position);
            holder.tvName.setText(item.getFirstname());
            holder.tvCertificates.setText(item.getContactid());
            holder.tvSex.setText(item.getSex());
            holder.tvOffice.setText(item.getName());
            holder.tvNegative.setText(item.getFmzs());
            holder.tvCommend.setText(item.getRyzs());
            holder.tvPublicWelfare.setText(item.getGyzs());
            holder.tvAssessment.setText(item.getYearcheck());
            holder.llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onsetItemClick != null)
                        onsetItemClick.onLvItemClick(position);
                }
            });
            holder.btnCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onsetItemClick != null) {
                        holder.btnCollection.setSelected(!holder.btnCollection.isSelected());
                        onsetItemClick.onCollection(position, holder.btnCollection.isSelected());
                    }
                }
            });
            return convertView;
        }


        public void onSetItemClick(OnsetItemClick onsetItemClick) {
            this.onsetItemClick = onsetItemClick;

        }
    }

    public interface OnsetItemClick {
        /**
         * 点击单条
         *
         * @param position
         */
        void onLvItemClick(int position);

        /**
         * 点击收藏
         *
         * @param position
         */
        void onCollection(int position, boolean state);
    }

    class ViewHolder {
        @Bind(R.id.ll_lawyer_root)
        LinearLayout llRoot;
        @Bind(R.id.btn_lawyer_collection)
        Button btnCollection;//收藏
        @Bind(R.id.tv_lawyer_name)
        TextView tvName;//律师名称
        @Bind(R.id.tv_lawyer_certificates)
        TextView tvCertificates;//执业证号
        @Bind(R.id.tv_lawyer_sex)
        TextView tvSex;//性别
        @Bind(R.id.tv_lawyer_office)
        TextView tvOffice;//所在律所
        @Bind(R.id.tv_lawyer_negative)
        TextView tvNegative;// 负面指数
        @Bind(R.id.tv_lawyer_commend)
        TextView tvCommend;//荣誉指数
        @Bind(R.id.tv_lawyer_public_welfare)
        TextView tvPublicWelfare;//公益服务
        @Bind(R.id.tv_lawyer_assessment)
        TextView tvAssessment;//年度考核

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

}
