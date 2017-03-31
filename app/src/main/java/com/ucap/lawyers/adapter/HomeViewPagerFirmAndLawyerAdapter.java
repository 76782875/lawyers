package com.ucap.lawyers.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ucap.lawyers.base.PagerBase;

import java.util.ArrayList;

/**
 * Created by wekingwang on 16/9/2.
 * 主页律所和律师viewPager使用的适配器
 */
public class HomeViewPagerFirmAndLawyerAdapter extends PagerAdapter {
    Context ctx;
    ArrayList<PagerBase> mListDate;
    String[] title;

    public HomeViewPagerFirmAndLawyerAdapter(Context ctx, ArrayList<PagerBase> mListDate, String[] title) {
        this.ctx = ctx;
        this.mListDate = mListDate;
        this.title = title;
    }

    @Override
    public int getCount() {
        return mListDate.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PagerBase basePager = mListDate.get(position);
        container.addView(basePager.mViewLayout);
        return basePager.mViewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
