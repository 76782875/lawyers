package com.ucap.lawyers.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.PublicData;
import com.ucap.lawyers.tools.PrefUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by wekingwang on 2016/12/1.
 */

public class FileListDialog extends BaseDialog {

    @Bind(R.id.iv_dismiss)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.tv_file_null)
    TextView tvFileNull;
    ArrayList<File> mListData;
    public ContentAdapter adapter;

    protected FileListDialog(Context context) {
        super(context);
    }

    public static void showDialog(Context ctx) {
        FileListDialog listDialog = new FileListDialog(ctx);
        listDialog.setCancelable(false);
        listDialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_file_list);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        lvContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                DeleteDialogView.showDialog(getContext(), new DeleteDialogView.OnDetermineListener() {
                    @Override
                    public void onDetermine() {
                        File file = adapter.getItem(position);
                        if (file.exists()) {
                            file.delete();//从本地删除
                            mListData.remove(position);
                            Toast.makeText(getContext(), "已删除", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            dataIsNull();
                            PrefUtils.remove(file.getName(), getContext());
                        }
                    }
                });
                return true;
            }
        });
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openFile(adapter.getItem(position));
            }
        });
    }

    /**
     * 打开文件
     *
     * @param item
     */
    private void openFile(File item) {
        String filePath = PrefUtils.getString(item.getName(), "文件地址丢失", getContext());
        String substring = filePath.substring("http://".length(), filePath.length());
        Log.i("filePath", "处理后地址：" + substring);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + substring));
//        intent.setData(Uri.parse("https://docs.google.com/gview?embedded=true&url=" + substring));
        getContext().startActivity(intent);
    }

    @Override
    public void setData() {
        mListData = new ArrayList<>();
        File path = new File("sdcard/律师系统附件/");
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                mListData.add(file);
                Log.i("fileListName", file.getPath());
            }
        }
        dataIsNull();
        adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);
    }

    /**
     * 判断是否有数据
     */
    public void dataIsNull() {
        if (mListData.size() > 0) {
            tvFileNull.setVisibility(View.GONE);
        } else {
            tvFileNull.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setClick(View v) {
        if (v == ivBack) {
            dismiss();
        }
    }

    class ContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public File getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_file_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            File item = getItem(position);
            holder.tvName.setText(item.getName());
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_file_name)
        TextView tvName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
