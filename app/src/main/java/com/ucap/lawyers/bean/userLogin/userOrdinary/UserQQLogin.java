package com.ucap.lawyers.bean.userLogin.userOrdinary;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/22.
 * qq登陆
 */

public class UserQQLogin {
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
         * OrdinaryUsersInfo : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!OrdinaryUsersInfo.action?fifter=0
         * userid : 0k4k84
         * state : true
         * openid : 9755EC937A8730E106CF070B83030C49
         * firstname : weking
         * OrdinaryUsersMyQuestions : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!OrdinaryUsersMyQuestions.action?fifter=0
         * MessageAlert : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!MessageAlert.action?fifter=0
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=9755EC937A8730E106CF070B83030C49_20161127012244
         * password : 0k4k84
         * size : 2
         */

        private int id;
        private String OrdinaryUsersInfo;
        private String userid;
        private boolean state;
        private String openid;
        private String firstname;
        private String OrdinaryUsersMyQuestions;
        private String MessageAlert;
        private String photo;
        private String password;
        private int size;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrdinaryUsersInfo() {
            return OrdinaryUsersInfo;
        }

        public void setOrdinaryUsersInfo(String OrdinaryUsersInfo) {
            this.OrdinaryUsersInfo = OrdinaryUsersInfo;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
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

        public String getOrdinaryUsersMyQuestions() {
            return OrdinaryUsersMyQuestions;
        }

        public void setOrdinaryUsersMyQuestions(String OrdinaryUsersMyQuestions) {
            this.OrdinaryUsersMyQuestions = OrdinaryUsersMyQuestions;
        }

        public String getMessageAlert() {
            return MessageAlert;
        }

        public void setMessageAlert(String MessageAlert) {
            this.MessageAlert = MessageAlert;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
