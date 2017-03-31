package com.ucap.lawyers.bean.userLogin.userOrdinary;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/24.
 */

public class UserProblemItem {

    private List<RowssBean> rowss;
    private List<RowsBean> rows;

    public List<RowssBean> getRowss() {
        return rowss;
    }

    public void setRowss(List<RowssBean> rowss) {
        this.rowss = rowss;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowssBean {
        /**
         * url : https://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!OrdinaryUsersMyQuestionsInfo.action?fifter=0
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class RowsBean {
        /**
         * createtime : 2016-11-25
         * id : 32908385589aaf8201589afebcf20001
         * title : 测试22
         * state : 1
         */

        private String createtime;
        private String id;
        private String title;
        private String state;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
