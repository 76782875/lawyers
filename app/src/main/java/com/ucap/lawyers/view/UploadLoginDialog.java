package com.ucap.lawyers.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ucap.lawyers.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2017/1/16.
 */

public class UploadLoginDialog extends BaseDialog {
    @Bind(R.id.tv_loading)
    TextView tvLoading;
    ArrayList<String> name;
    ArrayList<File> files;

    public UploadLoginDialog(Context context, ArrayList<String> name, ArrayList<File> files, OnUploadIsOKListener onUploadIsOKListener) {
        super(context);
        this.name = name;
        this.files = files;
        this.onUploadIsOKListener = onUploadIsOKListener;
    }

    public UploadLoginDialog(Context context, ArrayList<String> name, ArrayList<File> files) {
        super(context);
        this.name = name;
        this.files = files;
    }

    String submitFileUri = "http://192.168.0.107:9527/scslsxt/servlet/caseuploadservlet";

    /**
     * 提交文件
     */
    public void btnSubmitFile() {
        HttpUtils utils = new HttpUtils(180000);
        final RequestParams params = new RequestParams();
        if (files != null && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                if (file != null) {
                    params.addBodyParameter(file.getName(), file, "image/jpg");
                }
            }
        }
        utils.send(HttpMethod.POST, submitFileUri, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("filesInfo", "提交成功：" + responseInfo.result);
                tvLoading.setText("100%");
                if (onUploadIsOKListener != null)
                    onUploadIsOKListener.onUploadOk(responseInfo.result);
                dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.d("filesInfo", "提交失败：" + msg);
                Toast.makeText(getContext(), "提交失败，检查网络链接再重试!", Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                Log.i("filesInfo", ((int) (((int) current / (float) total) * 100)) + "isUploading--" + isUploading);
                int number = (int) (((int) current / (float) total) * 100);
                if (number < 96) {
                    tvLoading.setText(number + "%");
                } else {
                    tvLoading.setText(96 + "%");
                }
            }
        });
    }

    private OnUploadIsOKListener onUploadIsOKListener;

    /**
     * 提交成功回调
     */
    public interface OnUploadIsOKListener {
        void onUploadOk(String filesId);
    }

    public static void showDialog(Context ctx, ArrayList<String> name, ArrayList<File> files) {
        UploadLoginDialog dialog = new UploadLoginDialog(ctx, name, files);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_uploagin);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setClick(View v) {

    }
}
