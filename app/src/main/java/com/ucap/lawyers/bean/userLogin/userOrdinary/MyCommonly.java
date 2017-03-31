package com.ucap.lawyers.bean.userLogin.userOrdinary;

import java.util.List;

/**
 * Created by wekingwang on 2016/12/21.
 * 我的案件
 */

public class MyCommonly {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 125
         * lawlist : [{"groupname":"泰和泰律师事务所","firstname":"程守太","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101199910533886","contactid":"15101199910533886"},{"groupname":"泰和泰律师事务所","firstname":"江敏","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101200011949899","contactid":"15101200011949899"}]
         * zbls : 程守太
         * casename : 1312
         * archchoose : 立案
         * orderid : c6f48aa8df9b4d54a16174297d638d10
         * order_no : 20161221-11:52:17-788-449
         */

        private int id;
        private String zbls;
        private String casename;
        private String archchoose;
        private String orderid;
        private String order_no;
        private List<LawlistBean> lawlist;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getZbls() {
            return zbls;
        }

        public void setZbls(String zbls) {
            this.zbls = zbls;
        }

        public String getCasename() {
            return casename;
        }

        public void setCasename(String casename) {
            this.casename = casename;
        }

        public String getArchchoose() {
            return archchoose;
        }

        public void setArchchoose(String archchoose) {
            this.archchoose = archchoose;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public List<LawlistBean> getLawlist() {
            return lawlist;
        }

        public void setLawlist(List<LawlistBean> lawlist) {
            this.lawlist = lawlist;
        }

        public static class LawlistBean {
            /**
             * groupname : 泰和泰律师事务所
             * firstname : 程守太
             * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101199910533886
             * contactid : 15101199910533886
             */

            private String groupname;
            private String firstname;
            private String photo;
            private String contactid;

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
    }
}
