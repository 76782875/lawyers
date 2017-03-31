package com.ucap.lawyers.bean.ranking.answerClassify.ranking;

import java.util.List;

/**
 * Created by wekingwang on 2017/3/15.
 */

public class RankingLawyerAnswerDetailedBean {

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
         * content : 如果离婚协议中约定抚养费，或者离婚判决中对抚养费的标准进行了确认，你可以向对方主张支付抚养费。对方拒绝支付，如果是协议离婚，你可以到法院起诉要求对方支付抚养费，判决生效后由法院强制执行；如果已有判决，直接到法院申请强制执行。
         * creattime : 2017-02-15
         * firstname : 张灵姗
         * contactid : 15101201611911808
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201510555073_20170315053004
         */

        private String content;
        private String creattime;
        private String firstname;
        private String contactid;
        private String photo;

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
    }

    public static class RowBean {
        /**
         * publicConsultationcreatetime : 2017-02-13
         * publicConsultationcontent : 离婚后对方不给抚养费怎么办，可以打官司吗？
         * size : 7
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
