package com.ucap.lawyers.bean.ranking.answerClassify.ranking;

import java.util.List;

/**
 * Created by wekingwang on 2017/3/16.
 */

public class RankingFirmListBean {

    private List<RowBean> row;

    public List<RowBean> getRow() {
        return row;
    }

    public void setRow(List<RowBean> row) {
        this.row = row;
    }

    public static class RowBean {
        /**
         * jdzs : 10
         * groupname : 四川都江律师事务所
         * groupid : 460
         * dzzs : 3
         */

        private String jdzs;
        private String groupname;
        private String groupid;
        private String dzzs;

        public String getJdzs() {
            return jdzs;
        }

        public void setJdzs(String jdzs) {
            this.jdzs = jdzs;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getDzzs() {
            return dzzs;
        }

        public void setDzzs(String dzzs) {
            this.dzzs = dzzs;
        }
    }
}
