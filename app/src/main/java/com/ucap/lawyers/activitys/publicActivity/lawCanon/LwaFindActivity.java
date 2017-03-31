package com.ucap.lawyers.activitys.publicActivity.lawCanon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.law.LawBrowseFind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LwaFindActivity extends AppCompatActivity {
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.lv_content)
    ListView lvContent;//内容
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;//稍等
    @Bind(R.id.et_firm_name)
    EditText etFindText;//查询条件数据
    @Bind(R.id.tv_is_null)
    TextView tvIsNull;
    public RequestQueue requestQueue;
    public ArrayList<LawBrowseFind.RowBean> mData;
    public ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lwa_find);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(this);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiData();
        intiListener();

    }

    private void intiListener() {
        etFindText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    findDate(s.toString());
                } else {
                    mData.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LawBrowseFind.RowBean item = adapter.getItem(position);
                Intent intent = new Intent(LwaFindActivity.this, LawCanonDetailedActivity.class);
                intent.putExtra("itemId", item.getId());
                intent.putExtra("title", item.getName());
                startActivity(intent);
            }
        });
    }

    private void intiData() {
        mData = new ArrayList<>();
        adapter = new ContentAdapter(mData);
        lvContent.setAdapter(adapter);
    }

    /**
     * 从服务器查询数据
     *
     * @param name
     */
    private void findDate(final String name) {
        mData.clear();
        pbLoading.setVisibility(View.VISIBLE);
        tvIsNull.setVisibility(View.INVISIBLE);
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.LAW_BROWSE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    gsonData(response);
                } else {
                    pbLoading.setVisibility(View.INVISIBLE);
                    tvIsNull.setText("未查询到任何信息！");
                    tvIsNull.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbLoading.setVisibility(View.INVISIBLE);
                tvIsNull.setVisibility(View.VISIBLE);
                tvIsNull.setText("链接失败！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析数据
     *
     * @param data
     */
    public void gsonData(String data) {
        Gson gson = new Gson();
        LawBrowseFind lawBrowseFind = gson.fromJson(data, LawBrowseFind.class);
        if (lawBrowseFind != null) {
            List<LawBrowseFind.RowBean> row = lawBrowseFind.getRow();
            if (row != null && row.size() > 0) {

                mData.addAll(row);
                adapter.notifyDataSetChanged();
                pbLoading.setVisibility(View.GONE);
                tvIsNull.setVisibility(View.INVISIBLE);
            } else {
                pbLoading.setVisibility(View.INVISIBLE);
                tvIsNull.setText("未查询到任何信息！");
                tvIsNull.setVisibility(View.VISIBLE);
            }
        }
    }

    class ContentAdapter extends BaseAdapter {
        ArrayList<LawBrowseFind.RowBean> mData;

        public ContentAdapter(ArrayList<LawBrowseFind.RowBean> mData) {
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public LawBrowseFind.RowBean getItem(int position) {
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
                convertView = View.inflate(parent.getContext(), R.layout.item_lwa_find, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            LawBrowseFind.RowBean item = getItem(position);
            holder.tvTile.setText(item.getName());
            holder.tvType.setText("分类：" + item.getType());
            holder.tvProvider.setText("提供者：" + item.getGroupname() + "  " + item.getFirstname());
            holder.tvProviderSize.setText("累计提供：(" + item.getSize() + ")条");
            return convertView;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTile;//标题
        @Bind(R.id.tv_type)
        TextView tvType;//分类
        @Bind(R.id.tv_provider)
        TextView tvProvider;//提供者
        @Bind(R.id.tv_provider_size)
        TextView tvProviderSize;//累计提供

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
