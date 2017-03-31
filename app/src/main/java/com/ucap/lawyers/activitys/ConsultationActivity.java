package com.ucap.lawyers.activitys;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.SelectClassifyDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/***
 * 我要咨询
 */
public class ConsultationActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.et_title)
    EditText etTitle;//咨询标题
    @Bind(R.id.et_select_classify)
    EditText etSelectClassify;//选择咨询类别
    @Bind(R.id.et_content)
    EditText etContent;//咨询内容
    @Bind(R.id.tv_content_number)
    TextView tvContentNumber;
    @Bind(R.id.tv_consultation_size)
    TextView tvConsultationSize;//剩余还能咨询的总条数
    @Bind(R.id.btn_consultation)
    Button btnConsultation;//提交咨询
    private InputMethodManager inputKey;
    public RequestQueue requestQueue;
    public String consultation_type = "";//咨询 类别
    public LoadingDialogView loadingDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//启动时不弹出输入框,手动弹出键盘时不能让键盘把布局网上定
        setContentView(R.layout.activity_consultation);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiDate();
        intiListener();
    }

    private void intiListener() {
        loadingDialogView = new LoadingDialogView(this);
        requestQueue = Volley.newRequestQueue(this);
        etSelectClassify.setOnClickListener(this);
        btnConsultation.setOnClickListener(this);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                tvContentNumber.setText(length + "");
                if (length >= 500) {
                    tvContentNumber.setTextColor(Color.RED);
                } else {
                    tvContentNumber.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void intiDate() {
        etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});//咨询内容限五百字
        etTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});//咨询标题限20字
        inputKey = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        String consultationSize = getIntent().getStringExtra("size");
        tvConsultationSize.setText(consultationSize);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        inputKey.hideSoftInputFromWindow(etContent.getWindowToken(), 0); //强制隐藏键盘
    }

    @Override
    protected void onResume() {
        super.onResume();
//        友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    public void onClick(View view) {
        if (view == etSelectClassify) {
            SelectClassifyDialog.showDialog(this, new SelectClassifyDialog.OnSelectClassifyListener() {
                @Override
                public void selectClass(String classfy) {
                    Log.i("classify", classfy);
                    consultation_type = classify(classfy);
                }
            });
        } else if (view == btnConsultation) {
            String title = etTitle.getText().toString();
            String content = etContent.getText().toString();
            String user_id = PrefUtils.getString("user_id", "", getApplicationContext());//普通用户id，律师不能使用咨询
            if (!title.isEmpty() && !consultation_type.isEmpty() && !content.isEmpty()) {
                Log.i("consultationData", consultation_type);
                HashMap<String, String> map = new HashMap<>();
                map.put("title", title);
                map.put("content", content);
                map.put("userid", user_id);
                map.put("position", PrefUtils.getString("my_location", "", getApplicationContext()));
                map.put("type", consultation_type);
                consultation(map);
            } else if (title.isEmpty() && !consultation_type.isEmpty() && !content.isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_SHORT).show();
            } else if (!title.isEmpty() && consultation_type.isEmpty() && !content.isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入选择咨询类别", Toast.LENGTH_SHORT).show();
                SelectClassifyDialog.showDialog(this, new SelectClassifyDialog.OnSelectClassifyListener() {
                    @Override
                    public void selectClass(String classfy) {
                        Log.i("classify", classfy);
                        consultation_type = classify(classfy);
                    }
                });
            } else if (title.isEmpty() && !consultation_type.isEmpty() && content.isEmpty()) {
                Toast.makeText(getApplicationContext(), "咨询内容不能为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 提交咨询内容
     *
     * @param map 参数
     */
    private void consultation(final HashMap<String, String> map) {
        loadingDialogView.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, DataInterface.CONSULTATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("consultationData", response + "--");
                loadingDialogView.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("consultationData", error.getMessage() + "--");
                loadingDialogView.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 类别设置
     *
     * @param classify
     */
    public String classify(String classify) {
        if (classify.equals(SelectClassifyDialog.XIN_SHI_FA_WU)) {
            etSelectClassify.setText("刑事法务");
            return SelectClassifyDialog.XIN_SHI_FA_WU;
        } else if (classify.equals(SelectClassifyDialog.XING_ZHENG_FA_WU)) {
            etSelectClassify.setText("行政法务");
            return SelectClassifyDialog.XING_ZHENG_FA_WU;
        } else if (classify.equals(SelectClassifyDialog.JIN_JI_JIU_FENG)) {
            etSelectClassify.setText("经济纠纷");
            return SelectClassifyDialog.JIN_JI_JIU_FENG;
        } else if (classify.equals(SelectClassifyDialog.HUN_YING_JIA_TING)) {
            etSelectClassify.setText("婚姻家庭");
            return SelectClassifyDialog.HUN_YING_JIA_TING;
        } else if (classify.equals(SelectClassifyDialog.JIN_RONG_BAO_XIAN)) {
            etSelectClassify.setText("金融保险");
            return SelectClassifyDialog.JIN_RONG_BAO_XIAN;
        } else if (classify.equals(SelectClassifyDialog.SHE_BAO_NAO_ZI)) {
            etSelectClassify.setText("社保劳资");
            return SelectClassifyDialog.SHE_BAO_NAO_ZI;
        } else if (classify.equals(SelectClassifyDialog.JIAO_TONG_YI_LIAO)) {
            etSelectClassify.setText("交通医疗");
            return SelectClassifyDialog.JIAO_TONG_YI_LIAO;
        } else if (classify.equals(SelectClassifyDialog.QI_TA_FA_WU)) {
            etSelectClassify.setText("其他法务");
            return SelectClassifyDialog.QI_TA_FA_WU;
        }
        return "";
    }
}
