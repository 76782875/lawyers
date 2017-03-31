package com.ucap.lawyers.bean.ranking.answerClassify;

import java.util.List;

/**
 * Created by wekingwang on 2017/2/14.
 * 已答解问题，详细
 */

public class AnswerClassifyDetaildeBean {
    private List<UserrowBean> userrow;
    private List<RowBean> row;

    public List<UserrowBean> getUserrow() {
        return userrow;
    }

    public void setUserrow(List<UserrowBean> userrow) {
        this.userrow = userrow;
    }

    public List<RowBean> getRow() {
        return row;
    }

    public void setRow(List<RowBean> row) {
        this.row = row;
    }

    public static class UserrowBean {
        /**
         * content : 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
         * creattime : 2016-12-14
         * firstname : 周芳
         * photo : http://wx.qlogo.cn/mmopen/LzFHGtuSfYkSQafCuRIwOruWuEGrk8QZhTOFzXb0T9JjjqGBuLCdTghG3f0iapgkkib7jOibSxeh7lbDLElWHbRLA/0
         * contactid : ouAHSwuu58QtXwJAeBdItBIIDVCI
         */

        private String content;
        private String creattime;
        private String firstname;
        private String photo;
        private String contactid;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreattime() {
            return creattime;
        }

        public void setCreattime(String creattime) {
            this.creattime = creattime;
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

        public String getContactid() {
            return contactid;
        }

        public void setContactid(String contactid) {
            this.contactid = contactid;
        }
    }

    public static class RowBean {
        /**
         * publicConsultationcreatetime : 2016-11-25
         * publicConsultationcontent : 12312312
         * size : 12
         */

        private String publicConsultationcreatetime;
        private String publicConsultationcontent;
        private String size;

        public String getPublicConsultationcreatetime() {
            return publicConsultationcreatetime;
        }

        public void setPublicConsultationcreatetime(String publicConsultationcreatetime) {
            this.publicConsultationcreatetime = publicConsultationcreatetime;
        }

        public String getPublicConsultationcontent() {
            return publicConsultationcontent;
        }

        public void setPublicConsultationcontent(String publicConsultationcontent) {
            this.publicConsultationcontent = publicConsultationcontent;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
