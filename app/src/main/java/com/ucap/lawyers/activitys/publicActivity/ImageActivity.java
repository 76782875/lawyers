package com.ucap.lawyers.activitys.publicActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.ucap.lawyers.fragment.ImageItemFragment;
import com.umeng.analytics.MobclickAgent;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片浏览页面
 */
public class ImageActivity extends FragmentActivity {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.vp_content)
    ViewPager vpContent;
    @Bind(R.id.tv_index)
    TextView tvIndex;
    public ArrayList<String> uris;
    private ArrayList<ImageItemFragment> fragments;
    public int position;
    public ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        uris = intent.getStringArrayListExtra("uris");
        position = intent.getIntExtra("position", 0);
        intiData();
    }

    private void intiData() {
        fragments = new ArrayList<>();
        for (String uri : uris) {
            ImageItemFragment fragment = new ImageItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("uri", uri);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        adapter = new ContentAdapter(getSupportFragmentManager());
        vpContent.setAdapter(adapter);
        tvIndex.setText(position + 1 + "/" + uris.size());
        vpContent.setCurrentItem(position);
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndex.setText(position + 1 + "/" + uris.size());
                for (int i = 0; i < uris.size(); i++) {
                    if (i != position) {
                        fragments.get(position).update();//刷新图片控件
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class ContentAdapter extends FragmentPagerAdapter {
        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return uris.size();
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
