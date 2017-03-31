package com.ucap.lawyers.base.imple;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.adapter.HomeViewPagerFirmAndLawyerAdapter;
import com.ucap.lawyers.base.PagerBase;
import com.ucap.lawyers.base.imple.firmDate.FirmDateBase;
import com.ucap.lawyers.base.imple.vagueFindBase.FirmFindFmjl;
import com.ucap.lawyers.base.imple.vagueFindBase.FirmFindGfzs;
import com.ucap.lawyers.base.imple.vagueFindBase.FirmFindGyfw;
import com.ucap.lawyers.base.imple.vagueFindBase.FirmFindRybz;
import com.ucap.lawyers.bean.vagueFind.FirmFindData;
import com.ucap.lawyers.view.LoadingDialogView;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/12.
 */
public class FirmBase extends PagerBase implements View.OnClickListener {
    @Bind(R.id.vp_firm_date)
    ViewPager vpDates;

    @Bind(R.id.tab_page_firm)
    TabPageIndicator tebPage;

    @Bind(R.id.ll_firm_root)
    LinearLayout llRootLayout;//整个布局
    @Bind(R.id.ll_top_layout)
    LinearLayout llTopLayout;//上部分搜索布局
    @Bind(R.id.btn_firm_find)
    Button btnFind;
    @Bind(R.id.et_name)
    EditText etName;//输入律所名称
    @Bind(R.id.et_groupnum)
    EditText etGroupnum;//输入律所许可证号
    @Bind(R.id.vp_firm_find_date)
    ViewPager vpFindContent;//查询结果显示列表


    private ArrayList<PagerBase> mList;
    boolean stateUp = true;
    private int topHeight;
    ImageView ivUpInvisible;
    ArrayList<FirmFindData.FmjlrowBean> listFmjlrow;//查询结果分类数据（负面指数）
    ArrayList<FirmFindData.GfglrowBean> listGfglrow;//规范指数
    ArrayList<FirmFindData.GyfwrowBean> listGyfwrow;//公益服务
    ArrayList<FirmFindData.RybzrowBean> listRybzrow;//荣誉指数
    ArrayList<PagerBase> mListFind;//查询结果显示列表页面
    public String[] title;
    public LoadingDialogView loadingDialogView;

