package com.ucap.lawyers.bean.ranking.answerClassify.ranking;

import java.util.List;

/**
 * Created by wekingwang on 2017/3/16.
 */

public class RankingFirmLawyerListBean {

    private List<RowBean> row;

    public List<RowBean> getRow() {
        return row;
    }

    public void setRow(List<RowBean> row) {
        this.row = row;
    }

    public static class RowBean {
        /**
         * userid : 2017
         * firstname : 胡江
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201410476819_20170316022332
         */

        private int userid;
        private String firstname;
        private String photo;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
