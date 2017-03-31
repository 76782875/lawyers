package com.ucap.lawyers.bean.userLogin.userOrdinary;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/23.
 * 个人中心
 */

public class UserInfo {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 15362
         * sex : 男
         * phone : 无
         * emailaddress : 2121212
         * address : 金牛区
         * userid : 0k4k84
         * idcard : 9999999999999999999
         * openid : 9755EC937A8730E106CF070B83030C49
         * firstname : weking
         * photo : http://q.qlogo.cn/qqapp/1105754205/9755EC937A8730E106CF070B83030C49/100
         */

        private int id;
        private String sex;
        private String phone;
        private String emailaddress;
        private String address;
        private String userid;
        private String idcard;
        private String openid;
        private String firstname;
        private String photo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmailaddress() {
            return emailaddress;
        }

        public void setEmailaddress(String emailaddress) {
            this.emailaddress = emailaddress;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
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
