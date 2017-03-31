package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ucap.lawyers.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2017/2/10.
 */

public class SelectDialog extends BaseDialog {
    @Bind(R.id.lv_content)
    ListView lvContent;//类容
    ArrayList<String> mData;
    private OnItemClickGetContentListener onItemClickGetContentListener;
    public MyAdapter adapter;

    protected SelectDialog(Context context, ArrayList<String> mData, OnItemClickGetContentListener onItemClickGetContentListener) {
        super(context);
        this.mData = mData;
        this.onItemClickGetContentListener = onItemClickGetContentListener;

    }

    public static void dialogShow(Context context, ArrayList<String> mData, OnItemClickGetContentListener onItemClickGetContentListener) {
        SelectDialog dialog = new SelectDialog(context, mData, onItemClickGetContentListener);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_select);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setData() {
        if (mData != null) {
            adapter = new MyAdapter(mData);
            lvContent.setAdapter(adapter);
        }
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickGetContentListener != null)
                    onItemClickGetContentListener.onIteClickGetContent(adapter.getItem(position));
                dismiss();
            }
        });
    }

    /**
     * 点击条目回去数据
     */
    public interface OnItemClickGetContentListener {
        /**
         * @param content 点击获取的数据
         */
        void onIteClickGetContent(String content);
    }

    @Override
    public void setClick(View v) {

    }

    class MyAdapter extends BaseAdapter {
        ArrayList<String> mData;

        MyAdapter(ArrayList<String> mData) {
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
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
                convertView = View.inflate(parent.getContext(), R.layout.item_dialog_select, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String item = getItem(position);
            holder.tvContent.setText(item);
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
