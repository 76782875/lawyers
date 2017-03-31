package com.ucap.lawyers.bean.homeFind;

import java.util.List;

/**
 * Created by wekingwang on 16/9/22.
 * 首页(律师查询,律师详细页面数据)
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
         * screenname : A20085108022134
         * groupname : 新都区司法局
         * firstname : 朱明弟
         * yearcheck : 合格
         * contactid : 15101201010564633
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=
         * id : 2104
         * jobtype : 专职律师
         * address : 成都市新都区兴乐北路188号6幢5楼1-6号
         * name : 四川诚伦律师事务所
         * ryzs : 无
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
