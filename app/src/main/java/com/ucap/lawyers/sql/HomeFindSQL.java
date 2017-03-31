package com.ucap.lawyers.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wekingwang on 16/9/22.
 * 首页律师查询,已查询律师储存数据库
 */
public class HomeFindSQL extends SQLiteOpenHelper {
    public static String TABLE_LAWYER_NAME = "lawyerName";
    public static String TABLE_LAWYER_LICENSE = "lawyerLicense";

    public static String TABLE_FIRM_NAME = "firmName";
    public static String TABLE_FIRM_LICENSE = "firmLicense";

    public HomeFindSQL(Context context) {
        super(context, "HomeFind.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //------------------------------律所------------------------------------
        String sql2 = "create table " + TABLE_FIRM_NAME + "(name varchar(20) );";
        db.execSQL(sql2);
        String sql3 = "create table " + TABLE_FIRM_LICENSE + "(license varchar(20) );";
        db.execSQL(sql3);
        //------------------------------律师------------------------------------
        String sql0 = "create table " + TABLE_LAWYER_NAME + "(name varchar(20) );";
        db.execSQL(sql0);
        String sql1 = "create table " + TABLE_LAWYER_LICENSE + "(license varchar(20) );";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
