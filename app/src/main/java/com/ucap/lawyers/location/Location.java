package com.ucap.lawyers.location;

import android.content.Context;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

/**
 * Created by wekingwang on 16/9/20.
 */
public class Location {
    /**
     * 锦江区
     */
    public static final double[] JING_JIANG_QU = {30.67, 104.08};
    /**
     * 四川省 成都市 青羊区
     */
    public static final double[] QIN_YANG_QU = {30.68, 104.05};
    /**
     * 四川省 成都市 金牛区
     */
    public static final double[] JING_NIU_QU = {30.7, 104.05};
    /**
     * 四川省 成都市 武侯区
     */
    public static final double[] WU_HOU_QU = {30.65, 104.05};
    /**
     * 四川省 成都市 成华区
     */
    public static final double[] CHENG_HUA_QU = {30.67, 104.1};
    /**
     * 四川省 成都市 高新区 104.06728,30.565353
     */
    public static final double[] GAO_XIN_QU = {30.56, 104.06};
    /**
     * 四川省 成都市 龙泉驿
     */
    public static final double[] LONG_QUAN_YI_QU = {30.57, 104.27};
    /**
     * 四川省 成都市 青白江区
     */
    public static final double[] QING_BAI_JIANG_QU = {30.88, 104.23};
    /**
     * 四川省 成都市 新都区
     */
    public static final double[] XIN_DU_QU = {30.83, 104.15};
    /**
     * 四川省 成都市 温江
     */
    public static final double[] WNEG_JIANG_QU = {30.7, 103.83};
    /**
     * 四川省 成都市 金堂
     */
    public static final double[] JING_TANG_XIAN = {30.85, 104.43};
    /**
     * 四川省 成都市 双流
     */
    public static final double[] SHUANG_LIU_XIAN = {30.58, 103.92};
    /**
     * 四川省 成都市 郫县
     */
    public static final double[] PI_XIAN = {30.82, 103.88};
    /**
     * 四川省 成都市 大邑
     */
    public static final double[] DA_YI_XIAN = {30.58, 103.52};
    /**
     * 四川省 成都市 蒲江县
     */
    public static final double[] PU_JIANG_XIAN = {30.2, 103.5};
    /**
     * 四川省 成都市 新津
     */
    public static final double[] XIN_JIN_XIAN = {30.42, 103.82};
    /**
     * 四川省 成都市 都江堰
     */
    public static final double[] DU_JIANG_YAN_XIAN = {31.0, 103.62};
    /**
     * 四川省 成都市 彭州市
     */
    public static final double[] PENG_ZHOU_SHI = {30.98, 103.93};
    /**
     * 四川省 成都市 邛崃
     */
    public static final double[] QIONG_LAI_SHI = {30.42, 103.47};
    /**
     * 四川省 成都市 崇州
     */
    public static final double[] CHONG_ZHOU_SHI = {30.63, 103.67};
    /**
     * 四川省 成都市 简阳市
     */
    public static final double[] JIAN_YANG_SHI = {30.38, 104.53};
//    104.53	30.38
    /**
     * 四川省 成都市 天府新区
     * 104.07629,30.507677
     */
    public static final double[] TIAN_FU_XIN_QU = {30.50, 104.07};

    /**
     * 添加经度和纬度
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return
     */
    public static MarkerOptions getLocation(Context ctx, double latitude, double longitude) {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(latitude, longitude);
        markerOptions.position(latLng);
        markerOptions.draggable(true);
//        Bitmap iconLocation = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.icon_location);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iconLocation));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
        markerOptions.setFlat(true);
        return markerOptions;
    }


}
