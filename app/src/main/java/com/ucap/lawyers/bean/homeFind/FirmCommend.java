package com.ucap.lawyers.bean.homeFind;

import java.util.List;

/**
 * Created by wekingwang on 16/9/29.
 * 律所荣誉表彰
 */
public class FirmCommend {

    /**
     * id : 638
     * getdate : 2008-06-01
     * remark : 2008年，荣获ALB“中国律所二十强”
     * subject : 中国律所二十强
     * opername : 泰和泰律师事务所
     * disname : ALB
     */

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int id;
        private String getdate;
        private String remark;
        private String subject;
        private String opername;
        private String disname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGetdate() {
            return getdate;
        }

        public void setGetdate(String getdate) {
            this.getdate = getdate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getOpername() {
            return opername;
        }

        public void setOpername(String opername) {
            this.opername = opername;
        }

        public String getDisname() {
            return disname;
        }

        public void setDisname(String disname) {
            this.disname = disname;
        }
    }
}
