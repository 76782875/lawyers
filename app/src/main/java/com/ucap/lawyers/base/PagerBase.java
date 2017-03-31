package com.ucap.lawyers.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.ucap.lawyers.R;


/**
 * Created by wekingwang on 16/8/12.
 */
public class PagerBase {
    public Activity mActivity;
    public FrameLayout flContent;
    public View mViewLayout;

    public PagerBase(Activity mActivity) {
        this.mActivity = mActivity;
        mViewLayout = intiView();
        intiDate();
        intiListener();

    }

    /**
     * 初始化控件
     */
    public View intiView() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        flContent = (FrameLayout) view.findViewById(R.id.fl_content);
        return view;
    }

    /**
     * 初始化数据
     */
    public void intiDate() {
    }

    /**
     * 设置监听
     */
    public void intiListener() {
    }

    /**
     * 设置数据
     */
    public void setDate() {
    }

}
