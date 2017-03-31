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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.bigkoo.pickerview.OptionsPickerView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ucap.lawyers.InterfaceAddress.DataInterface;
import com.ucap.lawyers.R;
import com.ucap.lawyers.tools.PrefUtils;
import com.ucap.lawyers.view.EditPhotoDialogView;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.NewUserPasswordShowDialogView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新用户注册
 */
public class OrdinaryNewUserActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.ll_edit_photo)
    LinearLayout llEditPhoto;//编辑头像
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;//头像
    @Bind(R.id.tv_userid)
    TextView tvUserId;//账号（随机生成）
    @Bind(R.id.et_password)
    EditText etPassword;//密码
    @Bind(R.id.et_confirm_password)
    EditText etConfirmPassword;//确认密码
    @Bind(R.id.et_name)
    EditText etName;//姓名
    @Bind(R.id.et_ID)
    EditText etID;//身份证号
    @Bind(R.id.et_sex)
    EditText etSex;//性别
    @Bind(R.id.et_mail)
    EditText etMail;//邮箱地址
    @Bind(R.id.et_region)
    EditText etRegion;//地区
    @Bind(R.id.btn_submit)
    Button btnSubmit;//提交
    private Bitmap photo;
    private static final int CAMERA_WITH_DATA = 3023;//启动相机拍照code
    private static final int PHOTO_PICKED_WITH_DATA = 3021;//选择本地图片code
    private static final int CODE_RESULT_REQUEST = 3000;//启动系统剪切图片页面dode
    public RequestQueue requestQueue;
    public String userId;
    public HttpUtils httpUtils;
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public LoadingDialogView loadingDialogView;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_new_user);
        ButterKnife.bind(this);
        intiData();
        intiListener();
        getDataForService();

    }

    /**
     * 初始化数据
     */
    private void intiData() {
        photo = android.graphics.BitmapFactory.decodeResource(getResources(), R.drawable.icon_user2);//默认头像
        loadingDialogView = new LoadingDialogView(this);
        httpUtils = new HttpUtils();
        requestQueue = Volley.newRequestQueue(this);
    }

    /**
     * 设置监听
     */
    private void intiListener() {
        llEditPhoto.setOnClickListener(this);
        etSex.setOnClickListener(this);
        etRegion.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etConfirmPassword.setTextColor(Color.BLACK);
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
    }

    /**
     * 获取服务器数据
     */
    public void getDataForService() {
        StringRequest request = new StringRequest(DataInterface.NewUser.NEW_USER_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                userId = response;
                tvUserId.setText(response + "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "网络异常！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        requestQueue.add(request);
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
            File path = new File("/sdcard/lawyersPhoto");
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, userId + "_add.jpg");
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
        try {
            RequestParams params = new RequestParams();
            params.addBodyParameter("id", userId);
            params.addBodyParameter("imgbody", file, "image/jpg");
            httpUtils.send(HttpMethod.POST, DataInterface.NewUser.UPLOAD, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Log.i("uploadInfo", "----" + responseInfo.reasonPhrase + "---" + "---ok");
                    Log.i("uploadInfo", "----" + responseInfo.result);
                    PrefUtils.putString("user_photo", responseInfo.result, OrdinaryNewUserActivity.this);
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    Log.i("uploadInfo", total + "--loagin--" + current);
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    Log.i("infoUpdate", msg + "---err" + error.getMessage() + "---" + error.getExceptionCode());
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

    /**
     * 响应点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (view == llEditPhoto) {
            EditPhotoDialogView.show(this, new EditPhotoDialogView.OnEditPhotoDialogListener() {
                @Override
                public void OnCamera() {//启动相机
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, CAMERA_WITH_DATA);
                    } else {
                        //提示用户开户权限
                        String[] perms = {"android.permission.CAMERA"};
                        ActivityCompat.requestPermissions(OrdinaryNewUserActivity.this, perms, 0);
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
                        String[] imagePerms = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        ActivityCompat.requestPermissions(OrdinaryNewUserActivity.this, imagePerms, 1);
                    }
                }
            });
        } else if (etSex == view) {
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
                    etSex.setText(item.get(options1));
                }
            });
        } else if (etRegion == view) {
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
                    etRegion.setText(item.get(options1));
                }
            });
        } else if (view == btnSubmit) {
            password = etConfirmPassword.getText().toString();
            String name = etName.getText().toString();
            String ID = etID.getText().toString();
            String sex = etSex.getText().toString();
            String mail = etMail.getText().toString();
            String address = etRegion.getText().toString();
            if (!userId.isEmpty() && !password.isEmpty() && !name.isEmpty() && !ID.isEmpty() && !sex.isEmpty() && !mail.isEmpty() && !address.isEmpty()) {
                //判断两次密码是否一致
                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    //身份证号验证
                    if (ID.length() == 18) {
                        //验证邮箱
                        if (isEmail(mail)) {
                            btnSubmit.setEnabled(false);//防止用户重复点击重复提交数据
                            loadingDialogView.show();
                            HashMap<String, String> map = new HashMap<>();
                            map.put("userid", userId);
                            map.put("password", password);
                            map.put("firstname", name);
                            map.put("idcard", ID);
                            map.put("sex", sex);
                            map.put("emailaddress", mail);
                            map.put("address", address);
                            uploadInfo(map);
                            if (photo != null)//判断用户是否选择了头像
                            {
                                uploa(photo);
                            }
                        } else {
                            etMail.setTextColor(Color.RED);
                            Toast.makeText(this, "请输入正确的邮箱地址！", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        etID.setTextColor(Color.RED);
                        Toast.makeText(this, "请输入正确的身份证号！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "两次密码需一致！", Toast.LENGTH_SHORT).show();
                    etConfirmPassword.setTextColor(Color.RED);
                }
            } else {
                Toast.makeText(this, "请将信息填写完整！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 上传资料
     */

    public void uploadInfo(final HashMap<String, String> map) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.NewUser.UPLOAD_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("uploadInfo", "-----" + response);
                if (response.equals("注册成功")) {
                    btnSubmit.setEnabled(true);
                    PrefUtils.putString("ordinary_user", userId, getApplicationContext());//保存账号
                    PrefUtils.putString("ordinary_password", password, getApplicationContext());//密码
                    //弹窗提示用户牢记密码
                    NewUserPasswordShowDialogView.showDialog(OrdinaryNewUserActivity.this, userId, password, new NewUserPasswordShowDialogView.OnbtnDetermine() {
                        @Override
                        public void btnDetermine() {
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), response + "，请尝试重新提交！", Toast.LENGTH_SHORT).show();
                }
                loadingDialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnSubmit.setEnabled(true);
                Toast.makeText(getApplicationContext(), "网络链接失败！", Toast.LENGTH_SHORT).show();
                loadingDialogView.dismiss();

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
     * 验证邮箱
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String chinese) {
        return Pattern.matches(REGEX_EMAIL, chinese);
    }
}
