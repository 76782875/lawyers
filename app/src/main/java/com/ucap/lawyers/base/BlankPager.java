package com.ucap.lawyers.base;

import android.app.Activity;
import android.view.View;

import com.ucap.lawyers.R;

/**
 * Created by wekingwang on 2016/12/5.
 */

public class BlankPager extends PagerBase {
    public BlankPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.blank_pager, null);
        flContent.addView(view);
    }

    @Override
    public void intiListener() {

    }
}
