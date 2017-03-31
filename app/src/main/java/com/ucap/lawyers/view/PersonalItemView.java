package com.ucap.lawyers.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/23.
 * 登录角色选择的条目
 */
public class PersonalItemView extends RelativeLayout {
    @Bind(R.id.ll_personal_item_root)
    RelativeLayout llRoot;
    @Bind(R.id.iv_personal_photo)
    ImageView ivPhoto;
    @Bind(R.id.tv_personal_title)
    TextView tvTitle;

    public PersonalItemView(Context context) {
        super(context);
        intiView();
    }

    public PersonalItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiView();
    }

    public PersonalItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView();
    }

    public void intiView() {
        View view = View.inflate(getContext(), R.layout.item_personal_user, null);
        ButterKnife.bind(this, view);
        this.addView(view);
        llRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick();
            }
        });
    }

    /***
     * 设置头像
     *
     * @param resId
     */
    public void setIvPhoto(int resId) {
        ivPhoto.setImageResource(resId);
    }

    /**
     * 设置标题的颜色
     *
     * @param color
     */
    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setIvPhoto(Bitmap bitmap) {
        ivPhoto.setImageBitmap(bitmap);
    }

    /**
     * 设置显示的title
     *
     * @param title
     */
    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

    /***
     * 设置item的点击事件
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    /***
     * 条目点击回调
     */
    public interface OnItemClickListener {
        /***
         * 点击条目是回调
         */
        void onItemClick();
    }

    /**
     * 设置是否启用
     */

    public void setEnabled(boolean state) {
        llRoot.setEnabled(state);
        if (!state) {
            tvTitle.setTextColor(Color.GRAY);
        }
    }
}
