package com.ucap.lawyers.activitys;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.ucap.lawyers.R;
import com.ucap.lawyers.location.Location;
import com.ucap.lawyers.view.LocationListDialogVIew;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * AMapV2地图中介绍如何显示一个基本地图
 */
public class LocationActivity extends Activity {

    @Bind(R.id.iv_user_back)
    ImageView ivBack;
    private MapView mapView;
    private AMap aMap;
    String locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
        //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        //  MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            LatLng start = new LatLng(30.67, 104.08);//设置初始显示位置
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 10));
            //添加显示坐标
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.JING_JIANG_QU[0], Location.JING_JIANG_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.QIN_YANG_QU[0], Location.QIN_YANG_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.JING_NIU_QU[0], Location.JING_NIU_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.WU_HOU_QU[0], Location.WU_HOU_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.CHENG_HUA_QU[0], Location.CHENG_HUA_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.LONG_QUAN_YI_QU[0], Location.LONG_QUAN_YI_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.QING_BAI_JIANG_QU[0], Location.QING_BAI_JIANG_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.XIN_DU_QU[0], Location.XIN_DU_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.WNEG_JIANG_QU[0], Location.WNEG_JIANG_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.JING_TANG_XIAN[0], Location.JING_TANG_XIAN[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.SHUANG_LIU_XIAN[0], Location.SHUANG_LIU_XIAN[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.PI_XIAN[0], Location.PI_XIAN[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.DA_YI_XIAN[0], Location.DA_YI_XIAN[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.PU_JIANG_XIAN[0], Location.PU_JIANG_XIAN[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.XIN_JIN_XIAN[0], Location.XIN_JIN_XIAN[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.DU_JIANG_YAN_XIAN[0], Location.DU_JIANG_YAN_XIAN[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.PENG_ZHOU_SHI[0], Location.PENG_ZHOU_SHI[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.QIONG_LAI_SHI[0], Location.QIONG_LAI_SHI[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.CHONG_ZHOU_SHI[0], Location.CHONG_ZHOU_SHI[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.GAO_XIN_QU[0], Location.GAO_XIN_QU[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.JIAN_YANG_SHI[0], Location.JIAN_YANG_SHI[1]));
            aMap.addMarker(Location.getLocation(getApplicationContext(), Location.TIAN_FU_XIN_QU[0], Location.TIAN_FU_XIN_QU[1]));
            aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    LatLng position = marker.getPosition();//根据用户点击地图大区坐标点查询大区律所
                    int location = isLocation(position);
                    LocationListDialogVIew.showDialog(LocationActivity.this, location, locationName);
                    return true;
                }
            });
        }
    }

    /**
     * 判断用户点击相应坐标，返回相应地区
     * <p>
     * 212	简阳市
     * 141	天府新区
     * 140	高新区
     * 139	新津县
     * 138	浦江县
     * 137	大邑县
     * 136	郫县
     * 135	双流县
     * 134	金堂县
     * 133	崇州市
     * 132	邛崃市
     * 131	彭州市
     * 130	都江堰市
     * 129	温江区
     * 128	新都区
     * 127	青白江区
     * 126	龙泉驿区
     * 125	武侯区
     * 124	金牛区
     * 123	青羊区
     * 122	锦江区
     * 121	成华区
     *
     * @param latLng
     * @return
     */
    public int isLocation(LatLng latLng) {
        double latitude = latLng.latitude;//纬度
        double longitude = latLng.longitude;//经度
        if (Location.JING_JIANG_QU[0] == latitude && Location.JING_JIANG_QU[1] == longitude) {//锦江区
            locationName = "锦江区";
            return 122;
        } else if (Location.QIN_YANG_QU[0] == latitude && Location.QIN_YANG_QU[1] == longitude) {//青羊区
            locationName = "青羊区";
            return 123;
        } else if (Location.JING_NIU_QU[0] == latitude && Location.JING_NIU_QU[1] == longitude) {//金牛区
            locationName = "金牛区";
            return 124;
        } else if (Location.WU_HOU_QU[0] == latitude && Location.WU_HOU_QU[1] == longitude) {//武侯区
            locationName = "武侯区";
            return 125;
        } else if (Location.CHENG_HUA_QU[0] == latitude && Location.CHENG_HUA_QU[1] == longitude) {//成华区
            locationName = "成华区";
            return 121;
        } else if (Location.GAO_XIN_QU[0] == latitude && Location.GAO_XIN_QU[1] == longitude) {//高新区
            locationName = "高新区";
            return 140;
        } else if (Location.LONG_QUAN_YI_QU[0] == latitude && Location.LONG_QUAN_YI_QU[1] == longitude) {//龙泉驿区
            locationName = "龙泉驿区";
            return 126;
        } else if (Location.QING_BAI_JIANG_QU[0] == latitude && Location.QING_BAI_JIANG_QU[1] == longitude) {//青白江区
            locationName = "青白江区";
            return 127;
        } else if (Location.XIN_DU_QU[0] == latitude && Location.XIN_DU_QU[1] == longitude) {//新都区
            locationName = "新都区";
            return 128;
        } else if (Location.WNEG_JIANG_QU[0] == latitude && Location.WNEG_JIANG_QU[1] == longitude) {//温江区
            locationName = "温江区";
            return 129;
        } else if (Location.JING_TANG_XIAN[0] == latitude && Location.JING_TANG_XIAN[1] == longitude) {//金堂县
            locationName = "金堂县";
            return 134;
        } else if (Location.SHUANG_LIU_XIAN[0] == latitude && Location.SHUANG_LIU_XIAN[1] == longitude) {//双流县
            locationName = "双流县";
            return 135;
        } else if (Location.PI_XIAN[0] == latitude && Location.PI_XIAN[1] == longitude) {//郫县
            locationName = "郫县";
            return 136;
        } else if (Location.DA_YI_XIAN[0] == latitude && Location.DA_YI_XIAN[1] == longitude) {//大邑县
            locationName = "大邑县";
            return 137;
        } else if (Location.PU_JIANG_XIAN[0] == latitude && Location.PU_JIANG_XIAN[1] == longitude) {//蒲江
            locationName = "蒲江";
            return 138;
        } else if (Location.XIN_JIN_XIAN[0] == latitude && Location.XIN_JIN_XIAN[1] == longitude) {//新津
            locationName = "新津";
            return 139;
        } else if (Location.DU_JIANG_YAN_XIAN[0] == latitude && Location.DU_JIANG_YAN_XIAN[1] == longitude) {//都江堰
            locationName = "都江堰";
            return 130;
        } else if (Location.PENG_ZHOU_SHI[0] == latitude && Location.PENG_ZHOU_SHI[1] == longitude) {//彭州
            locationName = "彭州";
            return 131;
        } else if (Location.QIONG_LAI_SHI[0] == latitude && Location.QIONG_LAI_SHI[1] == longitude) {//邛崃
            locationName = "邛崃";
            return 132;
        } else if (Location.CHONG_ZHOU_SHI[0] == latitude && Location.CHONG_ZHOU_SHI[1] == longitude) {//崇州
            locationName = "崇州";
            return 133;
        } else if (Location.JIAN_YANG_SHI[0] == latitude && Location.JIAN_YANG_SHI[1] == longitude) {//简阳
            locationName = "简阳";
            return 212;
        } else if (Location.TIAN_FU_XIN_QU[0] == latitude && Location.TIAN_FU_XIN_QU[1] == longitude) {//天府新区
            locationName = "天府新区";
            return 141;
        }
        return 0;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //        友盟统计
        MobclickAgent.onResume(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        //        友盟统计
        MobclickAgent.onPause(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
