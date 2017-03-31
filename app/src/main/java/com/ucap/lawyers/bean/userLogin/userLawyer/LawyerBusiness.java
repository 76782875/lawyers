package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/15.
 * 我的业务
 */

public class LawyerBusiness {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * createtime : 2016-11-14 10:53:25
         * task : 律所在线业务-变更合伙人
         * displayname : 待律工处经办人员审批
         */

        private String createtime;
        private String task;
        private String displayname;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }
    }
}
