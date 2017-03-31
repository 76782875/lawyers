package com.ucap.lawyers.bean;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/7.
 * 版本更新
 */

public class VersionUpdate {


    /**
     * versionnumber : 1
     * versionurl : 无
     * versiondescription : 无
     * versioname : 无
     */

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int versionnumber;
        private String versionurl;
        private String versiondescription;
        private String versioname;

        public int getVersionnumber() {
            return versionnumber;
        }

        public void setVersionnumber(int versionnumber) {
            this.versionnumber = versionnumber;
        }

        public String getVersionurl() {
            return versionurl;
        }

        public void setVersionurl(String versionurl) {
            this.versionurl = versionurl;
        }

        public String getVersiondescription() {
            return versiondescription;
        }

        public void setVersiondescription(String versiondescription) {
            this.versiondescription = versiondescription;
        }

        public String getVersioname() {
            return versioname;
        }

        public void setVersioname(String versioname) {
            this.versioname = versioname;
        }
    }
}
