package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/15.
 * 个人细信息编辑
 */

public class LawyerInfoEdit {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 7559
         * phone : 13908022067
         * firstname : 程守太
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101199910533886
         */

        private int id;
        private String phone;
        private String firstname;
        private String photo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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
