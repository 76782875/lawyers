package com.ucap.lawyers.sql.sqlUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ucap.lawyers.sql.HomeFindSQL;

import java.util.ArrayList;

/**
 * Created by wekingwang on 16/9/22.
 * 首页查询,已经查询存储
 */
public class HomeFindSQLUtils {
    private final SQLiteDatabase db;

    public HomeFindSQLUtils(Context ctx) {
        HomeFindSQL find = new HomeFindSQL(ctx);
        db = find.getReadableDatabase();
    }
//----------------------------------------------------"律师"已查询信息保存-------------------------------------------------

    /**
     * 保存 律师姓名
     *
     * @param name
     */
    public void saveLawyerName(String name) {
        if (!queryLawyerNameIsExist(name)) {
            ContentValues values = new ContentValues();
            values.put("name", name);
            db.insert(HomeFindSQL.TABLE_LAWYER_NAME, null, values);
        }
    }

    /**
     * 保存 律师执业证号
     *
     * @param license
     */
    public void saveLawyerLicense(String license) {
        if (!queryLawyerLicenseIsExist(license)) {
            ContentValues values = new ContentValues();
            values.put("license", license);
            db.insert(HomeFindSQL.TABLE_LAWYER_LICENSE, null, values);
        }
    }


    /**
     * 查询律师姓名
     */
    public boolean queryLawyerNameIsExist(String name) {
        Cursor cursor = db.rawQuery("select name from " + HomeFindSQL.TABLE_LAWYER_NAME + " where " +
                " name='" + name + "'", null);
        return cursor.moveToFirst();
    }

    /**
     * 查询执业证号
     */
    public boolean queryLawyerLicenseIsExist(String license) {
        Cursor cursor = db.rawQuery("select license from " + HomeFindSQL.TABLE_LAWYER_LICENSE + " where " +
                " license='" + license + "'", null);
        return cursor.moveToFirst();
    }


    /**
     * 根据律师名称模糊查询
     *
     * @param lawyerName
     * @return
     */
    public ArrayList<String> queryLawyerName(String lawyerName) {                                                            //李
        Cursor cursor = db.rawQuery("select * from " + HomeFindSQL.TABLE_LAWYER_NAME + " where name like '%" + lawyerName + "%'", null);
        ArrayList<String> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        return data;
    }

    /**
     * 根据律师执业证号模糊查询
     *
     * @param lawyerLicense
     * @return
     */
    public ArrayList<String> queryLawyerLicense(String lawyerLicense) {                                                            //李
        Cursor cursor = db.rawQuery("select * from " + HomeFindSQL.TABLE_LAWYER_LICENSE + " where license like '%" + lawyerLicense + "%'", null);
        ArrayList<String> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(cursor.getString(cursor.getColumnIndex("license")));
        }
        return data;
    }


//----------------------------------------------------"律所"已查询信息保存-------------------------------------------------

    /**
     * 保存 律所姓名
     *
     * @param name
     */
    public void saveFirmName(String name) {
        if (!queryNameFirmIsExist(name)) {
            ContentValues values = new ContentValues();
            values.put("name", name);
            db.insert(HomeFindSQL.TABLE_FIRM_NAME, null, values);
        }
    }

    /**
     * 保存 律所许可证号
     *
     * @param license
     */
    public void saveFirmLicense(String license) {
        if (!queryLicenseFirmIsExist(license)) {
            ContentValues values = new ContentValues();
            values.put("license", license);
            db.insert(HomeFindSQL.TABLE_FIRM_LICENSE, null, values);
        }
    }

    /**
     * 查询这姓名是否已经保存
     *
     * @param name
     */
    public boolean queryNameFirmIsExist(String name) {
        Cursor cursor = db.rawQuery("select name from " + HomeFindSQL.TABLE_FIRM_NAME + " where " +
                " name='" + name + "'", null);
        return cursor.moveToFirst();
    }

    /**
     * 查询执业证号是否已经保存
     *
     * @param license
     */
    public boolean queryLicenseFirmIsExist(String license) {
        Cursor cursor = db.rawQuery("select license from " + HomeFindSQL.TABLE_FIRM_LICENSE + " where " +
                "license='" + license + "'", null);
        return cursor.moveToFirst();
    }

    /**
     * 根据律所名称模糊查询
     *
     * @param firmName
     * @return
     */
    public ArrayList<String> queryFirmName(String firmName) {                                                            //李
        Cursor cursor = db.rawQuery("select * from " + HomeFindSQL.TABLE_FIRM_NAME + " where name like '%" + firmName + "%'", null);
        ArrayList<String> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        return data;
    }

    /**
     * 根据律所许可证号模糊查询
     *
     * @param lawyerLicense
     * @return
     */
    public ArrayList<String> queryFirmLicense(String lawyerLicense) {                                                            //李
        Cursor cursor = db.rawQuery("select * from " + HomeFindSQL.TABLE_FIRM_LICENSE + " where license like '%" + lawyerLicense + "%'", null);
        ArrayList<String> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(cursor.getString(cursor.getColumnIndex("license")));
        }
        return data;
    }
}
