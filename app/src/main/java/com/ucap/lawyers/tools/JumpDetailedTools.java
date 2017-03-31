package com.ucap.lawyers.tools;

import android.content.Context;
import android.content.Intent;

import com.ucap.lawyers.activitys.publicActivity.FirmDetailedActivity;
import com.ucap.lawyers.activitys.publicActivity.PublicLawyerDetailedActivity;
import com.ucap.lawyers.bean.FirmDate;
import com.ucap.lawyers.bean.LawyerDate;
import com.ucap.lawyers.bean.LocationData;
import com.ucap.lawyers.bean.vagueFind.FirmFindData;
import com.ucap.lawyers.bean.vagueFind.LawyersFindData;

import java.util.ArrayList;

/**
 * Created by wekingwang on 2016/12/27.
 * 跳转到律师/律所详细页面
 */

public class JumpDetailedTools {
    //-------------------------------------------------律师（跳转）------------------------------------------------

    /**
     * 律师默认列表跳转到详细页面
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumLawyerDefaultListDetailed(Context ctx, LawyerDate.RowsBean rowsBean) {
        Intent intent = new Intent(ctx, PublicLawyerDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("contactid", rowsBean.getContactid());
        intent.putExtra("firstname", rowsBean.getFirstname());
        intent.putExtra("sex", rowsBean.getSex());
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("fmzs", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("photo", rowsBean.getPhoto());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("yearcheck", rowsBean.getYearcheck());
        intent.putExtra("screenname", rowsBean.getScreenname());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("jobtype", rowsBean.getJobtype());
        ctx.startActivity(intent);
    }

    /**
     * 律师分类查询列表（负面指数）
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpLawyerFmjlDetailed(Context ctx, LawyersFindData.FmjlrowsBean rowsBean) {
        Intent intent = new Intent(ctx, PublicLawyerDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("contactid", rowsBean.getContactid());
        intent.putExtra("firstname", rowsBean.getFirstname());
        intent.putExtra("sex", rowsBean.getSex());
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("fmzs", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("photo", rowsBean.getPhoto());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("yearcheck", rowsBean.getYearcheck());
        intent.putExtra("screenname", rowsBean.getScreenname());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("jobtype", rowsBean.getJobtype());
        ctx.startActivity(intent);

    }

    /**
     * 律师分类查询列表（公益服务）
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpLawyerGyfwDetailed(Context ctx, LawyersFindData.GyfwrowsBean rowsBean) {
        Intent intent = new Intent(ctx, PublicLawyerDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("contactid", rowsBean.getContactid());
        intent.putExtra("firstname", rowsBean.getFirstname());
        intent.putExtra("sex", rowsBean.getSex());
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("fmzs", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("photo", rowsBean.getPhoto());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("yearcheck", rowsBean.getYearcheck());
        intent.putExtra("screenname", rowsBean.getScreenname());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("jobtype", rowsBean.getJobtype());
        ctx.startActivity(intent);

    }

    /**
     * 律师分类查询列表（荣誉表彰）
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpLawyerGybzDetailed(Context ctx, LawyersFindData.RybzrowsBean rowsBean) {
        Intent intent = new Intent(ctx, PublicLawyerDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("contactid", rowsBean.getContactid());
        intent.putExtra("firstname", rowsBean.getFirstname());
        intent.putExtra("sex", rowsBean.getSex());
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("fmzs", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("photo", rowsBean.getPhoto());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("yearcheck", rowsBean.getYearcheck());
        intent.putExtra("screenname", rowsBean.getScreenname());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("jobtype", rowsBean.getJobtype());
        ctx.startActivity(intent);

    }

    /**
     * 律所详细页面，点击律师名单跳转到详细
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpFirmLawyerListDetailed(Context ctx, com.ucap.lawyers.bean.homeFind.LawyerDate.RowsBean rowsBean) {
        Intent intent = new Intent(ctx, PublicLawyerDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("contactid", rowsBean.getContactid());
        intent.putExtra("firstname", rowsBean.getFirstname());
        intent.putExtra("sex", rowsBean.getSex());
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("fmzs", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("photo", rowsBean.getPhoto());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("yearcheck", rowsBean.getYearcheck());
        intent.putExtra("screenname", rowsBean.getScreenname());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("jobtype", rowsBean.getJobtype());
        ctx.startActivity(intent);

    }

    //-------------------------------------------------律所（跳转）------------------------------------------------

    /**
     * 律所默认列表跳转到详细页面
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpFirmDefaultListDetailed(Context ctx, FirmDate.RowsBean rowsBean) {
        Intent intent = new Intent(ctx, FirmDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("description", rowsBean.getDescription());
        intent.putExtra("phone", rowsBean.getPhone());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("email", rowsBean.getEmail());
        intent.putExtra("weburl", rowsBean.getWeburl());
        intent.putExtra("fmjf", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("gfzs", rowsBean.getGfzs());
        intent.putExtra("groupnum", rowsBean.getGroupnum());
        intent.putExtra("typesettings", rowsBean.getTypesettings());
        intent.putExtra("yaerassess", rowsBean.getYearcheck());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("showname", rowsBean.getShowname());
        intent.putStringArrayListExtra("lawyerlist", (ArrayList<String>) rowsBean.getLawyerlist());
        intent.putStringArrayListExtra("hhrList", (ArrayList<String>) rowsBean.getHhrlist());
        ctx.startActivity(intent);

    }

    /**
     * 律所分类查询（负面指数）跳转到详细
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpFirmFmjlDetailed(Context ctx, FirmFindData.FmjlrowBean rowsBean) {
        Intent intent = new Intent(ctx, FirmDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("description", rowsBean.getDescription());
        intent.putExtra("phone", rowsBean.getPhone());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("email", rowsBean.getEmail());
        intent.putExtra("weburl", rowsBean.getWeburl());
        intent.putExtra("fmjf", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("gfzs", rowsBean.getGfzs());
        intent.putExtra("groupnum", rowsBean.getGroupnum());
        intent.putExtra("typesettings", rowsBean.getTypesettings());
        intent.putExtra("yaerassess", rowsBean.getYearcheck());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("showname", rowsBean.getShowname());
        intent.putStringArrayListExtra("lawyerlist", (ArrayList<String>) rowsBean.getLawyerlist());
        intent.putStringArrayListExtra("hhrList", (ArrayList<String>) rowsBean.getHhrlist());
        ctx.startActivity(intent);
    }

    /**
     * 律所分类查询（规范指数）跳转到详细
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpFirmGfzsDetailed(Context ctx, FirmFindData.GfglrowBean rowsBean) {
        Intent intent = new Intent(ctx, FirmDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("description", rowsBean.getDescription());
        intent.putExtra("phone", rowsBean.getPhone());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("email", rowsBean.getEmail());
        intent.putExtra("weburl", rowsBean.getWeburl());
        intent.putExtra("fmjf", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("gfzs", rowsBean.getGfzs());
        intent.putExtra("groupnum", rowsBean.getGroupnum());
        intent.putExtra("typesettings", rowsBean.getTypesettings());
        intent.putExtra("yaerassess", rowsBean.getYearcheck());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("showname", rowsBean.getShowname());
        intent.putStringArrayListExtra("lawyerlist", (ArrayList<String>) rowsBean.getLawyerlist());
        intent.putStringArrayListExtra("hhrList", (ArrayList<String>) rowsBean.getHhrlist());
        ctx.startActivity(intent);
    }

    /**
     * 律所分类查询（公益服务）跳转到详细
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpFirmGyfwDetailed(Context ctx, FirmFindData.GyfwrowBean rowsBean) {
        Intent intent = new Intent(ctx, FirmDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("description", rowsBean.getDescription());
        intent.putExtra("phone", rowsBean.getPhone());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("email", rowsBean.getEmail());
        intent.putExtra("weburl", rowsBean.getWeburl());
        intent.putExtra("fmjf", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("gfzs", rowsBean.getGfzs());
        intent.putExtra("groupnum", rowsBean.getGroupnum());
        intent.putExtra("typesettings", rowsBean.getTypesettings());
        intent.putExtra("yaerassess", rowsBean.getYearcheck());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("showname", rowsBean.getShowname());
        intent.putStringArrayListExtra("lawyerlist", (ArrayList<String>) rowsBean.getLawyerlist());
        intent.putStringArrayListExtra("hhrList", (ArrayList<String>) rowsBean.getHhrlist());
        ctx.startActivity(intent);
    }

    /**
     * 律所分类查询（荣誉表彰）跳转到详细
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpFirmRybzDetailed(Context ctx, FirmFindData.RybzrowBean rowsBean) {
        Intent intent = new Intent(ctx, FirmDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("description", rowsBean.getDescription());
        intent.putExtra("phone", rowsBean.getPhone());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("email", rowsBean.getEmail());
        intent.putExtra("weburl", rowsBean.getWeburl());
        intent.putExtra("fmjf", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("gfzs", rowsBean.getGfzs());
        intent.putExtra("groupnum", rowsBean.getGroupnum());
        intent.putExtra("typesettings", rowsBean.getTypesettings());
        intent.putExtra("yaerassess", rowsBean.getYearcheck());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("showname", rowsBean.getShowname());
        intent.putStringArrayListExtra("lawyerlist", (ArrayList<String>) rowsBean.getLawyerlist());
        intent.putStringArrayListExtra("hhrList", (ArrayList<String>) rowsBean.getHhrlist());
        ctx.startActivity(intent);
    }

    /**
     * 律所位置分布图选择律师跳转到详细
     *
     * @param ctx
     * @param rowsBean
     */
    public static void jumpFirmLocationDetailed(Context ctx, LocationData.RowsBean rowsBean) {
        Intent intent = new Intent(ctx, FirmDetailedActivity.class);
        intent.putExtra("id", rowsBean.getId() + "");
        intent.putExtra("name", rowsBean.getName());
        intent.putExtra("description", rowsBean.getDescription());
        intent.putExtra("phone", rowsBean.getPhone());
        intent.putExtra("address", rowsBean.getAddress());
        intent.putExtra("email", rowsBean.getEmail());
        intent.putExtra("weburl", rowsBean.getWeburl());
        intent.putExtra("fmjf", rowsBean.getFmzs());
        intent.putExtra("ryzs", rowsBean.getRyzs());
        intent.putExtra("gyzs", rowsBean.getGyzs());
        intent.putExtra("gfzs", rowsBean.getGfzs());
        intent.putExtra("groupnum", rowsBean.getGroupnum());
        intent.putExtra("typesettings", rowsBean.getTypesettings());
        intent.putExtra("yaerassess", rowsBean.getYearcheck());
        intent.putExtra("groupname", rowsBean.getGroupname());
        intent.putExtra("showname", rowsBean.getShowname());
        intent.putStringArrayListExtra("lawyerlist", (ArrayList<String>) rowsBean.getLawyerlist());
        intent.putStringArrayListExtra("hhrList", (ArrayList<String>) rowsBean.getHhrlist());
        ctx.startActivity(intent);
    }

}