    public FirmBase(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.base_firm, null);
        ButterKnife.bind(this, view);
        flContent.addView(view);
        ivUpInvisible = (ImageView) mActivity.findViewById(R.id.iv_firm_invisible);
        ivUpInvisible.setOnClickListener(this);
        btnFind.setOnClickListener(this);
        mList = new ArrayList<>();
        mList.add(new FirmDateBase(mActivity, DataInterface.Firm.FIRM_RYBZ, "FIRM_RYBZ"));//荣誉表彰
        mList.add(new FirmDateBase(mActivity, DataInterface.Firm.FIRM_GYFW, "FIRM_GYFW"));//公益服务
        mList.add(new FirmDateBase(mActivity, DataInterface.Firm.FIRM_FMZS, "FIRM_FMZS"));//负面指数
        mList.add(new FirmDateBase(mActivity, DataInterface.Firm.FIRM_GFZS, "FIRM_GFZS"));//规范指数
        title = new String[]{"荣誉表彰", "公益服务", "负面指数", "规范指数"};
        vpDates.setAdapter(new HomeViewPagerFirmAndLawyerAdapter(mActivity, mList, title));
        tebPage.setViewPager(vpDates);
        getIntiDate();
        listFmjlrow = new ArrayList<>();
        listGfglrow = new ArrayList<>();
        listGyfwrow = new ArrayList<>();
        listRybzrow = new ArrayList<>();
        mListFind = new ArrayList<>();
        loadingDialogView = new LoadingDialogView(mActivity);
        loadingDialogView.setCancelable(false);
    }

    /**
     * 初始化需要使用的数据
     */
    public void getIntiDate() {
        ViewTreeObserver topLayoutTreeObserver = llTopLayout.getViewTreeObserver();//获取头部布局的实际高
        topLayoutTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                topHeight = llTopLayout.getMeasuredHeight();
                return true;
            }
        });

    }

    /**
     * 获取查询数据
     */
    public void findData() {
        String name = etName.getText().toString();
        String groupnum = etGroupnum.getText().toString();
        if (!name.isEmpty() && groupnum.isEmpty()) {//输入名称，关键字模糊查询
            HashMap<String, String> map = new HashMap<>();
            map.put("name", name);
            getFinfDataForService(map);
        } else if (name.isEmpty() && !groupnum.isEmpty()) {//输入许可证关键字，模糊查询
            HashMap<String, String> map = new HashMap<>();
            map.put("groupnum", groupnum);
            getFinfDataForService(map);
        } else if (!name.isEmpty() && !groupnum.isEmpty()) {//输入名字和许可证全称查询
            HashMap<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("groupnum", groupnum);
            getFinfDataForService(map);
        }
    }

    /**
     * 从服务器查询数据
     *
     * @param map
     */
    public void getFinfDataForService(final HashMap<String, String> map) {
        //隐藏输入法
        InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
        loadingDialogView.show();
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Firm.FIRM_VAGUE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("findData", "—————律所分类列表，查询数据—————" + response);
                gsonFindData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialogView.dismiss();
                Toast.makeText(mActivity, "网络连接错误！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 解析查询到的数据
     *
     * @param data
     */
    public void gsonFindData(String data) {
        Gson gson = new Gson();
        FirmFindData firmFindData = gson.fromJson(data, FirmFindData.class);
        List<FirmFindData.FmjlrowBean> fmjlrow = firmFindData.getFmjlrow();
        listFmjlrow.clear();//清空旧数据
        listGfglrow.clear();
        listGyfwrow.clear();
        listRybzrow.clear();
        mListFind.clear();//清空旧数据显示列表
        if (fmjlrow != null && fmjlrow.size() > 0) {//判读是否查询到数据
            listFmjlrow.addAll(fmjlrow);

        } else {
            loadingDialogView.dismiss();
            Toast.makeText(mActivity, "未查询到数据！", Toast.LENGTH_SHORT).show();
            return;
        }
        List<FirmFindData.GfglrowBean> gfglrow = firmFindData.getGfglrow();
        if (gfglrow != null && gfglrow.size() > 0) {//判读是否查询到数据
            listGfglrow.addAll(gfglrow);
        }
        List<FirmFindData.GyfwrowBean> gyfwrow = firmFindData.getGyfwrow();
        if (gyfwrow != null && gyfwrow.size() > 0) {
            listGyfwrow.addAll(gyfwrow);
        }
        List<FirmFindData.RybzrowBean> rybzrow = firmFindData.getRybzrow();
        if (rybzrow != null && rybzrow.size() > 0) {
            listRybzrow.addAll(rybzrow);
        }
        mListFind.add(new FirmFindFmjl(mActivity, listFmjlrow));
        mListFind.add(new FirmFindGfzs(mActivity, listGfglrow));
        mListFind.add(new FirmFindGyfw(mActivity, listGyfwrow));
        mListFind.add(new FirmFindRybz(mActivity, listRybzrow));
        vpFindContent.setAdapter(new HomeViewPagerFirmAndLawyerAdapter(mActivity, mListFind, title));
        tebPage.setViewPager(vpFindContent);
        vpDates.setVisibility(View.GONE);//隐藏默认律所显示列表
        vpFindContent.setCurrentItem(vpFindContent.getCurrentItem());
        tebPage.setCurrentItem(vpFindContent.getCurrentItem());
        loadingDialogView.dismiss();
        Toast.makeText(mActivity, "数据列表已更新!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == ivUpInvisible) {
            if (stateUp) {
                setUpInvisibleState();
                setViewAnimation(0, -topHeight, llRootLayout);
                ivUpInvisible.setImageResource(R.drawable.icon_up_selecter);


            } else {
                setUpInvisibleState();
                setViewAnimation(-topHeight, 0, llRootLayout);
                ivUpInvisible.setImageResource(R.drawable.icon_up);
            }
        } else if (v == btnFind) {
            findData();

        }
    }

    /**
     * 设置上下移动布局的动画
     *
     * @param startY
     * @param endY
     * @param view
     */
    public void setViewAnimation(final float startY, float endY, final View view) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, startY, endY);
        animation.setDuration(500);
        view.startAnimation(animation);
        if (stateUp == false) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    llTopLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (stateUp == true) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    llTopLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    /**
     * 设置title收缩按钮的状态
     */
    private void setUpInvisibleState() {
        stateUp = !stateUp;
    }
}
