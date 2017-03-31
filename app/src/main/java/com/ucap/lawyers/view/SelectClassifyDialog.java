package com.ucap.lawyers.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ucap.lawyers.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/11/21.
 * 咨询类别选择
 * 213	刑事法务
 * 214	行政法务
 * 215	经济纠纷
 * 216	婚姻家庭
 * 217	金融保险
 * 218	社保劳资
 * 219	交通医疗
 * 220	其他法务
 */

public class SelectClassifyDialog extends BaseDialog {
    @Bind(R.id.tv_213)
    TextView tv213;

    @Bind(R.id.tv_214)
    TextView tv214;

    @Bind(R.id.tv_215)
    TextView tv215;

    @Bind(R.id.tv_216)
    TextView tv216;

    @Bind(R.id.tv_217)
    TextView tv217;

    @Bind(R.id.tv_218)
    TextView tv218;

    @Bind(R.id.tv_219)
    TextView tv219;

    @Bind(R.id.tv_220)
    TextView tv220;
    /**
     * 刑事法务
     */
    public static final String XIN_SHI_FA_WU = "213";
    /**
     * 行政法务
     */
    public static final String XING_ZHENG_FA_WU = "214";
    /**
     * 经济纠纷
     */
    public static final String JIN_JI_JIU_FENG = "215";
    /**
     * 216	婚姻家庭
     */
    public static final String HUN_YING_JIA_TING = "216";
    /**
     * 217	金融保险
     */
    public static final String JIN_RONG_BAO_XIAN = "217";
    /**
     * 218	社保劳资
     */
    public static final String SHE_BAO_NAO_ZI = "218";
    /**
     * 219	交通医疗
     */
    public static final String JIAO_TONG_YI_LIAO = "219";
    /**
     * 其他法务
     */
    public static final String QI_TA_FA_WU = "220";

    protected SelectClassifyDialog(Context context, OnSelectClassifyListener onSelectClassifyListener) {
        super(context);
        this.onSelectClassifyListener = onSelectClassifyListener;
    }

    public static void showDialog(Context ctx, OnSelectClassifyListener onSelectClassifyListener) {
        SelectClassifyDialog dialog = new SelectClassifyDialog(ctx, onSelectClassifyListener);
        dialog.show();
    }

    @Override
    public void setView() {
        setContentView(R.layout.dialog_select_classify);
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        tv213.setOnClickListener(this);
        tv214.setOnClickListener(this);
        tv215.setOnClickListener(this);
        tv216.setOnClickListener(this);
        tv217.setOnClickListener(this);
        tv218.setOnClickListener(this);
        tv219.setOnClickListener(this);
        tv220.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void setClick(View v) {
        switch (v.getId()) {
            case R.id.tv_213:
                if (onSelectClassifyListener != null) {
                    onSelectClassifyListener.selectClass(XIN_SHI_FA_WU);
                    dismiss();
                }
                break;
            case R.id.tv_214:
                if (onSelectClassifyListener != null) {
                    onSelectClassifyListener.selectClass(XING_ZHENG_FA_WU);
                    dismiss();
                }
                break;
            case R.id.tv_215:
                if (onSelectClassifyListener != null) {
                    onSelectClassifyListener.selectClass(JIN_JI_JIU_FENG);
                    dismiss();
                }
                break;
            case R.id.tv_216:
                if (onSelectClassifyListener != null) {
                    onSelectClassifyListener.selectClass(HUN_YING_JIA_TING);
                    dismiss();
                }
                break;
            case R.id.tv_217:
                if (onSelectClassifyListener != null) {
                    onSelectClassifyListener.selectClass(JIN_RONG_BAO_XIAN);
                    dismiss();
                }
                break;
            case R.id.tv_218:
                if (onSelectClassifyListener != null) {
                    onSelectClassifyListener.selectClass(SHE_BAO_NAO_ZI);
                    dismiss();
                }
                break;
            case R.id.tv_219:
                if (onSelectClassifyListener != null) {
                    onSelectClassifyListener.selectClass(JIAO_TONG_YI_LIAO);
                    dismiss();
                }
                break;
            case R.id.tv_220:
                if (onSelectClassifyListener != null) {
                    onSelectClassifyListener.selectClass(QI_TA_FA_WU);
                    dismiss();
                }
                break;


        }
    }

    private OnSelectClassifyListener onSelectClassifyListener;

    public interface OnSelectClassifyListener {
        /**
         * 选择类别监听
         *
         * @param classfy 类别对应编号
         *                213	刑事法务
         *                214	行政法务
         *                215	经济纠纷
         *                216	婚姻家庭
         *                217	金融保险
         *                218	社保劳资
         *                219	交通医疗
         *                220	其他法务
         */
        void selectClass(String classfy);
    }
}
