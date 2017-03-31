package com.ucap.lawyers.bean;

import java.util.List;

/**
 * Created by wekingwang on 16/8/22.\
 * 律师分类查询
 */
public class LawyerDate {
    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * gyzs : 无
         * sex : 男
         * screenname : 229769080225
         * groupname : 青羊区司法局
         * firstname : 王颖
         * yearcheck : 合格
         * contactid : 15101199510303220
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=
         * id : 2513
         * jobtype : 主任
         * address : 四川省成都都市提督街58号锦阳商厦26楼C座
         * name : 四川和睿律师事务所
         * ryzs : 12
         * fmzs : 无
         */

        private String gyzs;
        private String sex;
        private String screenname;
        private String groupname;
        private String firstname;
        private String yearcheck;
        private String contactid;
        private String photo;
        private String id;
        private String jobtype;
        private String address;
        private String name;
        private String ryzs;
        private String fmzs;

        public String getGyzs() {
            return gyzs;
        }

        public void setGyzs(String gyzs) {
            this.gyzs = gyzs;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getScreenname() {
            return screenname;
        }

        public void setScreenname(String screenname) {
            this.screenname = screenname;
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

        public String getYearcheck() {
            return yearcheck;
        }

        public void setYearcheck(String yearcheck) {
            this.yearcheck = yearcheck;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJobtype() {
            return jobtype;
        }

        public void setJobtype(String jobtype) {
            this.jobtype = jobtype;
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

        public String getRyzs() {
            return ryzs;
        }

        public void setRyzs(String ryzs) {
            this.ryzs = ryzs;
        }

        public String getFmzs() {
            return fmzs;
        }

        public void setFmzs(String fmzs) {
            this.fmzs = fmzs;
        }
    }
}
