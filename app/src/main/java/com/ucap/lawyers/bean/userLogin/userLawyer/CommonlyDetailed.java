package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/12/22.
 */

public class CommonlyDetailed {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * lawlist : [{"lawname":"程守太","lawcontactid":"15101199910533886","lawphone":"13908022067"},{"lawname":"江敏","lawcontactid":"15101200011949899","lawphone":"13608006197"}]
         * casenumber : 21313
         * casecose : 0
         * casename : 1312
         * create_time : 2016-12-21 11:52:17
         * optdate : 2016-12-21 00:00:00.0
         * casetype : 非诉讼法律事务-知识产权
         * order_no : 20161221-11:52:17-788-449
         * casecode : 3123
         */

        private String casenumber;
        private String casecose;
        private String casename;
        private String create_time;
        private String optdate;
        private String casetype;
        private String order_no;
        private String casecode;
        private List<LawlistBean> lawlist;

        public String getCasenumber() {
            return casenumber;
        }

        public void setCasenumber(String casenumber) {
            this.casenumber = casenumber;
        }

        public String getCasecose() {
            return casecose;
        }

        public void setCasecose(String casecose) {
            this.casecose = casecose;
        }

        public String getCasename() {
            return casename;
        }

        public void setCasename(String casename) {
            this.casename = casename;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getOptdate() {
            return optdate;
        }

        public void setOptdate(String optdate) {
            this.optdate = optdate;
        }

        public String getCasetype() {
            return casetype;
        }

        public void setCasetype(String casetype) {
            this.casetype = casetype;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getCasecode() {
            return casecode;
        }

        public void setCasecode(String casecode) {
            this.casecode = casecode;
        }

        public List<LawlistBean> getLawlist() {
            return lawlist;
        }

        public void setLawlist(List<LawlistBean> lawlist) {
            this.lawlist = lawlist;
        }

        public static class LawlistBean {
            /**
             * lawname : 程守太
             * lawcontactid : 15101199910533886
             * lawphone : 13908022067
             */

            private String lawname;
            private String lawcontactid;
            private String lawphone;

            public String getLawname() {
                return lawname;
            }

            public void setLawname(String lawname) {
                this.lawname = lawname;
            }

            public String getLawcontactid() {
                return lawcontactid;
            }

            public void setLawcontactid(String lawcontactid) {
                this.lawcontactid = lawcontactid;
            }

            public String getLawphone() {
                return lawphone;
            }

            public void setLawphone(String lawphone) {
                this.lawphone = lawphone;
            }
        }
    }
}
