package com.ucap.lawyers.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.resource.file.FileResource;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ucap.lawyers.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/11/7.
 */


public class UpdateLoadingDialog extends BaseDialog {
    @Bind(R.id.btn_out)
    Button btnOut;
    @Bind(R.id.btn_update)
    Button btnUpdate;
    @Bind(R.id.tv_warning)
    TextView tvWarning;
    @Bind(R.id.ll_update_content)
    ScrollView llUpdateContent;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_version_name)
    TextView tvVersionName;//新版本名称
    @Bind(R.id.tv_update_content)
    TextView tvUpdateContent;//新版本说明

    int versionCode;
    String versionName;
    String updateContent;
    String versionPath;
    Activity activity;
    public static String pathUri;
    public static HttpUtils utils;

    protected UpdateLoadingDialog(Context context, Activity activity, int versionCode, String versionName, String updateContent, String versionPath) {
        super(context);
        this.versionCode = versionCode;
        this.activity = activity;
        this.versionName = versionName;
        this.updateContent = updateContent;
        this.versionPath = versionPath;
    }

    public static void showDialog(Context context, Activity activity, int versionCode, String versionName, String updateContent, String versionPath) {
        UpdateLoadingDialog dialog = new UpdateLoadingDialog(context, activity, versionCode, versionName, updateContent, versionPath);
        dialog.setCancelable(false);
        dialog.show();
        pathUri = Environment.getExternalStorageDirectory().getPath() + "/lawyerAppForAndroid" + versionCode + ".apk";
        utils = new HttpUtils();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_update_loading);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        btnOut.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void setData() {
        tvVersionName.setText("新版本：" + versionName);
        tvUpdateContent.setText(updateContent);
    }

    @Override
    public void setClick(View v) {
        if (v == btnOut) {
            activity.finish();
        } else if (v == btnUpdate) {
            tvTitle.setText("新版本");
            btnUpdate.setText("正在更新...");
            tvWarning.setVisibility(View.GONE);
            llUpdateContent.setVisibility(View.VISIBLE);
            btnUpdate.setEnabled(false);
            update();
        }
    }

    /**
     * 下载更新包
     */
    private void update() {
        Log.i("update", versionPath);
        utils.download(versionPath, pathUri, true, true, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                appStart(responseInfo.result.getPath());
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("update", msg);
                Log.i("update", pathUri);
                if (new File(pathUri).exists()) {
                    appStart(pathUri);
                } else {
                    btnUpdate.setText("下载失败！");
                }
            }
        });
    }

    /**
     * 安装app
     *
     * @param uri
     */
    public void appStart(String uri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
/* 调用getMIMEType()来取得MimeType */
        String type = "application/vnd.android.package-archive";
/* 设置intent的file与MimeType */
        intent.setDataAndType(Uri.parse("file://" + uri), type);
        getContext().startActivity(intent);
    }

}
