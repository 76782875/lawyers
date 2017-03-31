package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/12/9.
 * 已答问题分类选择
 */

public class AnswerClassifySelectDialog extends BaseDialog {
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;

    @Bind(R.id.tv_213)
    TextView tv_213;

    @Bind(R.id.tv_214)
    TextView tv_214;

    @Bind(R.id.tv_215)
    TextView tv_215;

    @Bind(R.id.tv_216)
    TextView tv_216;

    @Bind(R.id.tv_217)
    TextView tv_217;

    @Bind(R.id.tv_218)
    TextView tv_218;

    @Bind(R.id.tv_219)
    TextView tv_219;

    @Bind(R.id.tv_220)
    TextView tv_220;

    protected AnswerClassifySelectDialog(Context context, OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
    }

    public static void showDialog(Context ctx, OnSelectListener onSelectListener) {
        AnswerClassifySelectDialog dialog = new AnswerClassifySelectDialog(ctx, onSelectListener);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_answer_classify_select);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        tv_213.setOnClickListener(this);
        tv_214.setOnClickListener(this);
        tv_215.setOnClickListener(this);
        tv_216.setOnClickListener(this);
        tv_217.setOnClickListener(this);
        tv_218.setOnClickListener(this);
        tv_219.setOnClickListener(this);
        tv_220.setOnClickListener(this);
        ivDismiss.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void setClick(View v) {
        if (v == tv_213) {
            if (onSelectListener != null) {
                onSelectListener.onSelect("213");
            }
        } else if (v == tv_214) {
            if (onSelectListener != null) {
                onSelectListener.onSelect("214");
            }
        } else if (v == tv_215) {
            if (onSelectListener != null) {
                onSelectListener.onSelect("215");
            }
        } else if (v == tv_216) {
            if (onSelectListener != null) {
                onSelectListener.onSelect("216");
            }
        } else if (v == tv_217) {
            if (onSelectListener != null) {
                onSelectListener.onSelect("217");
            }
        } else if (v == tv_218) {
            if (onSelectListener != null) {
                onSelectListener.onSelect("218");
            }
        } else if (v == tv_219) {
            if (onSelectListener != null) {
                onSelectListener.onSelect("219");
            }
        } else if (v == tv_220) {
            if (onSelectListener != null) {
                onSelectListener.onSelect("220");
            }
        } else if (ivDismiss == v) {
            dismiss();
        }

    }

    private OnSelectListener onSelectListener;

    public interface OnSelectListener {
        /**
         * 分类对于id
         * 213	刑事法务
         * 214	行政法务
         * 215	经济纠纷
         * 216	婚姻家庭
         * 217	金融保险
         * 218	社保劳资
         * 219	交通医疗
         * 220	其他法务
         *
         * @param id
         */
        void onSelect(String id);
    }
}
