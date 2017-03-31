package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/14.
 */

public class LawyerInfo {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 1179
         * PublicConsultationList : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!PublicConsultationList.action?fifter=0
         * InfoAnnouncement : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!InfoAnnouncement.action?fifter=0
         * MyAnswer : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!MyAnswer.action?fifter=0
         * MyBusiness : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!MyBusiness.action?fifter=0
         * lawyerinfo : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawyerInfo.action?fifter=0
         * userid : 877594
         * groupid : 339
         * firstName : 石磊
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201410877594
         * LawyerPhotoInfo : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawyerPhotoInfo.action?fifter=0
         */

        private int id;
        private String PublicConsultationList;
        private String InfoAnnouncement;
        private String MyAnswer;
        private String MyBusiness;
        private String lawyerinfo;
        private String userid;
        private String groupid;
        private String firstName;
        private String photo;
        private String LawyerPhotoInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPublicConsultationList() {
            return PublicConsultationList;
        }

        public void setPublicConsultationList(String PublicConsultationList) {
            this.PublicConsultationList = PublicConsultationList;
        }

        public String getInfoAnnouncement() {
            return InfoAnnouncement;
        }

        public void setInfoAnnouncement(String InfoAnnouncement) {
            this.InfoAnnouncement = InfoAnnouncement;
        }

        public String getMyAnswer() {
            return MyAnswer;
        }

        public void setMyAnswer(String MyAnswer) {
            this.MyAnswer = MyAnswer;
        }

        public String getMyBusiness() {
            return MyBusiness;
        }

        public void setMyBusiness(String MyBusiness) {
            this.MyBusiness = MyBusiness;
        }

        public String getLawyerinfo() {
            return lawyerinfo;
        }

        public void setLawyerinfo(String lawyerinfo) {
            this.lawyerinfo = lawyerinfo;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getLawyerPhotoInfo() {
            return LawyerPhotoInfo;
        }

        public void setLawyerPhotoInfo(String LawyerPhotoInfo) {
            this.LawyerPhotoInfo = LawyerPhotoInfo;
        }
    }
}
