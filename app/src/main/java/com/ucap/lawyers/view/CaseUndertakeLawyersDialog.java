package com.ucap.lawyers.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.bean.userLogin.userOrdinary.MyCommonly;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.tools.JumpDetailedTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 2016/12/20.
 * 案件承办律师
 */

public class CaseUndertakeLawyersDialog extends BaseDialog {
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.iv_dismiss)
    ImageView ivDismiss;
    public ArrayList<MyCommonly.RowsBean.LawlistBean> mDate;
    public LoadingDialogView lodingDialogView;
    public RequestQueue requestQueue;

    public CaseUndertakeLawyersDialog(Context context, List<MyCommonly.RowsBean.LawlistBean> lawlist) {
        super(context);
        this.mDate = (ArrayList<MyCommonly.RowsBean.LawlistBean>) lawlist;
    }

    @Override
    public void setView() {
        setContentView(View.inflate(getContext(), R.layout.dialog_case_undertake_lawyers, null));
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
        ivDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void setData() {
        requestQueue = Volley.newRequestQueue(getContext());
        lodingDialogView = new LoadingDialogView(getContext());
        for (final MyCommonly.RowsBean.LawlistBean data : mDate) {
            final View item = View.inflate(getContext(), R.layout.item_case_undertake_lawyers, null);
            ImageView ivPhoto = (ImageView) item.findViewById(R.id.iv_photo);//头像
            Glide.with(getContext()).load(data.getPhoto()).into(ivPhoto);
            TextView tvName = (TextView) item.findViewById(R.id.tv_lawyer_name);//律师名称
            tvName.setText(data.getFirstname());
            TextView tvNumber = (TextView) item.findViewById(R.id.tv_lawyer_number);//律师证号
            tvNumber.setText(data.getContactid());
            TextView tvFirm = (TextView) item.findViewById(R.id.tv_firm);//律所
            tvFirm.setText(data.getGroupname());
            LinearLayout llItem = (LinearLayout) item.findViewById(R.id.ll_item);
            llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("firstname", data.getFirstname());
                    map.put("contactid", data.getContactid());
                    findLawyerDateForService(map);
                }
            });
            llContent.addView(item);
        }
    }

    @Override
    public void setClick(View v) {

    }

    /**
     * 律师查询
     */
    public void findLawyerDateForService(final HashMap<String, String> map) {
        lodingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Lawyer.LAWYERS_VAGUE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("dataLawyers", "------" + response);
                Gson gson = new Gson();
                LawyersFindData lawyersFindData = gson.fromJson(response, LawyersFindData.class);
                List<LawyersFindData.FmjlrowsBean> rybzrows = lawyersFindData.getFmjlrows();
                if (rybzrows != null && rybzrows.size() > 0) {
                    List<LawyersFindData.FmjlrowsBean> fmjlrows = lawyersFindData.getFmjlrows();
                    if (!fmjlrows.isEmpty() && fmjlrows.size() > 0) {
                        LawyersFindData.FmjlrowsBean rowsBean = fmjlrows.get(0);
                        JumpDetailedTools.jumpLawyerFmjlDetailed(getContext(), rowsBean);
                    }
                }
                lodingDialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lodingDialogView.dismiss();
                Toast.makeText(getContext(), "网络连接失败!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        Log.i("lawyerUri", request.getUrl());
        requestQueue.add(request);

    }

}
