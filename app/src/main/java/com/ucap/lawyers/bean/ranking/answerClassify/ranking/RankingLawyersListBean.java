package com.ucap.lawyers.bean.ranking.answerClassify.ranking;

import java.util.List;

/**
 * Created by wekingwang on 2017/3/15.
 * 律师列表解答排行榜
 */

public class RankingLawyersListBean {

    private List<RowBean> row;

    public List<RowBean> getRow() {
        return row;
    }

    public void setRow(List<RowBean> row) {
        this.row = row;
    }

    public static class RowBean {
        /**
         * dianzansize : 46
         * amanofpraise : 12627
         * groupname : 四川秉卓律师事务所
         * firstname : 王兴
         * jiedasize : 1
         */

        private String dianzansize;
        private String amanofpraise;
        private String groupname;
        private String firstname;
        private String jiedasize;

        public String getDianzansize() {
            return dianzansize;
        }

        public void setDianzansize(String dianzansize) {
            this.dianzansize = dianzansize;
        }

        public String getAmanofpraise() {
            return amanofpraise;
        }

        public void setAmanofpraise(String amanofpraise) {
            this.amanofpraise = amanofpraise;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getJiedasize() {
            return jiedasize;
        }

        public void setJiedasize(String jiedasize) {
            this.jiedasize = jiedasize;
        }
    }
}
