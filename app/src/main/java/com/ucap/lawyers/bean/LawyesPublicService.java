package com.ucap.lawyers.bean;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/10.
 * 律师公益服务
 */

public class LawyesPublicService {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 121
         * getdate : 2016-02-01
         * remark :     市政协黄主席联系光明乡石燕镇进行调研活动，送法下乡，法治活动。
         * subject : 参加公益或志愿法律服务
         * opername : 周义红
         */

        private int id;
        private String getdate;
        private String remark;
        private String subject;
        private String opername;

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
    }
}
