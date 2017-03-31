package com.ucap.lawyers.activitys.userLogin.userLawyeActivitys;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
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
import com.ucap.lawyers.bean.userLogin.userLawyer.LawyerMyCommonlyJournal;
import com.ucap.lawyers.view.AddFilesDialogView;
import com.ucap.lawyers.view.CommonlyDetailedDialog;
import com.ucap.lawyers.view.CommonlyFileDialog;
import com.ucap.lawyers.view.EditPhotoDialogView;
import com.ucap.lawyers.view.FilesLstView;
import com.ucap.lawyers.view.LoadingDialogView;
import com.ucap.lawyers.view.UploadLoginDialog;

import net.bither.util.NativeUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 案件详细页面
 */
public class LawyerCommonlyDetailedActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_user_back)
    ImageView ivBack;

    @Bind(R.id.btn_select_progress)
    ImageButton btnSelectProgress;
    @Bind(R.id.btn_add_files)
    Button btnAddFiles;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.lv_files)
    FilesLstView lvFiles;
    @Bind(R.id.ll_files)
    LinearLayout llFiles;
    @Bind(R.id.ll_commonly_head)
    LinearLayout llCommonlyHead;//头部案件描述
    @Bind(R.id.tv_commonly_detailed)
    TextView tvCommonlyDetailed;//案件信息
    @Bind(R.id.btn_submit)
    Button btnSubmit;//提交

    @Bind(R.id.et_progress)
    EditText etProgress;//编辑办案进度
    @Bind(R.id.et_description)
    EditText etDescription;//编辑办案日志

    private int etProgressWidth;
    private static final int CAMERA_WITH_DATA = 3023;//启动相机拍照code
    private static final int PHOTO_PICKED_WITH_DATA = 3021;//选择本地图片code
    private static final int CODE_RESULT_REQUEST = 3000;//启动系统剪切图片页面dode

    ArrayList<String> filesData;//卷宗信息
    private FileItemAdapter fileAdapter;
    public String orderid;
    public RequestQueue requestQueue;
    public ArrayList<LawyerMyCommonlyJournal.RowsBean> mCommonlyJournalData;
    public LoadingDialogView loadingDialogView;
    public String sid;
    public String firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//启动时不弹出输入框
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_commonly_detailed);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intiDate();
        intiListener();
        getDataForService();

    }

    /**
     * 初始化数据
     */
    private void intiDate() {
        files = new ArrayList<>();
        Intent intent = getIntent();
        orderid = intent.getStringExtra("orderid");
        sid = intent.getStringExtra("sid");
        firstname = intent.getStringExtra("firstname");
        loadingDialogView = new LoadingDialogView(this);
        Log.i("myCommonlyJournalData", "我的案件，案件id： " + orderid);
        requestQueue = Volley.newRequestQueue(this);
        ViewTreeObserver vto = etProgress.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                etProgressWidth = etProgress.getMeasuredWidth();
                return true;
            }
        });
        filesData = new ArrayList<>();
        fileAdapter = new FileItemAdapter(filesData);
        lvFiles.setAdapter(fileAdapter);
        lvFiles.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }

    /**
     * 初始化 事件
     */
    private void intiListener() {
        btnSelectProgress.setOnClickListener(this);
        btnAddFiles.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvCommonlyDetailed.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    /**
     * 获取服务器数据
     */
    public void getDataForService() {
        loadingDialogView.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, DataInterface.TEST_MY_COMMONLY_JOURNAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("myCommonlyJournalData", "办案日志： " + response);
                if (!response.isEmpty()) {
                    gsonData(response);
                } else {
                    loadingDialogView.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("myCommonlyJournalData", "办案日志： 数据访问错误！" + error.getMessage());
                Toast.makeText(getApplicationContext(), "数据获取失败,请检测网络链接！", Toast.LENGTH_SHORT).show();
                loadingDialogView.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("orderid", orderid);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 解析data
     *
     * @param response
     */
    private void gsonData(String response) {
        Gson gson = new Gson();
        LawyerMyCommonlyJournal lawyerMyCommonlyJournal = gson.fromJson(response, LawyerMyCommonlyJournal.class);
        if (lawyerMyCommonlyJournal != null) {
            List<LawyerMyCommonlyJournal.RowsBean> rows = lawyerMyCommonlyJournal.getRows();
            if (rows != null && rows.size() > 0) {
                mCommonlyJournalData = new ArrayList<>();
                mCommonlyJournalData.addAll(rows);
                addCommonlyHead(mCommonlyJournalData);

            }
        }
        loadingDialogView.dismiss();
    }

    /**
     * 添加案件日志记录
     */
    private void addCommonlyHead(ArrayList<LawyerMyCommonlyJournal.RowsBean> mData) {
        llCommonlyHead.removeAllViews();//先清空
        for (final LawyerMyCommonlyJournal.RowsBean data : mData) {
            View view = View.inflate(this, R.layout.item_commonly_head, null);
            final TextView tvCommonlyDate = (TextView) view.findViewById(R.id.tv_commonly_date);//记录时间
            final TextView tvCommonlyDetailed = (TextView) view.findViewById(R.id.tv_show_commonly_detailed);//查看详细
            TextView tvCommonlyFile = (TextView) view.findViewById(R.id.tv_commonly_file);//案件卷宗
            TextView tvCommonlyState = (TextView) view.findViewById(R.id.tv_commonly_state);//案件进度
            tvCommonlyState.setText(data.getArchchoose());
            if ("调查".equals(data.getArchchoose())) {
                tvCommonlyState.setTextColor(getResources().getColor(R.color.item1));
            } else if ("立案".equals(data.getArchchoose())) {
                tvCommonlyState.setTextColor(getResources().getColor(R.color.number_1));
            } else if ("待审".equals(data.getArchchoose())) {
                tvCommonlyState.setTextColor(getResources().getColor(R.color.item3));
            } else if ("待判".equals(data.getArchchoose())) {
                tvCommonlyState.setTextColor(getResources().getColor(R.color.item4));
            } else if ("办结".equals(data.getArchchoose())) {
                tvCommonlyState.setTextColor(getResources().getColor(R.color.item2));
            } else {
                tvCommonlyState.setTextColor(getResources().getColor(R.color.item1));
            }
            tvCommonlyDate.setText(data.getOperate_time());
            tvCommonlyDetailed.setText(data.getContent());
            if (data.getFiles() != null && data.getFiles().size() > 0) {
                tvCommonlyFile.setText(data.getFiles().get(0).getRamark());
                tvCommonlyFile.setEnabled(true);
                tvCommonlyFile.setOnClickListener(new View.OnClickListener() {//点击卷宗
                    @Override
                    public void onClick(View view) {
                        CommonlyFileDialog dialog = new CommonlyFileDialog(LawyerCommonlyDetailedActivity.this, data.getFiles());
                        dialog.show();
                    }
                });
            } else {
                tvCommonlyFile.setText("暂无卷宗");
                tvCommonlyFile.setTextColor(Color.GRAY);
                tvCommonlyFile.setEnabled(false);
            }

            tvCommonlyDetailed.setOnClickListener(new View.OnClickListener() {//点击办案日志字段""查看详细
                @Override
                public void onClick(View view) {
                    tvCommonlyDetailed.setSelected(!tvCommonlyDetailed.isSelected());
                    tvCommonlyDetailed.setSingleLine(!tvCommonlyDetailed.isSelected());


                }
            });
            llCommonlyHead.addView(view);
        }
    }


    @Override
    public void onClick(View view) {
        if (view == btnSelectProgress) {
            showProgressWindow();
        } else {
            if (view == btnAddFiles) {

                if (filesData.size() <= 9 && files.size() <= 9) {
                    EditPhotoDialogView.show(this, new EditPhotoDialogView.OnEditPhotoDialogListener() {
                        @Override
                        public void OnCamera() {//启动相机.6.0 以上判断权限,外部储存权限
                            if ((PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)
                            )) {
                                //判断外部存储权限
                                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    try {
                                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(getApplicationContext())));
                                        startActivityForResult(intent, CAMERA_WITH_DATA);
                                    } catch (ActivityNotFoundException e) {
                                        Toast.makeText(getApplicationContext(), "can not open Camera", Toast.LENGTH_LONG).show();
                                    }
                                } else {//提示用户开启外部存储权限
                                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                    ActivityCompat.requestPermissions(LawyerCommonlyDetailedActivity.this, perms, 0);
                                }
                            } else {
                                //没有相机权限，提示用户开户权限
                                String[] perms = {"android.permission.CAMERA"};
                                ActivityCompat.requestPermissions(LawyerCommonlyDetailedActivity.this, perms, 0);
                            }
                        }

                        @Override
                        public void OnImage() {//选择本地图片
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                        }
                    });
                } else {
                    Toast.makeText(this, "最多只能添加十张！", Toast.LENGTH_LONG).show();
                }
            } else if (view == btnCancel) {
                finish();
            } else if (view == tvCommonlyDetailed) {
                CommonlyDetailedDialog dialog = new CommonlyDetailedDialog(LawyerCommonlyDetailedActivity.this, sid);
                dialog.show();
            } else if (btnSubmit == view) {
                if (files.size() > 0) {//上传卷宗
                    final HashMap<String, String> saveContent = getSaveContent();
                    if (saveContent != null) {
                        UploadLoginDialog dialog = new UploadLoginDialog(LawyerCommonlyDetailedActivity.this, filesData, files, new UploadLoginDialog.OnUploadIsOKListener() {

                            @Override
                            public void onUploadOk(String filesId) {
                                saveContent.put("attats", filesId);//添加图片的id
                                save(saveContent);
                                Log.i("upload", "卷宗id： " + filesId);
                                Log.i("upload", "用户信息userInfo: " + firstname + " 名字--orderid " + orderid);
                            }
                        });
                        dialog.setCancelable(false);
                        dialog.show();
                        dialog.btnSubmitFile();
                    } else {
                        Toast.makeText(getApplicationContext(), "请将信息填写完整，再提交！", Toast.LENGTH_SHORT).show();
                    }
                } else {//没有卷宗
                    final HashMap<String, String> saveContent = getSaveContent();
                    if (saveContent != null) {
                        save(saveContent);
                    } else {
                        Toast.makeText(getApplicationContext(), "请将信息填写完整，再提交！", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

    }

    /**
     * 获取需要保存的数据
     *
     * @return
     */
    public HashMap<String, String> getSaveContent() {
        String descr = etDescription.getText().toString();//办案日志
        String pro = etProgress.getText().toString();//办案进度
        if (!descr.isEmpty() && !pro.isEmpty()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("description", descr);
            hashMap.put("archchoose", pro);
            hashMap.put("orderid", orderid);
            hashMap.put("username", firstname);
            return hashMap;
        }
        return null;
    }

    /**
     * 保存编辑信息
     */
    public void save(final HashMap<String, String> data) {
        loadingDialogView.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, DataInterface.TEST_MY_COMMONLY_SAVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("uploadOk", "保存成功：---" + response);
                Toast.makeText(getApplicationContext(), "提交成功!", Toast.LENGTH_SHORT).show();
                getDataForService();//重新获取头部显示数据（刷新数据）
                loadingDialogView.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "保存失败，检测网络链接再重试!", Toast.LENGTH_SHORT).show();
                Log.i("uploadOk", "保存失败：---" + error.getLocalizedMessage());
                loadingDialogView.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return data;
            }
        };
        requestQueue.add(request);
    }

    ArrayList<File> files;//文件
    public static String CAMERA_FILE_IMAGE_PATH = "sdcard/lawyersFile/";
    public static String NAME = System.currentTimeMillis() + ".jpg";//原图地址
    public static String CAMERA_FILE_IMAGE_NAME = CAMERA_FILE_IMAGE_PATH + NAME;

    /**
     * 指定拍取的照片目录
     *
     * @param context
     * @return
     */
    private File getTempFile(Context context) {
        NAME = System.currentTimeMillis() + ".jpg";
        final File path = new File(CAMERA_FILE_IMAGE_PATH);
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(CAMERA_FILE_IMAGE_NAME);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_WITH_DATA://拍照获取
                final Bitmap bitmap = BitmapFactory.decodeFile(CAMERA_FILE_IMAGE_NAME);
                final String imagePath = CAMERA_FILE_IMAGE_PATH + System.currentTimeMillis() + ".jpg";//压缩后的图片路径
                NativeUtil.compressBitmap(bitmap, imagePath);//将原图压缩、
                AddFilesDialogView.showDialog(LawyerCommonlyDetailedActivity.this, BitmapFactory.decodeFile(imagePath), null,new AddFilesDialogView.OnSaveListener() {
                    @Override
                    public void onSave(String name) {
                        String renameto = CAMERA_FILE_IMAGE_PATH + name + ".jpg";
                        File file = new File(imagePath);
                        boolean b = file.renameTo(new File(renameto));//重命名
                        file.delete();
                        Log.i("renameto", b + "");
                        files.add(0, new File(renameto));
                        filesData.add(0, name);
                        fileAdapter.notifyDataSetChanged();
                    }
                });
                break;
            case PHOTO_PICKED_WITH_DATA://从本地选取
                Log.i("photo", "从本地获取");
                if (resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    final String picturePath = cursor.getString(columnIndex);
                    final String image = CAMERA_FILE_IMAGE_PATH + System.currentTimeMillis() + ".jpg";//压缩后的图片路径
                    NativeUtil.compressBitmap(BitmapFactory.decodeFile(picturePath), image);//将原图压缩、
                    cursor.close();
                    AddFilesDialogView.showDialog(LawyerCommonlyDetailedActivity.this, BitmapFactory.decodeFile(image), null, new AddFilesDialogView.OnSaveListener() {
                        @Override
                        public void onSave(String name) {
                            String renameto = CAMERA_FILE_IMAGE_PATH + name + ".jpg";
                            File file = new File(image);
                            boolean b = file.renameTo(new File(renameto));//重命名
                            Log.i("renameto", b + "");
                            file.delete();
                            files.add(0, new File(renameto));
                            filesData.add(0, name);
                            fileAdapter.notifyDataSetChanged();
                        }
                    });
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 弹出案件进度选择窗
     */
    private void showProgressWindow() {
        View contentView = View.inflate(this, R.layout.item_popup_window_lawyer_commonly, null);
        ListView lv = (ListView) contentView.findViewById(R.id.lv_content);
        final ArrayList<String> date = new ArrayList<>();
        date.add("调查");
        date.add("立案");
        date.add("待审");
        date.add("待判");
        date.add("办结");
        lv.setAdapter(new PopupWindowListAdapter(date));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final PopupWindow pw = new PopupWindow(contentView, etProgressWidth, lp.height, true);
        pw.setBackgroundDrawable(new ColorDrawable());
        pw.showAsDropDown(etProgress);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etProgress.setText(date.get(position));
                switch (position) {
                    case 0:
                        etProgress.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.item1));
                        break;
                    case 1:
                        etProgress.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.number_1));
                        break;
                    case 2:
                        etProgress.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.item3));
                        break;
                    case 3:
                        etProgress.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.item4));
                        break;
                    case 4:
                        etProgress.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.item2));
                        break;
                }
                pw.dismiss();
            }
        });
    }


    class PopupWindowListAdapter extends BaseAdapter {
        ArrayList<String> data;

        PopupWindowListAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(LawyerCommonlyDetailedActivity.this, R.layout.item_popup_window_list_view, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_text);
            tv.setText(data.get(position));
            switch (position) {
                case 0:
                    tv.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.item1));
                    break;
                case 1:
                    tv.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.number_1));
                    break;
                case 2:
                    tv.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.item3));
                    break;
                case 3:
                    tv.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.item4));
                    break;
                case 4:
                    tv.setTextColor(LawyerCommonlyDetailedActivity.this.getResources().getColor(R.color.item2));
                    break;
            }
            return convertView;
        }
    }

    class FileItemAdapter extends BaseAdapter {
        ArrayList<String> filesData;
        private View filesItem;

        FileItemAdapter(ArrayList<String> filesDate) {
            this.filesData = filesDate;
        }

        @Override
        public int getCount() {
            return filesData.size();
        }

        @Override
        public Object getItem(int position) {
            return filesData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            filesItem = View.inflate(LawyerCommonlyDetailedActivity.this, R.layout.item_files, null);
            TextView tvName = (TextView) filesItem.findViewById(R.id.tv_name);
            TextView tvDelete = (TextView) filesItem.findViewById(R.id.tv_delete);
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filesData.remove(position);
                    fileAdapter.notifyDataSetChanged();
                    files.remove(position);//删除图片
                }
            });
            String item = getItem(position) + "";
            tvName.setText(item);
            return filesItem;
        }
    }

}