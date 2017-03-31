package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.SelectDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 法律众筹
 */
public class LawUploadActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.et_file_name)
    EditText etFileName;//文件名称
    @Bind(R.id.et_file_department)
    EditText etUploadDepartment;//发布部门
    @Bind(R.id.et_select_classify)
    EditText etSelectClassify;//选择文件类型
    @Bind(R.id.et_select_file_nature)
    EditText etSelectFileNature;//选择文件性质
    @Bind(R.id.et_content)
    EditText etContent;//文件内容
    @Bind(R.id.btn_consultation)
    Button btnConsultation;//保存提交
    public ArrayList<String> fileClassify;//文件类型
    public ArrayList<String> fileNature; //文件性质分类
    public RequestQueue requestQueue;
    public String userId;
    public LoadingDialogView loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//启动时不弹出输入框,手动弹出键盘时不能让键盘把布局往上定
        setContentView(R.layout.activity_law_upload);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiData();
        intiListener();
    }

    /**
     * 初始化数据
     */
    private void intiData() {
        requestQueue = Volley.newRequestQueue(this);
        loadingDialog = new LoadingDialogView(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        //文件类型分类
        fileClassify = new ArrayList<>();
        fileClassify.add("宪法相关法类");
        fileClassify.add("民法类");
        fileClassify.add("商法类");
        fileClassify.add("行政法类");
        fileClassify.add("社会法类");
        fileClassify.add("刑法类");
        fileClassify.add("经济法类");
        fileClassify.add("诉讼与非诉讼程序法类");
        fileClassify.add("其他类");
        //文件性质分类
        fileNature = new ArrayList<>();
        fileNature.add("法律");
        fileNature.add("法规");
        fileNature.add("规章");
        fileNature.add("司法解释");
        fileNature.add("规范性文件");
        fileNature.add("解读");
    }

    /**
     * 初始化事件
     */
    private void intiListener() {
        etSelectClassify.setOnClickListener(this);
        etSelectFileNature.setOnClickListener(this);
        btnConsultation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_select_classify:
                SelectDialog.dialogShow(this, fileClassify, new SelectDialog.OnItemClickGetContentListener() {
                    @Override
                    public void onIteClickGetContent(String content) {
                        etSelectClassify.setText(content);
                    }
                });
                break;
            case R.id.et_select_file_nature:
                SelectDialog.dialogShow(this, fileNature, new SelectDialog.OnItemClickGetContentListener() {
                    @Override
                    public void onIteClickGetContent(String content) {
                        etSelectFileNature.setText(content);
                    }
                });
                break;
            case R.id.btn_consultation:
                String fileName = etFileName.getText().toString();
                String uploadDepartment = etUploadDepartment.getText().toString();
                String fileClassify = etSelectClassify.getText().toString();
                String fileNature = etSelectFileNature.getText().toString();
                String content = etContent.getText().toString();
                Log.i("TEST_LAW_UPLOAD", "文件名称：" + fileName);
                Log.i("TEST_LAW_UPLOAD", "发布部门：" + uploadDepartment);
                Log.i("TEST_LAW_UPLOAD", "文件类型：" + fileClassify);
                Log.i("TEST_LAW_UPLOAD", "文件性质：" + fileNature);
                Log.i("TEST_LAW_UPLOAD", "文件内容：" + content);
                Log.i("TEST_LAW_UPLOAD", "律师id：" + userId);
                if (!fileName.isEmpty() &&
                        !uploadDepartment.isEmpty() &&
                        !fileClassify.isEmpty() &&
                        !fileNature.isEmpty() &&
                        !content.isEmpty()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", fileName);
                    map.put("bumen", uploadDepartment);
                    map.put("type", fileClassify);
                    map.put("nature", fileNature);
                    map.put("content", content);
                    map.put("userid", userId);
                    upload(map);
                } else {
                    Toast.makeText(getApplicationContext(), "请将信息填写完整！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 提交数据
     *
     * @param data
     */
    public void upload(final HashMap<String, String> data) {
        loadingDialog.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.LAW_UPLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TEST_LAW_UPLOAD", "法律众筹提交成功返回数据：" + response);
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "提交失败，请检查网络链接再重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return data;
            }
        };
        requestQueue.add(request);
    }

}
