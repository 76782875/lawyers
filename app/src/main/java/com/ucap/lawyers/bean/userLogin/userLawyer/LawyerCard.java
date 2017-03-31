package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/15.
 * 电子名片
 */

public class LawyerCard {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * firstname : 程守太
         * code :
         * contactid : 15101199910533886
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101199910533886
         * id : 7559
         * jobtype : 主任
         * emailaddress : shoutai.cheng@tahota.com
         * weixin : 无
         * address : 成都高新区天府大道中段199号棕榈泉国际中心16楼、17楼
         * name : 泰和泰律师事务所
         * openid : 370102196702183737
         * qq : 无
         * conphone : 13908022067
         */

        private String firstname;
        private String code;
        private String contactid;
        private String photo;
        private int id;
        private String jobtype;
        private String emailaddress;
        private String weixin;
        private String address;
        private String name;
        private String openid;
        private String qq;
        private String conphone;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContactid() {
            return contactid;
        }

        public void setContactid(String contactid) {
            this.contactid = contactid;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJobtype() {
            return jobtype;
        }

        public void setJobtype(String jobtype) {
            this.jobtype = jobtype;
        }

        public String getEmailaddress() {
            return emailaddress;
        }

        public void setEmailaddress(String emailaddress) {
            this.emailaddress = emailaddress;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getConphone() {
            return conphone;
        }

        public void setConphone(String conphone) {
            this.conphone = conphone;
        }
    }
}
