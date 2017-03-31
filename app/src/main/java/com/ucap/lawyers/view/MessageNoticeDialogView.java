package com.ucap.lawyers.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaCodec;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.userLogin.userLawyeActivitys.LawyerMessageNoticeActivity;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerMessageNotice;
import com.ucap.lawyers.tools.PrefUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/11/1.
 * 信息公告，详细内容弹窗
 */

public class MessageNoticeDialogView extends BaseDialog {
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_message)
    TextView tvMessage;//发布内容
    @Bind(R.id.ll_files)
    LinearLayout llFiles;//附件列表
    HashMap<String, Object> data;
    @Bind(R.id.tv_file_null)
    TextView tvFileNull;//附件存储位置提示
    public static HashMap<String, String> filePaths = new HashMap<>();

    public MessageNoticeDialogView(Context context, HashMap<String, Object> data) {
        super(context);
        this.data = data;
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_message_notice);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(this);
    }

    @Override
    public void setData() {
        String id = (String) data.get("id");
        String massgeState = PrefUtils.getString("messageNoticeState", "", getContext());
        if (massgeState.indexOf(id) == -1) {
            PrefUtils.putString("messageNoticeState", id + "," + massgeState, getContext());//保存当前阅读的id
        }
        Log.i("massageId", PrefUtils.getString("messageNoticeState", "", getContext()));
        String optname = (String) data.get("optname");//发布者名称
        String pubdata = (String) data.get("pubdata");//发布时间
        String subject = (String) data.get("subject");//发布内容
        final HttpUtils utils = new HttpUtils();
        final String filePath = "sdcard/律师系统附件/";
        List<LawyerMessageNotice.RowsBean.PathobjsBean> pathobjs = (List<LawyerMessageNotice.RowsBean.PathobjsBean>) data.get("pathobjs");//附件详细
        tvTitle.setText(optname + " 发布于 " + pubdata);
        tvMessage.setText(subject);
        for (final LawyerMessageNotice.RowsBean.PathobjsBean bean : pathobjs) {
            String imgname = bean.getImgname();//如果名称等于空就没有附件
            if (imgname != null) {

                PrefUtils.putString(imgname, bean.getPath(), getContext());//以为文件名为key保存改文件下载地址到本地，用于在线浏览
                tvFileNull.setVisibility(View.VISIBLE);
                View view = View.inflate(getContext(), R.layout.item_message_notice_dialog_files, null);
                TextView tvFileName = (TextView) view.findViewById(R.id.tv_file_name);
                tvFileName.setText(imgname);
                final TextView tvDownload = (TextView) view.findViewById(R.id.tv_download);
                final String path = filePath + bean.getImgname();
                final File file = new File(path);
                if (file.exists()) {//判读是否已经下载附件
                    tvDownload.setText("已下载");
                    tvDownload.setEnabled(false);
                } else {
                    tvDownload.setEnabled(true);
                    tvDownload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            utils.download(bean.getPath(), path, true, false, new RequestCallBack<File>() {
                                @Override
                                public void onSuccess(ResponseInfo<File> responseInfo) {
                                    tvDownload.setText("已下载");
                                    tvDownload.setEnabled(false);
                                }

                                @Override
                                public void onStart() {
                                    tvDownload.setEnabled(false);
                                    tvDownload.setText("下载中..");
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    if (file.exists()) {
                                        tvDownload.setText("已下载");
                                        tvDownload.setEnabled(false);
                                    } else {
                                        tvDownload.setEnabled(true);
                                        tvDownload.setText("重新下载!");
                                    }
                                }
                            });
                        }
                    });
                }
                llFiles.addView(view);
            }
        }
    }


    @Override
    public void setClick(View v) {
        if (v == ivDismiss) {
            dismiss();
        }
    }
}
