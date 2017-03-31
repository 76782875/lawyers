package com.ucap.lawyers.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ucap.lawyers.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 闪屏页面,"欢迎您"从地步往上移动,这个页面慢慢放大透明。
 * 该activity是个透明页面
 */
public class SplashActivity extends Activity {

    @Bind(R.id.iv_image1)
    ImageView ivImage1;

    @Bind(R.id.iv_image4)
    ImageView ivImage4;

    @Bind(R.id.ll_root)
    LinearLayout llRoot;
    @Bind(R.id.ll_layout)
    LinearLayout llLayout;
    private AnimationSet animationSet;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (animationSet != null)
                llLayout.startAnimation(animationSet);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //去掉状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        final TranslateAnimation translateAnimation1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0
                , Animation.RELATIVE_TO_SELF, 0
                , Animation.RELATIVE_TO_SELF, 600
                , Animation.RELATIVE_TO_SELF, 0);
        translateAnimation1.setDuration(1000);
        ScaleAnimation scaleAnimation = new ScaleAnimation(5, 1, 5, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llRoot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        llRoot.startAnimation(scaleAnimation);
        ivImage4.startAnimation(translateAnimation1);

        translateAnimation1.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationSet = new AnimationSet(true);
                //透明
                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
                alphaAnimation.setDuration(1000);
                alphaAnimation.setFillAfter(true);
                //缩放
                ScaleAnimation scaleAnimation = new ScaleAnimation(1, 5, 1, 5, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(1000);
                scaleAnimation.setFillAfter(true);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(alphaAnimation);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                handler.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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
