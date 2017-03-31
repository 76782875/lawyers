package com.ucap.lawyers.activitys.publicActivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ucap.lawyers.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 收藏页面
 */
public class CollectionActivity extends AppCompatActivity {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.rv_collection_list)
    RecyclerView rvContentList;
    ArrayList<String> mListDate;
    @Bind(R.id.tv_loading)
    TextView tvLoading;
    private ContentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_collection);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();
    }

    private void intiDate() {
        mListDate = new ArrayList<>();
        mListDate.add("");
        mListDate.add("");
        mListDate.add("");
        mListDate.add("");
        mListDate.add("");
        if (mListDate.size() > 0) {//判断收藏列表是否有内容,如果没有内容就显示提示信息
            tvLoading.setVisibility(View.INVISIBLE);
            rvContentList.setVisibility(View.VISIBLE);
        } else {
            tvLoading.setVisibility(View.VISIBLE);
            rvContentList.setVisibility(View.INVISIBLE);
        }
        rvContentList.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ContentAdapter();
        adapter.cancelCollection(new OnCancelCollectionListener() {
            @Override
            public void cancelCollection(int position) {

            }
        });
        rvContentList.setAdapter(adapter);

    }

    class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        public OnCancelCollectionListener onCancelCollectionListener;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(getApplicationContext(), R.layout.item_ordinary_collection, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.btnDeleteCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CollectionActivity.this, "item:" + position, Toast.LENGTH_SHORT).show();
                    if (getItemCount() > 0 && position < mListDate.size()) {
                        mListDate.remove(position);
                        notifyItemRemoved(position);
//                        notifyDataSetChanged();
                    } else {
                        mListDate.clear();
                        rvContentList.setVisibility(View.INVISIBLE);
                        tvLoading.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mListDate.size();
        }

        /**
         * 取消收藏
         *
         * @param onCancelCollectionListener
         */
        public void cancelCollection(OnCancelCollectionListener onCancelCollectionListener) {
            this.onCancelCollectionListener = onCancelCollectionListener;
        }
    }

    /**
     * 取消收藏回调
     */
    interface OnCancelCollectionListener {
        void cancelCollection(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_photo)
        ImageView ivPhoto;
        @Bind(R.id.btn_delete_collection)
        Button btnDeleteCollection;
        @Bind(R.id.tv_name)
        TextView tvName;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        //        友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
