package com.ucap.lawyers.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ucap.lawyers.base.PagerBase;

import java.util.ArrayList;

/**
 * Created by wekingwang on 16/9/2.
 */
public class ViewPagerAdapter extends PagerAdapter {
    ArrayList<PagerBase> mPage;

    public ViewPagerAdapter(ArrayList<PagerBase> mPage) {
        this.mPage = mPage;
    }

    @Override
    public int getCount() {
        return mPage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PagerBase basePager = mPage.get(position);
        container.addView(basePager.mViewLayout);
        return basePager.mViewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
