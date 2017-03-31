package com.ucap.lawyers.activitys.userLogin.userOrdinaryActivitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ucap.lawyers.R;
import com.ucap.lawyers.activitys.publicActivity.BackPasswordActivity;
import com.ucap.lawyers.bean.userLogin.userOrdinary.UserInfo;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.EditPhotoDialogView;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.PasswordShowDialogView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/***
 * 用户信息页面
 */

public class OrdinaryInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.ll_edit_photo)
    LinearLayout llEditPhoto;
    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    @Bind(R.id.rl_modify_password)
    RelativeLayout rlModifyPassword;//修改密码
    @Bind(R.id.ll_edit_sex)
    LinearLayout llEditSex;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;//用户名（账号）
    @Bind(R.id.et_name)
    EditText tvName;//姓名
    @Bind(R.id.et_ID)
    EditText etID;//身份证号
    @Bind(R.id.tv_sex)
    TextView tvSex;//性别
    @Bind(R.id.et_mail)
    EditText etMail;//邮箱
    @Bind(R.id.et_region)
    EditText edRegion;//所在地
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;//头像
    @Bind(R.id.btn_save)
    Button btnSave;//保存
    @Bind(R.id.rl_user)
    RelativeLayout rlUser;//点击账号时提示用户，账号密码一直
    /**
     * 信息上传接口地址
     * <p>
     * Id：用户id
     * Firstname：姓名
     * Openid:身份证
     * Sex:性别
     * Email：邮箱
     * Address:所在地
     * Imgbody:头像
     */
    private String INFO_UPDATA = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!OrdinaryUsersEdit.action";
    /**
     * 上传头像
     */
    private String INFO_UPDATA_PHOTO = "http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/uploadservlet";
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    private static final int CAMERA_WITH_DATA = 3023;//启动相机拍照code
    private static final int PHOTO_PICKED_WITH_DATA = 3021;//选择本地图片code
    private static final int CODE_RESULT_REQUEST = 3000;//启动系统剪切图片页面dode
    private Bitmap photo;
    public String uri;
    public String id;
    public RequestQueue requestQueue;
    public LoadingDialogView loadingDialogView;
    public HttpUtils httpUtils;
    public String firstname;
    public String userid;
    public String myOpenId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_info);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//启动时不弹出输入框
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
        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etID.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etMail.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rlUser.setOnClickListener(this);
    }

    private void intiDate() {
        httpUtils = new HttpUtils();
        loadingDialogView = new LoadingDialogView(this);
        Intent intent = getIntent();
        requestQueue = Volley.newRequestQueue(this);
        id = intent.getStringExtra("id");//id
        uri = intent.getStringExtra("ordinaryUsersInfo");//个人中心
        Log.i("ordinaryUsersInfo", uri + "&id=" + id);
        getDataForService(uri, id);
        rlModifyPassword.setOnClickListener(this);
        llEditSex.setOnClickListener(this);
        llEditPhoto.setOnClickListener(this);
        edRegion.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        rlModifyPassword.setEnabled(false);//修改密码暂时禁用
    }

    /**
     * 获取服务器数据
     *
     * @param uri
     * @param id
     */
    private void getDataForService(String uri, final String id) {
        loadingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ordinaryInfo", response);
                gsonData(response);
                loadingDialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ordinaryInfo", "链接超时！");
                loadingDialogView.dismiss();
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
     * 解析数据
     *
     * @param response
     */
    private void gsonData(String response) {
        UserInfo.RowsBean rowsBean = new Gson().fromJson(response, UserInfo.class).getRows().get(0);
        for (int i = 0; i <= 2; i++) {
            Glide.with(this).load(rowsBean.getPhoto()).into(ivPhoto);
        }
        myOpenId = rowsBean.getOpenid();
        userid = rowsBean.getUserid();
        tvUserName.setText(userid);
        firstname = rowsBean.getFirstname();
        tvName.setText(firstname);
        etID.setText(rowsBean.getIdcard().equals("无") ? "" : rowsBean.getIdcard());
        tvSex.setText(rowsBean.getSex());
        etMail.setText(rowsBean.getEmailaddress().equals("无") ? "" : rowsBean.getEmailaddress());
        edRegion.setText(rowsBean.getAddress().equals("无") ? "" : rowsBean.getAddress());
    }


    @Override
    public void onClick(View v) {
        if (v == edRegion) {//修改地区
            final ArrayList<String> item = new ArrayList<>();
            item.add("青羊区");
            item.add("金牛区");
            item.add("武侯区");
            item.add("成华区");
            item.add("高新区");
            item.add("龙泉驿");
            item.add("青白江区");
            item.add("新都区");
            item.add("温江区");
            item.add("金堂县");
            item.add("双流县");
            item.add("郫县");
            item.add("大邑县");
            item.add("新津县");
            item.add("都江堰");
            item.add("彭州市");
            item.add("邛崃市");
            item.add("崇州");
            item.add("简阳市");
            OptionsPickerView option = new OptionsPickerView(this);
            option.setTitle("选择所在地区");
            option.setPicker(item);
            option.setCyclic(false);
            option.show();
            option.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    edRegion.setText(item.get(options1));
                }
            });
        } else if (v == llEditSex) {//修改性别
            final ArrayList<String> item = new ArrayList<>();
            item.add("男");
            item.add("女");
            OptionsPickerView option = new OptionsPickerView(this);
            option.setTitle("选择称谓");
            option.setPicker(item);
            option.setCyclic(false);
            option.show();
            option.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    tvSex.setText(item.get(options1));
                }
            });
        } else if (v == rlModifyPassword) {//修改密码页面
            Intent intent = new Intent(OrdinaryInfoActivity.this, BackPasswordActivity.class);
            startActivity(intent);
        } else if (v == llEditPhoto) {
            EditPhotoDialogView.show(this, new EditPhotoDialogView.OnEditPhotoDialogListener() {
                @Override
                public void OnCamera() {//启动相机
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, CAMERA_WITH_DATA);
                    } else {
                        //提示用户开户权限
                        String[] perms = {"android.permission.CAMERA"};
                        ActivityCompat.requestPermissions(OrdinaryInfoActivity.this, perms, CAMERA_WITH_DATA);
                    }

                }

                @Override
                public void OnImage() {//选择本地图片
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");//相片类型
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                    } else {
                        String[] imagePerms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        ActivityCompat.requestPermissions(OrdinaryInfoActivity.this, imagePerms, CAMERA_WITH_DATA);
                    }
                }
            });
        } else if (btnSave == v) {
            String firstName = tvName.getText().toString();
            String ID = etID.getText().toString();//身份证号
            String sex = tvSex.getText().toString();
            String email = etMail.getText().toString();
            String address = edRegion.getText().toString();

            if (!firstName.isEmpty() && !ID.isEmpty() && !sex.isEmpty() && !email.isEmpty() && !address.isEmpty()) {
                if (isIDCard(ID) && isEmail(email)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", id);
                    map.put("firstname", firstName);//姓名
                    map.put("openid", ID);//身份证
                    map.put("sex", sex);//性别
                    map.put("email", email);//邮箱
                    map.put("address", address);//所在地
                    saveInfo(map);
                } else {
                    if (!isIDCard(ID)) {
                        etID.setTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "请输入正确的身份信息", Toast.LENGTH_SHORT).show();
                    }
                    if (!isEmail(email)) {
                        etMail.setTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "请将信息填写完整！", Toast.LENGTH_SHORT).show();
            }
        } else if (v == rlUser) {//点击账号提示用户
            PasswordShowDialogView.showDialog(this, userid);

        }

    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String chinese) {
        return Pattern.matches(REGEX_EMAIL, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return (!idCard.isEmpty() && idCard.length() == 18);
//        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 保存编辑信息按钮
     */
    private void saveInfo(final HashMap<String, String> map) {
        loadingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, INFO_UPDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("infoUpdate", response);
                if ("修改成功".equals(response)) {
                    Toast.makeText(getApplicationContext(), "已保存", Toast.LENGTH_SHORT).show();
                    finish();
                }
                loadingDialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "保存失败，服务链接失败！", Toast.LENGTH_SHORT).show();
                loadingDialogView.dismiss();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 拉去授权回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_WITH_DATA://获取相机权限
                if (grantResults.length > 0 && permissions[0] == Manifest.permission.CAMERA) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, CAMERA_WITH_DATA);
                } else if (grantResults.length > 0 && permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");//相片类型
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                }
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_WITH_DATA://拍照获取头像
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    photo = (Bitmap) bundle.get("data");
                    if (photo != null)
                        photo.recycle();
                    photo = (Bitmap) data.getExtras().get("data");
                    if (photo != null) {
                        uploa(photo);
                        ivPhoto.setImageBitmap(photo);
                    }
                }
                break;
            case PHOTO_PICKED_WITH_DATA://从本地选取头像
                Log.i("photo", "从本地获取");
                if (data != null) {
                    cropRawPhoto(data.getData());//裁剪图片
                }
                break;
            case CODE_RESULT_REQUEST://裁剪同头像后设置给头像显示空间
                if (data != null) {
                    Bundle extras = data.getExtras();
                    photo = extras.getParcelable("data");
                    if (photo != null) {
                        uploa(photo);
                        ivPhoto.setImageBitmap(photo);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 将选择的头像bimap转为file
     *
     * @param photo
     */

    private void uploa(Bitmap photo) {
        try {
//            loadingDialogView.show();
            File path = new File("/sdcard/lawyersPhoto");
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, myOpenId + ".jpg");
            FileOutputStream bos = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            uploadPhoto(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传头像
     *
     * @param file
     */
    public void uploadPhoto(final File file) {

//        final String path = "http://192.168.0.152:9527/scslsxt/servlet/uploadservlet";
        try {
            RequestParams params = new RequestParams();
            params.addBodyParameter("id", id);
            params.addBodyParameter("imgbody", file, "image/jpg");
            httpUtils.send(HttpMethod.POST, INFO_UPDATA_PHOTO, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Log.i("infoUpdate", "----" + responseInfo.reasonPhrase + "---" + "---ok");
                    Log.i("infoUpdate", "----" + responseInfo.result + "---" + "---ok");
                    PrefUtils.putString("user_photo", responseInfo.result, OrdinaryInfoActivity.this);
                    loadingDialogView.dismiss();
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    Log.i("infoUpdate", total + "--loagin--" + current);
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    Log.i("infoUpdate", msg + "---err" + error.getMessage() + "---" + error.getExceptionCode());
                    loadingDialogView.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪原始的图片
     */

    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }


}
