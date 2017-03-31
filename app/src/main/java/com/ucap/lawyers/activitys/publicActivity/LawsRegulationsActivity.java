package com.ucap.lawyers.activitys.publicActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucap.lawyers.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 法律法规详细页面
 */
public class LawsRegulationsActivity extends AppCompatActivity {

    @Bind(R.id.iv_user_back)
    ImageView ivUserBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.web_content)
    WebView webContent;
    ArrayList<String> imageUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws_regulations);
        ButterKnife.bind(this);
        ivUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiDate();
    }

    private void intiDate() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Log.i("contentUri", content);
        tvTitle.setText(title);
        WebSettings setting = webContent.getSettings();
        webContent.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        setting.setJavaScriptEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        setting.setRenderPriority(WebSettings.RenderPriority.HIGH); //提高渲染速度
//        setting.setUseWideViewPort(true);//关键点
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setLoadWithOverviewMode(true);
        webContent.loadUrl(content);
        webContent.setWebViewClient(new WebViewClient() {
            //网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("webView", "网页开始加载");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("webView", "网页跳转");
                view.loadUrl(url);
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webContent.loadUrl("javascript:(function(){"
                        + "var objs=document.getElementsByTagName(\"img\");"
                        + "for(var i=0;i<objs.length;i++){"
                        + "     window.imagelistner.imagsUrl(objs[i].src);"
                        + "      objs[i].onclick=function(){"
                        + "          window.imagelistner.openImage(this.src)"
                        + " }"
                        + "}"
                        + "})()");
            }
        });
    }

    //js注入接口定义,方法名对应js方法名(解析后自动调用)
    private class JavascriptInterface {
        public JavascriptInterface(Context context) {
        }

        @android.webkit.JavascriptInterface
        public void openImage(final String img) {
            Log.i("image", "img" + img);
            Intent intent = new Intent(LawsRegulationsActivity.this, ImageActivity.class);
            intent.putStringArrayListExtra("uris", imageUris);
            int position = 0;
//            //判断用户点击的第几张图片
            for (int i = 0; i < imageUris.size(); i++) {
                if (imageUris.get(i).equals(img)) {
                    position = i;
                }
            }
            intent.putExtra("position", position);
            startActivity(intent);
        }

        @android.webkit.JavascriptInterface
        public void imagsUrl(String img) {
            Log.i("image", "img-----" + img);
            if (imageUris == null) {
                imageUris = new ArrayList<>();
            }
            imageUris.add(img);
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
