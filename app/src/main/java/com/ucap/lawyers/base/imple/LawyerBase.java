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
import com.ucap.lawyers.base.imple.lawyerDate.LawyerDateBase;
import com.ucap.lawyers.base.imple.vagueFindBase.LawyersFindFmjl;
import com.ucap.lawyers.base.imple.vagueFindBase.LawyersFindGyfw;
import com.ucap.lawyers.base.imple.vagueFindBase.LawyersFindRybz;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;
import com.ucap.lawyers.view.LoadingDialogView;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wekingwang on 16/8/12.
 * 律所查询列表，
 */
public class LawyerBase extends PagerBase implements View.OnClickListener {
    @Bind(R.id.tab_page_lawyer)
    TabPageIndicator tabPageIndicator;
    @Bind(R.id.vp_lawyer_date)
    ViewPager vp;//默认律师显示列表
    @Bind(R.id.vp_lawyer_find_date)
    ViewPager vpFindLawyers;//查询律师结果显示列表
    @Bind(R.id.ll_top_layout)
    LinearLayout llTopLayout;
    @Bind(R.id.ll_lawyer_root)
    LinearLayout llRootLayout;
    @Bind(R.id.btn_lawyer_find)
    Button btnFind;
    @Bind(R.id.et_name)
    EditText etLawyersName;//输入律师名称
    @Bind(R.id.et_groupnum)
    EditText etGroupunm;//输入律师执业许可证号
    boolean stateUp = true;
    ImageView ivUpInvisible;
    ArrayList<PagerBase> mListDate;
    private int topHeight;
    public RequestQueue requestQueue;
    public ArrayList<LawyersFindData.FmjlrowsBean> listFmjl;//查询分类显示"负面指数"
    public ArrayList<LawyersFindData.GyfwrowsBean> listGyfw;//"公益服务"
    public ArrayList<LawyersFindData.RybzrowsBean> listRybz;//"荣誉表彰"
    public ArrayList<PagerBase> mListFindData;
    public String[] title;
    public LoadingDialogView loadingDialogView;

    public LawyerBase(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void intiDate() {
        View view = View.inflate(mActivity, R.layout.base_lawyer, null);
        ButterKnife.bind(this, view);
        flContent.addView(view);
        ivUpInvisible = (ImageView) mActivity.findViewById(R.id.iv_lawyer_invisible);
        ivUpInvisible.setOnClickListener(this);
        getIntiDate();
        mListDate = new ArrayList<>();
        mListDate.add(new LawyerDateBase(mActivity, DataInterface.Lawyer.LAWYER_RYBZ, "LAWYER_RYBZ"));
        mListDate.add(new LawyerDateBase(mActivity, DataInterface.Lawyer.LAWYER_GYFW, "LAWYER_GYFW"));
        mListDate.add(new LawyerDateBase(mActivity, DataInterface.Lawyer.LAWYER_FMZS, "LAWYER_FMZS"));
        title = new String[]{"荣誉表彰", "公益服务", "负面指数"};
        vp.setAdapter(new HomeViewPagerFirmAndLawyerAdapter(mActivity, mListDate, title));
        tabPageIndicator.setViewPager(vp);
        listFmjl = new ArrayList<>();
        listGyfw = new ArrayList<>();
        listRybz = new ArrayList<>();
        mListFindData = new ArrayList<>();
//        vpFindLawyers.setVisibility(View.GONE);//默认隐藏，默认加载律师列表
        loadingDialogView = new LoadingDialogView(mActivity);
        loadingDialogView.setCancelable(false);
    }

    /**
     * 获取查询数据
     */
    public void findData() {
        requestQueue = Volley.newRequestQueue(mActivity);
        String name = etLawyersName.getText().toString();
        String groupunm = etGroupunm.getText().toString();
        if (!name.isEmpty() && groupunm.isEmpty()) {//根据名字模糊查
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("firstname", name);
            getFindDataForService(hashMap);
//            Toast.makeText(mActivity, "根据名字模糊查！", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty() && !groupunm.isEmpty()) {//根据执业证号模糊查
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("contactid", groupunm);
            getFindDataForService(hashMap);
//            Toast.makeText(mActivity, "根据执业证号模糊查！", Toast.LENGTH_SHORT).show();
        } else if (!name.isEmpty() && !groupunm.isEmpty()) {//根据名称和执业证号精确查
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("firstname", name);
            hashMap.put("contactid", groupunm);
            getFindDataForService(hashMap);
//            Toast.makeText(mActivity, "根据名称和执业证号精确查！", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 从服务器查询数据出来
     */
    public void getFindDataForService(final HashMap<String, String> map) {
        //隐藏输入法
        InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
        loadingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.Lawyer.LAWYERS_VAGUE_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("findLawyer", "----律师查询分类列表----" + response);
                if (!response.isEmpty())
                    gsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialogView.show();
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
     * 解析查询数据
     *
     * @param data
     */
    public void gsonData(String data) {
        Gson gson = new Gson();
        LawyersFindData lawyersData = gson.fromJson(data, LawyersFindData.class);
        if (lawyersData != null) {
            listFmjl.clear();//清楚旧数据，显示最新查询的数据
            listRybz.clear();
            listGyfw.clear();
            mListFindData.clear();///清空旧数据显示列表
            if (lawyersData.getFmjlrows() != null && lawyersData.getFmjlrows().size() > 0) {//判断是否查询到数据
                listFmjl.addAll(lawyersData.getFmjlrows());

            } else {
                loadingDialogView.dismiss();
                Toast.makeText(mActivity, "未查询到数据！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lawyersData.getGyfwrows() != null && lawyersData.getGyfwrows().size() > 0)
                listGyfw.addAll(lawyersData.getGyfwrows());
            if (lawyersData.getRybzrows() != null && lawyersData.getRybzrows().size() > 0)
                listRybz.addAll(lawyersData.getRybzrows());
            mListFindData.add(new LawyersFindFmjl(mActivity, listFmjl));
            mListFindData.add(new LawyersFindGyfw(mActivity, listGyfw));
            mListFindData.add(new LawyersFindRybz(mActivity, listRybz));
            vpFindLawyers.setAdapter(new HomeViewPagerFirmAndLawyerAdapter(mActivity, mListFindData, title));
            tabPageIndicator.setViewPager(vpFindLawyers);
            vp.setVisibility(View.GONE);//隐藏默认的律师列表
            vpFindLawyers.setCurrentItem(vpFindLawyers.getCurrentItem());//切换到第一页
            tabPageIndicator.setCurrentItem(vpFindLawyers.getCurrentItem());//切换到第一页
            loadingDialogView.dismiss();
            Toast.makeText(mActivity, "列表数据已更新！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void intiListener() {
        btnFind.setOnClickListener(this);
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
     * 设置title收缩按钮的状态
     */
    private void setUpInvisibleState() {
        stateUp = !stateUp;
    }
}
