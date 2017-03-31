package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.BackPasswordActivity;
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerInfoEdit;
import com.ucap.lawyers.view.EditPhotoDialogView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/***
 * 律师用户信息编辑
 */
public class LawyerInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.rl_edit_password)
    RelativeLayout rlEditPassword;//修改密码暂时禁用
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;//头像
    @Bind(R.id.tv_firstname)
    TextView tvFiratname;//名称
    @Bind(R.id.tv_phone)
    TextView tvPhone;//电话
    public RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_info);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intiDate();
        intiListener();
    }

    private void intiListener() {
//        rlEditPassword.setOnClickListener(this);
    }

    private void intiDate() {
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String uri = intent.getStringExtra("LawyerPhotoInfo");
        getDataForService(id, uri);
    }

    /**
     * 获取服务器数据
     *
     * @param id
     * @param uri
     */
    private void getDataForService(final String id, String uri) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gsonData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", id);
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析数据，并显示数据
     *
     * @param response
     */
    private void gsonData(String response) {
        Gson gson = new Gson();
        LawyerInfoEdit.RowsBean rowsBean = gson.fromJson(response, LawyerInfoEdit.class).getRows().get(0);
        tvFiratname.setText(rowsBean.getFirstname());
        tvPhone.setText(rowsBean.getPhone());
        Glide.with(this)
                .load(rowsBean.getPhoto())
                .placeholder(R.drawable.icon_user1)
                .error(R.drawable.icon_user1)
                .into(ivPhoto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_edit_password:
                Intent intent = new Intent(LawyerInfoActivity.this, BackPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

}
